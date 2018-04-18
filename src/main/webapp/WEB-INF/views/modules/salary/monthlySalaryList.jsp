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
			<li><label>支付：</label>
				<form:radiobuttons path="paid" items="${fns:getDictList('monthly_salary_paid')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>标准</th>
				<th>应发</th>
				<th>扣减</th>
				<th>实发</th>
				<th>支付方式</th>
				<th>支付</th>
				<th>更新时间</th>
				<th>备注信息</th>
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
					${monthlySalary.standard}
				</td>
				<td>
					${monthlySalary.planned}
				</td>
				<td>
					${monthlySalary.deducted}
				</td>
				<td>
					${monthlySalary.actual}
				</td>
				<td>
					${monthlySalary.payment.name}
				</td>
				<td>
					${fns:getDictLabel(monthlySalary.paid, 'monthly_salary_paid', '')}
				</td>
				<td>
					<fmt:formatDate value="${monthlySalary.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${monthlySalary.remarks}
				</td>
				<shiro:hasPermission name="salary:monthlySalary:edit"><td>
					<c:if test="${monthlySalary.paid!=1}"><a href="${ctx}/salary/monthlySalary/pay?id=${monthlySalary.id}" onclick="return confirmx('确认要支付该月度薪资吗？', this.href)">支付</a></c:if>
					<a href="${ctx}/salary/monthlySalary/form?id=${monthlySalary.id}">${monthlySalary.paid!=1?'修改':'查看'}</a>
					<a href="${ctx}/salary/monthlySalary/delete?id=${monthlySalary.id}" onclick="return confirmx('确认要删除该月度薪资吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>