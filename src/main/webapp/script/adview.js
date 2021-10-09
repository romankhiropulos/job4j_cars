$(document).ready(function showAd() {
        let curAdId = sessionStorage.getItem('curAdId');
        let ads = JSON.parse(sessionStorage.getItem('ads'));
        let ad = ads.find(ad => ad.id === Number(curAdId));
        let adData = '';
        let figcaptionText = ad.car.brand.name;
        // let pText = ad.brand.name + " " + ad.model.name;
        adData = `<figure className="sign">
                   <p>
        <!--           <img src="images/helen.jpg" width="150" height="212" alt="Скульптура">-->
                        <img src="http://localhost:8080/job4j_cars/carphoto?namekey=${ad.id}"
                        width="250px" height="200px" alt="АААААААААААаааавтомобиль!"/>
                   </p>
                   <figcaption>${figcaptionText}</figcaption>
                   </figure>
                   <p>${figcaptionText}</p>`;

        $('#adViewId').html(adData);
    }
)