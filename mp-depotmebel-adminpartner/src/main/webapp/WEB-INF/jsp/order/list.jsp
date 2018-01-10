<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Order Management</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  
  <link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.min.css" />">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <link rel="stylesheet" href="<c:url value="/lte/dist/css/AdminLTE.min.css" />">
  <link rel="stylesheet" href="<c:url value="/lte/dist/css/skins/skin-blue.min.css" />">
  <link rel="stylesheet" href="<c:url value="/biel/biel-table.css" />">
  <link rel="stylesheet" href="<c:url value="/biel/style.css" />">
  <link rel="stylesheet" href="<c:url value="/lte/plugins/datatables/dataTables.bootstrap.css" />">
  <%-- <c:url value="/lte/" /> --%>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

<%@ include file="../general/header.jsp" %>
<%@ include file="../general/sidebar-left.jsp" %>

<div class="content-wrapper">
	<section class="content-header">
    	<h1>
        	Order Management
        	<small>Manage all transaction</small>
      	</h1>
      	<ol class="breadcrumb">
        	<li><a href="<c:url value="/home"  />"><i class="fa fa-dashboard"></i> Home</a></li>
        	<li class="active">Vendor</li>
      	</ol>
	</section>
			
    <section class="content">
    	<div class="row">
	    	<!--Unallocated Orders-->
    		<div class="col-lg-3 col-sm-6 col-xs-12">
      			<div class="small-box bg-red">
			        <div class="inner">
			          <h3>${listbean.unalloactedNum}</h3>
			          <p>Unallocated</p>
			        </div>
			        <div class="icon">
			          <i class="fa fa-warning"></i>
			        </div>
			        <a href="<c:url value="/order/unallocateds" />" class="small-box-footer" >
			          View
			          <i class="fa fa-arrow-right"></i>
			        </a>
      			</div>
    		</div>
    		<div class="col-lg-3 col-sm-6 col-xs-12">
      			<div class="small-box bg-yellow">
			        <div class="inner">
			          <h3>${listbean.readyQcNum}</h3>
			          <p>Ready to QC</p>
			        </div>
			        <div class="icon">
			          <i class="fa fa-envelope-o"></i>
			        </div>
			        <c:choose>
				        <c:when test="${listbean.readyQcNum<1}">
				        	<a class="small-box-footer">
					          View
					          <i class="fa fa-arrow-right"></i>
					        </a>
				        </c:when>
				        <c:otherwise>
				        	<a href="<c:url value="/order/qclist" />" class="small-box-footer">
					          View
					          <i class="fa fa-arrow-right"></i>
					        </a>
				        </c:otherwise>	
			        </c:choose>
      			</div>
    		</div>
  		</div>
  		<div class="row">
    		<div class="col-md-12">
      			<div class="box box-default">
			        <div class="box-header with-border">
			          <h3 class="box-title">All Orders</h3>
			        </div>
	        		<div class="box-body">
		          		<!-- <div class="input-group box__item box__item--first">
		            		<input type="text" class="form-control" placeholder="Search order">
				            <span class="input-group-btn">
				              <button type="button" class="btn btn-default btn-flat">Search</button>
				            </span>
		          		</div> -->
	          			<div class="box-item">
		          			<div class="table-responsive">
		          				<table id="tbl_list" class="table table-hover">
					            <thead>
					            	<tr>
					            		<th>#</th>
					            		<th>Order No.</th>
					            		<th>Status</th>
					            		<th>Produk</th>
					            		<th>Vendor</th>
					            		<!-- <th>SLA Time</th> -->
					            		<th>Created Date</th>
					            		<th>Customer</th>
					            	</tr>
					            </thead>
		              			<tbody>
		              			<c:forEach items="${listbean.allList}" var="obj" varStatus="vs">
		              				<c:set var="bgcolor_" />
		              				<c:choose>
		              				<c:when test="${obj.status[0]=='1'}">
		              					<c:set var="bgcolor_" value="#FF0000" />
		              				</c:when>
		              				</c:choose>
		              				
		              				<tr>
						                <td>${vs.count}</td>
						                <td>
						                	<a href="<c:url value="/order/view?x=${obj.id}" />">${obj.no}</a>
						                </td>
						                <td>${obj.status[1]},<%-- ${obj.status[0]} --%><br />(${obj.status[2]})</td>
						                <td>${obj.itemArray[0]},<br />${obj.itemArray[1]}</td>
						                <td>${obj.vendor}</td>
						                <%-- <td>${obj.sla}</td> --%>
						                <td>${obj.created}</td>
						                <td>
						                  ${obj.customer[1]}
						                </td>
			              			</tr>
		              			</c:forEach>
		              			</tbody>
		            			</table>
		          			</div>
	          			</div>
	        		</div>
			        <!-- <div class="box-footer">
			          <ul class="pagination pagination-sm no-margin pull-right">
			            <li><a href="#">«</a></li>
			            <li><a href="#">1</a></li>
			            <li><a href="#">2</a></li>
			            <li><a href="#">3</a></li>
			            <li><a href="#">»</a></li>
			          </ul>
			        </div> -->
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
<script src="<c:url value="/lte/plugins/datatables/jquery.dataTables.min.js" />"></script>
<script src="<c:url value="/lte/plugins/datatables/dataTables.bootstrap.min.js" />"></script>
<script type="text/javascript">
$(function () {
	$("#tbl_list").DataTable();
});
</script>

</body>
</html>
