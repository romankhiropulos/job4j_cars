$(document).ready(function showAd() {
        let curAdId = sessionStorage.getItem('curAdId');
        let ads = JSON.parse(sessionStorage.getItem('ads')); // change to GET request
        let ad = ads.find(ad => ad.id === Number(curAdId));
        let adData = '';
        let figcaptionText = ad.car.brand.name + " " + ad.car.model.name + ", " + ad.car.year;

        let pText = "<big>" + ad.price + " р." + "</big>" + "<br/>" +
            "<span class=\"colortext\">" + "Марка: " + "</span>" + ad.car.brand.name + "<br/>" +
            "<span class=\"colortext\">" + "Модель: " + "</span>" + ad.car.model.name + "<br/>" +
            "<span class=\"colortext\">" + "Год выпуска: " + "</span>" + ad.car.year + "<br/>" +
            "<span class=\"colortext\">" + "Пробег: " + "</span>" + ad.car.mileage + " км" + "<br/>" +
            "<span class=\"colortext\">" + "Тип кузова: " + "</span>" + ad.car.bodyType.name + "<br/>" +
            "<span class=\"colortext\">" + "Тип двигателя: " + "</span>" + ad.car.engine.name + "<br/>" +
            "<span class=\"colortext\">" + "Мощность: " + "</span>" + ad.car.power + " л.с." + "<br/>" +
            "<span class=\"colortext\">" + "Объем: " + "</span>" + ad.car.size + " л" + "<br/>" +
            "<span class=\"colortext\">" + "Коробка передач: " + "</span>" + ad.car.transmission.name + "<br/>" +
            "<span class=\"colortext\">" + "Описание: " + "</span>" + ad.description + "<br/>";

        if (ad.owner.login === sessionStorage.getItem('curUser')) {
            pText = ad.sold ? pText + `<input type="radio" name = "r1" id="isSold" value="${ad.id}" checked> Продано `
                : pText + `<input type="radio" name = "r1" id="isSold" value="${ad.id}"> Продано `;
            pText = ad.sold ? pText + `<input type="radio" name = "r1" id="isNotSold" value="${ad.id}"> В наличии`
                : pText + `<input type="radio" name = "r1" id="isNotSold" value="${ad.id}" checked> В наличии`;
        }

        adData = `<figure class="sign">
                   <p>
                        <img src="http://localhost:8080/job4j_cars/carphotoload?namekey=${ad.id}"
                        width="850px" height="500px" alt="АААААААААААаааавтомобиль!"/>
                   </p>
                   <figcaption>${figcaptionText}</figcaption>
                  </figure>
                  <p>${pText}</p>`;

        $('#adViewId').html(adData);
    }
)

$(document).on('click', '#isSold', function () {
    let curAdId = $(this).val();
    updateAdStatus(true, curAdId);
});

$(document).on('click', '#isNotSold', function () {
    let curAdId = $(this).val();
    updateAdStatus(false, curAdId);
});

function updateAdStatus(isSold, curAdId) {
    let ads = JSON.parse(sessionStorage.getItem('ads'));
    let curAd = ads.find(ad => ad.id === Number(curAdId));
    curAd.sold = isSold;
    sessionStorage.setItem('ads', JSON.stringify(ads));
    let strAd = JSON.stringify(curAd);
    $.ajax({
        type: "POST",
        url: 'adupdate.do',
        data: {advertisement : strAd},
        success: function () {
            location.reload();
        },
        error: function (err) {
            errorHandler(err, "Only owner of ad can update ads status!");
        }
    })
}