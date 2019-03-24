<%--@elvariable id="ticketId" type="java.lang.String"--%>
<template:basic htmlTitle="View Tickt" bodyTitle="Ticket # ${ticketId}">
    <%--@elvariable id="ticket" type="com.saber.site.model.MyTicket"--%>
    <c:choose>
        <c:when test="${ticket==null}">
            <b><i><spring:message code="viewTicketPage.errorMessage"/></i></b>
        </c:when>
        <c:otherwise>
            <%--@elvariable id="comments" type="org.springframework.data.domain.Page<com.saber.site.model.TicketComment>"--%>
            <%--@elvariable id="comment" type="com.saber.site.model.TicketComment"--%>

            <table>
                <tr>
                    <th>Subject</th>
                    <th>DateCreated</th>
                    <th>Attachments</th>
                    <th>Comment</th>
                </tr>
                <tr>
                    <td>${ticket.subject}</td>
                    <td><fmt:formatDate value="${ticket.dateCreated}" pattern="yyyy/MM/dd HH:mm:ss"/> </td>
                    <td>
                        <c:forEach items="${ticket.attachments}" var="attachment">
                            <a href="<c:url value="/ticket/download/${attachment.attachmentId}"/> ">${attachment.attachmentName}</a>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forTokens items="${ticket.body}" delims="." var="msg">
                            ${msg}--<br/>
                        </c:forTokens>
                    </td>
                </tr>
            </table><br/><br/>


            <h3>Comments</h3>
            <c:choose>
                <c:when test="${comments.totalElements==0}">
                    <b><i>There Are No Comment For This Comment</i></b>
                </c:when>
                <c:otherwise>
                    <c:forEach begin="0" end="${comments.totalPages}" var="i">
                        ${i} --- &nbsp;
                    </c:forEach><br/>
                    <table>
                        <tr>
                            <th>Comment</th>
                            <th>DateCreated</th>
                            <th>Attachments</th>
                        </tr>
                        <c:forEach items="${comments.content}" var="comment">
                            <tr>
                                <td><c:forTokens items="${comment.body}" delims="." var="msg">
                                    ${msg}--<br/>
                                </c:forTokens>
                                </td>
                                <td>
                                    <fmt:formatDate value="${comment.dateCreated}" pattern="yyyy/MM/dd HH:mm:ss"/>
                                </td>
                                <td>
                                    <c:forEach items="${comment.attachments}" var="attachment">
                                        <a href="<c:url value="/ticket/download/${attachment.attachmentId}"/> ">${attachment.attachmentName}</a>
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:forEach>
                    </table><br/>
               </c:otherwise>
            </c:choose>
            <%--@elvariable id="ticketCommentForm" type="com.saber.site.model.TicketCommentForm"--%>
            <%--@elvariable id="errors" type="java.util.List<org.springframework.validation.ObjectError>"--%>
            <h3>Add Comment</h3>
            <c:url value="/ticket/addComment" var="addComment"/>
            <c:if test="${errors!=null}">
                <ul>
                    <c:forEach items="${errors}" var="error">
                        <li>${error.defaultMessage}</li>
                    </c:forEach>
                </ul>

            </c:if>

            <form:form method="post" modelAttribute="ticketCommentForm" action="${addComment}" enctype="multipart/form-data">
                <input type="hidden" name="ticketId" value="${ticketId}">

                <form:label path="attachments" cssClass="left">
                    <spring:message code="field.ticketCommentForm.attachments"/>
                </form:label>
                <input type="file" name="attachments" class="right" id="attachments" multiple="multiple"><br/><br/>

                <form:label path="body" cssClass="clear">
                    <spring:message code="field.ticketCommentForm.body"/>
                </form:label><br/>
                <form:textarea path="body" rows="8" cols="30"/><br/><br/>

                <input type="submit" value="ADD Comment" class="clear">
            </form:form>
        </c:otherwise>
    </c:choose>

</template:basic>