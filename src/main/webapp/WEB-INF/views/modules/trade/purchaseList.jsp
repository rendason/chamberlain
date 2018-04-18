<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>采购管理</title>
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
		<li class="active"><a href="${ctx}/trade/purchase/">采购列表</a></li>
		<shiro:hasPermission name="trade:purchase:edit"><li><a href="${ctx}/trade/purchase/form">采购添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="purchase" action="${ctx}/trade/purchase/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>采购员：</label>
				<sys:treeselect id="user" name="user.id" value="${purchase.user.id}" labelName="user.name" labelValue="${purchase.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>类型：</label>
				<form:radiobuttons path="type" items="${fns:getDictList('purchase_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li><label>供货商：</label>
				<form:input path="seller" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>采购员</th>
				<th>类型</th>
				<th>供货商</th>
				<th>种类</th>
				<th>金额</th>
				<th>支付方式</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="trade:purchase:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="purchase">
			<tr>
				<td><a href="${ctx}/trade/purchase/form?id=${purchase.id}">
					${purchase.user.name}
				</a></td>
				<td>
					${fns:getDictLabel(purchase.type, 'purchase_type', '')}
				</td>
				<td>
					${purchase.seller}
				</td>
				<td>
					${purchase.kind}
				</td>
				<td>
					${purchase.total}
				</td>
				<td>
					${purchase.payment.name}
				</td>
				<td>
					<fmt:formatDate value="${purchase.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${purchase.remarks}
				</td>
				<shiro:hasPermission name="trade:purchase:edit"><td>
    				<a href="${ctx}/trade/purchase/form?id=${purchase.id}">查看</a>
					<a href="${ctx}/trade/purchase/delete?id=${purchase.id}" onclick="return confirmx('确认要删除该采购吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>