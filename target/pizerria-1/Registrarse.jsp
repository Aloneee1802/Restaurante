<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pizzeria</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" rel="stylesheet" >
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link href="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/alertify.min.css" rel="stylesheet" />
        <link href="assets/css/navbar.css" rel="stylesheet" />
        <link href="assets/css/style.css"  rel="stylesheet">
        <link href="assets/css/form.css" rel="stylesheet" type="text/css"/>
    </head>

    <body>
        <jsp:include page="template/HeaderCliente.jsp" />
        <section class="ftco-section">
            <div class="container-fluid">

                <div class="row justify-content-center">
                    <div class="col-md-12 col-lg-12">
                        <div class="wrap d-md-flex">
                            <div class="img" style="background-image: url(assets/img/slide1.webp);">
                            </div>
                            <div class=" p-4 p-md-5">
                                <c:if test="${error != null}">
                                    <div class="alert alert-danger" role="alert">
                                        ${error}
                                    </div>
                                </c:if>
                                <div class="d-flex">
                                    <div class="w-100">
                                        <h3 class="mb-4">Registrate aqui</h3>
                                        <hr />
                                    </div>
                                </div>

                                <form id="frmGuardar" class="signin-form">
                                    <div class="modal-body">
                                        <div class="row">
                                            <div class="col-sm-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Nombres: <span style="color: red;">(*)</span></label>
                                                    <input onkeypress="return soloLetras(event)"  type="text" class="form-control" id="nombres"  name="nombres" maxlength="100">
                                                </div>
                                            </div>

                                            <div class="col-sm-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Apellidos: <span style="color: red;">(*)</span></label>
                                                    <input onkeypress="return soloLetras(event)"  type="text" class="form-control" id="apellidos" name="apellidos" maxlength="100">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-sm-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Teléfono:</label>
                                                    <input onkeypress="return soloNumeros(event)"  type="text" class="form-control" id="telefono" name="telefono" maxlength ="9">
                                                </div>
                                            </div>

                                            <div class="col-sm-3">
                                                <div class="mb-3">
                                                    <label class="form-label">Tipo Doc: <span style="color: red;">(*)</span></label>
                                                    <select class="form-control" name="tipoDoc" id="tipoDoc">
                                                        <option value="">::: Seleccione :::</option>
                                                        <option value="Carnet de extranjeria">Carnet de extranjeria</option>
                                                        <option value="Dni">Dni</option>
                                                        <option value="Pasaporte">Pasaporte</option>
                                                    </select>

                                                </div>
                                            </div>

                                            <div class="col-sm-3">
                                                <div class="mb-3">
                                                    <label class="form-label">Nro Doc: <span style="color: red;">(*)</span></label>
                                                    <input onkeypress="return soloNumeros(event)" type="text" class="form-control" id="nroDoc" name="nroDoc" maxlength ="15">
                                                </div>
                                            </div>
                                        </div>

                                        <span class="badge bg-primary">Autentificación</span>

                                        <div class="row mt-2">
                                            <div class="col-sm-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Correo: <span style="color: red;">(*)</span></label>
                                                    <input type="text" class="form-control" id="correo" name="correo" maxlength ="100">
                                                </div>
                                            </div>

                                            <div class="col-sm-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Contraseña: <span style="color: red;">(*)</span></label>
                                                    <input type="password" class="form-control" id="password" name="password" maxlength ="100">
                                                </div>
                                            </div>
                                        </div>
                                        <button onclick="fnGuardar()" type="button" class="form-control btn btn-primary rounded submit px-3">Registrarse</button>
                                    </div>
                                </form>
                                <hr />
                                <p class="text-center">¿Ya tienes una cuenta? <a data-toggle="tab" href="Login.jsp">Iniciar sesión</a></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </section>
        <jsp:include page="template/FooterCliente.jsp" />
        <jsp:include page="template/JsEmp.jsp" />
        
    </body>
    <script>
        function fnValidarCampos() {
            var data = getDatos();

            if (data.nombres.length === 0) {
                fnInfo("El nombre requerido.");
                return false;
            }

            if (data.apellidos.length === 0) {
                fnInfo("El apellido requerido.");
                return false;
            }

            if (data.tipoDoc.length === 0) {
                fnInfo("Por favor seleccione un tipo de documento.");
                return false;
            }

            if (data.nroDoc.length === 0) {
                fnInfo("El nro de documento es requerido.");
                return false;
            }

            if (data.correo.length === 0) {
                fnInfo("El correo electrónico es requerido.");
                return false;
            } else if (!emailRegex.test(data.correo)) {
                fnInfo("Formato del correo no válido.");
                return false;
            }

            if (data.id_cliente === 0) {
                if (data.password.length === 0) {
                    fnInfo("La contraseña es requerido.");
                    return false;
                }
            }

            if (data.estado.length === 0) {
                fnInfo("Por favor seleccione un estado.");
                return false;
            }

            return true;
        }

        function fnGuardar() {
            if (!fnValidarCampos()) {
                return false;
            }

            var data = getDatos();
            data.accion = 'guardar';

            axios
                    .post("Cliente", {}, {params: data})
                    .then((response) => {
                        const msg = response.data;

                        if (msg === 'OK') {
                            fnSuccess('Cuenta creada con éxito!');
                            $('#frmGuardar')[0].reset();
                        } else {
                            fnInfo(msg);
                        }
                    })
                    .catch((error) => {
                        fnError('Error interno! Al guardar datos.');
                        console.dir(error.message);
                    });
        }

        function getDatos() {
            var _params = {
                id_usuario: 0,
                id_cliente: 0,
                nombres: $('#nombres').val().trim(),
                apellidos: $('#apellidos').val().trim(),
                tipoDoc: $('#tipoDoc').val().trim(),
                nroDoc: $('#nroDoc').val().trim(),
                estado: 1,
                telefono: $('#telefono').val().trim(),
                correo: $('#correo').val().trim(),
                password: $('#password').val().trim()
            };
            return _params;
        }

    </script>
</html>
