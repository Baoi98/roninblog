/**
 * 初始化wysihtml5
 */
$(document).ready(function() {
    $('.textarea').wysihtml5();
});

/**
 * 用户评论
 */
$("#cSubmit").click(function () {
    var commentText = $("#commentText").val();
    var commentEmail = $("#commentEmail").val();
    $.ajax({
        "type" : "post",
        "url" : "/comment_submit",
        "data" : {"commentText":commentText,"commentEmail":commentEmail},
        "success" : function (data) {
            if(data.message == "留言成功") {
                //延迟跳转
                setTimeout(window.location = "/comment", 1000);
            }
            else{
                $("#cMessage").html(data.message);
                $("#myModal").modal();
            }
        },
        "dataType" : "json"
    });
});

/**
 * 查看留言详情
 * @param cId
 */
function showDetail(cId) {
    $.ajax({
        "type" : "post",
        "url" : "/comment_detail",
        "data" : {"commentId":cId},
        "success" : function (data) {
            $("#cEmail").html(data.commentEmail);
            $("#cDeatil").html(data.commentText);
            $("#detailModal").modal();
        },
        "dataType" : "json"
    });
}