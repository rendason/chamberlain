<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>固定资产管理</title>
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
		<li class="active"><a href="${ctx}/assets/fixedAssets/">固定资产列表</a></li>
		<shiro:hasPermission name="assets:fixedAssets:edit"><li><a href="${ctx}/assets/fixedAssets/form">固定资产添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="fixedAssets" action="${ctx}/assets/fixedAssets/" method="post" class="breadcrumb form-search">
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
				<th>分类</th>
				<th>数量</th>
				<th>单位</th>
				<th>价格</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="assets:fixedAssets:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fixedAssets">
			<tr>
				<td><a href="${ctx}/assets/fixedAssets/form?id=${fixedAssets.id}">
					${fixedAssets.name}
				</a></td>
				<td>
					${fixedAssets.category}
				</td>
				<td>
					${fixedAssets.quantity}
				</td>
				<td>
					${fixedAssets.unit}
				</td>
				<td>
					${fixedAssets.price}
				</td>
				<td>
					<fmt:formatDate value="${fixedAssets.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fixedAssets.remarks}
				</td>
				<shiro:hasPermission name="assets:fixedAssets:edit"><td>
    				<a href="${ctx}/assets/fixedAssets/form?id=${fixedAssets.id}">修改</a>
					<a href="${ctx}/assets/fixedAssets/delete?id=${fixedAssets.id}" onclick="return confirmx('确认要删除该固定资产吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>