<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>love</title>
    <link href="favicon.ico" rel="shortcut icon" />
    <link href="../boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script>
        function changeImage(){
            var Img = document.getElementById("imgVcode");
            Img.src = "${pageContext.request.contextPath }/admin/creatImage?xx="+Math.random();
        }
       $(function () {
           <%--$("#imgVcode").click(function () {--%>
           <%--    $("#imgVcode").src="${pageContext.request.contextPath}/admin/creatImage?xx="+Math.random();--%>
           <%--})--%>
            $("#log").click(function () {
                $.get(
                    "${pageContext.request.contextPath}/admin/adminLogin",
                    "name="+$("#name").val()+
                    "&password="+$("#password").val()+
                    "&code="+$("#code").val(),
                    function (message) {
                        if (message=="登陆成功"){
                            location.href="${pageContext.request.contextPath}/jsp/main.jsp"
                        }else{
                            $("#msg").html("<font color='red'>"+message+"</font>");
                        }
                    },
                    "json"
                )
            });
       })
    </script>
</head>
<%--<body style=" background: url(../img/timg.jpg); background-size: 100%;">--%>


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post" >
        <div class="modal-body" id = "model-body">
            <div class="form-group">
                <input id="name" type="text" class="form-control"placeholder="用户名" autocomplete="off" name="username">
            </div>
            <div class="form-group">
                <input id="password" type="password" class="form-control" placeholder="密码" autocomplete="off" name="password">
            </div>
            <div class="form-group">
                <input id="code" type="text" class="form-control"placeholder="验证码" autocomplete="off" name="code">
            </div>

            <div class="row">
                <div class="col-sm-6">
                     <div class="form-group">
                         <a  href="javascript:void(0);">
                            <img id="imgVcode" src="${pageContext.request.contextPath}/admin/creatImage" alt="" style="width: 100%" onClick="changeImage()">
                         </a>
                    </div>
                </div>
            </div>

            <span id="msg"></span>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" class="btn btn-primary form-control" id="log">登录</button>
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-default form-control">注册</button>
            </div>

        </div>
        </form>
    </div>
</div>
</body>
</html>
