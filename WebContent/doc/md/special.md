- <a name="changeSignCode">刷新签到码</a>

		POST /tw_epm/actions/project_changeSignCode.action
		#只有管理员自身有权限进行本操作，若当前用户无权限中断程序并返回401
		to:{
			projectId:#项目Id
		}

		正确返回时状态码为201
		return{
			signCode:#新的签到码
		}
		
		错误时除返回码还要返回错误信息
		return{
			error:'错误信息'
		}
	
		错误码：
			401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
			403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
			404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
			406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
			500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。

- <a name="isInProjectArea">查询某地点是否在工作区域</a>

		POST /tw_epm/actions/sign_isInProjectArea.action
		to:{
			longitude(float):#经度，
			latitude(float):#纬度，
			project(int):#所属项目Id，
		}

		正确返回时状态码为200
		return{
			result:"true",#(true|false),json传输的是字符串不能把本返回结果直接放到判断语句里
		}
		
		错误时除返回码还要返回错误信息
		return{
			error:'错误信息'
		}
	
		错误码：
			401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
			403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
			404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
			406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
			500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。

