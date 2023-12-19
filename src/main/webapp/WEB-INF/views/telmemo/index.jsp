<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actTel" value="${ForwardConst.ACT_TEL.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
       <h2>【電話メモ 一覧】</h2>
        <table id="tel_memo_list">
            <tbody>
                <tr>
                    <th class="tel_memo_name">氏名</th>
                    <th class="tel_memo_date">日付</th>
                    <th class="tel_memo_title">タイトル</th>
                    <th class="tel_memo_action">操作</th>
                </tr>
                <c:forEach var="tel_memo" items="${tels}" varStatus="status">

                    <tr class="row${status.count % 2}">
                        <td class="tel_memo_name"><c:out
                                value="${tel_memo.employee.name}" /></td>
                                <td class="tel_memo_name"><c:out
                                value="${tel_memo.fmtTel_date}" /></td>
                        <td class="tel_memo_title">${tel_memo.title}</td>
                        <td class="tel_memo_action"><a
                            href="<c:url value='?action=${actTel}&command=${commShow}&id=${tel_memo.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

         <div id="pagination">
            （全 ${tels_count} 件）<br />
            <c:forEach var="i" begin="1"
                end="${((tels_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == telpage}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a
                            href="<c:url value='?action=${actTop}&command=${commIdx}&telpage=${i}' />"><c:out
                                value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p>
                     <a href="<c:url value='?action=${actTel}&command=${commNew}' />">新規電話メモの登録</a>

        </p>

    </c:param>
</c:import>