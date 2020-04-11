<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
  <head>
      <meta http-equiv="content-type" content="text/html; charset=UTF-8">
      <title>Home Page</title>
  </head>
<body>
<div id="content">
<h1>Sistema de Gestión</h1>

<p>Hola <%=request.getUserPrincipal().getName()%>; este es el menu de la aplicación:</p>

<p>Este es el login que uso: <%=request.getRemoteUser()%></p>
<p>Solo puede acceder a los enlaces con una flecha</p>

<h3>Módulo Ventas</h3></td>
<p><a href="ventas/gestion_clientes.jsp">Operación: gestionar clientes</a>
<% if (request.isUserInRole("GV") || request.isUserInRole("AU")){ %>
	<i style="font-size:24px;">-></i>
<% { %>
</p>
<p><a href="ventas/gestion_presupuestos.jsp">Operación: gestionar presupuestos</a>
<% if (request.isUserInRole("GV")){ %>
        <i style="font-size:24px;">-></i>
<% { %>
</p>
<p><a href="ventas/gestion_facturas.jsp">Operación: gestionar facturas</a>
<% if (request.isUserInRole("GV")){ %>
        <i style="font-size:24px;">-></i>
<% { %>
</p>

<h3>Módulo Compras</h3></td>
<p><a href="compras/gestion_proveedores.jsp">Operación: gestionar proveedores</a>
<% if (request.isUserInRole("GC")){ %>
        <i style="font-size:24px;">-></i>
<% { %>
</p>
<p><a href="compras/gestion_compras.jsp">Operación: gestionar compras</a>
<% if (request.isUserInRole("GC")){ %>
        <i style="font-size:24px;">-></i>
<% { %>
</p>
<p><a href="compras/autorizar_compras.jsp">Operación: autorizar compras</a>
<% if (request.isUserInRole("GV") || request.isUserInRole("AU")){ %>
        <i style="font-size:24px;">-></i>
<% { %>
</p>

<h3>Módulo Nóminas</h3></td>
<p><a href="nominas/gestion_trabajadores.jsp">Operación: gestionar trabajadores</a>
<% if (request.isUserInRole("GN")){ %>
        <i style="font-size:24px;">-></i>
<% { %>
</p>
<p><a href="nominas/gestion_nominas.jsp">Operación: gestionar nóminas</a>
<% if (request.isUserInRole("GN") || request.isUserInRole("AU")){ %>
        <i style="font-size:24px;">-></i>
<% { %>
</p>

<p><a href='<%= response.encodeURL("../index.jsp?logoff=true") %>'>Salida</a></p>


</body>
</html>
