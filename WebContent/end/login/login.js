var  username=document.getElementById("Username").value;
var password=document.getElementById("Password").value
var type;

function oFocus_1() {
  username=document.getElementById("Username").value;
}

function oBlur_1() {
username=document.getElementById("Username").value;
if(!username){
	  layer.msg('请输入用户名！', {
                        icon: 5
                    });
	  return false;
}/*else{//用户名不为空，正则验证手机号码
	 if(!(/^1[34578]\d{9}$/.test(username))){ 
     	 layer.msg('请输入正确的手机号码！', {
                        icon: 5
                    });
     	 return false;
     	} 


}*/
}

function oFocus_2() {
 password=document.getElementById("Password").value;
}

function oBlur_2() {
password=document.getElementById("Password").value;
if(!password){
	  layer.msg('请输入密码！', {
                        icon: 5
                    });
	  return false;
}
}

function submitTest() {
   username=document.getElementById("Username").value;
 password=document.getElementById("Password").value
    if (!username&& !password) { //用户框value值和密码框value值都为空
     	 layer.msg('请输入用户名和密码！', {
                        icon: 5
                    });
        return false; //只有返回true表单才会提交
    } else if (!username) { //用户框value值为空
      layer.msg('请输入用户名！', {
                        icon: 5
                    });
        return false;
    } else if (!password) { //密码框value值为空
        layer.msg('请输入密码！', {
                        icon: 5
                    });
        return false;
    }
       radios=document.getElementsByName("Type");
        for(var i=0;i<radios.length;i++)
        {
            if(radios[i].checked==true)
            {
            	type=radios[i].value;
            }
        }
        /* if(!(/^1[34578]\d{9}$/.test(username))){ 
     	 layer.msg('请输入正确的手机号码！', {
                        icon: 5
                    });
     	 return false;
     	} */
     	
     	   layer.load(2);

       $.ajax({
        url: '/tw_epm/actions/user_login.action',
        type: 'POST',
        dataType: 'json',
        data: {
            loginName:username,
            password:hex_md5(password)
        },
        success: function(data) {
            layer.msg('登录成功', {
                icon: 6
            });
            layer.closeAll('loading');
            window.location="/tw_epm/end/admin/index/index.jsp";
        },
        error: function(data) {
            layer.closeAll('loading');
            layer.msg(JSON.parse(data.responseText).error, {
                icon: 5
            });
        }
    });



}