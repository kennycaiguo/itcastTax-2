<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>系统异常信息</title>
  </head>
  
  <body>
  	<img src="<%=request.getContextPath() %>/images/common/error.jpg">
    <br>
    <s:if test="exception.errorMsg != '' && exception.errorMsg != null">
    	<s:property value="exception.errorMsg"/>
    </s:if>
    <s:else>
    	操作失败！<s:property value="exception.message"/>
    </s:else>
  </body>
</html>