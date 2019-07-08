/**
 * treeTable初始化
 */
$(function () {
    $("#treeTable").treeTable({
        column:0,
        expandLevel:1
    });
});

/**
 * 编辑分类
 * @param cid
 */
function detail(cid) {
    $("#categoryPid").empty();
    $.ajax({
        "type" : "post",
        "url" : "/admin/cate_detail",
        "data" : {"categoryId" : cid},
        "success" : function (data) {
            $("#myModalLabel").html(data.message);
            $("#categoryId").val(data.categoryDetail.categoryId);
            $("#categoryName").val(data.categoryDetail.categoryName);
            $("#categoryDescription").val(data.categoryDetail.categoryDescription);
            // 防止多个/根分类出现
            var i = 0;
            //遍历集合
            $.each(data.categoryList,function (id,category) {
                var _option = "";
                var pid = category.categoryPid;
                if(pid == 0 && i == 0){
                    _option += "<option value='0' th:selected = '"+data.categoryDetail.categoryPid+" == 0'>/</option>";
                    i++;
                }
                //判断遍历的分类ID是否和选中的分类的PID相同
                if(data.categoryDetail.categoryPid == category.categoryId){
                    _option += "<option value='"+category.categoryId+"' selected='selected'>"+category.categoryName+"</option>";
                }else {
                    _option += "<option value='" + category.categoryId + "'>" + category.categoryName + "</option>";
                }
                $("#categoryPid").append(_option);
            });
            $("#myModal").modal('show');
        },
        "dataType" : "json"
    });
}

/**
 * 新增按钮
 */
function add() {
    $("#categoryPid").empty();
    $.ajax({
        "type" : "post",
        "url" : "/admin/cate_detail",
        "success" : function (data) {
            var _option = "<option value='0' selected='selected'>/</option>";
            $("#myModalLabel").html(data.message);
            $.each(data.categoryList,function (id,category) {
                _option += "<option value='"+category.categoryId+"'>"+category.categoryName+"</option>";
            });
            $("#categoryPid").append(_option);
            $("#myModal").modal('show');
        },
        "dataType" : "json"
    });
}

/**
 * 给删除模态框的确定按钮添加事件
 * @param Id
 */
function getId(Id){
    $("#cateDelete").attr("onclick","deleteCate("+Id+")");
}

/**
 * Ajax删除分类
 * @param Id
 */
function deleteCate(Id) {
    $.ajax({
        "type" : "post",
        "url" : "/admin/cate_delete",
        "data" : {"categoryId" : Id},
        "success" : function (data) {
            var message = data.message;
            if(message == "删除分类成功") {
                $("#deMessage").attr("class","alert alert-success alert-dismissable");
                $("#meg").html(message+"，请等待页面刷新...");
                $("#deMessage").fadeIn("slow");
                //延迟跳转
                setTimeout('restart()', 1500);
            }
            else {
                $("#deMessage").attr("class","alert alert-danger alert-dismissable");
                $("#meg").html(message);
                $("#deMessage").fadeIn("slow");
            }
        },
        "dataType" : "json"
    });
}

/**
 * 页面刷新
 */
function restart() {
    window.location="/admin/category";
}