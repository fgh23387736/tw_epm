var path = "/sanhe/src/v1/admin/admin.php";
var index = parent.layer.getFrameIndex(window.name);

function submitData() {
    if (checkPhone()&&checkPassword()&&checkRepassword()&&checkFrom()) {
        var theData = {};
        var x = document.getElementById("form");
        for (var i = 0; i < x.elements.length; i++) {
            if(x.elements[i].name=="Password"||x.elements[i].name=="RepeatPassword"){
                theData[x.elements[i].name]=hex_md5(x.elements[i].value);
            }else{
                theData[x.elements[i].name] = x.elements[i].value;
            }
            

        }
        layer.load(2);
        if(id!=''){
            $.ajax({
                url: path,
                type: 'PUT',
                dataType: 'json',
                data: {
                    Type:0,
                    Id:id,
                    Update:theData
                },
                success: function(data) {
                    layer.closeAll('loading');
                    parent.layer.msg('提交成功', {
                        icon: 6
                    });
                    parent.pageListmanage.searchAll();
                    parent.layer.close(index);
                },
                error: function() {
                    layer.closeAll('loading');
                    layer.msg(JSON.parse(data.responseText).Error, {
                        icon: 5
                    });
                }
            });
        }else{
            $.ajax({
                url: path,
                type: 'POST',
                dataType: 'json',
                data: theData,
                success: function(data) {
                    layer.closeAll('loading');
                    parent.layer.msg('提交成功', {
                        icon: 6
                    });
                    parent.pageListmanage.searchAll();
                    parent.layer.close(index);
                },
                error: function(data) {
                    layer.closeAll('loading');
                    layer.msg(JSON.parse(data.responseText).Error, {
                        icon: 5
                    });
                }
            });
        }
        
    }
}

function checkFrom() {
    var username = $("#Level").val();
    if (username == "") {
        layer.msg('级别不能为空！', {
            icon: 7
        });
        return false;
    }
    return true;

}




function init(){
    /*初始化列表*/

    /*初始化数据*/
    if(id!=''){
        layer.load(2);
        $.ajax({
            url: path,
            type: 'GET',
            dataType: 'json',
            data:{
                Type:0,
                Keys:'',
                Search:{
                    Id:id
                }
            },
            success: function(data) {
                $('#Name').attr('value', data.ResultList[0].Name);
                layer.closeAll('loading');
            },
            error: function() {
                layer.closeAll('loading');
                layer.msg(JSON.parse(data.responseText).Error, {
                    icon: 5
                });
            }
        });
    }
}

init();


//手机号验证(以1开头的11位数字)
function checkPhone(){
    name=document.getElementById("Phone").value;
    var nameRegex=/^[1][0-9]{10}$/;
    //document.getElementById("Phone").value=name.checkPhone();
    if(!nameRegex.test(name)){
        layer.msg("请输入11位手机号", {
            icon: 5
        });
    }else{
        return true;
    }
}

//验证密码（长度在8个字符到16个字符）
function checkPassword(){
    password=document.getElementById("Password").value;
    //密码长度在8个字符到16个字符，由字母、数字和"_"组成
    var passwordRegex=/^[0-9A-Za-z_]\w{7,15}$/;
    if(!passwordRegex.test(password)){
        layer.msg("密码长度必须是由字母、数字和_组成的8到16个字符", {
            icon: 5
        });
    }else{
        return true;
    }
}

//验证校验密码（和上面密码必须一致）
function checkRepassword(){
    password=document.getElementById("Password").value;
    repassword=document.getElementById("RepeatPassword").value;
    //校验密码和上面密码必须一致
    console.log(repassword);
    console.log(password);
    if(repassword!=password){
        //document.getElementById("col").innerHTML="两次输入的密码不一致";
        layer.msg("两次输入的密码不一致", {
            icon: 5
        });
    }else if(repassword==password){
        //document.getElementById("col").innerHTML="";
        return true;
    }
}