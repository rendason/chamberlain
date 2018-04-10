<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>标准薪资管理</title>
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
		<li><a href="${ctx}/salary/baseSalary/">标准薪资列表</a></li>
		<li class="active"><a href="${ctx}/salary/baseSalary/form?id=${baseSalary.id}">标准薪资<shiro:hasPermission name="salary:baseSalary:edit">${not empty baseSalary.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="salary:baseSalary:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="baseSalary" action="${ctx}/salary/baseSalary/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">用户：</label>
			<div class="controls">
				<sys:treeselect id="user" name="user.id" value="${baseSalary.user.id}" labelName="user.name" labelValue="${baseSalary.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">标准薪资条目：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>名称</th>
								<th>金额</th>
								<th>备注信息</th>
								<shiro:hasPermission name="salary:baseSalary:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="baseSalaryItemList">
						</tbody>
						<shiro:hasPermission name="salary:baseSalary:edit"><tfoot>
							<tr><td colspan="5"><a href="javascript:" onclick="addRow('#baseSalaryItemList', baseSalaryItemRowIdx, baseSalaryItemTpl);baseSalaryItemRowIdx = baseSalaryItemRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="baseSalaryItemTpl">//<!--
						<tr id="baseSalaryItemList{{idx}}">
							<td class="hide">
								<input id="baseSalaryItemList{{idx}}_id" name="baseSalaryItemList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="baseSalaryItemList{{idx}}_delFlag" name="baseSalaryItemList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="baseSalaryItemList{{idx}}_name" name="baseSalaryItemList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="50" class="input-small required"/>
							</td>
							<td>
								<input id="baseSalaryItemList{{idx}}_amount" name="baseSalaryItemList[{{idx}}].amount" type="text" value="{{row.amount}}" class="input-small required number"/>
							</td>
							<td>
								<input id="baseSalaryItemList{{idx}}_remarks" name="baseSalaryItemList[{{idx}}].remarks" type="text" value="{{row.remarks}}" maxlength="255" class="input-small "/>
							</td>
							<shiro:hasPermission name="salary:baseSalary:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#baseSalaryItemList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var baseSalaryItemRowIdx = 0, baseSalaryItemTpl = $("#baseSalaryItemTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(baseSalary.baseSalaryItemList)};
							for (var i=0; i<data.length; i++){
								addRow('#baseSalaryItemList', baseSalaryItemRowIdx, baseSalaryItemTpl, data[i]);
								baseSalaryItemRowIdx = baseSalaryItemRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="salary:baseSalary:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>