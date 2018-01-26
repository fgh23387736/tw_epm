layui.use(["layer","fsCommon"],function(){
    var layer = layui.layer,
    fsCommon = layui.fsCommon;

    var pointId = fsCommon.getUrlPra("pointId");
    if(pointId == null){
        layer.msg("节点不存在",{icon:5});
    }else{
        init();
    }
    function init(){
        initProblem(pointId);
    }
    function initProblem(id){
        $("#pointProblems").children().remove();
        layer.load(2);
            $.ajax({
                url: "/tw_epm/actions/pointProblem_getByPoint.action",
                type: 'POST',
                dataType: 'json',
                data: {
                    keys:"pointProblemId+problem+user+date+answers",
                    point:id
                },
                success: function(data) {
                    var theProblemStr = "";
                    var theAnswerStr = "";
                    for (var i = 0; i < data.resultList.length; i++) {
                        theAnswerStr = "";
                        var answers = data.resultList[i].answers;
                        for (var j = 0; j < answers.length; j++) {
                            theAnswerStr += ''
                            +'<div class="answer">'
                            +    '<div class="name">'
                            +        answers[j].user.name
                            +    '</div>'
                            +    '<div class="content">'
                            +        answers[j].answer
                            +    '</div>'
                            +    '<div class="time">'
                            +        answers[j].date
                            +    '</div>'
                            +'</div>';
                        };

                        theProblemStr += ''
                        +'<div class="pointProblemDiv">'
                        +    '<div class="name">'
                        +        data.resultList[i].user.name
                        +    '</div>'
                        +    '<div class="content">'
                        +        data.resultList[i].problem
                        +    '</div>'
                        +    '<div class="time">'
                        +        data.resultList[i].date+'<span onclick="answerTheProblem('+data.resultList[i].pointProblemId+')">[回复]</span>'
                        +    '</div>'
                        +    '<div class="answerList">'
                        +        theAnswerStr
                        +    '</div>'
                        +'</div>'
                    };

                    $("#pointProblems").append(theProblemStr);
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

    window.answerTheProblem = function(problemId){
        $("#answerBoxAnswer").val("");
        layer.open({
          type: 1,
          shade: false,
          title: "回复", //不显示标题
          content: $('#answerBox'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
          btn:["保存"],
          maxWidth:500,
          cancel: function(){
            
          },
          btn1:function(index){
            layer.close(index);
            layer.load(2);
            $.ajax({
                url: "/tw_epm/actions/pointAnswer_add.action",
                type: 'POST',
                dataType: 'json',
                data: {
                    answer:$("#answerBoxAnswer").val(),
                    pointProblem:problemId
                },
                success: function(data) {
                    layer.msg('增加成功', {
                        icon: 6
                    });
                    layer.closeAll('loading');
                    initProblem(pointId);
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
    }
});
