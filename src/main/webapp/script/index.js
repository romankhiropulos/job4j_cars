function User(login, password) {
    this.login = login;
    this.password = password;
}

$(document).ready(function () {
    let curLogin = sessionStorage.getItem('curUser');
    if (curLogin !== null) {
        $('#curLogin').after(`<p><a href="auth.html">${curLogin}</a></p>`);
    } else {
        $('#curLogin').after(`<a href="auth.html">Авторизация</a> | <a href="reg.html">Регистрация</a>`);
    }
})

$(document).ready(function () {
    showAds();
});

$(document).ready(function () {
        $.ajax({
            type: "GET",
            url: 'http://localhost:8080/job4j_cars/brands',
            dataType: "json",
            success: function (respData) {
                let brands = "";
                brands += `<option value="allBrands">Все бренды</option>`;
                for (let i = 0; i < respData.length; i++) {
                    brands += "<option value=" + respData[i]['id'] + ">" + respData[i]['name'] + "</option>";
                }
                $('#filterBrandSelect').html(brands);
            },
            error: function (err) {
                alert(err);
            }
        })
});

function showAds(filter, brandId) {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8080/job4j_cars/ads',
        dataType: "json",
        contentType: "text/json;charset=utf-8",
        data: ({
            filter: filter,
            brandId: brandId
        }),
        success: function (respData) {

            let ads = "";
            let adsArr = [];
            let sold = "";
            let description = "";
            let photo = "";

            for (let i = 0; i < respData.length; i++) {

                let curAd = respData[i];
                adsArr.push(curAd);

                sold = curAd.sold ? "<td bgcolor = #f08080 align='center'>" + "Продано" + "</td>"
                    : "<td bgcolor = #7fffd4 align='center'>" + "Актуально" + "</td>";

                photo = `<img src="http://localhost:8080/job4j_cars/carphoto?namekey=${curAd.id}" width="150px"
                     height="100px"/>`;

                description = curAd.car.brand.name + " " + curAd.car.model.name + ", " + curAd.car.year
                    + "<br/>"
                    + curAd.price + " р."
                    + "<br/>"
                    + curAd.car.mileage + " км" + ", " + curAd.car.power + " л.с.";

                ads += `<tr>
                                  <td>${photo}</td>
                                  <td>${description}</td>
                                  <td>${curAd.created}</td>
                                  <td>${curAd.owner.login}</td>
                                  ${sold}              
                                </tr>`;
            }

            localStorage.setItem('items', JSON.stringify(adsArr));
            $('#tbodyId').html(ads);
        },
        error: function (err) {
            errorHandler(err);
        }
    })
}

function showFilteredAds() {
    let filterSelect = $("#filterSelect").val();
    let filterBrandSelect = $("#filterBrandSelect").val();
    if (filterSelect !== 'defaultFilter' || filterBrandSelect !== 'allBrands') {
        filterSelect = filterSelect === 'defaultFilter' ? null : filterSelect;
        filterBrandSelect = filterBrandSelect === 'allBrands' ? null : filterBrandSelect;
        showAds(filterSelect, filterBrandSelect);
    } else {
        showAds();
    }
}

function createItem() {
    let valid = true;
    let description = document.getElementById('description').value;
    let cIds = $("#categorySelect").val();
    if (description === '' || cIds.length === 0) {
        valid = false;
        alert("Пожалуйста, заполните все поля.");
    } else {
        let strCIds = JSON.stringify(cIds);
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/job4j_todo/item.do',
            data: ({
                description: description,
                cIds: strCIds
            }),
            success: function () {
                alert("New task created!");
                location.reload();
            },
            error: function (err) {
                errorHandler(err, "Only authorized users can create tasks!");
                valid = false;
            }
        })
    }
    return valid;
}

$(document).on('click', '#changeDoneItem', function () {
    let curId = $(this).val();
    if ($(this).is(':checked')) {
        updateItem(true, curId);
    } else {
        updateItem(false, curId);
    }
});

function updateItem(hasDone, curId) {
    let items = JSON.parse(localStorage.getItem('items'));
    let curItem = null;

    curItem = items.find(id === curId);

    let strItem = JSON.stringify(curItem);
    $.ajax({
        type: "POST",
        url: 'http://localhost:8080/job4j_cars/itemupdate.do',
        data: {item: strItem},
        success: function () {
            location.reload();
        },
        error: function (err) {
            errorHandler(err, "Only authorized users can update task status!");
        }
    })
}

