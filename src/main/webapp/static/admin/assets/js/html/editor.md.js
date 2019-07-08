$(function() {
    var editor = editormd("test-editor", {
        width: "75%",
        height: 650,
        path : '../static/admin/assets/plugins/editor.md/lib/',
        codeFold : true,
        saveHTMLToTextarea : true,    // 保存 HTML 到 Textarea
        searchReplace : true,
        //watch : false,                // 关闭实时预览
        htmlDecode : "style,script,iframe|on*",            // 开启 HTML 标签解析，为了安全性，默认不开启
        //toolbar  : false,             //关闭工具栏
        //previewCodeHighlight : false, // 关闭预览 HTML 的代码块高亮，默认开启
        emoji : true,
        taskList : true,
        tocm : true,         // Using [TOCM]
        tex : true,                   // 开启科学公式TeX语言支持，默认关闭
        flowChart : true,             // 开启流程图支持，默认关闭
        sequenceDiagram : true,       // 开启时序/序列图支持，默认关闭,
        imageUpload : true,
        imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUploadURL : "/admin/upload",
    });
});

function word(){
    var html_text = $("#articleDetail").val();
    $("#articleHtml").val(html_text);
    //获取content
    var content_text = $(".markdown-body.editormd-preview-container").html();
    $("#articleContent").val(content_text);
}