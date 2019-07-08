var _dataTable = $("#dataTable").DataTable({
    // 是否开启本地分页
    "paging": true,
    // 是否开启本地排序
    "ordering": false,
    // 是否显示左下角信息
    "info": true,
    //每页初始显示5条记录
    'iDisplayLength': 5,
    // 是否允许用户改变表格每页显示的记录数
    "lengthChange": true,
    //表格显示记录数集合
    "lengthMenu":[5,10,15,20],
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
        "url": "/admin/page",
        "type":"POST"
    },
    // 分页按钮显示选项
    "pagingType": "full_numbers",
    // 设置列的数据源
    "columns": [
        {"data": "articleId"},
        {"data": "articleTitle"},
        {"data": function (row, type, val, meta) {
                var path = row.articleUrl;
                return '<img src= "'+path+'" alt="文章展示图" style="width: 100px;height: 100px;text-align: center;">'
            }},
        {"data": function (row, type, val, meta) {
            if(row.articleStatus == 1){
                return '<p style="color:orangered;">置顶</p>'
            }
            else{
                return '<p>未置顶</p>'
            }
            }},
        {"data": "articleViewCount"},
        {"data":function (row, type, val, meta) {
                var date = DateTime.format(row.articleUpdateTime,"yyyy-MM-dd HH:mm:ss");
                return '<p>'+date+'</p>'
            }},
        {
            "data": function (row, type, val, meta) {
                return '<a href="/admin/detail?articleId='+row.articleId+'" type="button" class="btn btn-sm btn-default"><i class="fa fa-search"></i> 查看</a>&nbsp;&nbsp;&nbsp;' +
                    '<a href="/admin/edit?articleId='+row.articleId+'" type="button" class="btn btn-sm btn-primary"><i class="fa fa-edit"></i> 编辑</a>&nbsp;&nbsp;&nbsp;' +
                    '<button onclick="getId('+row.articleId+')" data-toggle="modal" data-target="#myModal" type="button" class="btn btn-sm btn-danger"><i class="fa fa-trash-o"></i> 删除</button>'
            }
        }
    ],
    // 国际化
    "language": {
        "sProcessing": "处理中...",
        "sLengthMenu": "显示 _MENU_ 项结果",
        "sZeroRecords": "没有您想要的数据",
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
    $("#aInput").attr("onclick","deleteText("+Id+")");
}

/**
 * 删除文章
 */
function deleteText(articleId) {
    $.ajax({
        "type":"POST",
        "url":"/admin/delete",
        "data":{"articleId":articleId},
        "success":function (data) {
            var message = data.message;
            if(message == "删除成功") {
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

/**
 * 刷新页面
 */
function retry() {
    window.location = "/admin/article";
}

/**
 * 条件查询
 */
function search() {
    var title = $("#articleTitle").val();
    var content = $("#articleContent").val();
    var status = $("#articleStatus").val();
    var params = {
        "articleTitle": title,
        "articleContent": content,
        "articleStatus": status
    };
    _dataTable.settings()[0].ajax.data = params;
    _dataTable.ajax.reload();
}