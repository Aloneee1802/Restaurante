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
                                    <h5 class="card-title mb-0">Gestión Productos</h5>
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
                                        <label class="form-label">Nombre: <span style="color: red;">(*)</span></label>
                                        <input type="text" class="form-control" id="nombre"  name="nombre" maxlength="100">
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="mb-3">
                                        <label class="form-label">Precio: <span style="color: red;">(*)</span></label>
                                        <input type="number" class="form-control" id="precio" name="precio" step="0.1">
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="mb-3">
                                        <label class="form-label">Stock: <span style="color: red;">(*)</span></label>
                                        <input type="number" class="form-control" id="stock" name="stock" min="0" >
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="mb-3">
                                        <label class="form-label">(%) Descuento:</label>
                                        <input type="number" class="form-control" id="descuento" name="descuento" step="0.1">
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="mb-3">
                                        <label class="form-label">Url Imagen: <span style="color: red;">(*)</span></label>
                                        <input type="text" class="form-control" id="imagen" name="imagen">
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="mb-3">
                                        <label class="form-label">Descripción:</label>
                                        <textarea class="form-control" rows="3" name="descripcion" id="descripcion"></textarea>
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
                        <input type="hidden" id="id" name="id" value="0">
                    </form>
                </div>
            </div>
        </div>

        <div class="modal fade" id="modalConfEliminar" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" >::: Eliminación Producto :::</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form id="frmGuardar">
                        <div class="modal-body">
                            <p>¿Está seguro que desea eliminar el producto: <span class="negrita" id="lblNombreElim"></span>?</p>
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
                    .get("Producto", {params: _params})
                    .then((response) => {
                        $("#resultado").html(response.data);
                    })
                    .catch((error) => {
                        fnError("No se pudo cargar productos.");
                        console.dir(error);
                    });
        }

        function fnCargarDatos(id) {

            var _params = {
                accion: 'buscar',
                id: id
            };
            axios
                    .get("Producto", {params: _params})
                    .then((response) => {
                        response = response.data;

                        $('#id').val(response.idProd);
                        $('#nombre').val(response.nombre);
                        $('#precio').val(response.precio);
                        $('#descripcion').val(response.descripcion);
                        $('#imagen').val(response.imagen);
                        $('#descuento').val(response.porcDesc);
                        $('#stock').val(response.stock);

                        $('#modalGuardar').modal('show');
                        $('#lblTitulo').html('Editar Producto');
                    })
                    .catch((error) => {
                        fnError("No se pudo cargar datos.");
                        console.dir(error.message);
                    });
        }

        function fnValidarCampos() {
            var data = getDatos();

            if (data.nombre.length === 0) {
                fnInfo("El nombre del producto es requerido.");
                return false;
            }

            if (data.stock.length === 0) {
                fnInfo("El stock es requerido.");
                return false;
            } else if (parseInt(data.stock) < 0) {
                fnInfo("El stock debe ser 0 o mayor.");
                return false;
            }

            if (data.precio.length === 0) {
                fnInfo("El precio es requerido.");
                return false;
            } else if (parseInt(data.precio) <= 0) {
                fnInfo("El precio debe ser mayor a cero.");
                return false;
            }

            if (data.imagen.length === 0) {
                fnInfo("La Url imagen es requerido.");
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
                    .post("Producto", {}, {params: data})
                    .then((response) => {
                        const msg = response.data;

                        if (msg === 'OK') {
                            $('#modalGuardar').modal('hide');
                            fnSuccess(data.id_prod === 0 ? 'Producto creado!' : 'Producto actualizado!');
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
                    .get("Producto", {params: _params})
                    .then((response) => {
                        const msg = response.data;

                        if (msg === 'OK') {
                            $('#modalConfEliminar').modal('hide');
                            fnSuccess('Producto eliminado!');
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
                id_prod: parseInt($('#id').val().trim()),
                nombre: $('#nombre').val().trim(),
                precio: $('#precio').val().trim(),
                descripcion: $('#descripcion').val().trim(),
                imagen: $('#imagen').val().trim(),
                descuento: isNull($('#descuento').val().trim() , 0),
                stock: $('#stock').val().trim()
            };
            return _params;
        }

        function fnLimpiarCampos() {
            $("#frmGuardar")[0].reset();
            $('#id').val("0");
        }

        function fnNuevo() {
            $('#modalGuardar').modal('show');
            $('#lblTitulo').html('Nuevo Producto');
            fnLimpiarCampos();
        }

        fnListarTodos();
    </script>
</body>