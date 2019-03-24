<spring:message code="loginPage.title" var="loginPage"/>
<template:logged htmlTitle="${loginPage}" bodyTitle="${loginPage}">
    <%--@elvariable id="loginForm" type="com.saber.site.model.LoginForm"--%>
    <%--@elvariable id="loginFailed" type="java.lang.Boolean"--%>
    <%--@elvariable id="successRegister" type="java.lang.Boolean"--%>
    [<a href="<c:url value="/register"/>">Register Page</a>]<br/>
    <h2><spring:message code="loginPage.welcomeMessage"/></h2>
    <c:if test="${loginFailed}">
        <b><i><spring:message code="loginPage.errorMessage"/></i></b><br/>
    </c:if>
    <c:if test="${successRegister}">
        <b><i><spring:message code="loginPage.successMessage"/></i></b><br/>
    </c:if>
    <div >
        <c:url value="/login" var="login"/>
        <form:form method="post" modelAttribute="loginForm" action="${login}">
            <form:label path="username" cssClass="left">
                <spring:message code="field.registerForm.username"/>&nbsp;
                <form:errors path="username"/>
            </form:label>
            <form:input path="username" cssClass="right"/><br/><br/>

            <form:label path="password" cssClass="left">
                <spring:message code="field.registerForm.password"/>&nbsp;
                <form:errors path="password"/>
            </form:label>
            <form:password path="password" cssClass="right"/><br/><br/>

            <input type="submit" value="Login" class="clear"/>
        </form:form>
    </div>
</template:logged>