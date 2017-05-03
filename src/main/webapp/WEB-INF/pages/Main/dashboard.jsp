<%-- Created by IntelliJ IDEA. --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:spring="" xmlns:c="http://www.w3.org/1999/XSL/Transform">

<%@ include file="head.jsp" %>

<body>

<div class="wrapper">
    <div class="sidebar" data-color="purple" data-image="/resources/img/sidebar-4.jpg">
        <div class="sidebar-wrapper">
            <div class="logo">
                <a href="/dashboard" class="simple-text">
                    MyBudget
                </a>
            </div>
            <ul class="nav">
                <li class="active">
                    <a href="/dashboard">
                        <i class="pe-7s-graph"></i>
                        <p>Revenus & Dépenses</p>
                    </a>
                </li>
                <li class="active">
                    <a href="/epargne">
                        <i class="pe-7s-graph1"></i>
                        <p>Mes projets d'épargne</p>
                    </a>
                </li>
            </ul>
        </div>
    </div>

    <div class="main-panel">
        <nav class="navbar navbar-default navbar-fixed">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation-example-2">
                        <span class="sr-only">Navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="/dashboard">Revenus & Dépenses</a>
                </div>
                <div class="collapse navbar-collapse">
                   <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="/deleteSession">
                                Déconnexion
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>


        <div class="content">
            <div class="container-fluid">
                <div class="row">

                    <div class="col-md-5">
                        <div class="card">
                            <div class="row">
                                <div class="col-md-8 col-xs-9">
                                    <div class="header">
                                        <h2 class="title">Dashboard </h2>
                                        <p class="category">Epargne potentielle : ${sessionScope.EpargneP}€</p>
                                        <br />
                                    </div>
                                </div>
                                <div class="col-md-4 col-xs-3">
                                    <div class="text-right botox">
                                        <button type="button" class="btn chouf btn-primary" data-toggle="modal" data-target="#myModal">
                                            +
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="row">

                    <div class="col-md-4">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Tableau de mouvements </h4>
                                <p class="category">Liste vos revenus et dépenses</p>
                            </div>
                            <div class="content table-responsive table-full-width" style="overflow: auto; max-height: 500px;">
                                <table class="table table-hover table-fixed">
                                    <thead>
                                    <th>Désignation</th>
                                    <th>Montant</th>
                                    <th style="text-align: right">Action</th>
                                    </thead>
                                    <tbody class="category">

                                    <c:forEach items="${Mouvements}" var="Mouvement">
                                        <c:choose>
                                            <c:when test="${Mouvement.signe == true}">
                                                <tr class="alert-info">
                                            </c:when>
                                            <c:otherwise>
                                                <tr>
                                            </c:otherwise>
                                        </c:choose>
                                            <td class="labelTd">${Mouvement.label}</td>
                                            <td class="montantTd" style="text-align: right;">
                                            <c:if test="${Mouvement.signe == false}">-</c:if>${Mouvement.montant}€
                                            </td>
                                            <td class="" style="text-align: right">
                                                <div class="">
                                                <button type="button" value="${Mouvement.id}" rel="tooltip" title="Modifier" class="modifMouvBtn btn btn-info btn-simple btn-xs">
                                                    <i class="fa fa-edit"></i>
                                                </button>

                                                <button type="button" value="${Mouvement.id}" rel="tooltip" title="Supprimer" class="deleteMouvBtn btn btn-danger btn-simple btn-xs">
                                                    <i class="fa fa-times"></i>
                                                </button>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    </tbody>
                                </table>

                            </div>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Il vous reste : ${Solde}€</h4>
                                <p class="category">Somme des revenus et dépenses</p>
                            </div>
                            <canvas id="BarChart" max-width="381" max-height="381"></canvas>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Répartition des dépenses</h4>
                            </div>
                            <canvas id="myChart" max-width="381" max-height="381"></canvas>
                        </div>
                    </div>

                </div>

            </div>
        </div>
        <footer class="footer">
            <div class="container-fluid">
                <p class="copyright pull-right">
                    &copy; 2017 Hamza Miya.
                </p>
            </div>
        </footer>

    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">Ajouter un mouvement</h4>
            </div>
            <div class="modal-body">
                <form id="FormAddMouv" class="">
                    <div class="form-group">
                        <label for="labelMouv">Désignation</label>
                        <input type="text" id="labelMouv" class="form-control" placeholder="Ex : Loyer, Cinéma...">
                    </div>
                    <div class="form-group">
                        <label for="montantMouv">Montant</label>
                        <input type="number" id="montantMouv" class="form-control" placeholder="Ex : 350">
                    </div>
                    <div class="form-group">
                        <input type="radio" name="signeMouv" value="-" checked><i class="pe-7s-less"></i>
                        <input type="radio" name="signeMouv" value="+"><i class="pe-7s-plus"></i>
                    </div>
                    <button type="button" id="AddMouvBtn" class="btn btn-info btn-fill pull-right">Ajouter</button>
                </form>
            </div>
            <div class="modal-footer">
            </div>
        </div>
    </div>
</div>

<input type="hidden" id="dataGraph" value="<c:forEach items='${Depenses}' var='depense'>${depense.montant},</c:forEach>"/>
<input type="hidden" id="labelGraph" value='<c:forEach items="${Depenses}" var="depense">${depense.label},</c:forEach>'/>
<input type="hidden" id="colorGraph" value='#FF6384,#36A2EB,#FFCE56,#22CECE,#CBD1DD,#ea80fc,#ff9100,#FFCE56,#CBD1DD,#ea80fc,#ff9100'/>
<input type="hidden" id="BarChartR" value="${SommeRecettes}"/>
<input type="hidden" id="BarChartD" value="${SommeDepenses}"/>

<%@ include file="foot.jsp" %>

<spring:url value="/resources/js/init_graph.js" var="initGraph" />
<script type="text/javascript" src="${initGraph}" ></script>

</body>

<script type="text/javascript">

</script>

</html>