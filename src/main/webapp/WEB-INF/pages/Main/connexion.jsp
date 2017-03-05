<%-- Created by IntelliJ IDEA. --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:spring="" xmlns:c="http://www.w3.org/1999/XSL/Transform">

<head xmlns:spring="http://java.sun.com/xml/ns/javaee">
    <title>MyBudget</title>
    <meta charset="UTF-8">
    <link rel="icon" type="image/png" href="/resources/img/favicon.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
    <meta name="viewport" content="width=device-width" />

    <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
    <link href="${bootstrapCss}" rel="stylesheet" />
    <spring:url value="/resources/css/formStyle.css" var="formCss" />
    <link href="${formCss}" rel="stylesheet" />

</head>

  <body>

  <div class="container">
      <div class="row">
          <div class="col-md-6 col-md-offset-3">
              <div class="panel panel-login">
                  <div class="panel-heading">
                      <div class="row">
                          <div class="col-xs-6">
                              <a href="#" class="active" id="login-form-link">Se connecter</a>
                          </div>
                          <div class="col-xs-6">
                              <a href="#" id="register-form-link">S'inscrire</a>
                          </div>
                      </div>
                      <hr>
                  </div>
                  <div class="panel-body">
                      <div class="row">
                          <div class="col-lg-12">
                              <form:form id="login-form" method="POST" modelAttribute="UserLoginForm" action="/login" role="form" style="display: block;">
                                  <div class="form-group">
                                      <form:input type="email" id="emailLogin" class="form-control" tabindex="1" path="emailLogin" placeholder="Identifiant" />
                                      <form:errors path="emailLogin" cssClass="alert-danger" />
                                      <c:if test="${not empty MessageIdMdpInvalid}" >
                                          <span class="alert-danger"> ${MessageIdMdpInvalid} </span>
                                      </c:if>
                                  </div>
                                  <div class="form-group">
                                      <form:input type="password" id="mdpLogin" class="form-control" tabindex="2" path="mdpLogin" placeholder="Mot de passe"/>
                                      <form:errors path="mdpLogin" cssClass="alert-danger" />
                                  </div>
                                  <!--<div class="form-group text-center">
                                      <input type="checkbox" tabindex="3" class="" name="remember" id="remember">
                                      label for="remember"> Remember Me</label>
                                  </div> -->
                                  <div class="form-group">
                                      <div class="row">
                                          <div class="col-sm-6 col-sm-offset-3">
                                              <input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Connexion">
                                          </div>
                                      </div>
                                  </div>
                                  <!--<div class="form-group">
                                      <div class="row">
                                          <div class="col-lg-12">
                                              <div class="text-center">
                                                  <a href="#" tabindex="5" class="forgot-password">Forgot Password?</a>
                                              </div>
                                          </div>
                                      </div>
                                  </div>-->
                              </form:form>
                              <form:form id="register-form" method="POST" modelAttribute="UserRegisterForm" style="display: none;" action="/addUser" role="form">
                                  <div class="form-group">
                                      <form:input type="text" id="fnameRegister" class="form-control" tabindex="1" path="fnameRegister" placeholder="Prénom" />
                                      <form:errors path="fnameRegister" cssClass="alert-danger" />
                                  </div>
                                  <div class="form-group">
                                      <form:input type="text" id="lnameRegister" class="form-control" tabindex="1" path="lnameRegister" placeholder="Nom" />
                                      <form:errors path="lnameRegister" cssClass="alert-danger" />
                                  </div>
                                  <div class="form-group">
                                      <form:input type="email" id="emailRegister" class="form-control" tabindex="1" path="emailRegister" placeholder="Email" />
                                      <form:errors path="emailRegister" cssClass="alert-danger" />
                                      <c:if test="${not empty MessageUserExist}" >
                                          <span class="alert-danger"> ${MessageUserExist} </span>
                                      </c:if>
                                  </div>
                                  <div class="form-group">
                                      <form:input type="password" id="mdpRegister" class="form-control" tabindex="2" path="mdpRegister" placeholder="Mot de passe" />
                                      <form:errors path="mdpRegister" cssClass="alert-danger" />
                                  </div>
                                  <div class="form-group">
                                      <form:input type="password" id="mdp2Register" class="form-control" tabindex="2" path="mdp2Register" placeholder="Confirmation mot de passe" />
                                      <form:errors path="mdp2Register" cssClass="alert-danger" />
                                      <c:if test="${not empty MessageMdp}" >
                                          <span class="alert-danger"> ${MessageMdp} </span>
                                      </c:if>
                                  </div>
                                  <div class="form-group">
                                      <div class="row">
                                          <div class="col-sm-6 col-sm-offset-3">
                                              <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Enregistrer">
                                          </div>
                                      </div>
                                  </div>
                              </form:form>
                          </div>
                      </div>
                  </div>
              </div>
          </div>
      </div>
  </div>

  </body>

<spring:url value="/resources/js/jquery-1.10.2.js" var="jqueryJs" />
<script type="text/javascript" src="${jqueryJs}" ></script>

<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJs" />
<script type="text/javascript" src="${bootstrapJs}" ></script>

<script type="text/javascript">
    $(function() {

        $('#login-form-link').click(function(e) {
            $("#login-form").delay(100).fadeIn(100);
            $("#register-form").fadeOut(100);
            $('#register-form-link').removeClass('active');
            $(this).addClass('active');
            e.preventDefault();
        });
        $('#register-form-link').click(function(e) {
            $("#register-form").delay(100).fadeIn(100);
            $("#login-form").fadeOut(100);
            $('#login-form-link').removeClass('active');
            $(this).addClass('active');
            e.preventDefault();
        });

        //au submit du register mettre la variable de sessionStorage à register (et inverse)
        $('#register-submit').click(function(e) {
            sessionStorage.setItem("formShow", "register");
        });
        $('#login-submit').click(function(e) {
            sessionStorage.setItem("formShow", "login");
        });
        var formShow = sessionStorage.getItem("formShow");
        //if form = register alors afficher d'abord register form
        if(formShow == "register"){
            $("#register-form").delay(10).fadeIn(10);
            $("#login-form").fadeOut(10);
            $('#login-form-link').removeClass('active');
            $('#register-form-link').addClass('active');
        }else if(formShow == "login"){
            $("#login-form").delay(10).fadeIn(10);
            $("#register-form").fadeOut(10);
            $('#register-form-link').removeClass('active');
            $('#login-form-link').addClass('active');
        }

    });
</script>



</html>