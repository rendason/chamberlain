<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>采购管理</title>
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
		<li><a href="${ctx}/trade/purchase/">采购列表</a></li>
		<li class="active"><a href="${ctx}/trade/purchase/form?id=${purchase.id}">采购<shiro:hasPermission name="trade:purchase:edit">${not empty purchase.id?'查看':'添加'}</shiro:hasPermission><shiro:lacksPermission name="trade:purchase:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="purchase" action="${ctx}/trade/purchase/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">采购员：</label>
			<div class="controls">
				<sys:treeselect id="user" name="user.id" value="${purchase.user.id}" labelName="user.name" labelValue="${purchase.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true" disabled="${not empty purchase.id?'disabled':''}"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:radiobuttons path="type" items="${fns:getDictList('purchase_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" disabled="${not empty purchase.id?'true':'false'}"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">供货商：</label>
			<div class="controls">
				<form:input path="seller" htmlEscape="false" maxlength="50" class="input-xlarge required" readonly="${not empty purchase.id?'true':'false'}"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付方式：</label>
			<div class="controls">
				<form:select path="payment.id" class="input-xlarge required" disabled="${not empty purchase.id?'true':'false'}">
                    <form:option value="" label=""/>
                    <form:options items="${payments}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                </form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge" readonly="${not empty purchase.id?'true':'false'}"/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">采购项目：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>名称</th>
								<th>数量</th>
								<th>单位</th>
								<th>价格</th>
								<th>备注信息</th>
								<shiro:hasPermission name="trade:purchase:edit"><c:if test="${empty purchase.id?'true':'false'}"><th width="10">&nbsp;</th></c:if></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="purchaseItemList">
						</tbody>
						<shiro:hasPermission name="trade:purchase:edit"><c:if test="${empty purchase.id}"><tfoot>
							<tr><td colspan="7"><a href="javascript:" onclick="addRow('#purchaseItemList', purchaseItemRowIdx, purchaseItemTpl);purchaseItemRowIdx = purchaseItemRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></c:if></shiro:hasPermission>
					</table>
					<script type="text/template" id="purchaseItemTpl">//<!--
						<tr id="purchaseItemList{{idx}}">
							<td class="hide">
								<input id="purchaseItemList{{idx}}_id" name="purchaseItemList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="purchaseItemList{{idx}}_delFlag" name="purchaseItemList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="purchaseItemList{{idx}}_name" name="purchaseItemList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="50" class="input-small required" ${not empty purchase.id?'disabled':''}/>
							</td>
							<td>
								<input id="purchaseItemList{{idx}}_quantity" name="purchaseItemList[{{idx}}].quantity" type="text" value="{{row.quantity}}" maxlength="11" class="input-small required digits" ${not empty purchase.id?'disabled':''}/>
							</td>
							<td>
								<input id="purchaseItemList{{idx}}_unit" name="purchaseItemList[{{idx}}].unit" type="text" value="{{row.unit}}" maxlength="20" class="input-small required" ${not empty purchase.id?'disabled':''}/>
							</td>
							<td>
								<input id="purchaseItemList{{idx}}_price" name="purchaseItemList[{{idx}}].price" type="text" value="{{row.price}}" class="input-small required number" ${not empty purchase.id?'disabled':''}/>
							</td>
							<td>
								<input id="purchaseItemList{{idx}}_remarks" name="purchaseItemList[{{idx}}].remarks" type="text" value="{{row.remarks}}" maxlength="255" class="input-small" ${not empty purchase.id?'disabled':''}/>
							</td>
							<shiro:hasPermission name="trade:purchase:edit"><c:if test="${empty purchase.id}"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#purchaseItemList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></c:if></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var purchaseItemRowIdx = 0, purchaseItemTpl = $("#purchaseItemTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(purchase.purchaseItemList)};
							for (var i=0; i<data.length; i++){
								addRow('#purchaseItemList', purchaseItemRowIdx, purchaseItemTpl, data[i]);
								purchaseItemRowIdx = purchaseItemRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="trade:purchase:edit"><c:if test="${empty purchase.id}"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</c:if></shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>