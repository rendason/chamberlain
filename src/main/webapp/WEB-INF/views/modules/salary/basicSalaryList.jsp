<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>基础薪资管理</title>
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
		<li class="active"><a href="${ctx}/salary/basicSalary/">基础薪资列表</a></li>
		<shiro:hasPermission name="salary:basicSalary:edit"><li><a href="${ctx}/salary/basicSalary/form">基础薪资添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="basicSalary" action="${ctx}/salary/basicSalary/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户：</label>
				<sys:treeselect id="user" name="user.id" value="${basicSalary.user.id}" labelName="user.name" labelValue="${basicSalary.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
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
				<th>项目</th>
				<th>金额</th>
				<shiro:hasPermission name="salary:basicSalary:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="basicSalary">
			<tr>
				<td><a href="${ctx}/salary/basicSalary/form?id=${basicSalary.id}">
					${basicSalary.user.name}
				</a></td>
				<td>
					${basicSalary.itemName}
				</td>
				<td>
					${basicSalary.amount}
				</td>
				<shiro:hasPermission name="salary:basicSalary:edit"><td>
    				<a href="${ctx}/salary/basicSalary/form?id=${basicSalary.id}">修改</a>
					<a href="${ctx}/salary/basicSalary/delete?id=${basicSalary.id}" onclick="return confirmx('确认要删除该基础薪资吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>