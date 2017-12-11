1.  <a name='learnDoc'></a>**学习资料[增](#learnDoc_add)、[删](#learnDoc_delete)、[改](#learnDoc_change)、[查](#learnDoc_search)**
	- <a name="learnDoc_add">增</a>

			POST /tw_epm/actions/learnDoc_add.action
			#只有管理员有权限操作
			to{
				content(byte[]):#内容，
				name(String):#名称，
				type(Integer):#类型，
				project(Integer):#所属项目
			}
			#状态码为201时表示增加成功 并返回下列信息
			return {
				learnDocId:#项目Id
			}
			#修改失败时（状态码非201）并返回下列信息
			return {
				error:#出错原因
			}
			
			401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
			422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
	- <a name="learnDoc_delete">删</a>

			POST /tw_epm/actions/learnDoc_deleteByIds.action
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
	- <a name="learnDoc_change">改</a>

			POST /tw_epm/actions/learnDoc_updateByIds.action
			#只有管理员自身有权限进行本操作，若当前用户无权限中断程序并返回401
			to:{
				ids:'1+',#修改Id
				keys:'key1+key2+...'
				key1:
				key2:
				.....
			}
			
			keys∈{
				name(String):#名称，
				type(Integer):#类型
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
	- <a name="learnDoc_search">查</a>  
			
			#根据Id搜索
			POST /tw_epm/actions/learnDoc_getByIds.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				ids:'1+2+3+...',#搜索记录Id
			}
			
			#根据创建用户搜索
			POST /tw_epm/actions/learnDoc_getByProject.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				project:,#所属项目Id
			}

			#若Keys为空则表示搜索下方全部字段
			Keys∈{
				learnDocId(int):#id
				content(String):#文件所处路径，
				name(String):#名称，
				type(Integer):#类型，
				project:{
					projectId:项目id
					name：项目名称
				},#所属项目
				user:{
					userId:id
					name：名称
				}#上传人
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
