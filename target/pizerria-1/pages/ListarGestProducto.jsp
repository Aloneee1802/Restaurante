<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="tabla table table-bordered table-hover table-striped mt-3 " >
    <thead class="bg-primary text-white">
        <tr>
            <th>Imagen</th>
            <th>Producto</th>
            <th>Precio</th>
            <th>% Descuento</th>
            <th>Stock</th>
            <th>Descripción</th>
            <th>Acción</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${lista}" >
            <tr>
                <td>
                    <img src="${item.imagen}"
                         onerror="src='assets/img/no_disponible.png'"
                         width="60" height="60">
                </td>
                <td>${item.nombre}</td>
                <td>${item.precio}</td>
                <td>${item.porcDesc}</td>
                <td>${item.stock}</td>
                <td>${item.descripcion}</td>
                <td>
                    <a href="javascript:fnCargarDatos(${item.idProd})"  class="btn btn-info btn-sm" title="Editar">
                        <i class="fa fa-edit" ></i>
                    </a>
                    <a href="javascript:fnConfirmarEliminacion(${item.idProd} ,'${item.nombre}' )"  class="btn btn-danger btn-sm" title="Eliminar">
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