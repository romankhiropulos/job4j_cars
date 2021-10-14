$(document).ready(function () {
    loadFieldsData();
});

// $('form#adFormData').submit(function (e) {
$(document).on('submit', '#adFormData', function (event) {
    event.stopPropagation();
    event.preventDefault();
    let formData = new FormData();
    let filePhoto = $('#adFilePhotoName')[0].files[0];

    let valid = false;
    let adsFields = getAdsInputData();
    valid = validateAdInput(adsFields);
    if (valid) {
        let ad = prepareNewAd(adsFields);

        formData.append('adPhoto', filePhoto);
        let strAd = JSON.stringify(ad);
        formData.append("adFields", strAd);

        for (var [key, value] of formData.entries()) {
            console.log(key, value);
        }
    }

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "ad.do");
    xhr.send(formData);

    // $.ajax({
    //     url: 'http://localhost:8080/job4j_cars/ad.do',
    //     type: 'POST',
    //     contentType: false,
    //     data: ({
    //         // description: description,
    //         advertisement: formData,
    //     }),
    //     cache: false,
    //     processData: false,
    //     success: function (data) {
    //         alert(data)
    //     },
    // });
});

function createAd() {
    let valid = false;
    let adsFields = getAdsInputData();
    valid = validateAdInput(adsFields);
    if (valid) {
        let ad = prepareNewAd(adsFields);
        let strAd = JSON.stringify(ad);
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/job4j_cars/ad.do',
            data: ({
                // description: description,
                advertisement: strAd,
            }),
            success: function () {
                alert("Объявление опубликовано");
                uploadPhoto();
                window.location.replace("index.html");
            },
            error: function (err) {
                errorHandler(
                    err, "Только авторизованные пользователи могут опубликовать объявление!"
                );
                valid = false;
            }
        })
    }

    return valid;
}

function uploadPhoto() {
    // $(function() {
    $('#adFilePhotoName').ajaxForm({
        success: function (msg) {
            alert("File has been uploaded successfully");
        },
        error: function (msg) {
            $("#upload-error").text("Couldn't upload file");
        }
    });
    // });
}

function getAdsInputData() {
    let brand = $("#selectBrand").val();
    let model = $("#selectModel").val();
    let bodyType = $("#selectBodyType").val();
    let engine = $("#selectEngine").val();
    let transmission = $("#selectTransmission").val();
    let city = $("#selectCity").val();
    let price = $("#adPrice").val();
    let year = $("#adYear").val();
    let mileage = $("#adMileage").val();
    let power = $("#adPower").val();
    let size = $("#adSize").val();
    let description = $("#adDescription").val();
    let photo = $("#adFilePhotoName").val();
    return new Map([["model", model], ["bodyType", bodyType], ["brand", brand],
        ["engine", engine], ["transmission", transmission], ["city", city],
        ["price", price], ["year", year], ["mileage", mileage],
        ["power", power], ["size", size], ["description", description],
        ["photo", photo]]);
}

function validateAdInput(fields) {
    let result = true;
    fields.forEach(function (value, key, map) {
        if (value === "") {
            alert("Необходимо заполнить все поля объявления!");
            result = false;
        }
    });
    return result;
}

function prepareNewAd(adsFields) {
    let curLogin = sessionStorage.getItem('curUser');
    return {
        "owner": {"login": curLogin},
        "car": {
            "year": adsFields.get("year"),
            "mileage": adsFields.get("mileage"),
            "power": adsFields.get("power"),
            "size": adsFields.get("size"),
            "brand": {"id": adsFields.get("brand")},
            "model": {"id": adsFields.get("model")},
            "engine": {"id": adsFields.get("engine")},
            "bodyType": {"id": adsFields.get("bodyType")},
            "transmission": {"id": adsFields.get("transmission")},
        },
        "photo": {"name": adsFields.get("photo")},
        "city": {"id": adsFields.get("city")},
        "price": adsFields.get("price"),
        "description": adsFields.get("description"),
        "sold": false,
    };
}

function loadFieldsData() {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8080/job4j_cars/formselect',
        dataType: "json",
    }).done(function (respData) {
        loadSelectionList(respData.brands, '#selectBrand');
        loadSelectionList(respData.transmissions, '#selectTransmission');
        loadSelectionList(respData.bodyTypes, '#selectBodyType');
        loadSelectionList(respData.models, '#selectModel');
        loadSelectionList(respData.engines, '#selectEngine');
        loadSelectionList(respData.cities, '#selectCity');

        // $('.js-ads-type').val(data.fields.adsType.id);
        // $('.js-user').val(data.user.id);
        // $('select').formSelect();
        // $('.js-add-next').prop("disabled", false);
    }).fail(function (err) {
        errorHandler(err);
    });
}

function loadSelectionList(options, selectId) {
    if (options !== null) {
        options.forEach(function (option) {
            let str = '<option value="' + option.id + '">' + option.name + '</option>';
            $(selectId).append(str);
        });
    }
}