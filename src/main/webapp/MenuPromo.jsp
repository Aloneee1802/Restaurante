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
    </head>

    <style>
        .cart .cart-info{
            background:#409000;
            width:18px;
            height:18px;
            text-align:center;
            line-height:16px;
            font-size:12px;
            color:#FFF;
            border-radius:100%;

            position:absolute;
        }

    </style>
    <body >
        <jsp:include page="template/HeaderCliente.jsp" />
        <div class="m-top8">
            <div class="col-md-12">
                <h2 class="text-center text-uppercase">Nuestras promociones</h2>
                <hr>
            </div>

            <div class="container">
                <p>Este es nuestro menú de platos con grandes descuento. Que esperas consulta los precios, ofertas y los platos disponibles.</p>
                <div class="row">
                    <c:forEach items="${productos}" var="item" varStatus="loop">
                        <div class="col-md-4 col-sm-6 col-xs-12 m-bottom3">
                            <div class="text-center pro-display" style="background: white;">
                                <div class="m-bottom2"><img style="height: 220px;"  class="img-responsive" src="${item.imagen}"></div>
                                <h3 class="font-thin font-black m-bottom2" style="font-size: 17px;">${item.nombre}</h3>
                                <h2 class="font20 font-bold font-color m-bottom1">
                                    <c:choose>
                                        <c:when test="${item.porcDesc > 0 }">
                                               <span style="text-decoration:line-through; color: red; font-size: 14px;">S/${item.precio}</span> &nbsp;   S/<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.PrecioCnDesc()}"/>
                                        </c:when>
                                        <c:otherwise>
                                            S/<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.precio}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </h2>
                                <div class="button full-wid left m-top2">
                                    <a class="btn boxed-color-xs uppercase font-bold" href="#"
                                       data-bs-toggle="modal" data-bs-target="#modalAgregar${loop.index}">
                                        <i class="fa fa-info-circle"></i>
                                        Ver detalle
                                    </a>
                                    <a onclick="fnAgregarCarrito(${item.idProd}, -1)" class="btn boxed-color-xs uppercase font-bold" href="#">
                                        <i class="fa fa-shopping-cart"></i>
                                        Agregar al carrito
                                    </a>
                                </div>
                            </div>
                        </div>


                        <div class="modal fade" id="modalAgregar${loop.index}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                            <div class="modal-dialog ">
                                <div class="modal-content">
                                    <div class="modal-header"  style="background: #121618 !important; color: white;" >
                                        <h5 class="modal-title" id="staticBackdropLabel"> ::: Detalle Plato :::</h5>
                                        <button style="color: white;" type="button" class="btn-close bg-light" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="row">
                                            <div class="col-sm-5 d-flex align-items-center">
                                                <img src="${item.imagen}" alt="${item.nombre}" style="width: 170px">
                                            </div>

                                            <div class="col-sm-7">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <h3 class="font20 font-bold  m-bottom1">
                                                            ${item.nombre} 
                                                        </h3>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="form-group row">
                                                            <label class="col-sm-3 col-form-label">Precio:</label>
                                                            <div class="col-sm-9">
                                                                <h2 class="font20 font-bold  m-bottom1">
                                                                    S/<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.PrecioCnDesc()}"/>
                                                                </h2>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="form-group row">
                                                            <label class="col-sm-3 col-form-label">Cantidad:</label>
                                                            <div class="col-sm-9">
                                                                <input type="number" id="cantidad${loop.index}" class="form-control" min="1" max="${item.stock}" value="1" required="">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="button full-wid left m-top2 d-grid gap-2 d-md-flex justify-content-md-end  mt-2" > 
                                                    <a onclick="fnAgregarCarrito(${item.idProd}, ${loop.index})" class="btn-sm btn boxed-color-xs uppercase font-bold" href="#">
                                                        <i class="fa fa-shopping-cart"></i>
                                                        Agregar al carrito
                                                    </a>
                                                </div>
                                            </div>
                                            <hr />
                                            
                                            <figure>
                                                <blockquote class="blockquote">
                                                    <p >Descripción:</p>
                                                </blockquote>
                                                <figcaption class="blockquote-footer mt-2">
                                                    ${item.descripcion}
                                                </figcaption>
                                            </figure>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>



        <jsp:include page="template/FooterCliente.jsp" />
        <jsp:include page="template/JsEmp.jsp" />

    </body>
    <script>

        function fnAgregarCarrito(id, indice) {
            var cantidad = 0;

            if (indice === -1) {
                cantidad = 1;
            } else {
                cantidad = parseInt($('#cantidad' + indice).val());

                if (isNaN(cantidad)) {
                    fnInfo("La cantidad ingresada no es valida.");
                    return;
                } else if (cantidad <= 0) {
                    fnInfo("La cantidad debe ser un valor positivo.");
                    return;
                }
            }

            var data = {
                id: id,
                cantidad: cantidad,
                accion: 'agregar'
            };

            axios
                    .post("Carrito", {}, {params: data})
                    .then((response) => {
                        response = response.data;

                        if (response.msg === 'OK') {
                            fnSuccess("Plato agregado al carrito!");
                            $('#lblCantCarrito').html(response.data);
                        } else {
                            fnInfo(response.msg);
                        }
                    })
                    .catch((error) => {
                        fnError('Error interno! Al guardar datos.');
                        console.dir(error.message);
                    });
        }

    </script>
</html>
