var myDropzone = new Dropzone("#dropz", {
    url: "/admin/upload", // 文件提交地址
    method: "post",  // 也可用put
    paramName: "dropzFile", // 默认为file
    maxFiles: 1,// 一次性上传的文件数量上限
    maxFilesize: 5, // 文件大小，单位：MB
    acceptedFiles: ".jpg,.gif,.png,.jpeg", // 上传的类型
    addRemoveLinks: true,
    parallelUploads: 1,// 一次上传的文件数量
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
            $("#articleUrl").val(data.path);
        });
    }
});
Dropzone.autoDiscover = false;