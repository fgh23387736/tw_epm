var iPhone;
var Password;
var NewPassword;
var RepeatNewPassword;

var  HeadImgUrl;
var Name;
var Sex;
var Phone;


information();
init();

//原始密码
function oldPassword(){
    Password=document.getElementById("Password").value;
    //密码长度在8个字符到16个字符，由字母、数字和"_"组成
    var passwordRegex=/^[0-9A-Za-z_]\w{7,15}$/;
    if(!passwordRegex.test(Password)){
        layer.msg("请输入原始密码", {
            icon: 5
        });
    }else{
        return true;
    }
}

//新密码（长度在8个字符到16个字符）
function checkPassword(){
    NewPassword=document.getElementById("NewPassword").value;
    //密码长度在8个字符到16个字符，由字母、数字和"_"组成
    var passwordRegex=/^[0-9A-Za-z_]\w{7,15}$/;
    if(!passwordRegex.test(NewPassword)){
        layer.msg("密码长度必须是由字母、数字和_组成的8到16个字符", {
            icon: 5
        });
    }else{
        return true;
    }
}

//校验新密码（和上面密码必须一致）
function checkRepassword(){
    NewPassword=document.getElementById("NewPassword").value;
    RepeatNewPassword=document.getElementById("RepeatNewPassword").value;
    //校验密码和上面密码必须一致
    if(RepeatNewPassword!=NewPassword){
        layer.msg("两次输入的密码不一致", {
            icon: 5
        });
    }else if(RepeatNewPassword==NewPassword){
        return true;
    }
}

//修改密码
function validateForm(){
    if(oldPassword()&&checkPassword()&&checkRepassword()){
        layer.load(2);
        $.ajax({
            url: '/tw_epm/actions/user_changePassword.action',
            type: 'POST',
            dataType: 'json',
            data: {
                password:hex_md5(Password),
                newPassword:hex_md5(NewPassword),
                rePassword:hex_md5(RepeatNewPassword)
                
            },
            success: function(data) {
                layer.closeAll('loading');
                layer.alert('密码修改成功', {
                   icon: 6
                  ,closeBtn: 0
                }, function(){
                    window.location='/tw_epm/end/login/login.jsp';
                });
                
            },
            error: function(data) {
                layer.closeAll('loading');
                layer.msg(JSON.parse(data.responseText).error, {
                    icon: 5
                });
            }
        });
    }
}


function information(){
    layer.load(2);
    $.ajax({
        url: '/tw_epm/actions/user_getByIds.action',
        type: 'POST',
        dataType: 'json',
        data: {
            keys:'loginName+name+userId+tel+email+wechat',
            ids:""
        },
        success: function(data) {
            $('#Name').attr('value', data.resultList[0].name);
            $('#Phone').attr('value', data.resultList[0].loginName);
            $('#Tel').attr('value', data.resultList[0].tel);
            $('#Wechat').attr('value', data.resultList[0].wechat);
            $('#Email').attr('value', data.resultList[0].email);
            layer.closeAll('loading');

        },
        error: function(data) {
            layer.closeAll('loading');
             layer.msg(JSON.parse(data.responseText).error, {
                icon: 5
            });
        }
    });
}


function oFocus_2() {
 Name=document.getElementById("Name").value;
}

function oBlur_2() {
Name=document.getElementById("Name").value;
if(!Name){
      layer.msg('请输入真实姓名！', {
                        icon: 5
                    });
      return false;
}
}



function submit(){
	Name=$("#Name").val();
    if(!Name){
         layer.msg('请输入真实姓名！', {
                        icon: 5
                    });
      return false;
    }

  layer.load(2);
     $.ajax({
        url: '/tw_epm/actions/user_updateByIds.action',
        type: 'POST',
        dataType: 'json',
        data: {
        	ids:"",
        	keys:"name+tel+email+wechat",
            name:Name,
            tel:$("#Tel").val(),
            email:$("#Email").val(),
            wechat:$("#Wechat").val()
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
             layer.msg(JSON.parse(data.responseText).error, {
                icon: 5
            });
        }
    });

}