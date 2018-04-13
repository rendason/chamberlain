<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>销售管理</title>
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

		function changeInventory(e) {
		    var select = $(e);
		    var idPrefix = select.attr("id").replace("name", "");
		    var option = select.find("option:selected");
		    $("#" + idPrefix + "quantity").attr("max", option.attr("data-quantity"));
		    $("#" + idPrefix + "quantity").val("1");
		    $("#" + idPrefix + "unit").val(option.attr("data-unit"));
		    $("#" + idPrefix + "price").val(option.attr("data-price"));
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/trade/sale/">销售列表</a></li>
		<li class="active"><a href="${ctx}/trade/sale/form?id=${sale.id}">销售<shiro:hasPermission name="trade:sale:edit">${not empty sale.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="trade:sale:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sale" action="${ctx}/trade/sale/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">销售员：</label>
			<div class="controls">
				<sys:treeselect id="user" name="user.id" value="${sale.user.id}" labelName="user.name" labelValue="${sale.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true" disabled="disabled"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">会员：</label>
			<div class="controls">
				<form:select path="member.id" class="input-xlarge">
                    <form:option value="" label=""/>
                    <form:options items="${members}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                </form:select>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">收款方式：</label>
			<div class="controls">
				<form:select path="receipt.id" class="input-xlarge required" disabled="${not empty sale.id?'true':'false'}">
                    <form:option value="" label=""/>
                    <form:options items="${receipts}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                </form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">折扣(%)：</label>
			<div class="controls">
				<form:input path="discount" htmlEscape="false" class="input-xlarge  number" readonly="${not empty sale.id?'true':'false'}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">减免：</label>
			<div class="controls">
				<form:input path="exempt" htmlEscape="false" class="input-xlarge  number" readonly="${not empty sale.id?'true':'false'}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge" readonly="${not empty sale.id?'true':'false'}"/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">销售条目：</label>
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
								<shiro:hasPermission name="trade:sale:edit"><c:if test="${empty sale.id}"><th width="10">&nbsp;</th></c:if></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="saleItemList">
						</tbody>
						<shiro:hasPermission name="trade:sale:edit"><c:if test="${empty sale.id}"><tfoot>
							<tr><td colspan="7"><a href="javascript:" onclick="addRow('#saleItemList', saleItemRowIdx, saleItemTpl);saleItemRowIdx = saleItemRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></c:if></shiro:hasPermission>
					</table>
					<script type="text/template" id="saleItemTpl">//<!--
						<tr id="saleItemList{{idx}}">
							<td class="hide">
								<input id="saleItemList{{idx}}_id" name="saleItemList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="saleItemList{{idx}}_delFlag" name="saleItemList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<select id="saleItemList{{idx}}_name" name="saleItemList[{{idx}}].name" data-value="{{row.name}}" class="input-small required" onchange="changeInventory(this)" ${not empty sale.id?'disabled':''}>
                                    <option value=""></option>
                                    <c:forEach items="${inventories}" var="item">
                                        <option value="${item.name}" data-price="${item.sellingPrice}" data-quantity="${item.quantity}" data-unit="${item.unit}">${item.name}-${item.sellingPrice}元/${item.unit}</option>
                                    </c:forEach>
                                </select>
							</td>
							<td>
								<input id="saleItemList{{idx}}_quantity" name="saleItemList[{{idx}}].quantity" type="number" value="{{row.quantity}}" min="1" class="input-small required digits" ${not empty sale.id?'disabled':''}/>
							</td>
							<td>
								<input id="saleItemList{{idx}}_unit" name="saleItemList[{{idx}}].unit" type="text" value="{{row.unit}}" maxlength="20" class="input-small required" ${not empty sale.id?'disabled':'readonly'}/>
							</td>
							<td>
								<input id="saleItemList{{idx}}_price" name="saleItemList[{{idx}}].price" type="text" value="{{row.price}}" class="input-small required number" ${not empty sale.id?'disabled':'readonly'}/>
							</td>
							<td>
								<input id="saleItemList{{idx}}_remarks" name="saleItemList[{{idx}}].remarks" type="text" value="{{row.remarks}}" maxlength="255" class="input-small" ${not empty sale.id?'disabled':''}/>
							</td>
							<shiro:hasPermission name="trade:sale:edit"><c:if test="${empty sale.id}"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#saleItemList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></c:if></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var saleItemRowIdx = 0, saleItemTpl = $("#saleItemTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(sale.saleItemList)};
							for (var i=0; i<data.length; i++){
								addRow('#saleItemList', saleItemRowIdx, saleItemTpl, data[i]);
								saleItemRowIdx = saleItemRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="trade:sale:edit"><c:if test="${empty sale.id}"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</c:if></shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>