function errorHandler(err, authMsg) {
    switch (err.status) {
        case 401:
            alert(authMsg);
            window.location.replace("auth.html");
            break;
        case 400:
            alert("Bad request!");
            break;
        case 500:
            alert("Internal server error!");
            break;
        default:
            alert(err.status + " " + err.responseText);
            console.log(err.status + " " + err.responseText);
    }
}