var doLogin = function () {

    $("#login-btn").text('登陆中...');

    $.post("/user/login",$("#login-form").serialize(),function (data) {
        // layer.alert(data.msg)
        if(data.code == 200) {
            parent.location.href ='index.html';
        }else {
            $("#login-btn").text('登陆');
            layer.alert(data.msg);
        }
    })
}