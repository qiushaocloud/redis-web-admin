<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<title>集群配置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			// 设置隐藏列
            hideShowTableTd('contentTable', '${colHide}', 0);
			$("#searchForm").validate({
				submitHandler: function(form){
					loading('正在查询，请稍等...');
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
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
       /**
          * table列显示隐藏
          * @param tableId
          * @param columns table列索引 例： 0,1，2,3
          * @param type 显示隐藏列 1.显示table列 2.隐藏table列
          */
        function hideShowTableTd(tableId, columns, type) {
            var strs = new Array(); //定义一数组
            strs = columns.split(","); //字符分割
            var tableTd = "";
            for (var i = 0; i < strs.length; i++) {
                tableTd += "td:eq(" + strs[i] + "),th:eq(" + strs[i] + "),"
            }
            tableTd = tableTd.substring(0, tableTd.length - 1);
            if (type == 0) {
                $('#' + tableId + ' tr').find(tableTd).hide();
            }
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/redis/redisCluster/">集群配置列表</a></li>
		<shiro:hasPermission name="redis:redisCluster:edit"><li><a href="${ctx}/redis/redisCluster/form">添加集群配置</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="redisCluster" action="${ctx}/redis/redisCluster/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>redis主机名：</label>
				<form:input path="redisHostName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>redis端口：</label>
				<form:input path="redisPort" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-nowrap">
		<thead>
			<tr>
				<th class="sort-column id">编号</th>
				<th class="sort-column redisHostName">redis主机名</th>
				<th class="sort-column redisPort">redis端口</th>
				<th class="sort-column remarks">备注信息</th>
				<shiro:hasPermission name="redis:redisCluster:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="redisCluster">
			<tr>
				<td><a href="${ctx}/redis/redisCluster/form?id=${redisCluster.id}">
					${redisCluster.id}
				</a></td>
				<td>
					${redisCluster.redisHostName}
				</td>
				<td>
					${redisCluster.redisPort}
				</td>
				<td>
					${redisCluster.remarks}
				</td>
				<shiro:hasPermission name="redis:redisCluster:edit"><td>
    				<a href="${ctx}/redis/redisCluster/form?id=${redisCluster.id}">修改</a>
					<a href="${ctx}/redis/redisCluster/delete?id=${redisCluster.id}" onclick="return confirmx('确认要删除该集群配置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>