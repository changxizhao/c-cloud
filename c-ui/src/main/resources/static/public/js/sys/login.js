var doLogin = function () {
    $.post("/user/login",$("#login-form").serialize(),function (data) {
        // layer.alert(data.msg)
        if(data.code == 200) {
            parent.location.href ='index.html';
        }else {
            layer.alert(data.msg);
        }
    })
}