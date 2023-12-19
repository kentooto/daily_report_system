<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<fmt:parseDate value="${reply.replyDate}" pattern="yyyy-MM-dd" var="replyDay" type="date" />
<label for="${AttributeConst.REPLY_DATE.getValue()}">日付</label><br />
<input type="date" name="${AttributeConst.REP_DATE.getValue()}" id="${AttributeConst.REPLY_DATE.getValue()}" value="<fmt:formatDate value='${replyDay}' pattern='yyyy-MM-dd' />" />
<br /><br />

<label>氏名</label><br />
<c:out value="${sessionScope.login_employee.name}" />
<br /><br />

<label for="${AttributeConst.REPLY_CONTENT.getValue()}">内容</label><br />
<textarea  name="${AttributeConst.REPLY_CONTENT.getValue()}" id="${AttributeConst.REPLY_CONTENT.getValue()}" rows="10" cols="50">${reply.content}</textarea>
<br /><br />
<input type="hidden" name="${AttributeConst.REPLY_ID.getValue()}" value="${reply.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>