<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Pizzeria</title>
    <jsp:include page="template/CssEmp.jsp" />
</head>

<body>
    <div class="wrapper">
        <jsp:include page="template/HeaderEmp.jsp" />
  
        <div class="main">
            <jsp:include page="template/SidebarEmp.jsp" />
            <main class="content">
                <div class="container-fluid p-0">
                    <div class="row">

                        <div class="col-md-12 col-xl-12">
                            <div class="card">
                                <div class="card-body h-100">
                                    <h5 class="card-title mb-0">Gestión Cliente</h5>
                                    <a href="javascript:fnNuevo()" class="btn btn-success btn-sm mt-2" title="Nuevo">
                                        <i class="fa fa-plus-circle"></i> Nuevo
                                    </a>
                                    <hr >
                                    <div  id="resultado"></div>
                                </div>  
                            </div>
                        </div>
                    </div>

                </div>
            </main>
            <jsp:include page="template/FooterEmp.jsp" />
        </div>

        <div class="modal fade" id="modalGuardar" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog  modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" >::: <span id="lblTitulo"></span> :::</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form id="frmGuardar">
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

                              <!-- 
                                <div id="grupo_password" class="col-sm-3">
                                    <div class="mb-3">
                                        <label class="form-label">Contraseña: <span style="color: red;">(*)</span></label>
                                        <input type="password" class="form-control" id="password" name="password" maxlength ="100">
                                    </div>
                                </div>
                              -->

                                <div class="col-sm-3">
                                    <div class="mb-3">
                                        <label class="form-label">Estado: <span style="color: red;">(*)</span></label>
                                        <select class="form-control" name="estado" id="estado">
                                            <option value="">::: Seleccione :::</option>
                                            <option value="1">Activo</option>
                                            <option value="0">Inactivo</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button onclick="fnGuardar()" type="button" class="btn btn-primary btn-sm">
                                Guardar
                            </button>
                            <button type="button" class="btn btn-default btn-sm" data-bs-dismiss="modal">
                                Cancelar
                            </button>
                        </div>

                        <input type="hidden" id="id_usuario" name="id_usuario" value="0">
                        <input type="hidden" id="id" name="id" value="0">
                    </form>
                </div>
            </div>
        </div>

        <div class="modal fade" id="modalConfEliminar" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" >::: Eliminación Cliente :::</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form id="frmGuardar">
                        <div class="modal-body">
                            <p>¿Está seguro que desea eliminar al cliente <span class="negrita" id="lblNombreElim"></span>?</p>
                        </div>
                        <div class="modal-footer">
                            <button onclick="fnEliminar()" type="button" class="btn btn-primary btn-sm">
                                Confirmar
                            </button>
                            <button type="button" class="btn btn-default btn-sm" data-bs-dismiss="modal">
                                Cancelar
                            </button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="template/JsEmp.jsp" />

    <script>

        function fnListarTodos() {

            var _params = {
                accion: 'listar'
            };

            axios
                    .get("Cliente", {params: _params})
                    .then((response) => {
                        $("#resultado").html(response.data);
                    })
                    .catch((error) => {
                        fnError("No se pudo cargar clientes.");
                        console.dir(error);
                    });
        }

        function fnCargarDatos(id) {

            var _params = {
                accion: 'buscar',
                id: id
            };
            axios
                    .get("Cliente", {params: _params})
                    .then((response) => {
                        response = response.data;

                        $('#id_usuario').val(response.idUsuario);
                        $('#id').val(response.idCliente);
                        $('#nombres').val(response.nombres);
                        $('#apellidos').val(response.apellidos);
                        $('#tipoDoc').val(response.tipoDoc);
                        $('#nroDoc').val(response.nroDoc);
                        $('#telefono').val(response.telefono);
                        $('#correo').val(response.correo);
                        $('#estado').val(response.estado);
                        $('#modalGuardar').modal('show');
                        $('#lblTitulo').html('Editar Cliente');

                        $("#grupo_password").hide();
                    })
                    .catch((error) => {
                        fnError("No se pudo cargar datos.");
                        console.dir(error.message);
                    });
        }

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
                            $('#modalGuardar').modal('hide');
                            fnSuccess(data.id_cliente === 0 ? 'Cliente creado!' : 'Cliente actualizado!');
                            fnListarTodos();
                        } else {
                            fnInfo(msg);
                        }
                    })
                    .catch((error) => {
                        fnError('Error interno! Al guardar datos.');
                        console.dir(error.message);
                    });
        }

        function fnConfirmarEliminacion(id, nombre) {
            $('#modalConfEliminar').modal('show');
            $('#lblNombreElim').html(nombre);
            $('#id').val(id);
        }

        function fnEliminar() {
            var id = $('#id').val();

            var _params = {
                accion: 'eliminar',
                id: id
            };
            axios
                    .get("Cliente", {params: _params})
                    .then((response) => {
                        const msg = response.data;

                        if (msg === 'OK') {
                            $('#modalConfEliminar').modal('hide');
                            fnSuccess('Cliente eliminado!');
                            fnListarTodos();
                        } else {
                            fnInfo(msg);
                        }
                    })
                    .catch((error) => {
                        fnError('Error interno! No se pudo procesar operación.');
                        console.dir(error.message);
                    });
        }

        function getDatos() {
            var _params = {
                id_usuario: parseInt(isNull($('#id_usuario').val().trim(), "0")),
                id_cliente: parseInt($('#id').val().trim()),
                nombres: $('#nombres').val().trim(),
                apellidos: $('#apellidos').val().trim(),
                tipoDoc: $('#tipoDoc').val().trim(),
                nroDoc: $('#nroDoc').val().trim(),
                estado: $('#estado').val().trim(),
                telefono: $('#telefono').val().trim(),
                correo: $('#correo').val().trim()
            };
            return _params;
        }

        function fnLimpiarCampos() {
            $("#frmGuardar")[0].reset();
            $('#id').val("0");
        }

        function fnNuevo() {
            $('#modalGuardar').modal('show');
            $('#lblTitulo').html('Nuevo Cliente');
            $("#grupo_password").show();
            fnLimpiarCampos();
        }

        fnListarTodos();
    </script>
</body>