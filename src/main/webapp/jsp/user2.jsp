<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--轮播图模块--%>
<script type="text/javascript">
    $(function () {
        //初始化jqGrid插件
        $("#queryPage").jqGrid({
            url:"${pageContext.request.contextPath}/user/queryAllUser",
            datatype:"json",
            colNames:['ID', '电话','密码','个性签名', '法号', '性别', '所在地', '头像','注册时间','最后登录时间','状态'],
            colModel:[
                {name: 'id', align: "center", hidden: true},
                {name: 'phone', align: "center", editable: true},
                {name: 'password', align: "center", editable: true},
                {name: 'sign', align: "center", editable: true},
                {name: 'nickName', align: "center", editable: true},
                {
                    name: 'sex',
                    align: "center",
                    formatter: function (data) {
                        if (data == "男") {
                            return "男";
                        } else return "女";
                    },
                    editable: true,
                    editrules: {required: true},
                    edittype: "select",
                    editoptions: {value: "男:男;女:女"}
                },
                {name: 'location', align: "center", editable: true},
                {
                    name: 'photo', align: "center", formatter: function (data) {
                        return "<img style='width: 180px;height: 80px' src='"+"${pageContext.request.contextPath}/"+ data + "'>"
                    }, editable: true, edittype: "file", editoptions: {enctype: "multipart/form-data"}
                },
                {
                    name: 'registDate',
                    align: "center",
                },
                {name: 'lastLogin', align: "center"},
                {
                    name: 'status',
                    align: "center",
                    formatter: function (data) {
                        if (data == "1") {
                            return "展示";
                        } else return "冻结";
                    },
                    editable: true,
                    editrules: {required: true},
                    edittype: "select",
                    editoptions: {value: "1:展示;2:冻结"}
                }
            ],
            autowidth:true,
            viewrecords:true,
            pager:"#pager",
            rowNum:5,
            page:1,
            height:"40%",
            styleUI:"Bootstrap",
            multiselect:true,
            editurl:"${pageContext.request.contextPath}/user/save"
        }).jqGrid("navGrid","#pager",{add:true},
            {
                closeAfterEdit: true,
                afterSubmit:function (response,postData) {
                    var userId = response.responseJSON.userId;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/user/uploadUser",
                        type:"post",
                        datatype: "json",
                        data:{userId:userId},
                        fileElementId:"photo",
                        success:function (data) {
                            $("#queryPage").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            },
            //添加之后上传文件
            {
                closeAfterAdd:true,
                afterSubmit:function (response,postData) {
                    var userId = response.responseJSON.userId;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/user/uploadUser",
                        type:"post",
                        datatype: "json",
                        data:{userId:userId},
                        fileElementId:"photo",
                        success:function (data) {
                            $("#queryPage").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            })
    });
</script>
<div>
    <table id="queryPage"></table>
    <%--定义分页小工具栏--%>
    <div id="pager" style="height:60px"></div>
</div>