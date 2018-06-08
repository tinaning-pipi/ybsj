<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <%@include file="/WEB-INF/jsp/common.jsp" %>
    <link rel="stylesheet" href="${ctx}/support/css/userManageFrame.css">
    <script src="${ctx}/support/js/userManageFrame.js"></script>
    <script src="${ctx}/support/js/jquery-3.1.1.js"></script>
    <title>供应商样本预约管理系统</title>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo"><img alt="hill" src="${ctx}/support/img/hill2.png" width="300" height="30"
                                     style="margin-top: 6px"></div>
        <ul class="layui-nav layui-layout-left"></ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="#" id="edituser">
                    <img src="${ctx}/support/img/user.png" class="layui-nav-img" ><span id="showuid">${userId}</span>
                </a>
            </li>
            <li class="layui-nav-item"><a alt="${userId}" id="logon" href="#">退出系统</a>
            </li>
        </ul>
    </div>
    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <ul class="layui-nav layui-nav-tree" id="urls">
                <c:forEach items="#{func}" var="item">
                    <li class="layui-nav-item"><a href="#" alt="${ctx}${item.fUrl}">${item.fName}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="layui-body">
        <iframe class="show_page" id="show_page" name="show_page" frameborder="0"></iframe>
    </div>
    <div class="layui-footer">
        ® www.hill.org.cn
    </div>
</div>
</body>
<%--用户修改密码--%>
<div id="changepassword" hidden="hidden">
    <div class="uform" id="uform">
        <form class="layui-form" lay-filter="uform" >
            <div class="layui-form-item">
                    <input type="hidden" id="userid" name="userEntity.userid"
                           autocomplete="off" value="${userId}" class="layui-input"/>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">原密码</label>
                <div class="layui-input-inline">
                    <input type="password" id="oldupwd"  required lay-verify="required"
                           placeholder="请输入密码" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">新密码</label>
                <div class="layui-input-inline">
                    <input type="password" id="newupwd"  required lay-verify="required"
                           placeholder="请输入密码" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">确认密码</label>
                <div class="layui-input-inline">
                    <input type="password" id="confirmupwd" name="userEntity.upwd" required lay-verify="required"
                           placeholder="请输入密码" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" id="uBtn" lay-submit lay-filter="uBtn">确定修改</button>
                    <button type="button" id="uCan" class="layui-btn layui-btn-primary">取消</button>
                </div>
            </div>
        </form>
    </div>
</div>
</html>