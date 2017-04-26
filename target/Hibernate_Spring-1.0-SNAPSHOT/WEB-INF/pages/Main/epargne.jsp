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
                        <p>Mon épargne</p>
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
                    <a class="navbar-brand" href="/epargne">Mon Épargne</a>
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

                    <div class="col-md-4">
                        <div class="card">
                            <div class="header">
                                <h2 class="title">Mes Projets </h2>
                                <p class="category">Epargne potentielle : ${sessionScope.EpargneP}€</p>
                                <br />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row" id="rowListProjet">

                    <c:forEach items="${Projets}" var="Projet">
                        <div class="col-md-3 each-projet">
                            <div class="card card-user">
                                <div class="image" style="background-color: yellowgreen">
                                    <div class="row" style="text-align: right">
                                        <div class="col-md-6"></div>
                                        <div class="col-md-6 nbIncr">
                                            <span class="montant_moisProj">+${Projet.montant_epargneParMois}€</span>
                                        </div>
                                    </div>
                                    <!-- img -->
                                </div>
                                <div class="content" style="min-height: 0; text-align: center;">
                                    <div class="author">

                                        <img class="avatar border-gray" src="/resources/img/tirelire-economie.jpg">

                                        <h4 class="title"><span class="labelProj">${Projet.label}</span>
                                            <br>
                                            <small><span class="montant_acquisProj">${Projet.montant_acquis}</span>€</small> / <small><span class="montant_objProj">${Projet.montant_objectif}</span>€</small>
                                        </h4>

                                    </div>
                                    <div class="projProgress">
                                        <progress value="${Projet.montant_acquis}" max="${Projet.montant_objectif}"></progress>
                                    </div>
                                </div>
                                <hr>
                                <div class="text-center">
                                    <button type="button" rel="" value="${Projet.id}" title="Modifier" class="modifProjBtn btn btn-info btn-simple btn-xs">
                                        <i class="fa fa-edit"></i>
                                    </button>
                                    <button type="button" rel="tooltip" value="${Projet.id}" title="Enregistrer" class="secondBtnSave invisible btn btn-info btn-simple btn-xs">
                                        <i class="fa fa-floppy-o"></i>
                                    </button>
                                    <button type="button" rel="tooltip" value="${Projet.id}" title="Supprimer" class="deleteProjBtn btn btn-danger btn-simple btn-xs">
                                        <i class="fa fa-times"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                </div>

                <div class="row">

                    <form id="FormAddProjet">
                        <div class="form-group">
                            <label for="labelProj">Désignation </label>
                            <input type="text" id="labelProj" class="form-control" placeholder="Ex : Vacances, Voiture...">
                        </div>
                        <div class="form-group">
                            <label for="montantEp">Montant épargné chaque mois </label>
                            <input type="number" id="montantEp" class="form-control" placeholder="Ex : 100">
                        </div>
                        <div class="form-group">
                            <label for="montantObj">Objectif à atteindre </label>
                            <input type="number" id="montantObj" class="form-control" placeholder="Ex : 2300">
                        </div>
                        <button type="button" id="AddProjBtn" class="btn btn-info btn-fill pull-right">Ajouter</button>
                    </form>

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

<input type="hidden" id="" value="${SommeDepenses}"/>

<%@ include file="foot.jsp" %>

</body>

<script type="text/javascript">

</script>

</html>