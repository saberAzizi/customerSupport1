<template:logged htmlTitle="Error Page" bodyTitle="Error">
    <div class="errorForm">
        <%--@elvariable id="servletName" type="java.lang.String"--%>
        <%--@elvariable id="statusCode" type="java.lang.Integer"--%>
        <%--@elvariable id="throwable" type="java.lang.Throwable"--%>
        <%--@elvariable id="requestUri" type="java.lang.String"--%>
        <nav>
            <ul>
                <li>Servlet Name : ${servletName}</li>
                <li>Status Code : ${statusCode}</li>
                <li>Message : ${throwable.message}</li>
                <li>Request Uri : ${requestUri}</li>
            </ul>
        </nav>
    </div>
</template:logged>