
- <a name="">站点数据模块</a>  
			
			#根据Id搜索
			POST /tw_epm/actions/adminPublic_getWebsiteData.action
			to:{
				
			}

			#正确返回时状态码为200
			return{
				userNumber(int):#注册人数
				projectNumber(int):#项目数量
				finishedProjectNumber(int):#完成项目数量
				adminNumber(int):#管理员数量（用户权限大于0的）
				startProject(arr,json):[
					{
						date:#日期，年-月，
						number:#数量
					}
				]#过去6个月每个月开始了多少项目
				endProject(arr,json):[
					{
						date:#日期，年-月，
						number:#数量
					}
				]#过去6个月每个月结束了多少项目
		
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
				410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
				500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。
