<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>标准薪资管理</title>
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
		<li class="active"><a href="${ctx}/salary/baseSalary/">标准薪资列表</a></li>
		<shiro:hasPermission name="salary:baseSalary:edit"><li><a href="${ctx}/salary/baseSalary/form">标准薪资添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="baseSalary" action="${ctx}/salary/baseSalary/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户：</label>
				<sys:treeselect id="user" name="user.id" value="${baseSalary.user.id}" labelName="user.name" labelValue="${baseSalary.user.name}"
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
				<th>薪资</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="salary:baseSalary:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="baseSalary">
			<tr>
				<td><a href="${ctx}/salary/baseSalary/form?id=${baseSalary.id}">
					${baseSalary.user.name}
				</a></td>
				<td>
                    ${baseSalary.total}
                </td>
				<td>
					<fmt:formatDate value="${baseSalary.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${baseSalary.remarks}
				</td>
				<shiro:hasPermission name="salary:baseSalary:edit"><td>
    				<a href="${ctx}/salary/baseSalary/form?id=${baseSalary.id}">修改</a>
					<a href="${ctx}/salary/baseSalary/delete?id=${baseSalary.id}" onclick="return confirmx('确认要删除该标准薪资吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>