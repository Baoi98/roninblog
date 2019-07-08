/**
 * 全选和全不选
 */
$(function () {
    /**
     * 初始化多选框
     */
    app.customCheckbox();
    $('.tooltips').tooltip()
    $('.textarea').wysihtml5();

    _checkParent = $("#checkParent");
    _checkbox = $("tbody").find("[type='checkbox']").not("[disabled]");
    _checkParent.on("ifClicked", function (e) {
        // 当前状态已选中，点击后应取消选择
        if (e.target.checked) {
            _checkbox.iCheck("uncheck");
        }
        // 当前状态未选中，点击后应全选
        else {
            _checkbox.iCheck("check");
        }
    });
});

/**
 * 留言详情
 * @param cId
 */
function showDetail(cId) {
    $.ajax({
        "type" : "post",
        "url" : "/comment_detail",
        "data" : {"commentId":cId},
        "success" : function (data) {
            var num = Math.ceil(Math.random()*9);
            $("#emailHead").html("<img src='/static/admin/assets/img/avatar"+num+".png' class='img-circle' width='50' height='50'><strong><span>"+data.commentEmail+"</span></strong> to Me")
            $("#emailBody").html(data.commentText);
            $("#hidId").val(cId);
            $("#hidEmail").val(data.commentEmail);
        },
        "dataType" : "json"
    });
}

/**
 * 加载特效
 */
function load() {
    var html = $(".textarea").html();
    if(html != null && html != ''){
        $("#loading").css("display","block");
    }
}

/**
 * 删除单一留言
 */
function deleteOne() {
    var id = $("#hidId").val();
    if(id != null && id != ''){
        $.ajax({
           "type":"post",
           "url":"/admin/mail_delete_one",
           "data":{"commentId":id},
           "success":function (data) {
                var message = data.message;
                if(message == "删除留言成功") {
                    $("#message").attr("class","alert alert-success alert-dismissable");
                    $("#meg").html(message+"，请等待页面刷新...");
                    $("#message").fadeIn("slow");
                    //延迟跳转
                    setTimeout('retry()', 1500);
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
    else{
        $("#modal_body").html("您还没有选中留言呢...没办法删除哦")
        $("#myModal").modal();
    }
}

/**
 * 批量删除按钮
 * @private
 */
function _checkBox() {
    var _array = new Array();
    _checkbox.each(function () {
        var _value = $(this).attr("value");
        if(_value != null && _value != "undefine" && $(this).is(":checked")){
            _array.push(_value);
        }
    });
    if(_array.length > 0){
        $.ajax({
            type : "post",
            url : "/admin/mail_delete_array",
            data : {commentIdArray : _array},
            traditional:true,
            success : function (data) {
                var message = data.message;
                if(message == "批量删除留言成功") {
                    $("#meg").html(message+"，请等待页面刷新...");
                    $("#message").fadeIn("slow");
                    //延迟跳转
                    setTimeout('retry()', 1500);
                }
                else {
                    $("#message").attr("class","alert alert-danger alert-dismissable");
                    $("#meg").html(message);
                    $("#message").fadeIn("slow");
                }
            },
            dataType : "json"
        });
    }
    else{
        $("#modal_body").html("您还没有选中留言呢...没办法删除哦");
        $("#myModal").modal();
    }

}

/**
 * 刷新页面
 */
function retry() {
    window.location="/admin/mail";
}

