/**
 * 初始化图片插件
 */
slider.init();
/**
 * 图片上传插件
 */
var myDropzone = new Dropzone("#dropz", {
    url: "/admin/banner_upload", // 文件提交地址
    method: "post",  // 也可用put
    paramName: "dropzFile", // 默认为file
    maxFiles: 3,// 一次性上传的文件数量上限
    maxFilesize: 5, // 文件大小，单位：MB
    acceptedFiles: ".jpg,.gif,.png,.jpeg", // 上传的类型
    addRemoveLinks: true,
    parallelUploads: 3,// 一次上传的文件数量
    //previewsContainer:"#preview", // 上传图片的预览窗口
    dictDefaultMessage: '拖动文件至此或者点击上传',
    dictMaxFilesExceeded: "您最多只能上传1个文件！",
    dictResponseError: '文件上传失败!',
    dictInvalidFileType: "文件类型只能是*.jpg,*.gif,*.png,*.jpeg。",
    dictFallbackMessage: "浏览器不受支持",
    dictFileTooBig: "文件过大上传文件最大支持.",
    dictRemoveLinks: "删除",
    dictCancelUpload: "取消",
    init: function () {
        this.on("success", function (file, data) {
            setTimeout('window.location = "/admin/banner";',1000)
        });
    }
});
Dropzone.autoDiscover = false;

/**
 * 修改轮播图状态
 * @param bId
 * @param index
 */
function changeStatus(bId,index) {
    var id = bId + 100;
    var bStatus = $("#"+id).val();

    $.ajax({
       type:"post",
       url:"/admin/banner_status",
       data:{"bannerId":bId,"bannerStatus":bStatus},
       success:function (data) {
          var message = data.message;
          var status = data.status;
          if(message == "修改成功"){
            if(status == 1){
                $("#"+bId).attr("src","/static/admin/assets/img/start.png");
                $("#"+id).val(1);
            }
            else if(status == 0){
                $("#"+bId).attr("src","/static/admin/assets/img/stop.png");
                $("#"+id).val(0);
            }
          }else{
              $("#meg").html(message);
              $("#message").fadeIn("slow");
              setTimeout('$("#message").fadeOut("slow");',3000);
          }
       },
       dataType:"json"
    });
}