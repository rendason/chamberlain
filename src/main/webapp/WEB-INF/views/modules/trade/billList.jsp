<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账单管理</title>
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
		<li class="active"><a href="${ctx}/trade/bill/">账单列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="bill" action="${ctx}/trade/bill/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>付款人：</label>
				<form:input path="payment" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>收款人：</label>
				<form:input path="payee" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:radiobuttons path="type" items="${fns:getDictList('bill_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>付款人</th>
				<th>金额</th>
				<th>收款人</th>
				<th>类型</th>
				<th>创建时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="trade:bill:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bill">
			<tr>
				<td><a href="${ctx}/trade/bill/form?id=${bill.id}">
					${bill.name}
				</a></td>
				<td>
					${bill.payment}
				</td>
				<td>
					${bill.amount}
				</td>
				<td>
					${bill.payee}
				</td>
				<td>
					${fns:getDictLabel(bill.type, 'bill_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${bill.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${bill.remarks}
				</td>
				<shiro:hasPermission name="trade:bill:edit"><td>
					<a href="${ctx}/trade/bill/delete?id=${bill.id}" onclick="return confirmx('确认要删除该账单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>