1.  <a name='pointAnswer'></a>**节点问题回答[增](#pointAnswer_add)、[删](#pointAnswer_delete)、[改](#pointAnswer_change)、[查](#pointAnswer_search)**
	- <a name="pointAnswer_add">增</a>

			POST /tw_epm/actions/pointAnswer_add.action
			#只有管理员有权限操作
			to{
				answer(String):#回答，
				pointProblem(Integer):#所属问题
			}
			#状态码为201时表示增加成功 并返回下列信息
			return {
				pointAnswerId:#项目Id
			}
			#修改失败时（状态码非201）并返回下列信息
			return {
				error:#出错原因
			}
			
			401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
			422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
	- <a name="pointAnswer_delete">删</a>

			POST /tw_epm/actions/pointAnswer_deleteByIds.action
			#只有高级别管理员有权限进行本操作，若不是高级别管理员中断程序并返回401
			to:{
				ids：id1+id2+id3+... #要删除的id
			}
			
			
		
			正确返回时状态码为204
			return{
			}
			
			错误时除返回码还要返回错误信息
			return{
				error:'错误信息'
			}
		
			错误码：
				401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
				403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
				404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
				406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）
				500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。
	- <a name="pointAnswer_change">改</a>

			POST /tw_epm/actions/pointAnswer_updateByIds.action
			#只有管理员自身有权限进行本操作，若当前用户无权限中断程序并返回401
			to:{
				ids:'1+',#修改Id
				keys:'key1+key2+...'
				key1:
				key2:
				.....
			}
			
			keys∈{
				answer(String):#问题
			}	
		
			
			正确返回时状态码为201
			return{

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
	- <a name="pointAnswer_search">查</a>  
			
			#根据Id搜索
			POST /tw_epm/actions/pointAnswer_getByIds.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				ids:'1+2+3+...',#搜索记录Id
			}
			
			#根据创建用户搜索
			POST /tw_epm/actions/pointAnswer_getByPointProblem.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				pointProblem(Integer):#所属问题
			}

			#若Keys为空则表示搜索下方全部字段
			Keys∈{
				pointAnswerId(int):#id
				answer(String):#回答，
				pointProblem:{
					pointProblemId:问题id
					problem：问题
					user:{
						userId:#id
						name:#提问人姓名
					}#提问人
				},#所属项目
				user:{
					userId:id
					name：名称
				}#回答人
				date(String):#上传时间
				
			}

			#正确返回时状态码为200
			return{
				total：10,#未分页时搜索到总数据条数，当Page和PageSize不存在时就是resultList的长度
				resultList[
					{
						Id:,
						...
					},
					{
						Id:,
						...
					},
					...
				]

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
