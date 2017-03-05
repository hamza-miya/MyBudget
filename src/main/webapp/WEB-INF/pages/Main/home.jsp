<%-- Created by IntelliJ IDEA. --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns:form="http://www.w3.org/1999/xhtml" xmlns:spring="" xmlns:c="http://www.w3.org/1999/XSL/Transform">

<%@ include file="head.jsp" %>

  <body>

  <form:form method="POST" modelAttribute="User" action="/addUser">
      <div class="header">
        <p>Inscription</p>
      </div>
      <div class="description">
        <p>Avec MyBudget la gestion de votre budget devient un plaisir.</p>
      </div>

      <form:errors path="*" cssClass="errorblock" element="div" />
      <div class="input">
        <label for="message">Prénom : </label>
        <form:input type="text" id="fname" class="form-control" path="fname" />
        <form:errors path="fname" cssClass="error" />
      </div>
      <div class="input">
        <label for="message">Nom : </label>
        <form:input type="text" id="lname" class="form-control" path="lname" />
        <form:errors path="lname" cssClass="error" />
      </div>
      <div class="input">
        <label for="message">Email : </label>
        <form:input type="text" id="email" class="form-control" path="email" />
        <form:errors path="email" cssClass="error" />
      </div>
      <div class="input">
        <label for="message">Mot de passe : </label>
        <td><form:input type="text" id="mdp" class="form-control" path="mdp" /></td>
        <td><form:errors path="mdp" cssClass="error" /></td>
      </div>
      <div class="input">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Enregister</button>
      </div>
  </form:form>

  <c:if test="${not empty sessionScope.User.fname}" >
      <form:form method="POST" action="/deleteSession">
          <form:errors path="*" cssClass="errorblock" element="div" />
          <button class="btn btn-lg btn-primary btn-block" type="submit">Se déconnecter : ${sessionScope.User.fname} ${sessionScope.User.lname}</button>
      </form:form>
  </c:if>

  <%@ include file="foot.jsp" %>

  </body>

<script type="text/javascript">
    $(document).ready(function(){

        demo.initChartist();

        $.notify({
            icon: 'pe-7s-gift',
            message: "Bienvenue sur <b>MyBudget</b> - un super outil pour gérer vos ressources financières."
        },{
            type: 'info',
            timer: 4000
        });

    });
</script>


</html>