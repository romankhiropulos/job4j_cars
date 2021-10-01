$(document).ready(function () {
    loadFieldsData();
});



function loadFieldsData() {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8080/job4j_cars/formselect',
        dataType: "json",
    }).done(function(respData) {
        loadSelectionList(respData.cities, '#selectBrand');
        loadSelectionList(respData.models, '#selectTransmission');
        loadSelectionList(respData.carBodyTypes, '#selectBodyType');
        loadSelectionList(respData.carEngineTypes, '#selectModel');
        loadSelectionList(respData.carTransmissionBoxTypes, '#selectEngine');
        loadSelectionList(respData.carTransmissionBoxTypes, '#selectEngine');

        // $('.js-ads-type').val(data.fields.adsType.id);
        // $('.js-user').val(data.user.id);
        // $('select').formSelect();
        // $('.js-add-next').prop("disabled", false);
    }).fail(function(err) {
        errorHandler(err);
    });
}

function loadSelectionList(options, selectId) {
    options.forEach(function (option) {
        let str = '<option value="' + option.id + '">' + option.name + '</option>';
        $(selectId).append(str);
    });
}