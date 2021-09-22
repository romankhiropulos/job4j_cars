function User(login, password) {
    this.login = login;
    this.password = password;
}

function createUser() {
    let isValid = validateUserData();
    if (isValid) {
        let strUser = JSON.stringify(getInputUser());
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/job4j_cars/reg',
            data: {user: strUser},
            dataType: "json",
            success: function (response) {
                let login = response.login;
                if (login !== undefined) {
                    sessionStorage.setItem('curUser', login);
                    window.location.replace("index.html");
                } else {
                    alert(response);
                }
            },
            error: function (err) {
                errorHandler(err);
                isValid = false;
            }
        })
    }

    return isValid;
}

function authUser() {
    let isValid = validateUserData();
    if (isValid) {
        let user = getInputUser();
        let strUser = JSON.stringify(user);
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/job4j_cars/auth',
            data: {user: strUser},
            dataType: "json",
            success: function (response) {
                let login = response.login;
                if (login !== undefined) {
                    sessionStorage.setItem('curUser', login);
                    window.location.href = "index.html";
                } else {
                    alert(response);
                }
            },
            error: function (err) {
                errorHandler(err, "Wrong login or password!");
                isValid = false;
            }
        })
    }
    return isValid;
}

function getInputUser() {
    let login = document.getElementById('login').value;
    let password = document.getElementById('password').value;
    return new User(login, password);
}

function validateUserData() {
    let valid = true;
    let user = getInputUser();
    if (user.login === '') {
        valid = false;
        alert("Нужно заполнить поле \"Логин\"");
    } else if (user.password === '') {
        valid = false;
        alert("Нужно заполнить поле \"Пароль\"");
    }
    return valid;
}