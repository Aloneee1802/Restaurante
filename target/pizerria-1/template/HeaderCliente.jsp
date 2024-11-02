<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    .navbar-nav .dropdown .dropdown-menu {
        width: 94.500% !important;
    }
    .header.headr-style-2 .dropdown-menu > li > a {
        border-bottom: 1px solid #3b3e44 !important;
    }
</style>
<nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar" > <!-- navbar-petjoy -->
    <div class="container-fluid" >
        <img src="assets/img/logo2.png" style="width: 70px; height: 60px;" />
        <a class="navbar-brand" href="index.jsp">
            <span class="flaticon-pizza-1 mr-1"></span>Pizza<br><small>Little Caesars</small>
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse  " id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto" >
                <li class="nav-item"><a href="index.jsp" class="nav-link">Inicio</a></li>
                <li class="nav-item"><a href="Menu?accion=listar" class="nav-link">Menu</a></li>
                <li class="nav-item"><a href="Menu?accion=promocion" class="nav-link">Promoción</a></li>
                <li class="nav-item">

                    <a href="Carrito.jsp" class="nav-link">
                        <i class="fa fa-shopping-cart"></i> (<span id="lblCantCarrito">${sessionScope.carrito!= null ? sessionScope.carrito.size(): 0}</span>) Carrito
                    </a>
                </li>

                <c:if test="${sessionScope.usuario != null}">
                    <li class="nav-item">
                        <a  class="nav-link" aria-current="page" href="Venta?accion=historial"><i
                                class="bi bi-person-circle"></i> Mis Ventas</a>
                    </li>
                </c:if>

                <c:if test="${sessionScope.usuario == null}">
                    <li class="nav-item">
                        <a  class="nav-link" aria-current="page" href="Login.jsp"><i
                                class="bi bi-person-circle"></i> Login</a>
                    </li>
                </c:if>

                <c:if test="${sessionScope.usuario != null}">

                    <li class="nav-item dropdown"  >
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" 
                           role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            ${sessionScope.usuario.correo}
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">

                            <li><a class="dropdown-item" href="Auth?accion=logout" >Cerrar Sesión</a></li>
                        </ul>
                    </li>
                </c:if>

            </ul>


        </div>
    </div>
</nav>