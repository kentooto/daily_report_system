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
<fmt:parseDate value="${tel_memo.tel_memo_date}" pattern="yyyy-MM-dd" var="tel_memoDay" type="date" />
<label for="${AttributeConst.TEL_DATE.getValue()}">日付</label><br />
<input type="datetime-local" name="${AttributeConst.TEL_DATE.getValue()}" id="${AttributeConst.TEL_DATE.getValue()}" value="<fmt:formatDate value='${tel_memoDay}' pattern='yyyy-MM-dd' />" />
<br /><br />

<label>氏名</label><br />
<c:out value="${sessionScope.login_employee.name}" />
<br /><br />

<label for="${AttributeConst.TEL_TITLE.getValue()}">タイトル</label><br />
<input type="text" name="${AttributeConst.TEL_TITLE.getValue()}" id="${AttributeConst.TEL_TITLE.getValue()}" value="${tel_memo.title}" />
<br /><br />

<label for="${AttributeConst.TEL_CUSTOMER.getValue()}">客先</label><br />
<input type="text" name="${AttributeConst.TEL_CUSTOMER.getValue()}" id="${AttributeConst.TEL_CUSTOMER.getValue()}" value="${customer}" />
<br /><br />


<label for="${AttributeConst.TEL_ATE_ID.getValue()}">宛先</label><br />
<input type="text" name="${AttributeConst.TEL_ATE_ID.getValue()}" id="${AttributeConst.TEL_ATE_ID.getValue()}" value="${employee.code}" />
<br /><br />

<label for="${AttributeConst.TEL_CONTENT.getValue()}">内容</label><br />
<textarea  name="${AttributeConst.TEL_CONTENT.getValue()}" id="${AttributeConst.TEL_CONTENT.getValue()}" rows="10" cols="50">${tel_memo.content}</textarea>
<br /><br />

<input type="hidden" name="${AttributeConst.TEL_ID.getValue()}" value="${tel.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>