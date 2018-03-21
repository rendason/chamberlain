<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>月度薪资管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/salary/monthlySalary/">月度薪资列表</a></li>
		<shiro:hasPermission name="salary:monthlySalary:edit"><li><a href="${ctx}/salary/monthlySalary/form">月度薪资添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="monthlySalary" action="${ctx}/salary/monthlySalary/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户：</label>
				<sys:treeselect id="user" name="user.id" value="${monthlySalary.user.id}" labelName="user.name" labelValue="${monthlySalary.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>年：</label>
				<form:input path="year" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>月：</label>
				<form:input path="month" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户</th>
				<th>年</th>
				<th>月</th>
				<th>是否支付</th>
				<shiro:hasPermission name="salary:monthlySalary:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="monthlySalary">
			<tr>
				<td><a href="${ctx}/salary/monthlySalary/form?id=${monthlySalary.id}">
					${monthlySalary.user.name}
				</a></td>
				<td>
					${monthlySalary.year}
				</td>
				<td>
					${monthlySalary.month}
				</td>
				<td>
					${fns:getDictLabel(monthlySalary.paid, 'salary_paid', '')}
				</td>
				<shiro:hasPermission name="salary:monthlySalary:edit"><td>
    				<a href="${ctx}/salary/monthlySalary/form?id=${monthlySalary.id}">修改</a>
					<a href="${ctx}/salary/monthlySalary/delete?id=${monthlySalary.id}" onclick="return confirmx('确认要删除该月度薪资吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>