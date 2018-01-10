<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Vendor List</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  
  <link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.min.css" />">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <link rel="stylesheet" href="<c:url value="/lte/dist/css/AdminLTE.min.css" />">
  <link rel="stylesheet" href="<c:url value="/lte/dist/css/skins/skin-blue.min.css" />">
  <link rel="stylesheet" href="<c:url value="/biel/biel-table.css" />">
  <link rel="stylesheet" href="<c:url value="/biel/style.css" />">
  <%-- <c:url value="/lte/" /> --%>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

<%@ include file="../general/header.jsp" %>
<%@ include file="../general/sidebar-left.jsp" %>

<div class="content-wrapper">
	<section class="content-header">
    	<h1>
        	Vendor Management
        	<small>Manage all vendors</small>
      	</h1>
      	<ol class="breadcrumb">
       		<li><a href="<c:url value="/home"  />"><i class="fa fa-dashboard"></i> Home</a></li>
        	<li class="active">Vendor</li>
		</ol>
    </section>
			
    <section class="content">
    	<a href="<c:url value="/vendor/add"/>  " class="btn btn-primary" style="margin-bottom: 7px;">New Vendor</a>
		
		<div class="box box-primary">
			<div class="box-header with-border">
				<h3 class="box-title">Vendor List</h3>
			</div>
			<div class="box-body">
				<div class="input-group box__item box__item--first">
					<input type="text" class="form-control" placeholder="Search vendor">
				    <span class="input-group-btn">
				    	<button type="button" class="btn btn-default btn-flat">Search</button>
					</span>
				</div>
				<div class="box__item--last">
					<div style="overflow-x:auto;">
				    	<table class="table table-hover">
					  	<thead>
						<tr>
					    	<th>#</th>
					    	<th>Vendor</th>
					    	<th>PIC</th>
							<th>Address</th>
					        <th>Status</th>
					        <th>Action</th>
						</tr>
						</thead>
						<tbody>
						<% int no = 1; %>
		                <c:forEach var="obj" items="${objList}">
		                	<tr>
					            <td><%= no++ %></td>
					            <td>${obj.name}</td>
					            <td>${obj.picName}</td>
					            <td>${obj.address}</td>
					            <td>${obj.status}</td>
					            <td>
					              <a href="<c:url value="/vendor/detail?q=${obj.id}"/>" class="btn btn-default btn-xs">View</a>
					            </td>
							</tr>
		                </c:forEach>
					    </tbody>
						</table>
					</div>
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
</div>

<%-- <c:url value="/lte/" /> --%>
<script src="<c:url value="/lte/plugins/jQuery/jquery-2.2.3.min.js" />"></script>
<script src="<c:url value="/bootstrap/js/bootstrap.min.js" /> "></script>
<script src="<c:url value="/lte/dist/js/app.min.js" />"></script>

<script type="text/javascript">
</script>



</body>
</html>
