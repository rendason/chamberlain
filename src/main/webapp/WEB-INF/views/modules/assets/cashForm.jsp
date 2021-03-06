<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>现金管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		function changeAmount() {
            var amount = $("#amount");
            amount.val(amount.val() - 0 ${operate=='increase'?'+':'-'} ($("#change").val() - 0));
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/assets/cash/">现金列表</a></li>
		<li class="active"><a href="${ctx}/assets/cash/form?id=${cash.id}">
		    现金<shiro:hasPermission name="assets:cash:edit">${not empty cash.id?(operate=='increase'?'充值':'提现') :'开户'}</shiro:hasPermission><shiro:lacksPermission name="assets:cash:edit">查看</shiro:lacksPermission>
		</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cash" action="${ctx}/assets/cash/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="amount"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">金额：</label>
			<div class="controls">
				<input id="change" type="text" class="input-xlarge required number" onchange="changeAmount()" disable="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="assets:cash:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>