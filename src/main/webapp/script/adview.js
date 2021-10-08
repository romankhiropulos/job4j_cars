$(document).ready(function showAd() {
        let curAdId = sessionStorage.getItem('curAdId');
        let ad = JSON.parse(sessionStorage.getItem('ads')).find(id === curAdId);
        let adData = '';

        adData = `<figure className="sign">
                   <p>
        <!--           <img src="images/helen.jpg" width="150" height="212" alt="Скульптура">-->
                        <img src="http://localhost:8080/job4j_cars/carphoto?namekey=${ad.id}"
                        width="250px" height="200px" alt="АААААААААААаааавтомобиль!"/>
                   </p>
                   <figcaption>${ad.brand.name}, ${ad.model.name}</figcaption>
                   </figure>
                   <p>${ad.year}, ${ad.price},  </p>`;

        $('#adViewId').html(adData);
    }
)