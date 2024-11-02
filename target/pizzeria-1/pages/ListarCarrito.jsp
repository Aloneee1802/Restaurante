<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${sessionScope.carrito != null && sessionScope.carrito.size() > 0 }">
    <div class="row d-flex justify-content-center">
        <div class="col-lg-8">
            <div class="card">
                <div class="card-body p-4">
                    <div class="row">
                        <h5 class="mb-3"><a href="Menu?accion=listar" class="text-body"><i
                                    class="fas fa-long-arrow-alt-left me-2"></i>Seguir comprando</a></h5>
                        <hr class="my-2">

                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <p class="mb-0">Tiene(s) ${sessionScope.carrito.size()} plato(s) en tu carrito</p>
                        </div>

                        <c:forEach items="${sessionScope.carrito}" var="item" varStatus="loop">
                            <div class="card mb-3">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between">
                                        <div class="d-flex flex-row align-items-center">
                                            <div>
                                                <img
                                                    src="${item.producto.imagen}"
                                                    class="img-fluid rounded-3" alt="Shopping item" style="width: 65px;">
                                            </div>
                                            <div class="ms-3">
                                                <p class="small mb-0">${item.producto.nombre}</p>
                                            </div>
                                        </div>
                                        <div class="d-flex flex-row align-items-center">
                                            <div style="width: 40px;">
                                                <h5 class="fw-normal mb-0">${item.cantidad}</h5>
                                            </div>
                                            <div style="width: 90px;">
                                                <h5 class="mb-0">S/<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.Total()}"/>
                                                </h5>

                                            </div>
                                            <a href="javascript:fnEliminarCarrito(${loop.index})"  style="color: #cecece;" title="Eliminar">
                                                <i class="fas fa-trash-alt"></i>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </c:forEach>
                    </div>

                </div>
            </div>
        </div>

        <div class="col-lg-4">
            <div class="card">
                <div class="card-body p-4">
                    <form method="post" action="Transacion">
                        <div class="row">
                            <h5 class="mb-3">RESUMEN COMPRA</h5>

                            <hr class="my-2">

                            <div class="d-flex justify-content-between">
                                <p class="mb-2">Subtotal</p> 
                                <p class="mb-2">S/<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${sessionScope.total}"/></p>
                            </div>

                            <div class="d-flex justify-content-between">
                                <p class="mb-2">Descuento</p>
                                <p class="mb-2">S/0.00</p>
                            </div>

                            <div class="d-flex justify-content-between mb-4">
                                <p class="mb-2">Total</p>
                                <p class="mb-2">S/<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${sessionScope.total}"/></p>
                            </div>

                            <button type="submit" class="btn btn-info btn-block btn-lg">
                                <input type="hidden" name="accion" value="autorizar">
                                <div class="d-flex justify-content-between">
                                    <span></span>
                                    <span>Procesar <i class="fas fa-long-arrow-alt-right ms-2"></i></span>
                                </div>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</c:if>

<c:if test="${!(sessionScope.carrito != null && sessionScope.carrito.size() > 0) }">
    <div style="text-align: center">
        <img src="assets/img/carrito.png"  width="30%"/>
        <h4>Tu carrito está vacío, llénalo!</h4>
    </div>
</c:if>

