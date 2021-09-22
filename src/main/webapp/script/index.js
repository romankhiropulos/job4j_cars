function Advertisement(id, description, created, done, user) {
    this.id = id;
    this.created = created;
    this.user = user;
    // this.car = car;
    // this.city = city;
    // this.price = price;
    this.description = description;
    this.sold = sold;
}

function User(login, password) {
    this.login = login;
    this.password = password;
}

$(document).ready(function () {
    showAds("all");
});

function showAds(mode) {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8080/job4j_cars/ads',
        dataType: "json",
        contentType: "text/json;charset=utf-8",
        data: ({
            mode: mode
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
                sold = curAd.sold ? `<input type="checkbox" value=${curAd.id} id="changeDoneItem"
                                           disabled checked>`
                    : `<input type="checkbox" value=${curAd.id} id="changeDoneItem">`;

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
                                  <td>${sold}</td>
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

// $(document).ready(function () {
//     $.ajax({
//         type: "GET",
//         url: 'http://localhost:8080/job4j_todo/categories',
//         dataType: "json",
//         success: function (respData) {
//             let categories = "";
//             for (let i = 0; i < respData.length; i++) {
//                 categories += "<option value=" + respData[i]['id'] + ">" + respData[i]['name'] + "</option>";
//             }
//             $('#categorySelect').html(categories);
//         },
//         error: function (err) {
//             alert(err);
//         }
//     })
// });

// $(document).ready(function () {
//     $('#showNotDone').click(function () {
//
//         const income = 300;
//         switch(income){
//             case 100 :
//                 console.log("Доход равен 100");
//                 break;
//             case 200 :
//                 console.log("Доход равен 200");
//                 break;
//             case 300 :
//                 console.log("Доход равен 300");
//                 break;
//             default:
//                 console.log("Доход неизвестной величины");
//                 break;
//         }
//
//         if ($(this).is(':checked')) {
//             showItems(true);
//         } else {
//             showItems(false);
//         }
//     });
// });

$(document).ready(function () {
    let curLogin = sessionStorage.getItem('curUser');
    if (curLogin !== null) {
        $('#curLogin').after(`<p><a href="auth.html">${curLogin}</a></p>`);
    } else {
        $('#curLogin').after(`<a href="auth.html">Авторизация</a> | <a href="reg.html">Регистрация</a>`);
    }
})

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
    // for (let i = 0; i < items.length; i++) {
    //     if (items[i]['id'] == curId) {
    //         curItem = items[i];
    //         curItem.done = hasDone;
    //         break;
    //     }
    // }
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

