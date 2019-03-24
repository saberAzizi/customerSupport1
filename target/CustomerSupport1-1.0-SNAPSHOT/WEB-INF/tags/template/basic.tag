<%@tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@attribute name="htmlTitle" type="java.lang.String"  required="true" rtexprvalue="true" %>
<%@attribute name="bodyTitle" type="java.lang.String"  required="true" rtexprvalue="true" %>
<%@include file="/WEB-INF/jsp/base.jspf"%>
<template:main htmlTitle="${htmlTitle}" bodyTitle="${bodyTitle}">
    <jsp:attribute name="navigationBar">
        <nav>
            <ul>
                <li><a href="">List Tickets</a></li>
                <li><a href="">Search Ticket</a></li>
                <li><a href="">Create Ticket</a></li>
                <li><a href="">logout</a></li>
            </ul>
        </nav>
    </jsp:attribute>
    <jsp:body>
        <jsp:doBody/>
    </jsp:body>
</template:main>