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

    <body>
        <jsp:include page="template/HeaderCliente.jsp" />
        <section class="ftco-section">
            <div class="container">

                <div class="row justify-content-center">
                    <div class="col-md-12 col-lg-10">
                        <div class="wrap d-md-flex">
                            <div class="img" style="background-image: url(assets/img/slide4.webp);">
                            </div>
                            <div class="  login-wrap p-4 p-md-5">
                                <c:if test="${error != null}">
                                    <div class="alert alert-danger" role="alert">
                                        ${error}
                                    </div>
                                </c:if>
                                <div class="d-flex">
                                    <div class="w-100">
                                        <h3 class="mb-4">Login</h3>
                                        <hr />
                                    </div>
                                </div>

                                <form method="post" action="Auth" class="signin-form">
                                    <div class="form-group mb-3">
                                        <label class="label" for="name">Correo</label>
                                        <input value="jne33@gmail.com"  class="form-control form-control-lg" type="email" name="correo"  required=""/>
                                    </div>
                                    <div class="form-group mb-3">
                                        <label class="label" for="password">Contraseña</label>
                                        <input value="123456" class="form-control form-control-lg" type="password" name="password" required=""/>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="form-control btn btn-primary rounded submit px-3">Iniciar Sesión</button>
                                    </div>
                                    <div class="form-group d-md-flex">
                                        <div class="w-50 text-md-right">
                                            <a href="RecuperarCuenta.jsp">¿Olvidaste tu contraseña?</a>
                                        </div>
                                    </div>
                                    <input type="hidden" name="accion" value="login">
                                </form><hr />
                                <p class="text-center">¿No tienes una cuenta? <a data-toggle="tab" href="Registrarse.jsp">Registrarse aquí</a></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <jsp:include page="template/FooterCliente.jsp" />
        <jsp:include page="template/JsEmp.jsp" />
    </body>
</html>
