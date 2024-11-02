<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav id="sidebar" class="sidebar js-sidebar" >
    <div class="sidebar-content js-simplebar" >
        <a class="sidebar-brand" href="#" style="text-decoration: none">
            <img src="assets/img/logo2.png" alt=""/> 
            <span >Pizzeria Little Caesars</span>
        </a>

        <ul class="sidebar-nav" >
            <li class="sidebar-header">
                Dashboard
            </li>

            <li class="sidebar-item">
                <a class="sidebar-link" href="Inicio?accion=principal">
                    <i class="align-middle" data-feather="sliders"></i> <span class="align-middle">Inicio</span>
                </a>
            </li>


            <li class="sidebar-header">
                Gestión
            </li>

            <li class="sidebar-item">
                <a class="sidebar-link" href="GestProductos.jsp">
                    <i class="align-middle" data-feather="sliders"></i> <span class="align-middle">Productos</span>
                </a>
            </li>

            <li class="sidebar-item">
                <a class="sidebar-link" href="GestClientes.jsp">
                    <i class="align-middle" data-feather="user"></i> <span class="align-middle">Clientes</span>
                </a>
            </li>

            <li class="sidebar-item">
                <a class="sidebar-link" href="Venta?accion=consultar">
                    <i class="align-middle" data-feather="sliders"></i> <span class="align-middle">Consultar Ventas</span>
                </a>
            </li>
        </ul>
    </div>
</nav>