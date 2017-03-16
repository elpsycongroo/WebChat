<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>WebChat | 用户信息</title>
    <jsp:include page="include/commonfile.jsp"/>
</head>
<body>
<jsp:include page="include/header.jsp"/>
<div class="am-cf admin-main">
    <jsp:include page="include/sidebar.jsp"/>

    <!-- content start -->
    <div class="admin-content">
        <div class="am-cf am-padding">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">用户信息</strong> /
                <small>info</small>
            </div>
        </div>
        <div class="am-tabs am-margin" data-am-tabs>
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">用户信息</a></li>
            </ul>
            <div class="am-tabs-bd">
                <div class="am-tab-panel am-fade am-in am-active" id="tab1">
                    <div class="am-g">
                        <c:set value="${userview}" var="userview"/>
                        <div class="am-u-md-3"><b>昵称:</b></div>
                        <div class="am-u-md-3">
                            ${userview.nickname} ${requestScope.error}
                        </div>
                        <div class="am-u-md-6" style="float: right">
                            <c:choose>
                                <c:when test="${userview.profilehead == null || userview.profilehead == ''}">
                                    ${requestScope.error}
                                </c:when>
                                <c:otherwise>
                                    <img class="am-circle" src="${ctx}/${userview.profilehead}" width="140" height="140"
                                         alt="${userview.nickname}"/>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="am-u-md-3"><b>性别:</b></div>
                        <div class="am-u-md-3">
                            <c:if test="${userview.sex == null || userview.sex == ''}">未知</c:if>
                            <c:if test="${userview.sex != null && userview.sex != ''}">
                                <c:if test="${userview.sex == 1}">男</c:if>
                                <c:if test="${userview.sex == 0}">女</c:if>
                                <c:if test="${userview.sex == -1}">保密</c:if>
                            </c:if>
                        </div>
                        <div class="am-u-md-3"><b>年龄:</b></div>
                        <div class="am-u-md-3">
                            <c:if test="${userview.age == null || userview.age == ''}">未知</c:if>
                            <c:if test="${userview.age != null && userview.age != ''}">${userview.age}</c:if>
                        </div>
                        <div class="am-u-md-3"><b>简介:</b></div>
                        <div class="am-u-md-3">
                            <c:if test="${userview.profile == null || userview.profile == ''}">
                                这个人很懒,什么都没有留下!
                            </c:if>
                            <c:if test="${userview.profile != null && userview.profile != ''}">
                                ${userview.profile}
                            </c:if>
                        </div>
                        <div class="am-u-md-3"><b>注册时间</b></div>
                        <div class="am-u-md-3">${userview.firsttime}${requestScope.error}</div>
                        <div class="am-u-md-3"><b>最后登录</b></div>
                        <div class="am-u-md-3">${userview.lasttime}${requestScope.error}</div>
                    </div>
                </div>
            </div>
        </div>
        <!-- content end -->
    </div>
    <a href="#" class="am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}">
        <span class="am-icon-btn am-icon-th-list"></span>
    </a>
    <jsp:include page="include/footer.jsp"/>
    <script>
        if("${message}"){
            layer.msg('${message}', {
                offset: 0
            });
        }
        if ("${error}") {
            layer.msg('${error}', {
                offset: 0,
                shift: 6
            });
        }
    </script>
</body>
</html>
