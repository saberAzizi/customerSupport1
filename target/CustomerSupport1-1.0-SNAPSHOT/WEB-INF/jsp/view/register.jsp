<spring:message code="registerPage.title" var="registerPage"/>
<template:logged htmlTitle="${registerPage}" bodyTitle="${registerPage}">
    <%--@elvariable id="registerForm" type="com.saber.site.model.RegisterForm"--%>
    [<a href="<c:url value="/login"/>">Login Page</a>]<br/>
    <div >
        <form:form method="post" modelAttribute="registerForm">
            <form:label path="username" cssClass="left">
                <spring:message code="field.registerForm.username"/> &nbsp;
                <form:errors path="username" cssClass="error"/>
            </form:label>
            <form:input path="username" cssClass="right"/><br/><br/>
            <form:label path="password" cssClass="left">
                <spring:message code="field.registerForm.password"/> &nbsp;
                <form:errors path="password" cssClass="error"/>
            </form:label>
            <form:password path="password" cssClass="right"/><br/><br/>
            <input type="submit" value="Register" class="clear">
        </form:form>
    </div>
</template:logged>