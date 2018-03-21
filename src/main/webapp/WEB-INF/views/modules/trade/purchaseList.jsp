<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>购买管理</title>
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
		<li class="active"><a href="${ctx}/trade/purchase/">购买列表</a></li>
		<shiro:hasPermission name="trade:purchase:edit"><li><a href="${ctx}/trade/purchase/form">购买添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="purchase" action="${ctx}/trade/purchase/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>品名：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>卖方：</label>
				<form:input path="saler" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>买方：</label>
				<sys:treeselect id="consumer" name="consumer.id" value="${purchase.consumer.id}" labelName="consumer.name" labelValue="${purchase.consumer.name}"
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
				<th>品名</th>
				<th>数量</th>
				<th>单价</th>
				<th>单位</th>
				<th>卖方</th>
				<th>买方</th>
				<th>备注</th>
				<shiro:hasPermission name="trade:purchase:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="purchase">
			<tr>
				<td><a href="${ctx}/trade/purchase/form?id=${purchase.id}">
					${purchase.name}
				</a></td>
				<td>
					${purchase.quantity}
				</td>
				<td>
					${purchase.price}
				</td>
				<td>
					${purchase.unit}
				</td>
				<td>
					${purchase.saler}
				</td>
				<td>
					${purchase.consumer.name}
				</td>
				<td>
					${purchase.remark}
				</td>
				<shiro:hasPermission name="trade:purchase:edit"><td>
    				<a href="${ctx}/trade/purchase/form?id=${purchase.id}">修改</a>
					<a href="${ctx}/trade/purchase/delete?id=${purchase.id}" onclick="return confirmx('确认要删除该购买吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>