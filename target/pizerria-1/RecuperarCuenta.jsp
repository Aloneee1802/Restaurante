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
                                        <div >
                                            <h5 > Reiniciar Contraseña</h5>
                                            <hr />
                                        </div>
                                    </div>
                                </div>

                                <div class="p-2">
                                    <form class="form-horizontal" method="POST" >
                                        <label  class="form-label">Correo eléctronico:</label>
                                        <input type="email" class="form-control " id="correo" name="correo">

                                        <div class="text-end mb-3 my-3">
                                            <button id="btnReiniciar" onclick="fnGenerarCodigo()" class="btn btn-primary" type="button">Reiniciar </button>
                                        </div>
                                        <div class="mb-1">
                                            ¿Has recordado la contraseña? 
                                            <a href="Login.jsp" class="fw-medium text-primary"> Inicia sesión</a>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <div id="bloque2" class="card ">
                            <div class="card-body ">
                                <div class="row">
                                    <div class="col-12">
                                        <div >
                                            <h5>Ingresa el código de seguridad</h5>
                                            <hr />
                                            <span>Comprueba si recibiste un correo electrónico con tu código de 4 dígitos.</span>
                                            <p>Enviamos el código a: <span style="font-weight: bold;" id="lblCorreo"></span></p>
                                        </div>
                                    </div>
                                </div>

                                <form class="form-horizontal" method="POST" >
                                    <label  class="form-label">Codigo de Seguridad:</label>
                                    <input onkeypress="return soloNumeros(event)"   type="text" class="form-control " id="codigo" name="codigo" maxlength="4">

                                    <div class="text-end mb-3 my-3">
                                        <button onclick="fnValidarCodigo()" id="btnValidarCodigo"  class="btn btn-primary" type="button">Continuar </button>
                                    </div>
                                    <div class="mb-1">
                                        <a href="javascript:fnDirigirAccion(1)" class="fw-medium text-primary">¿No recibiste el código?</a>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <div id="bloque3" class="card ">
                            <div class="card-body ">
                                <div class="row">
                                    <div class="col-12">
                                        <div >
                                            <h5>Elige una contraseña nueva</h5>
                                            <hr />
                                            <span style="text-align: justify">Crea una contraseña nueva de seis caracteres como mínimo. Una contraseña segura tiene una combinación de letras, números y signos de puntuación.</span>
                                        </div>
                                    </div>
                                </div>

                                <form class="form-horizontal mt-4" method="POST" >
                                    <label  class="form-label">Contraseña nueva</label>
                                    <input type="password" class="form-control " id="nueva" name="nueva" >

                                    <div class="text-end mb-3 my-3">
                                        <button onclick="fnValidarConfirmacion()" id="btnValidarCodigo"  class="btn btn-primary" type="button">Confirmar</button>
                                    </div>
                                    <p class="text-center">¿Ya tienes una cuenta? <a data-toggle="tab" href="Login.jsp">Iniciar sesión</a></p>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <jsp:include page="template/FooterCliente.jsp" />
        <jsp:include page="template/JsEmp.jsp" />
    </body>
    <script>
        var datos = {};

        function fnValidarConfirmacion() {
            var nueva = $("#nueva").val().trim();

            if (nueva === null || nueva === undefined || nueva === "") {
                fnInfo("Ingrese una contraseña.");
                return false;
            } else if (nueva.length < 6) {
                fnInfo("Debe contener como mínimo 6 caracteres.");
                return false;
            }

            var _params = {
                nueva: nueva,
                id: datos.idUsuario,
                accion: 'actualizar_password'
            };

            axios
                    .post("Auth", {}, {params: _params})
                    .then((response) => {
                        response = response.data;

                        if (response.msg === 'OK') {
                            fnSuccess("Contraseña actualizada de forma correcta.");
                            $("#nueva").val("");
                            $("#correo").val("");

                            fnDirigirAccion(1);
                        } else {
                            fnInfo(response.msg);
                        }
                    })
                    .catch((error) => {
                        fnError('Error interno! Al actualizar contraseña.');
                        fnEnviando(false);
                        console.dir(error.message);
                    });

        }

        function fnValidarCodigo() {
            var code = $("#codigo").val().trim();

            if (code === null || code === undefined || code === "") {
                fnInfo("Ingrese un codigo de seguridad.");
                return false;
            }

            var _params = {
                codigo: code,
                id: datos.idUsuario,
                accion: 'validar_codigo'
            };

            axios
                    .post("Auth", {}, {params: _params})
                    .then((response) => {
                        response = response.data;

                        if (response.msg === 'OK') {
                            $("#codigo").val("");
                            fnDirigirAccion(3);
                        } else {
                            fnInfo(response.msg);
                        }
                    })
                    .catch((error) => {
                        fnError('Error interno! Al validar codigo.');
                        fnEnviando(false);
                        console.dir(error.message);
                    });
        }


        function fnDirigirAccion(bloque) {
            if (bloque === 1) {
                $("#bloque1").show();
                $("#bloque2").hide();
                $("#bloque3").hide();
            } else if (bloque === 2) {
                $("#bloque2").show();
                $("#bloque1").hide();
                $("#bloque3").hide();
            } else if (bloque === 3) {
                $("#bloque3").show();
                $("#bloque1").hide();
                $("#bloque2").hide();
            }
        }

        function fnGenerarCodigo() {
            var correo = $("#correo").val().trim();
            if (correo === null || correo.length === 0) {
                fnInfo("Por favor ingrese un correo.");
                return false;
            }

            var _params = {
                correo: correo,
                accion: 'generar_codigo'
            };

            fnEnviando(true);

            axios
                    .post("Auth", {}, {params: _params})
                    .then((response) => {
                        response = response.data;

                        if (response.msg === 'OK') {
                            datos = response.data;
                            $('#lblCorreo').html(correo);
                            fnSuccess('En Hora buena! Se envió un codigo de seguridad a su correo electrónico.');
                            fnDirigirAccion(2);
                        } else {
                            fnInfo(response.msg);
                        }
                        fnEnviando(false);
                    })
                    .catch((error) => {
                        fnError('Error interno! Al generar codigo.');
                        fnEnviando(false);
                        console.dir(error.message);
                    });
        }

        function fnEnviando(status) {
            document.getElementById('btnReiniciar').disabled = status;
        }

        function fnIniciar() {
            fnDirigirAccion(1);
        }

        fnIniciar();
    </script>
</html>
