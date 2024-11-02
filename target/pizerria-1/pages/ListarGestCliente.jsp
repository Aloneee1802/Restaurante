<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="tabla table table-bordered table-hover table-striped mt-3 " >
    <thead class="bg-primary text-white">
        <tr>
            <th>Nombres</th>
            <th>Apellidos</th>
            <th>Tipo Doc</th>
            <th>Nro Doc</th>
            <th>Telefono</th>
            <th>Correo</th>
            <th>Estado</th>
            <th>Acción</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${lista}" >
            <tr>
                <td>${item.nombres}</td>
                <td>${item.apellidos}</td>
                <td>${item.tipoDoc}</td>
                <td>${item.nroDoc}</td>
                <td>${item.telefono}</td>
                <td>${item.correo}</td>
                <td>${item.nomEstado()}</td>
                <td>
                    <a href="javascript:fnCargarDatos(${item.idCliente})"  class="btn btn-info btn-sm" title="Editar">
                        <i class="fa fa-edit" ></i>
                    </a>
                    <a href="javascript:fnConfirmarEliminacion(${item.idCliente} ,'${item.nomCompletos()}' )"  class="btn btn-danger btn-sm" title="Eliminar">
                        <i class="fa fa-trash" ></i>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>


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