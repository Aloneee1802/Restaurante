<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Restaurante</title>
    <jsp:include page="template/CssEmp.jsp" />
</head>
<style>
    .stats-icon{
        font-size:42px;
        height:56px;
        width:56px;
        text-align:center;
        line-height:56px;
        margin-left:15px;
        color:#fff;position:absolute;
        right:15px;
        top:15px;
        opacity:.2;
    }
    .stats-info h4{
        font-size:14px;
        margin:5px 0;
        color:#fff}
    </style>
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
                                    <h5 class="card-title mb-0">Dashboard</h5>

                                    <hr >
                                    <div class="card-block table-border-style">
                                        <div class="row">
                                            <div class="col-md-3 col-sm-6">
                                                <div class="card  bg-success">
                                                    <div class="card-body">
                                                        <div class="stats-icon"><i class="fa fa-user-circle"></i></div>
                                                        <div class="stats-info">
                                                            <h4>TOTAL CLIENTES</h4>
                                                            <p>${cantClientes}</p>	
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-3 col-sm-6">
                                                <div class="card bg-danger">
                                                    <div class="card-body">
                                                        <div class="stats-icon"><i class="fa fa-desktop"></i></div>
                                                        <div class="stats-info">
                                                            <h4>TOTAL PLATOS</h4>
                                                            <p>${cantProductos}</p>	
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-3 col-sm-6">
                                                <div class="card bg-warning">
                                                    <div class="card-body">
                                                        <div class="stats-icon"><i class="fa fa-shopping-cart"></i></div>
                                                        <div class="stats-info">
                                                            <h4>TOTAL VENTAS DEL MES</h4>
                                                            <p>${totalVentas}</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-3 col-sm-6">
                                                <div class="card bg-primary">
                                                    <div class="card-body">
                                                        <div class="stats-icon"><i class="fa fa-shopping-cart"></i></div>
                                                        <div class="stats-info">
                                                            <h4>TOTAL GANANCIA DEL MES</h4>
                                                            <p>${totalGanancia}</p>	
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="">
                                                <div id="grafico1" class="">
                                                    cargando...
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>  
                            </div>
                        </div>
                    </div>

                </div>
            </main>
            <jsp:include page="template/FooterEmp.jsp" />
        </div>

        <jsp:include page="template/JsEmp.jsp" />
        <script>
            const nomMes = ['','Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];
            const annioAct = new Date().getFullYear();
            
            function CargarDatos() {
                var _params = {
                    accion: 'reporte1'
                };

                axios
                        .get("Inicio", {params: _params})
                        .then((response) => {
                            console.dir(response);
                            var data = response.data;
                            var ventas = [];
                            var mes = [];
                            
                            for (var i = 0; i < data.length; i++) {
                                mes.push(nomMes[ data[i].nroMes]);
                                ventas.push(data[i].total);
                            }

                            Graficar(mes, ventas);
                        })
                        .catch((error) => {
                            fnError("No se pudo cargar datos del reporte 1.");
                            console.dir(error);
                        });


            }

            function Graficar(mes, ventas) {
                chart = Highcharts.chart('grafico1', {
                    title: {
                        text: 'Ventas totales del año ' + annioAct
                    },
                    xAxis: {
                        categories: mes
                    },
                    yAxis: {
                        title: {
                            text: 'Ventas realizadas'
                        }
                    },
                    series: [{
                            type: 'column',
                            colorByPoint: true,
                            data: ventas,
                            showInLegend: false
                        }]

                });
            }


            CargarDatos();
        </script>
</body>