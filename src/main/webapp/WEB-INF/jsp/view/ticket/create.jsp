<spring:message code="createTicketPage" var="createTicketPage"/>
<template:basic htmlTitle="${createTicketPage}" bodyTitle="${createTicketPage}">
    <%--@elvariable id="ticketForm" type="com.saber.site.model.TicketForm"--%>
    <form:form method="post" modelAttribute="ticketForm" enctype="multipart/form-data">
        <ul>
            <li><form:errors path="subject"/></li>
            <li><form:errors path="body"/></li>
            <li><form:errors path="attachments"/></li>
        </ul>
        <form:label path="subject" cssClass="left">
            <spring:message code="field.ticketForm.subject"/>
        </form:label>
        <form:input path="subject" cssClass="right"/><br/><br/>
        <form:label path="attachments" cssClass="left">
            <spring:message code="field.ticketForm.attachments"/>
        </form:label>
        <input type="file" name="attachments" id="attachments" class="right" multiple="multiple"><br/><br/>

        <form:label path="body" cssClass="clear">
            <spring:message code="field.ticketForm.body"/>
        </form:label><br/>
        <form:textarea path="body" rows="8" cols="30"/><br/><br/>
        <input type="submit" value="Create Ticket"/>
    </form:form>
</template:basic>