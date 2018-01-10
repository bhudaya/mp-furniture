<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Products</title>
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
     			Product Management
     			<small>Manage all product</small>
   			</h1>
   			<ol class="breadcrumb">
     			<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
     			<li class="active">Product</li>
   			</ol>
 		</section>

 		<section class="content">
 			<div class="row">
			    <div class="col-lg-3 col-sm-6 col-xs-12">
			      <div class="small-box bg-blue">
			        <div class="inner">
			          <h3>${prodbean.sumProduct}</h3>
			          <p>Products</p>
			        </div>
			        <div class="icon">
			          <i class="fa fa-shopping-bag"></i>
			        </div>
			        <a href="<c:url value="/product/create" />" class="small-box-footer">
			          Create
			          <i class="fa fa-cog"></i>
			        </a>
			      </div>
			    </div>
			    <div class="col-lg-3 col-sm-6 col-xs-12">
			      <div class="small-box bg-green">
			        <div class="inner">
			          <h3>${prodbean.sumCategory}</h3>
			          <p>Category</p>
			        </div>
			        <div class="icon">
			          <i class="fa fa-shopping-bag"></i>
			        </div>
			        <a href="<c:url value="/category" />" class="small-box-footer">
			          Configure
			          <i class="fa fa-cog"></i>
			        </a>
			      </div>
			    </div>
			  </div>
			  <div class="box box-primary">
			    <div class="box-header with-border">
			      <div class="input-group box__item box__item box__item--first">
			        <input type="text" class="form-control" placeholder="Search product">
			        <span class="input-group-btn">
			          <button type="button" class="btn btn-default btn-flat">Search</button>
			        </span>
			      </div>
			    </div>
			    <div class="box-body">
			      <div class="row">
			        <c:forEach var="prod" items="${prodbean.products}">
			        <div class="col-md-2">
			          <div class="catalog">
			            <div class="catalog__item">
			              <div class="catalog__image">
			                <div class="image" style="background-image: url('${prod.image}');"></div>
			                <div class="catalog__action">
			                  <a href="<c:url value="/product/detail?q=${prod.id}"/>" class="btn btn-default btn-sm">View</a>
			                </div>
			              </div>
			              <div class="catalog__product text-center">
			                <p class="catalog__product__title">${prod.name}</p>
			                <p class="catalog__product__subtitle">Rp ${prod.basePriceIdr}</p>
			              </div>
			            </div>
			          </div>
			        </div>
			        </c:forEach>
			      </div>
			    </div>
			    <div class="box-footer">
			      <ul class="pagination pagination-sm no-margin pull-right">
			        <li><a href="#">«</a></li>
			        <!-- <li><a href="#">1</a></li>
			        <li><a href="#">2</a></li>
			        <li><a href="#">3</a></li> -->
			        <li><a href="#">»</a></li>
			      </ul>
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
