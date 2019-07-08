$("#dataTable").DataTable({
    // 是否开启本地分页
    "paging": true,
    // 是否开启本地排序
    "ordering": false,
    // 是否显示左下角信息
    "info": true,
    //每页初始显示5条记录
    'iDisplayLength': 5,
    // 是否允许用户改变表格每页显示的记录数
    "lengthChange": false,
    // 是否显示处理状态(排序的时候，数据很多耗费时间长的话，也会显示这个)
    //"processing": true,
    // 是否允许 DataTables 开启本地搜索
    "searching": false,
    // 是否开启服务器模式
    "serverSide": true,
    // 控制 DataTables 的延迟渲染，可以提高初始化的速度
    "deferRender": true,
    // 增加或修改通过 Ajax 提交到服务端的请求数据
    "ajax": {
        "url": "/admin/tag_list",
        "type":"POST"
    },
    // 分页按钮显示选项
    "pagingType": "full_numbers",
    // 设置列的数据源
    "columns": [
        {"data": "tagId"},
        {"data": "tagName"},
        {"data": "tagDescription"},
        {
            "data": function (row, type, val, meta) {
                return '<button onclick="getTag('+row.tagId+')" data-toggle="modal" data-target="#tagModal" type="button" class="btn btn-sm btn-primary"><i class="fa fa-edit"></i> 编辑</button>&nbsp;&nbsp;&nbsp;' +
                    '<button onclick="getId('+row.tagId+')" data-toggle="modal" data-target="#myModal" type="button" class="btn btn-sm btn-danger"><i class="fa fa-trash-o"></i> 删除</button>'
            }
        }
    ],
    // 国际化
    "language": {
        "sProcessing": "处理中...",
        "sLengthMenu": "显示 _MENU_ 项结果",
        "sZeroRecords": "没有匹配结果",
        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix": "",
        "sSearch": "搜索:",
        "sUrl": "",
        "sEmptyTable": "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands": ",",
        "oPaginate": {
            "sFirst": "首页",
            "sPrevious": "上页",
            "sNext": "下页",
            "sLast": "末页"
        },
        "oAria": {
            "sSortAscending": ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    }
});

/**
 * 设置删除模态框的ID传值
 * @param Id
 */
function getId(Id){
    $("#sInput").attr("onclick","deleteTag("+Id+")");
}

/**
 * 获取标签内容
 * @param Id
 */
function getTag(Id) {
    $.ajax({
        "url":"/admin/tag_detail",
        "type":"get",
        "data":{"tagId":Id},
        "success":function (data) {
            $("#modal_tagId").val(data.tId);
            $("#modal_tagName").val(data.tName);
            $("#modal_tagDescription").val(data.tDescription);
        },
        "dataType":"json"
    });
}

/**
 * 删除标签
 * @param Id
 */
function deleteTag(Id) {
    $.ajax({
        "url":"/admin/tag_delete",
        "type":"post",
        "data":{"tagId":Id},
        "success":function (data) {
            var message = data.message;
            if(message == "删除成功") {
                $("#message").attr("class","alert alert-success alert-dismissable");
                $("#meg").html(message+"，请等待页面刷新...");
                $("#message").fadeIn("slow");
                //延迟跳转
                setTimeout('restart()', 1500);
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

/**
 * 新增或修改标签
 */
function tagDetail() {
    //取属性
    var tagId = $("#modal_tagId").val();
    var tagName = $("#modal_tagName").val();
    var tagDescription = $("#modal_tagDescription").val();
    //Ajax
    $.ajax({
        "url":"/admin/tag_change",
        "type":"post",
        "data":{"tagId":tagId,"tagName":tagName,"tagDescription":tagDescription},
        "success":function (data) {
            var message = data.message;
            if(message == "更新标签成功") {
                $("#message").attr("class","alert alert-success alert-dismissable");
                $("#meg").html(message+"，请等待页面刷新...");
                $("#message").fadeIn("slow");
                //延迟跳转
                setTimeout('restart()', 1500);
            }
            else {
                $("#message").attr("class","alert alert-danger alert-dismissable");
                $("#meg").html(message);
                $("#message").fadeIn("slow");
            }
        },
        "dataType":"json"
    });

    clean();
}

/**
 * 清除模态框中输入框的值
 */
function clean(){
    $("#modal_tagId").val("");
    $("#modal_tagName").val("");
    $("#modal_tagDescription").val("");
}

/**
 * 页面刷新
 */
function restart() {
    window.location="/admin/tag";
}