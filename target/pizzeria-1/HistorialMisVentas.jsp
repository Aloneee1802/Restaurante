<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Restaurante</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" rel="stylesheet" >
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link href="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/alertify.min.css" rel="stylesheet" />
        <link href="assets/css/navbar.css" rel="stylesheet" />
        <link href="assets/css/style.css"  rel="stylesheet">
        <link href="assets/css/menu_cart.css" rel="stylesheet" />
        <link href="https://cdn.datatables.net/1.13.1/css/dataTables.bootstrap5.min.css" rel="stylesheet" >
    </head>
    <body>
        <jsp:include page="template/HeaderCliente.jsp" />

        <section class="h-100 h-custom" style="background-color: #eee;">
            <div class="container py-5 h-100">
                <div class="col-md-12">
                    <h2 class="text-center text-uppercase">Mis Compras</h2>
                    <hr>
                </div>
                <div class="card">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="tabla table table-bordered table-hover table-striped mt-3 tabla " >
                                <thead class="bg-primary text-white">
                                    <tr>
                                        <th style="color: white;"># Venta</th>
                                        <th style="color: white;">Fecha</th>
                                        <th style="color: white;">Total</th>
                                        <th style="color: white;">Estado</th>
                                        <th style="color: white;">Ver detalle</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${lista}" var="item" varStatus="loop">
                                        <tr>
                                            <td>${item.nroVenta}</td>
                                            <td>${item.fecha}</td>
                                            <td>${item.total}</td>
                                            <td>${item.estadoEntrega}</td>
                                            <td>
                                                <a href="#"  class="btn btn-info" title="Ver detalle"
                                                   data-bs-toggle="modal" data-bs-target="#modalDetalle${loop.index}">
                                                    <i class="fa fa-info-circle" ></i>
                                                </a>

                                                <div class="modal fade" id="modalDetalle${loop.index}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                                    <div class="modal-dialog modal-lg">
                                                        <div class="modal-content">
                                                            <div class="modal-header bg-primary text-white">
                                                                <h5 class="modal-title">VENTA N°. ${item.nroVenta}</h5>
                                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <dl class="row">

                                                                    <dt class = "col-sm-2" style="text-align: right;">Cliente: </dt>
                                                                    <dd class = "col-sm-10" style="text-align: left;">
                                                                        ${item.cliente.nomCompletos()}
                                                                    </dd>

                                                                    <dt class = "col-sm-2"  style="text-align: right;">Fecha:</dt>
                                                                    <dd class = "col-sm-10"  style="text-align: left;">
                                                                        ${item.fecha}
                                                                    </dd>
                                                                </dl>

                                                                <div class="table-responsive mt-4">
                                                                    <table class="table table-bordered text-center table-striped"  style="background: white">
                                                                        <thead class="bg-primary text-white">
                                                                            <tr>
                                                                                <th class="text-center">#</th>
                                                                                <th class="text-center">Producto</th>
                                                                                <th class="text-center">Precio (S/)</th>
                                                                                <th class="text-center">Cantidad</th>
                                                                                <th class="text-center">Importe (S/)</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <c:forEach var="detalle" items="${item.detalles}"  varStatus="loop" >
                                                                                <tr >
                                                                                    <td>${loop.index + 1}</td>
                                                                                    <td>${detalle.producto.nombre}</td>
                                                                                    <td>${detalle.precio}</td>
                                                                                    <td>${detalle.cantidad}</td>
                                                                                    <td>
                                                                                        <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"  value="  ${detalle.precio * detalle.cantidad}"/>
                                                                                    </td>
                                                                                </tr>
                                                                            </c:forEach>   

                                                                            <tr>
                                                                                <td colspan="4" class="font-weight-bold">Total (S/)</td>
                                                                                <td  class="font-weight-bold">
                                                                                    <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.total}"/>
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>

                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

        </section>

        <jsp:include page="template/FooterCliente.jsp" />
        <jsp:include page="template/JsEmp.jsp" />
        <jsp:include page="template/Mensaje.jsp" />
    </body>
    <script>
        $(document).ready(function () {
            $('.tabla').DataTable({
                language: {
                    "decimal": "",
                    "emptyTable": "No hay información",
                    "info": "Mostrando _START_ a _END_ de _TOTAL_ Entradas",
                    "infoEmpty": "Mostrando 0 to 0 of 0 Entradas",
                    "infoFiltered": "(Filtrado de _MAX_ total entradas)",
                    "infoPostFix": "",
                    "thousands": ",",
                    "lengthMenu": "Mostrar _MENU_ Entradas",
                    "loadingRecords": "Cargando...",
                    "processing": "Procesando...",
                    "search": "Buscar:",
                    "zeroRecords": "Sin resultados encontrados",
                    "paginate": {
                        "first": "Primero",
                        "last": "Ultimo",
                        "next": "Siguiente",
                        "previous": "Anterior"
                    }
                }
            });
        });
    </script>
</html>
