1.  <a name='sign'></a>**签到[增](#sign_add)、[删](#sign_delete)、[改](#sign_change)、[查](#sign_search)**
	- <a name="sign_add">增</a>

			POST /tw_epm/actions/sign_addByPosition.action
			#根据位置签到
			to{
				longitude(float):#经度，
				latitude(float):#纬度，
				project(int):#所属项目，
			}

			POST /tw_epm/actions/sign_addBySignCode.action
			#根据签到码签到
			to{
				signCode(String):#签到码，从管理员处获取
				project(int):#所属项目，
			}

			POST /tw_epm/actions/sign_addBySignCodeAndPosition.action
			#根据签到码和位置签到
			to{
				signCode(String):#签到码，从管理员处获取
				longitude(float):#经度，
				latitude(float):#纬度，
				project(int):#所属项目
			}

			#状态码为201时表示增加成功 并返回下列信息
			return {
				signId:#项目Id
			}
			#修改失败时（状态码非201）并返回下列信息
			#如果不在规定区域error会提示：您不在规定区域无法签到
			return {
				error:#出错原因
			}
			
			401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
			422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
	- <a name="sign_delete">删</a>

			POST /tw_epm/actions/sign_deleteByIds.action
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
	- <a name="sign_change">改</a>

			POST /tw_epm/actions/sign_updateByIds.action
			#只有管理员自身有权限进行本操作，若当前用户无权限中断程序并返回401
			to:{
				ids:'1+',#修改Id
				keys:'key1+key2+...'
				key1:
				key2:
				.....
			}
			
			keys∈{
				date(String):#时间 eg "2017-10-10 00:00:00"，
				project(int):#所属项目，
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
	- <a name="sign_search">查</a>  
			
			#根据Id搜索
			POST /tw_epm/actions/sign_getByIds.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				ids:'1+2+3+...',#搜索记录Id
			}
			
			#根据创建用户搜索
			POST /tw_epm/actions/sign_getByUser.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				user(int):,#所属用户Id
			}

			#根据所属项目搜索
			POST /tw_epm/actions/sign_getByProject.action
			to:{
				keys:'Id+Title+Price...',#需要获取的属性名，每个属性之间用'+'隔开
				page:1,#当前页数，（可选，Page和PageSize必须同时存在）
				pageSize：10，#每页数据条数（可选，Page和PageSize必须同时存在）
				project(int):,#所属项目Id
			}

			#若Keys为空则表示搜索下方全部字段
			Keys∈{
				signId(int):#项目id
				date(String):#时间 eg "2017-10-10 00:00:00"，
				longitude(float):#经度，
				latitude(float):#纬度，
				signCode(String):#签到码，
				type(int):#签到方式∈{
						0：位置签到
						1：签到码签到
						2：签到码和位置签到
				}
				Project:{
					projectId：，
					name
				}#所属项目，
				user:{
					userId：，
					name
				}#所属用户，
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
