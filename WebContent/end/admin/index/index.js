
init();
function init(){
	var time=getNowFormatDate();
	document.getElementById('nowDate').innerHTML=time;
	layer.load(2);
    $.ajax({
        url: '/tw_epm/actions/adminPublic_getWebsiteData.action',
        type: 'GET',
        dataType: 'json',
        data: {
        },
        success: function(data) {
            document.getElementById('UserNumber').innerHTML=data.userNumber;
            document.getElementById('ProjectNumber').innerHTML=data.projectNumber;
            document.getElementById('FinishedProjectNumber').innerHTML=data.finishedProjectNumber;
            document.getElementById('AdminNumber').innerHTML=data.adminNumber;
            makeCharts(data.startProject,data.endProject);
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

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}
function format(format,date)
{
 var o = {
 "M+" : date.getMonth()+1, //month
 "d+" : date.getDate(),    //day
 "h+" : date.getHours(),   //hour
 "m+" : date.getMinutes(), //minute
 "s+" : date.getSeconds(), //second
 "q+" : Math.floor((date.getMonth()+3)/3),  //quarter
 "S" : date.getMilliseconds() //millisecond
 }
 if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
 (date.getFullYear()+"").substr(4 - RegExp.$1.length));
 for(var k in o)if(new RegExp("("+ k +")").test(format))
 format = format.replace(RegExp.$1,
 RegExp.$1.length==1 ? o[k] :
 ("00"+ o[k]).substr((""+ o[k]).length));
 return format;
}

function makeCharts(startProject,endProject){
	var theStartProject = [];
	var theEndProject = [];
	var theDate = [];
	for (var i = 0; i < startProject.length; i++) {
		theDate[i] = startProject[i].date;
		theStartProject[startProject[i].date] = startProject[i].number;
	};

	for (var i = 0; i < endProject.length; i++) {
		theEndProject[endProject[i].date] = endProject[i].number;
	};
	var option = {
		title:{
			text:'最近半年数据'
		},
		legend:{
			show:true,
			data:[
				{
					name:'发起项目量',
					textStyle:{
						color:'#3398DB'
					}
					
				},
				{
					name:'完成项目量',
					textStyle:{
						color:'#41B314'
					}
				}
			],
			backrroungColor:'#FCBE56'

		},
	    color: ['#3398DB'],
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            data : [],
	            axisTick: {
	                alignWithLabel: true
	            }
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        {
	            name:'发起项目量',
	            type:'line',
	            data:[]
	        },
	        {
	            name:'完成项目量',
	            itemStyle : {  
                    normal : {  
                        color:'#41B314'  
                    }  
                }, 
	            type:'line',
	            data:[]
	        }
	    ]
	};
	for (var i = 0; i < theDate.length; i++) {
		option.xAxis[0].data[i] = theDate[i];
		option.series[0].data[i] = theStartProject[theDate[i]];
		option.series[1].data[i] = theEndProject[theDate[i]];
	};
	var myChart = echarts.init(document.getElementById('Charts'));
	myChart.setOption(option);

}


