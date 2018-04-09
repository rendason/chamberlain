<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>月度薪资管理</title>
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
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/salary/monthlySalary/">月度薪资列表</a></li>
		<li class="active"><a href="${ctx}/salary/monthlySalary/form?id=${monthlySalary.id}">月度薪资<shiro:hasPermission name="salary:monthlySalary:edit">${not empty monthlySalary.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="salary:monthlySalary:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="monthlySalary" action="${ctx}/salary/monthlySalary/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">用户：</label>
			<div class="controls">
				<sys:treeselect id="user" name="user.id" value="${monthlySalary.user.id}" labelName="user.name" labelValue="${monthlySalary.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年：</label>
			<div class="controls">
				<form:input path="year" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">月：</label>
			<div class="controls">
				<form:input path="month" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付：</label>
			<div class="controls">
				<form:radiobuttons path="paid" items="${fns:getDictList('monthly_salary_paid')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">月度薪资条目：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>名称</th>
								<th>金额</th>
								<th>类型</th>
								<shiro:hasPermission name="salary:monthlySalary:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="monthlySalaryItemList">
						</tbody>
						<shiro:hasPermission name="salary:monthlySalary:edit"><tfoot>
							<tr><td colspan="5"><a href="javascript:" onclick="addRow('#monthlySalaryItemList', monthlySalaryItemRowIdx, monthlySalaryItemTpl);monthlySalaryItemRowIdx = monthlySalaryItemRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="monthlySalaryItemTpl">//<!--
						<tr id="monthlySalaryItemList{{idx}}">
							<td class="hide">
								<input id="monthlySalaryItemList{{idx}}_id" name="monthlySalaryItemList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="monthlySalaryItemList{{idx}}_delFlag" name="monthlySalaryItemList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="monthlySalaryItemList{{idx}}_name" name="monthlySalaryItemList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="50" class="input-small required"/>
							</td>
							<td>
								<input id="monthlySalaryItemList{{idx}}_amount" name="monthlySalaryItemList[{{idx}}].amount" type="text" value="{{row.amount}}" class="input-small required number"/>
							</td>
							<td>
								<c:forEach items="${fns:getDictList('monthly_salary_item_coefficient')}" var="dict" varStatus="dictStatus">
									<span><input id="monthlySalaryItemList{{idx}}_coefficient${dictStatus.index}" name="monthlySalaryItemList[{{idx}}].coefficient" type="radio" value="${dict.value}" data-value="{{row.coefficient}}"><label for="monthlySalaryItemList{{idx}}_coefficient${dictStatus.index}">${dict.label}</label></span>
								</c:forEach>
							</td>
							<shiro:hasPermission name="salary:monthlySalary:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#monthlySalaryItemList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var monthlySalaryItemRowIdx = 0, monthlySalaryItemTpl = $("#monthlySalaryItemTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(monthlySalary.monthlySalaryItemList)};
							for (var i=0; i<data.length; i++){
								addRow('#monthlySalaryItemList', monthlySalaryItemRowIdx, monthlySalaryItemTpl, data[i]);
								monthlySalaryItemRowIdx = monthlySalaryItemRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="salary:monthlySalary:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>