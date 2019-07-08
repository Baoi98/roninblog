function changePassword(){

    var userName = $("#InputName").val();
    var userEmail = $("#InputEmail").val();
    var userPass = $("#InputPassword").val();
    var userPassRetype = $("#InputPassword1").val();

    $.ajax({
        "type":"POST",
        "url":"/admin/forget",
        "data":{"userName":userName,"userEmail":userEmail,
            "userPass":userPass,"userPassRetype":userPassRetype},
        "success":function (data) {
            var message = data.message;
            if(message == "修改密码成功") {
                $("#message").attr("class","alert alert-success alert-dismissable");
                $("#meg").html(message+"，请等待页面跳转...");
                $("#message").fadeIn("slow");
                //延迟跳转
                setTimeout('login()', 2000);
            }
            else {
                $("#message").attr("class","alert alert-danger alert-dismissable");
                $("#meg").html(message);
                $("#message").fadeIn("slow");
            }
        },
        "dataType":"json"
    });

}
function login() {
    window.location = "/admin/login";
}