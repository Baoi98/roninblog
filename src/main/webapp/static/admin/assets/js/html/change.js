/**
 * 初始化前端模板
 */
$(document).ready(function() {
    app.formWizard();
});

/**
 * 校验管理员Email和Name
 */
function checkEmail() {
    var userEmail = $("#userEmail").val();
    var userName = $("#userName").val();
    if(userEmail != '' && userName != ''){
        $.ajax({
            type:"post",
            url:"/admin/check_email_name",
            data:{"userEmail":userEmail,"userName":userName},
            success:function (data) {
                if(data.message == "校验通过"){
                    $("#message").attr("class","alert alert-success alert-dismissable");
                    $("#meg").html(data.message);
                    $("#message").fadeIn();
                    $(".actions").css("display","block");
                }
                else {
                    $("#message").attr("class","alert alert-danger alert-dismissable");
                    $("#meg").html(data.message);
                    $("#message").fadeIn("slow");
                }
                setTimeout($("#message").fadeOut(),1500);
            },
            dataTpye:"json"
        });
    }
}

/**
 * 校验密码
 */
function checkPass() {
    var userEmail = $("#userEmail").val();
    var oldPassword = $("#oldPassword").val();
    var newPassword = $("#newPassword").val();
    var retryPassword = $("#retryPassword").val();
    if(oldPassword != '' && newPassword != '' && retryPassword != ''){
        $.ajax({
           type:"post",
           url:"/admin/check_pass",
           data:{"userEmail":userEmail,"oldPassword":oldPassword,"newPassword":newPassword,"retryPassword":retryPassword},
           success:function (data) {
               if(data.message == "校验通过"){
                   $("#message").attr("class","alert alert-success alert-dismissable");
                   $("#meg").html(data.message);
                   $("#message").fadeIn("slow");
                   $(".actions").css("display","block");
               }
               else {
                   $("#message").attr("class","alert alert-danger alert-dismissable");
                   $("#meg").html(data.message);
                   $("#message").fadeIn("slow");
               }
               setTimeout($("#message").fadeOut("slow"),1500);
           },
           dataType:"json"
        });
    }
}

/**
 * 修改密码完成
 */
function finish() {
    var finish = $(".btn-next").html();
    if(finish == "下一步"){
        $(".btn-next").html("确定");
    }
    if(finish == "确定"){
        var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
        var index = layer.load(1, {
            shade: [0.5,'#fff']
        });
        var userEmail = $("#userEmail").val();
        var newPassword = $("#newPassword").val();
        var userName = $("#userName").val();
        $.ajax({
            type:"post",
            url:"/admin/change_pass",
            data:{"userEmail":userEmail,"userName":userName,"newPassword":newPassword},
            success:function (data) {
                window.location = "/admin/login";
            },
            dataType:"json"
        });
    }
}