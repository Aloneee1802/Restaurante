<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pizzeria</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" rel="stylesheet" >
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link href="assets/css/navbar.css" rel="stylesheet" />
        <link href="assets/css/style.css"  rel="stylesheet">
        <link href="assets/css/form.css" rel="stylesheet" type="text/css"/>
    </head>
    <style>
        .form-gap {
            padding-top: 70px;
        }
    </style>
    <body>
        <jsp:include page="template/HeaderCliente.jsp" />
        <div class="my-5 pt-sm-5">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-8 col-lg-6 col-xl-5">
                        <div id="bloque1" class="card ">
                            <div class="card-body ">
                                <div class="row">
                                    <div class="col-12">
                                    <h5 > Error:  <p>${error}</p></h5>
                                    <h5 > payer  <p>${payer}</p></h5>
                                    <h5 > transaction  <p>${transaction}</p></h5>
                                       
                                    </div>
                                </div>

       
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <jsp:include page="template/FooterCliente.jsp" />
        <jsp:include page="template/JsEmp.jsp" />
    </body>
 
</html>
