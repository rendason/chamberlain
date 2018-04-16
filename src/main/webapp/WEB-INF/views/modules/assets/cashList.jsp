<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>现金管理</title>
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
		<li class="active"><a href="${ctx}/assets/cash/">现金列表</a></li>
		<shiro:hasPermission name="assets:cash:edit"><li><a href="${ctx}/assets/cash/form">现金开户</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cash" action="${ctx}/assets/cash/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>金额</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="assets:cash:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cash">
			<tr>
				<td><a href="${ctx}/assets/cash/form?id=${cash.id}">
					${cash.name}
				</a></td>
				<td>
					${cash.amount}
				</td>
				<td>
					<fmt:formatDate value="${cash.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${cash.remarks}
				</td>
				<shiro:hasPermission name="assets:cash:edit"><td>
    				<a href="${ctx}/assets/cash/increase/form?id=${cash.id}">充值</a>
    				<a href="${ctx}/assets/cash/decrease/form?id=${cash.id}">提现</a>
					<a href="${ctx}/assets/cash/delete?id=${cash.id}" onclick="return confirmx('确认要删除该现金吗？', this.href)">销户</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>