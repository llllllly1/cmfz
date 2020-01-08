<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--轮播图模块--%>
<script type="text/javascript">
    $(function () {
        //初始化jqGrid插件
        $("#queryPage").jqGrid({
            url:"${pageContext.request.contextPath}/banner/queryByPage",
            datatype:"json",
            colNames:["ID","标题","图片","超链接","创建日期","描述","状态"],
            colModel:[
                {name:"id"},
                {name:"title",editable: true},
                {name:"url",editable: true,formatter:function (data) {
                        return "<img  style='width:100px;height:50px'  src='"+"${pageContext.request.contextPath}"+data+"'/>";
                    },edittype:"file",editoptions:{enctype:"multipart/form-data"}},
                {name:"href",editable: true},
                {name:"createDate"},
                {name:"description",editable: true},
                {name:"status",editable: true,formatter:function (data) {
                        if(data==1){
                            return "展示";
                        }else{
                            return "冻结";
                        }
                    },editable:true,edittype:"select",editoptions:{value:"1:展示;2:冻结"}},
            ],
            autowidth:true,
            viewrecords:true,
            pager:"#pager",
            rowNum:5,
            page:1,
            height:"40%",
            styleUI:"Bootstrap",
            multiselect:true,
            editurl:"${pageContext.request.contextPath}/banner/save"
        }).jqGrid("navGrid","#pager",{add:true},
            {
                closeAfterEdit: true,
                afterSubmit:function (response,postData) {
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/banner/uploadBanner",
                        type:"post",
                        datatype: "json",
                        data:{bannerId:bannerId},
                        fileElementId:"url",
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
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/banner/uploadBanner",
                        type:"post",
                        datatype: "json",
                        data:{bannerId:bannerId},
                        fileElementId:"url",
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
<div>

<%--###############################################easyExcel操作#####################################################################--%>

    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">轮播图列表</a></li>
        <li role="presentation" ><a href="${pageContext.request.contextPath}/banner/exportBanner">导出轮播图信息</a></li>
        <li><a onclick="importBannerExcel()">导入轮播图Excel信息</a></li>
    </ul>

</div>

<table id="bannerList"></table>
<div id="bannerPager" style="height: 60px"></div>


<script>
    // 点击导入BannerExcel时触发事件
    function importBannerExcel() {
        $("#bannerModal").modal("show");
    }
    function bannerSub() {
        $.ajaxFileUpload({
            url: "${pageContext.request.contextPath}/banner/importBanner",
            type: "post",
            data: {
            },
            datatype: "json",
            fileElementId: "bannerFile",
            success: function (data) {
                $("#bannerModal").modal("hide");
                $("#queryPage").trigger("reloadGrid");
            }
        })

    }
</script>