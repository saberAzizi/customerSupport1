<%@tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@attribute name="htmlTitle" type="java.lang.String" required="true" rtexprvalue="true" %>
<%@attribute name="bodyTitle" type="java.lang.String" required="true" rtexprvalue="true" %>
<%@attribute name="navigationBar" fragment="true" required="false" %>
<%@include file="/WEB-INF/jsp/base.jspf" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${htmlTitle}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/main.css">
</head>
<body>
<div class="navigationBar">
    <header>
        <h3>The CRM Project</h3>
    </header>
    <jsp:invoke fragment="navigationBar"/>
</div>
<div class="content">
    <h2>${bodyTitle}</h2>
    <jsp:doBody/>
</div>
</body>
</html>