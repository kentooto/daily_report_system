<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>

<c:set var="actRep" value="${ForwardConst.ACT_REPL.getValue()}" />
<c:set var="actTel" value="${ForwardConst.ACT_TEL.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>電話メモ 詳細ページ</h2>

        <table>
            <tbody>
                <tr>
                    <th>氏名</th>
                    <td><c:out value="${tel.employee.name}" /></td>
                </tr>
                <tr>
                    <th>日付</th>
                    <td class="tel_memo_name"><c:out
                                value="${tel.fmtTel_date}" /></td>
                </tr>
                <tr>
                    <th>内容</th>
                    <td>
                            <c:out value="${tel.content}" />
                      </td>
                </tr>
                 <tr>
                    <th>登録日時</th>
                    <fmt:parseDate value="${tel.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <td><fmt:formatDate value="${createDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
                <tr>
                    <th>更新日時</th>
                    <fmt:parseDate value="${tel.updatedAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                    <td><fmt:formatDate value="${updateDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>



                <c:forEach var="reply" items="${replys}" varStatus="status">
                    <fmt:parseDate value="${reply.replyDate}" pattern="yyyy-MM-dd"
                        var="replyDay" type="date" />

                    <tr class="row${status.count % 2}">
                        <td class="reply_name"><c:out value="${reply.employee.name}" /></td>
                        <td class="reply_date"><fmt:formatDate value='${replyDay}'
                                pattern='yyyy-MM-dd' /></td>
                        <td class="reply_title">${reply.title}</td>
                        <td class="reply_action"><a
                            href="<c:url value='?action=${actRepl}&command=${commShow}&id=${reply.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>

        <c:if test="${sessionScope.login_employee.id == reply.employee.id}">
            <p>
                <a
                    href="<c:url value='?action=${actRepl}&command=${commEdt}&id=${reply.id}' />">このリプライを編集する</a>
            </p>
        </c:if>

        <p>
            <a href="<c:url value='?action=${actTel}&command=${commIdx}' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>
