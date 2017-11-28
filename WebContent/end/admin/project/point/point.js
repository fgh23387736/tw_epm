var pageListmanage=new MyPage();
var pageListmanageRole=new MyPage();
var pageListmanageUser=new MyPage();
var pageListmanageWorksiteRecord=new MyPage();
var pageListmanagePoint=new MyPage();
var pageListmanageChoseUser=new MyPage();
laydate({
  	elem: '#thePointDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	format: 'YYYY-MM-DD hh:mm:ss',
	istime: true //是否开启时间选择
});
laydate.skin('molv');  //加载皮肤，参数lib为皮肤名 

var id = pageListmanage.getUrlPra("id");
if(id == null){
	history.back();
}else{
	init();
}
function init(){
	initPoint(id);
	initProblem(id);
}

function initPoint(id){
	layer.load(2);
    $.ajax({
        url: "/tw_epm/actions/point_getByIds.action",
        type: 'POST',
        dataType: 'json',
        data: {
            keys:"name+date+describe+state",
			ids:id
        },
        success: function(data) {
            if(data.total <= 0){
            	layer.closeAll('loading');
            	layer.msg("节点不存在", {
	                icon: 5
	            });
            }else{

            	$("#pointTitle").html(data.resultList[0].name);
            	$("#thePointName").val(data.resultList[0].name);
            	$("#thePointDate").val(data.resultList[0].date);
            	$("#thePointDescribe").val(data.resultList[0].describe);
            	$("#thePointState").val(data.resultList[0].state);
                layer.closeAll('loading');
            }
        },
        error: function(data) {
            pageListmanage.searchAll();
            layer.closeAll('loading');
            layer.msg(JSON.parse(data.responseText).error, {
                icon: 5
            });
        }
    });
}

function savePoint(){
	layer.load(2);
    $.ajax({
        url: "/tw_epm/actions/point_updateByIds.action",
        type: 'POST',
        dataType: 'json',
        data: {
            keys:"name+date+describe+state",
			ids:id,
			name:$("#thePointName").val(),
			date:$("#thePointDate").val(),
			describe:$("#thePointDescribe").val(),
			state:$("#thePointState").val()
        },
        success: function(data) {
            layer.closeAll('loading');
        	layer.msg("保存成功", {
                icon: 6
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


function answerTheProblem(problemId){
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
                initProblem(id);
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