<%@ page import="model.Account" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Order" %>
<%@ page import="model.OrderProductVariant" %>
<%@ page import="service.NumberUtils" %>
<%@ page import="config.URLConfig" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Đơn hàng</title>
    <link href="../resources/libs/datepicker/daterangepicker.css" rel="stylesheet" media="all">
    <link rel="stylesheet" href="../resources/css/user/profile.css">
    <link href="../resources/css/user/main.css" rel="stylesheet" media="all">
    <%@include file="/common/libraries.jsp" %>
</head>
<style>
    body {
        background-color: #F5F5FA;
    }

    * {
        font-size: 14px;
        font-family: Inter, Helvetica, Arial, sans-serif;
    }
</style>
<body>
<%@include file="/common/header.jsp" %>
<%--<%--%>
<%--    Account loggedInUser = (Account)session.getAttribute("account");--%>
<%--    if (loggedInUser == null) {--%>
<%--        response.sendRedirect("login.jsp");--%>
<%--        return;--%>
<%--    }--%>
<%--%>--%>

<div class="container" style="margin-bottom: 30px;">
    <div class="col">
        <%
            List<OrderProductVariant> listor = (List<OrderProductVariant>) request.getAttribute("order_Account");
            if (listor==null){
        %>
        <div style="margin-top: 30px;">
            <h2 style="color: red">Chưa có đơn hàng nào!</h2>
            <a href="${pageContext.request.contextPath}/home">
                <button class="btn btn-success">Quay lại trang chính</button>
            </a>
        </div>
        <%
            }
        %>
        <%
            List<OrderProductVariant> list = (List<OrderProductVariant>) request.getAttribute("order_Account");

            if (list != null) {
                for (OrderProductVariant o : list) {
        %>
        <div class="card" style="border-radius: 10px">
            <div class="card-body p-4">
                <div class="col-lg-12">
                    <div class="mb-1" style="justify-content: end;text-align: end">
                        <span class="black_14_400" style="color: #ee4d2d">
    <%
        int status = o.getStatus();
        if (status == 0) {
            out.print("Đã hủy");
        } else if (status == 1) {
            out.print("Đang chuẩn bị hàng");
        } else if (status == 2) {
            out.print("Đang giao");
        } else if (status == 3) {
            out.print("Đã nhận hàng");
        }
    %>
</span>

                    </div>
                    <hr style="width: 100%">
                    <div class="mt-3 mb-3 flex" style="display: flex;justify-content: space-between">
                        <div>
                            <img
                                    src="${pageContext.request.contextPath}/<%=URLConfig.URL_SAVE_IMAGE%>/<%=o.getProductVariant().getProductImages().get(0).getImage_url()%>"
                                    class="img-fluid rounded-3" alt="Shopping item"
                                    style="width: 85px;margin: 12px 7px 12px 7px;border:gainsboro solid 0.5px;padding: 1px ">
                        </div>
                        <div class="flex-1"
                             style="justify-content: space-between;padding-left: 12px;width: 80%;margin-top: 10px">
                            <div class="d-flex " style="justify-content: space-between">
                                <h6 class="text-text-primary break-work"
                                    style="background-color: white"><%=o.getProductVariant().getProduct().getName()%>
                                </h6>
                            </div>
                            <div class="mt-2 d-flex " style="justify-content: space-between">
                                <div>
                                    <!--                                            style="display: flex;justify-content: space-between;-->
                                    <div class="" style="display: flex">
                                        <div>
                                            <p class="grey_14_300" style="background-color: white">Màu sắc</p>
                                        </div>
                                        <div style="margin-left: 20px">
                                            <p class="grey_14_300"
                                               style="background-color: white;"><%=o.getProductVariant().getColor().getName()%>
                                            </p>
                                        </div>
                                    </div>
                                    <div class="" style="display: flex">
                                        <div><p class="grey_14_300" style="background-color: white">Số lượng</p>
                                        </div>
                                        <div style="margin-left: 20px">
                                            <p class="grey_14_300"
                                               style="background-color: white;margin-left: 10px"><%=o.getQuantity()%>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <div class="" style="margin-top: 30px">
                                    <div class="price" style="display: flex">
                                                <span class="price-current"
                                                      style="color: #e00;font-size: 14px;font-weight: 500;text-align: left;line-height: 150%;">
                                                   <%=NumberUtils.formatNumberWithCommas(o.getProductVariant().getPrice())%>
                                                </span>
                                        <span class="price-old"
                                              style="color: lightgrey;font-weight: 100;
                                                      text-decoration: line-through;line-height: 150%;margin-left: 5px">
                                                    <%=NumberUtils.formatNumberWithCommas(o.getProductVariant().getPrice())%>
                                                </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr style="width: 100%">
                    <div class="mt-3" style="display: flex;justify-content: space-between">
                        <div>
                            <a style="text-decoration: unset"
                               href="${pageContext.request.contextPath}/product-detail?id=<%=o.getProductVariant().getProduct().getId()%>">
                                <button type="button" class="btn btn-primary btn-lg"
                                        style="background-color: #ee4d2d;padding: 4px 8px 4px 8px">
                                    Mua lại
                                </button>
                            </a>
                        </div>
                        <div style="align-items: center;text-align: center;padding-top: 10px">
                            <span>Thành tiền: </span>
                            <label><%=NumberUtils.formatNumberWithCommas(o.getTotal_price())%>
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%
            }
        }
        %>
    </div>
</div>
<%@include file="/common/footer.jsp" %>
<%@include file="/common/libraries_js.jsp" %>
</body>
</html>
