layui.use(["layer","element","fsCommon","form"],function(){
    var layer = layui.layer,
    element = layui.element,
    form = layui.form,
    fsCommon = layui.fsCommon;
    var proRoleId = fsCommon.getUrlPra("proRole");
    if(!$.isEmpty(proRoleId)){
        var authArr = [
            'DOCUMENT',
            'SPECIFICATION',
            'NOTICE',
            'WORKSITE_RECORD',
            'POINT',
            'LOG',
            'MATERIAL',
            'LEARN_DOC',
            'POINT_PROBLEM',
            'POINT_ANSWER',
            'SIGN_CODE'
        ];
        for (var i = 0; i < authArr.length; i++) {
            //$("#checkbox_"+authArr[i]).bootstrapSwitch('setState',false);
            //$("#checkbox_"+authArr[i]).attr('title',0);
        };
        layer.load(2);
        $.ajax({
            url: "/tw_epm/actions/proRole_getByIds.action",
            type: 'POST',
            dataType: 'json',
            data: {
                ids:proRoleId,
                keys:'auths'
            },
            success: function(data) {
                if(data.total > 0){
                    for (var i = 0; i < data.resultList[0].auths.length; i++) {
                        var auth = data.resultList[0].auths[i];
                        // $("#checkbox_"+auth.name).bootstrapSwitch('setState',true);
                        // $("#checkbox_"+auth.name).attr('title',auth.proRoleAuthId);

                        $("[name="+auth.name+"]").attr("checked",true);
                        $("[name="+auth.name+"]").attr("value",auth.proRoleAuthId);
                        
                    };
                    form.render("checkbox");

                }
                layer.closeAll('loading');
            },
            error: function(data) {
                layer.closeAll('loading');
                layer.msg(JSON.parse(data.responseText).error, {
                    icon: 5
                });
            }
        });
        form.on('switch(switchTest)',
        function(data) {
            console.log(data.elem); //得到checkbox原始DOM对象
            console.log(data.elem.checked); //开关是否开启，true或者false
            console.log(data.value); //开关value值，也可以通过data.elem.value得到
            console.log(data.othis); //得到美化后的DOM对象
            console.log(data.elem.name);
            if(data.elem.checked){
                layer.load(2);
                $.ajax({
                    url: "/tw_epm/actions/proRoleAuth_add.action",
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        auth: data.elem.name,
                        proRole:proRoleId
                    },
                    success: function(redata) {
                        layer.msg('修改成功', {
                            icon: 6
                        });
                        data.elem.value = redata.proRoleAuthId;
                        layer.closeAll('loading');
                    },
                    error: function(redata) {
                        layer.closeAll('loading');
                        data.elem.checked = false;
                        form.render("checkbox");
                        layer.msg(JSON.parse(redata.responseText).error, {
                            icon: 5
                        });
                    }
                });    
            }else{
                layer.load(2);
                $.ajax({
                    url: "/tw_epm/actions/proRoleAuth_deleteByIds.action",
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        ids:data.value
                    },
                    success: function(redata) {
                        layer.msg('修改成功', {
                            icon: 6
                        });
                        layer.closeAll('loading');
                    },
                    error: function(redata) {
                        layer.closeAll('loading');
                        layer.msg(JSON.parse(redata.responseText).error, {
                            icon: 5
                        });
                    }
                });
            }
        });
    }else{
        layer.msg("角色不存在", {
            icon: 5
        });
    }
       

});