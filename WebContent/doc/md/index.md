## 一些公共api

> 不采用标准RESTful

> <label id="CD" />restful 状态码
>
	200 OK - [GET]：服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）。
	201 CREATED - [POST/PUT/PATCH]：用户新建或修改数据成功。
	202 Accepted - [*]：表示一个请求已经进入后台排队（异步任务）
	204 NO CONTENT - [DELETE]：用户删除数据成功。
	400 INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
	401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
	403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
	404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
	406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
	410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
	422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
	500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。

> <label id="auth" /> 用户权限分配
>
	用户账号权限：
		0：普通用户，不具有任何高级权限
		1：项目经理，有权发布项目，对项目进行操作，但只能操作自己的项目
		2：总经理，可以任命项目经理，给普通用户提升权限，可以查看修改所有项目
	项目角色权限
		DOCUMENT:处理项目资料
		SPECIFICATION:处理项目规范
		NOTICE:发布公告
		WORKSITE_RECORD:提交现场记录
		POINT:创建项目进度节点
		LOG:上传日志
		MATERIAL:上传项目材料记录
		LEARN_DOC:上传学习资料
		POINT_PROBLEM:针对节点提问
		POINT_ANSWER:回答节点问题
		*:项目经理(项目发起人),拥有所有权限


	注意：所有公告只能发布，和删除，无法修改


#通用接口

##### 1. [项目](project.html)<已完成>
##### 2. [用户](user.html)<已完成>
##### 3. [项目资料](document.html)<已完成>
##### 4. [规范](specification.html)<已完成>
##### 5. [签到](sign.html)<已完成>
##### 6. [公告](notice.html)<已完成>
##### 7. [公告接受角色](noticeRole.html)<已完成>
##### 8. [现场记录](worksiteRecord.html)<已完成>
##### 9. [进度节点](point.html)<已完成>
##### 10. [日志](log.html)<已完成>
##### 11. [材料表](material.html)<已完成>
##### 12. [学习资料表](learnDoc.html)<已完成>
##### 13. [用户项目](userPro.html)<已完成>
##### 14. [项目角色](proRole.html)<已完成>
##### 15. [节点问题](pointProblem.html)<已完成>
##### 16. [节点问题回答](pointAnswer.html)<已完成>
##### 17. [项目角色权限](proRoleAuth.html)<已完成>


#专门页面接口

##### 1. [管理员首页](adminIndex.html)<已完成>
