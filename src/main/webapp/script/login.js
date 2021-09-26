function User(login, password, name) {
    this.login = login;
    this.password = password;
    this.name = name;
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
    let nameObj = document.getElementById('name');
    if (nameObj !== null) {
        name = nameObj.value;
    }
    return new User(login, password, name);
}

function validateUserData() {
    let valid = true;
    let user = getInputUser();
    if (user.login === '') {
        valid = false;
        alert("Нужно заполнить обязательное поле \"Логин\"");
    } else if (user.password === '') {
        valid = false;
        alert("Нужно заполнить обязательное поле \"Пароль\"");
    }
    return valid;
}