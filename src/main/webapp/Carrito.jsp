<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <body>
        <jsp:include page="template/HeaderCliente.jsp" />

        <section class="h-100 h-custom" style="background-color: #eee;">
            <div class="container py-5 h-100">
                <div class="col-md-12">
                    <h2 class="text-center text-uppercase">Carrito de compra</h2>
                    <hr>
                </div>
                <div id="listCarrito"></div>
            </div>

        </section>

        <jsp:include page="template/FooterCliente.jsp" />
        <jsp:include page="template/JsEmp.jsp" />
        <jsp:include page="template/Mensaje.jsp" />
    </body>
    <script>
        function fnListarCarrito() {
            var _params = {
                accion: 'listar'
            };

            axios
                    .get("Carrito", {params: _params})
                    .then((response) => {
                        $("#listCarrito").html(response.data);
                    })
                    .catch((error) => {
                        fnError("No se pudo cargar carrito.");
                        console.dir(error);
                    });
        }

        function fnEliminarCarrito(indice) {
            var _params = {
                accion: 'eliminar',
                indice: indice
            };

            axios
                    .get("Carrito", {params: _params})
                    .then((response) => {
                        response = response.data;
                        if (response.msg === "OK") {
                            $('#lblCantCarrito').html(response.data);
                            fnListarCarrito();
                        } else {
                            fnInfo(response.msg);
                        }
                    })
                    .catch((error) => {
                        fnError("No se pudo cargar carrito.");
                        console.dir(error);
                    });
        }

        function fnIniciar() {
            fnListarCarrito();
        }

        fnIniciar();
    </script>
</html>
