<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Category Detail</title>
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
     			Category Detail
     			<small></small>
   			</h1>
   			<ol class="breadcrumb">
     			<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
     			<li><a href="#">Category</a></li>
     			<li class="active">Detail</li>
   			</ol>
 		</section>

 		<section class="content">
 			
 			<a href="<c:url value="/category/"/>" class="btn btn-default page__button">Back</a>
 			
  			<div class="row">
    			<div class="col-md-5 col-sm-12">
		      		<!--General Information-->
		      		<div class="box box-primary">
        				<div class="box-header with-border">
          					<h3 class="box-title">General Information</h3>
          					<div class="box-tools pull-right">
            					<button class="btn btn-box-tool" onclick="modal_show_general();"><i class="fa fa-pencil fa-fw"></i></button>
          					</div>
        				</div>
        				<div class="box-body">
          					<div class="form-group">
            					<label class="control-label">Name</label>
            					<p class="form-control-static">${obj.name}</p>
          					</div>
				          	<div class="form-group">
				            	<label class="control-label">Status</label>
				            	<p class="forom-control-static">
			            			<span class="bg-green">&nbsp;${obj.status}&nbsp;</span>
				            	</p>
				          	</div>
        				</div>
      				</div>
      				<div class="box box-default">
        				<div class="box-header with-border">
          					<h3 class="box-title">Sub Category</h3>
        				</div>
        				<div class="box-body">
        					<button class="btn btn-default">Add</button>
          					<div class="table-responsive">
          						<table class="table table-hover">
		              			<thead>
		              				<tr>
		                				<th>No.</th>
		                				<th>Name</th>
		                				<th>Action</th>
		              				</tr>
		              			</thead>
		              			<tbody>
		              			<c:forEach items="${obj.subList}" var="item" varStatus="vs">
		              				<tr>
					              		<td>${vs.count}</td>
					              		<td>${item[1]}</td>
					              		<td>#</td>
					              	</tr>
		              			</c:forEach>
		              			</tbody>
		              			</table>
          					</div>
        				</div>
      				</div>
    			</div>
    			<div class="col-md-7 col-sm-12">
			      	<!--Images-->
			      	<div class="box box-default">
	        			<div class="box-header with-border">
	          				<h3 class="box-title">Specification</h3>
	        			</div>
	        			<div class="box-body">
	          				<div class="table-responsive">
          						<table class="table table-hover">
		              			<thead>
		              				<tr>
		                				<th>No.</th>
		                				<th>Name</th>
		                				<th>Action</th>
		              				</tr>
		              			</thead>
		              			<tbody>
		              			<c:forEach items="${obj.specList}" var="item" varStatus="vs">
		              				<tr>
					              		<td>${vs.count}</td>
					              		<td>${item[1]}</td>
					              		<td>#</td>
					              	</tr>
		              			</c:forEach>
		              			</tbody>
		              			</table>
          					</div>
	        			</div>
	      			</div>
			      	<!--Category-->
			      	<div class="box box-default">
			        	<div class="box-header with-border">
			          		<h3 class="box-title">Attribute</h3>
			        	</div>
				        <div class="box-body">
				          	<div class="table-responsive">
          						<table class="table table-hover">
		              			<thead>
		              				<tr>
		                				<th>No.</th>
		                				<th>Name</th>
		                				<th>Action</th>
		              				</tr>
		              			</thead>
		              			<tbody>
		              			<c:forEach items="${obj.attrList}" var="item" varStatus="vs">
		              				<tr>
					              		<td>${vs.count}</td>
					              		<td>${item[1]}</td>
					              		<td>#</td>
					              	</tr>
		              			</c:forEach>
		              			</tbody>
		              			</table>
          					</div>
	        			</div>
      				</div>
    			</div>
  			</div>
 		</section>
	</div>

	<%@ include file="../general/footer.jsp" %>
</div>

<%-- <c:url value="/lte/" /> --%>
<script src="<c:url value="/lte/plugins/jQuery/jquery-2.2.3.min.js" />"></script>
<script src="<c:url value="/bootstrap/js/bootstrap.min.js" /> "></script>
<script src="<c:url value="/lte/dist/js/app.min.js" />"></script>

<script type="text/javascript">
$(document).ready(function(){
    //$("#errmsg").hide();
});

</script>


</body>
</html>
