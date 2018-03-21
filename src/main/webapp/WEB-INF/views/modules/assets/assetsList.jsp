<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资产管理</title>
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
		<li class="active"><a href="${ctx}/assets/assets/">资产列表</a></li>
		<shiro:hasPermission name="assets:assets:edit"><li><a href="${ctx}/assets/assets/form">资产添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="assets" action="${ctx}/assets/assets/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>分类：</label>
				<form:input path="category" htmlEscape="false" maxlength="50" class="input-medium"/>
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
				<th>类型</th>
				<th>分类</th>
				<th>数量</th>
				<th>价格</th>
				<th>单位</th>
				<th>备注</th>
				<shiro:hasPermission name="assets:assets:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="assets">
			<tr>
				<td><a href="${ctx}/assets/assets/form?id=${assets.id}">
					${assets.name}
				</a></td>
				<td>
					${fns:getDictLabel(assets.type, 'assets_type', '')}
				</td>
				<td>
					${assets.category}
				</td>
				<td>
					${assets.quantity}
				</td>
				<td>
					${assets.price}
				</td>
				<td>
					${assets.unit}
				</td>
				<td>
					${assets.remark}
				</td>
				<shiro:hasPermission name="assets:assets:edit"><td>
    				<a href="${ctx}/assets/assets/form?id=${assets.id}">修改</a>
					<a href="${ctx}/assets/assets/delete?id=${assets.id}" onclick="return confirmx('确认要删除该资产吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>