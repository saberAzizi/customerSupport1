<spring:message code="listPage.tickets" var="listTickets"/>
<template:basic htmlTitle="${listTickets}" bodyTitle="${listTickets}">
<%--@elvariable id="tickets" type="java.util.List<com.saber.site.model.MyTicket>"--%>
    <c:if test="${tickets!=null}">
        <c:choose>
            <c:when test="${fn:length(tickets)==0}">
                <b><i><spring:message code="listTickets.noTickets" /></i></b><br/>
            </c:when>
            <c:otherwise>
                <table>
                    <tr>
                        <th>Customer Name</th>
                        <th>Subject</th>
                        <th>Date Created</th>
                        <th>Body</th>
                    </tr>
                    <c:forEach items="${tickets}" var="ticket">
                        <tr>
                            <td>${ticket.userPrincipal.username}</td>
                            <td>
                            <a href="<c:url value="/ticket/viewTicket">
                            <c:param name="ticketId" value="${ticket.id}"/>
                        </c:url> "> ${ticket.subject}</a>
                            </td>
                            <td><fmt:formatDate value="${ticket.dateCreated}" pattern="yyyy/MM/dd HH:mm:ss"/> </td>
                            <td><c:forTokens items="${ticket.body}" delims="." var="msg">
                                ${msg}<br/>
                            </c:forTokens> </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </c:if>
</template:basic>