layui.use(["layer","element","fsCommon"],function(){
    var layer = layui.layer,
    element = layui.element,
    fsCommon = layui.fsCommon;
    var projectId = fsCommon.getUrlPra("project");
    initProject(projectId);
    function initProject(id){
        layer.load(2);
        $.ajax({
            url: "/tw_epm/actions/project_getByIds.action",
            type: 'POST',
            dataType: 'json',
            data: {
                keys:"name+startDate+endDateB+endDateA+content+percentage+points",
                ids:id
            },
            success: function(data) {
                if(data.total <= 0){
                    layer.closeAll('loading');
                    layer.msg("项目不存在", {
                        icon: 5
                    });
                }else{

                    // $("#projectTitle").html(data.resultList[0].name);
                    // $("#theProjectName").val(data.resultList[0].name);
                    // $("#theProjectStartDate").val(data.resultList[0].startDate);
                    // $("#theProjectEndDateA").val(data.resultList[0].endDateA);
                    // $("#theProjectEndDateB").val(data.resultList[0].endDateB);
                    // $("#theProjectContent").val(data.resultList[0].content);
                    // $("#projectPercentLable").html(data.resultList[0].percentage+"%");
                    // $("#projectPercentInside").html(data.resultList[0].percentage+"%");
                    element.progress('demo', data.resultList[0].percentage+"%");
                    var points = data.resultList[0].points;
                    $("#pointListForPercentBar").children().remove();
                    for (var i = 0; i < points.length; i++) {
                        var thePercent = points[i].state+"%";
                        $("#pointListForPercentBar").append(
                                '<div class="pointForPercentBar" style="left:'+thePercent+';" title="'+points[i].name+'" >'
                                +thePercent
                                +'</div>'
                            );
                        
                    };
                    layer.closeAll('loading');
                }
                
            },
            error: function(data) {
                layer.closeAll('loading');
                layer.msg(JSON.parse(data.responseText).error, {
                    icon: 5
                });
            }
        });
    }


});