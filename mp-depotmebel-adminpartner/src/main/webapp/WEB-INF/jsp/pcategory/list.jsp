<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Product Line</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  
  <link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.min.css" />">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <link rel="stylesheet" href="<c:url value="/lte/dist/css/AdminLTE.min.css" />">
  <link rel="stylesheet" href="<c:url value="/lte/dist/css/skins/skin-blue.min.css" />">
  <link rel="stylesheet" href="<c:url value="/biel/style.css"/>">
  <%-- <c:url value="/lte/" /> --%>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

	<%@ include file="../general/header.jsp" %>
	<%@ include file="../general/sidebar-left.jsp" %>

	<div class="content-wrapper">
 		<section class="content-header">
   			<h1>
     			Category
     			<small>Configure category</small>
   			</h1>
   			<ol class="breadcrumb">
     			<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
     			<li><a href="<c:url value="/product" />">Product</a></li>
     			<li class="active">Category</li>
   			</ol>
 		</section>

 		<section class="content">
 			<a href="<c:url value="/category/create" />" class="btn btn-primary page__button">New Category</a>
		  	<a href="<c:url value="/category/"/>" class="btn btn-default page__button">Back</a>
 			<div class="box box-default">
			    <div class="box-header with-border">
			      	<h3 class="box-title">Categories</h3>
			    </div>
			    <div class="box-body table-responsive">
			      <table class="table table-hover">
			        <thead>
			        <tr>
			          <th>#</th>
			          <th>Name</th>
			          <th>Status</th>
			          <th>Action</th>
			        </tr>
			        </thead>
			        <tbody>
			        <% int no = 1; %>
			        <c:forEach var="obj" items="${beanlist}">
				        <tr>
				          <td><%= no++ %></td>
				          <td>${obj.name}</td>
				          <td>${obj.status}</td>
				          <td>
				            <a href="<c:url value="/category/detail?q=${obj.id}"/>" class="btn btn-default btn-xs">View</a>
				          </td>
				        </tr>
			        </c:forEach>
			        </tbody>
			      </table>
			    </div>
		  	</div>	
 		</section>
	</div>

	<%@ include file="../general/footer.jsp" %>
	<%-- <%@ include file="../general/sidebar-control.jsp" %> --%>
 
</div>

<%-- <c:url value="/lte/" /> --%>
<script src="<c:url value="/lte/plugins/jQuery/jquery-2.2.3.min.js" />"></script>
<script src="<c:url value="/bootstrap/js/bootstrap.min.js" /> "></script>
<script src="<c:url value="/lte/dist/js/app.min.js" />"></script>

<!-- REQUIRED JS SCRIPTS -->



</body>
</html>
