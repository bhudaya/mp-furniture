<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Organization Approval Detail</title>
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
		        	Organization Approval
        			<small>Approve submitted organization request</small>
		      	</h1>
		      	<ol class="breadcrumb">
		        	<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
			        <li>Organization</li>
			        <li class="active">Approval</li>
		    	</ol>
		    </section>

    		<section class="content page">
    			<!--Requester-->
    			<div class="box box-primary">
			        <div class="box-header with-border">
			           <h3 class="box-title">Requester</h3>
			        </div>
			        <div class="box-body">
          				<div class="form-horizontal">
          					<div class="form-group">
				              <label class="col-sm-3 control-label">Username</label>
				              <div class="col-sm-9">
				                <p class="form-control-static">${bean.name}</p>
				              </div>
				            </div>
				            <div class="form-group">
				              <label class="col-sm-3 control-label">Email</label>
				              <div class="col-sm-9">
				                <p class="form-control-static">${bean.email}</p>
				              </div>
				            </div>
				            <div class="form-group">
				              <label class="col-sm-3 control-label">Phone</label>
				              <div class="col-sm-9">
				                <p class="form-control-static">${bean.phone}</p>
				              </div>
				            </div>
          				</div>
			        </div>
				</div>	    
				<!--Organization Detail-->
			    <div class="box box-primary">
			    	<div class="box-header with-border">
			          <h3 class="box-title">Organization Detail</h3>
			        </div>
			        <div class="box-body">
          				<form class="form-horizontal">
          					<div class="form-group">
				            	<label class="col-sm-3 control-label">Organization name</label>
				              	<div class="col-sm-9">
				                	<input type="text" class="form-control" value="${bean.corptName}"/>
				              	</div>
				            </div>
				            <div class="form-group">
				            	<label class="col-sm-3 control-label">Organization Legal Name</label>
				              	<div class="col-sm-9">
				                	<input type="text" class="form-control" value="${bean.corptLegalName}"/>
				              	</div>
				            </div>
				            <div class="form-group">
				              	<label class="col-sm-3 control-label">Organization category</label>
				              	<div class="col-sm-9">
				                	<select class="form-control">
				                  		<option>Pemerintah</option>
				                  		<option>BUMN</option>
				                  		<option>Swasta</option>
				                	</select>
				              	</div>
				            </div>
				            <div class="form-group">
				              	<label class="col-sm-3 control-label">Organization NPWP</label>
				              	<div class="col-sm-9">
				                	<input type="text" class="form-control" value="${bean.corptNpwp}"/>
				              	</div>
				            </div>
				            <hr>
				            <c:forEach items="${bean.corptContacts}" var="contact">
					            <h4>Contact ${fn:length(corptContacts) gt 1?'fn:length(corptContacts)':''}</h4>
					            <div class="form-group">
					              	<label class="col-sm-3 control-label">Label</label>
					              	<div class="col-sm-9">
					                	<input type="text" class="form-control" value="${contact.label}"/>
					              	</div>
					            </div>
					            <div class="form-group">
					              	<label class="col-sm-3 control-label">Contact Name</label>
					              	<div class="col-sm-9">
					                	<input type="text" class="form-control" value="${contact.picName}"/>
					              	</div>
					            </div>
					            <div class="form-group">
					              	<label class="col-sm-3 control-label">Position</label>
					              	<div class="col-sm-9">
					                	<input type="text" class="form-control" value="${contact.picPosition}"/>
					              	</div>
					            </div>
					            <div class="form-group">
					              	<label class="col-sm-3 control-label">Department</label>
					              	<div class="col-sm-9">
					                	<input type="text" class="form-control" value="${contact.picDepartment}"/>
					              	</div>
					            </div>
					            <div class="form-group">
					              	<label class="col-sm-3 control-label">Address</label>
					              	<div class="col-sm-9">
					                	<textarea class="form-control" rows="3">${contact.address}</textarea>
					              	</div>
					            </div>
					            <%-- <div class="form-group">
					              	<label class="col-sm-3 control-label">City</label>
					              	<div class="col-sm-9">
					                	<input type="text" class="form-control" value="${contact.cityName}"/>
					              	</div>
					            </div> --%>
					            <div class="form-group">
					              	<label class="col-sm-3 control-label">Phone</label>
					              	<div class="col-sm-9">
					                	<input type="number" class="form-control" value="${contact.picPhone}"/>
					              	</div>
					            </div>
					            <div class="form-group">
					              	<label class="col-sm-3 control-label">Email</label>
					              	<div class="col-sm-9">
					                	<input type="email" class="form-control" value="${contact.picEmail}"/>
					              	</div>
					            </div>
					            <div class="form-group">
					              	<div class="col-sm-offset-3 col-sm-9">
					                    <div class="checkbox">
					                      <label><input type="checkbox"> Is Default Delivery?</label>
					                	</div>
					            	</div>
					            </div>
					            <div class="form-group">
					              	<div class="col-sm-offset-3 col-sm-9">
					                    <div class="checkbox">
					                      <label><input type="checkbox"> Is Billing?</label>
					                	</div>
					            	</div>
					            </div>
					            <hr>
				            </c:forEach>
				            
				            
				            <c:if test="${fn:length(corptContacts) gt 1}">
					            <h4>Attachment</h4>
					            <div class="form-group">
					              	<label class="col-sm-3 control-label">NPWP</label>
					              	<div class="col-sm-9">
					                	<p class="form-control-static">File Name</p>
					                	<button class="btn btn-default btn-xs">Download</button>
					              	</div>
					            </div>
					            <div class="form-group">
					              	<label class="col-sm-3 control-label">SIUP</label>
					              	<div class="col-sm-9">
					                	<p class="form-control-static">File Name</p>
					                	<button class="btn btn-default btn-xs">Download</button>
					              	</div>
					            </div>
					            <div class="form-group">
					              	<label class="col-sm-3 control-label">Others</label>
					              	<div class="col-sm-9">
					                	<p class="form-control-static">File Name</p>
					                	<button class="btn btn-default btn-xs">Download</button>
					              	</div>
					            </div>
					            <hr>
				            </c:if>
				            
				            <h4>Users</h4>
				            <div class="table-responsive">
							<table class="table table-hover">
				                <thead>
				                <tr>
				                  <th>#</th>
				                  <th>Name</th>
				                  <th>Phone</th>
				                  <th>Email</th>
				                  <th>Role</th>
				                  <th>Action</th>
				                </tr>
				                </thead>
				                <tbody>
				                <c:forEach items="${bean.corptUsers}" var="user" varStatus="vs">
					                <tr>
					                  <td>${vs.count}</td>
					                  <td>${user.name}</td>
					                  <td>${user.phone}</td>
					                  <td>${user.email}</td>
					                  <td>${user.role}</td>
					                  <td>
					                    <button class="btn btn-default btn-xs" type="button" data-toggle="modal" data-target="#userRemove">Remove</button>
					                  </td>
					                </tr>
				                </c:forEach>
				                </tbody>
				            </table>
				            </div>
          				</form>
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
