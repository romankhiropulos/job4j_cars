$(document).ready(function showAd() {
        let curAdId = sessionStorage.getItem('curAdId');
        let ads = JSON.parse(sessionStorage.getItem('ads'));
        let ad = ads.find(ad => ad.id === Number(curAdId));
        let adData = '';
        let figcaptionText = ad.car.brand.name + " " + ad.car.model.name + ", " + ad.car.year;

        let pText = "<big>" + ad.price + " р." + "</big>" + "<br/>" +
            "<span class=\"colortext\">" + "Марка: " + "</span>" + ad.car.brand.name + "<br/>" +
            "<span class=\"colortext\">" + "Модель: " + "</span>" + ad.car.model.name + "<br/>" +
            "<span class=\"colortext\">" + "Год выпуска: " + "</span>" +  ad.car.year + "<br/>" +
            "<span class=\"colortext\">" + "Пробег: " + "</span>" + ad.car.mileage +  " км"  + "<br/>" +
            "<span class=\"colortext\">" + "Тип кузова: " + "</span>" + ad.car.bodyType.name + "<br/>" +
            "<span class=\"colortext\">" + "Тип двигателя: " + "</span>" + ad.car.engine.name + "<br/>" +
            "<span class=\"colortext\">" + "Мощность: " + "</span>" + ad.car.power + " л.с." + "<br/>" +
            "<span class=\"colortext\">" + "Объем: " + "</span>" + ad.car.size + " л" + "<br/>" +
            "<span class=\"colortext\">" + "Коробка передач: " + "</span>" + ad.car.transmission.name + "<br/>" +
            "<span class=\"colortext\">" + "Описание: " + "</span>" + ad.description;

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