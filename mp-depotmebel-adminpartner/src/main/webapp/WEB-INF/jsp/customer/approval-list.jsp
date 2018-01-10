<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Organization Approval List</title>
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
		        	Organization Approval List
		        	<small>List of organization requests</small>
		      	</h1>
		      	<ol class="breadcrumb">
		        	<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
		        	<li class="active">Organization</li>
		    	</ol>
		    </section>

    		<section class="content page">
    			<div class="box box-primary">
			        <div class="box-header with-border">
			          <h3 class="box-title">Organization Requests</h3>
			        </div>
			        <div class="box-body">
          				<div class="table-responsive">
	          				<table class="table table-hover">
	          				<thead>
				            <tr>
				                <th>#</th>
				                <th>Requester</th>
				                <th>Company</th>
				                <th>Action</th>
				            </tr>
				            </thead>
				            <tbody>
				            <c:forEach items="${list}" var="obj" varStatus="vs">
				            <tr>
				                <td>${vs.count}</td>
				                <td>${obj.name}</td>
				                <td>${obj.corptName}</td>
				                <td>
				                  <a href="<c:url value="detail?id=${obj.id}" />" type="button" class="btn btn-default btn-xs">View</a>
				                </td>
				            </tr>
				            </c:forEach>
				            </tbody>
	          				</table>
          				</div>
			        </div>
				</div>	        
    		</section>
  		</div>
  		
  		<div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  	<div class="modal-dialog modal-sm" role="document">
		    	<div class="modal-content">
		    		<form action="<c:url value="/bg/delete"/>" method="post">
			      		<div class="modal-header">
				        	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        	<h4 class="modal-title" id="myModalLabel">Delete</h4>
				      	</div>
		      			<div class="modal-body">
		        			Are you sure?
		        			<input type="hidden" id="del_code" name="del_code" >
		      			</div>
				      	<div class="modal-footer">
					        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					        <button type="submit" class="btn btn-primary">Delete</button>
				      	</div>
		    		</form>  
		    	</div>
		  	</div>
		</div>

  		<%@ include file="../general/footer.jsp" %>
  		<%-- <%@ include file="../general/sidebar-control.jsp" %> --%>
	</div>

<%-- <c:url value="/lte/" /> --%>
<script src="<c:url value="/lte/plugins/jQuery/jquery-2.2.3.min.js" />"></script>
<script src="<c:url value="/bootstrap/js/bootstrap.min.js" /> "></script>
<script src="<c:url value="/lte/dist/js/app.min.js" />"></script>

<script type="text/javascript">
function showDeleteModal(code) {
	$("#del_code").val(code);
	$('#delModal').modal('show');
}
</script>



</body>
</html>
