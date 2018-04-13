<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>销售管理</title>
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
		<li class="active"><a href="${ctx}/trade/sale/">销售列表</a></li>
		<shiro:hasPermission name="trade:sale:edit"><li><a href="${ctx}/trade/sale/form">销售添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sale" action="${ctx}/trade/sale/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>销售员：</label>
				<sys:treeselect id="user" name="user.id" value="${sale.user.id}" labelName="user.name" labelValue="${sale.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<%-- <li><label>会员：</label>
				<form:select path="member.id" class="input-medium required">
                    <form:option value="" label=""/>
                    <form:options items="${members}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                </form:select>
			</li> --%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>销售员</th>
				<%-- <th>会员</th> --%>
				<th>种类</th>
				<th>应收</th>
				<th>折扣(%)</th>
				<th>减免</th>
                <th>实收</th>
				<th>收款方式</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="trade:sale:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sale">
			<tr>
				<td><a href="${ctx}/trade/sale/form?id=${sale.id}">
					${sale.user.name}
				</a></td>
				<%-- <td>
					${sale.member.name}
				</td> --%>
				<td>
					${sale.kind}
				</td>
				<td>
					${sale.expected}
				</td>
				<td>
					${sale.discount}
				</td>
				<td>
					${sale.exempt}
				</td>
				<td>
					${sale.expected}×${sale.discount}%-${sale.exempt}=${sale.actual}
				</td>
				<td>
                    ${sale.receipt.name}
                </td>
				<td>
					<fmt:formatDate value="${sale.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sale.remarks}
				</td>
				<shiro:hasPermission name="trade:sale:edit"><td>
    				<a href="${ctx}/trade/sale/form?id=${sale.id}">修改</a>
					<a href="${ctx}/trade/sale/delete?id=${sale.id}" onclick="return confirmx('确认要删除该销售吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>