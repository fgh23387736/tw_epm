
var ue = UE.getEditor('Introduce',{
    toolbars: [
        [
            'source', //源代码
            'undo', //撤销
            'redo', //重做
            'bold', //加粗
            'indent', //首行缩进
            'italic', //斜体
            'underline', //下划线
            'strikethrough', //删除线
            'subscript', //下标
            'fontborder', //字符边框
            'superscript', //上标
            'formatmatch', //格式刷
            'blockquote', //引用
            'pasteplain', //纯文本粘贴模式
            'selectall', //全选
            'preview', //预览
            'horizontal', //分隔线
            'removeformat', //清除格式
            'time', //时间
            'date', //日期
            'unlink', //取消链接
            'insertrow', //前插入行
            'insertcol', //前插入列
            'mergeright', //右合并单元格
            'mergedown', //下合并单元格
            'deleterow', //删除行
            'deletecol', //删除列
            'splittorows', //拆分成行
            'splittocols', //拆分成列
            'splittocells', //完全拆分单元格
            'deletecaption', //删除表格标题
            'inserttitle', //插入标题
            'mergecells', //合并多个单元格
            'deletetable', //删除表格
            'cleardoc', //清空文档
            'insertparagraphbeforetable', //"表格前插入行"
            'fontfamily', //字体
            'fontsize', //字号
            'paragraph', //段落格式
            'simpleupload', //单图上传
            'edittable', //表格属性
            'edittd', //单元格属性
            'link', //超链接
            'spechars', //特殊字符
            'searchreplace', //查询替换
            'justifyleft', //居左对齐
            'justifyright', //居右对齐
            'justifycenter', //居中对齐
            'justifyjustify', //两端对齐
            'forecolor', //字体颜色
            'backcolor', //背景色
            'insertorderedlist', //有序列表
            'insertunorderedlist', //无序列表
            'fullscreen', //全屏
            'directionalityltr', //从左向右输入
            'directionalityrtl', //从右向左输入
            'rowspacingtop', //段前距
            'rowspacingbottom', //段后距
            'pagebreak', //分页
            'imagenone', //默认
            'imageleft', //左浮动
            'imageright', //右浮动
            'imagecenter', //居中
            'lineheight', //行间距
            'edittip ', //编辑提示
            'customstyle', //自定义标题
            'autotypeset', //自动排版});
            'touppercase', //字母大写
            'tolowercase', //字母小写
            'inserttable', //插入表格
            'charts', // 图表
        ]
    ], 
    autoFloatEnabled:false,
    zIndex :9999,
    elementPathEnabled:false,
    autoHeightEnabled :false,
    wordCount:false
});
ue.ready(function() {
    information();
});
function information(){
    layer.load(2);
    $.ajax({
        url: '/sanhe/src/v1/operation/website.php',
        type: 'GET',
        dataType: 'json',
        data: {
            Type:0,
            Keys:'Name+Phone+Email+Address+Longitude+Latitude+Introduce+LocalPhone',
            Search:{
                Id:''
            }
        },
        success: function(data) {
            ue.setContent(data.Introduce);
            $('#Name').val(data.Name);
            $('#Phone').val(data.Phone);
            $('#LocalPhone').val(data.LocalPhone);
            $('#Email').val(data.Email);
            $('#Address').val(data.Address);
            $('#Longitude').val(data.Longitude);
            $('#Latitude').val(data.Latitude);
            layer.closeAll('loading');
        },
        error: function(data) {
            layer.closeAll('loading');
             layer.msg(JSON.parse(data.responseText).Error, {
                icon: 5
            });
        }
    });
}

function submit(){

  layer.load(2);
     $.ajax({
        url: '/sanhe/src/v1/operation/website.php',
        type: 'PUT',
        dataType: 'json',
        data: {
            Update:{
                Name:$('#Name').val(),
                Phone:$('#Phone').val(),
                LocalPhone:$('#LocalPhone').val(),
                Email:$('#Email').val(),
                Address:$('#Address').val(),
                Longitude:$('#Longitude').val(),
                Latitude:$('#Latitude').val(),
                Introduce:ue.getContent()
            }
        },
        success: function(data) {
            layer.closeAll('loading');
            layer.alert('修改成功', {
               icon: 6
              ,closeBtn: 0
            }, function(){
              location.reload();
            });
            
        },
        error: function(data) {
            layer.closeAll('loading');
             layer.msg(JSON.parse(data.responseText).Error, {
                icon: 5
            });
        }
    });

}
