<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Vendor Detail</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  
  <link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.min.css" />">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <link rel="stylesheet" href="<c:url value="/lte/dist/css/AdminLTE.min.css" />">
  <link rel="stylesheet" href="<c:url value="/lte/dist/css/skins/skin-blue.min.css" />">
  <link rel="stylesheet" href="<c:url value="/lte/plugins/select2/select2.min.css" />">
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
    	Vendor Detail
    	<small>View vendor details</small>
    </h1>
    <ol class="breadcrumb">
    	<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
    	<li><a href="#">Vendor</a></li>
    	<li class="active">Detail</li>
    </ol>
</section>

<section class="content">
	<a href="#" class="btn btn-danger page__button" data-toggle="modal" data-target="#deleteVendor">Delete</a>
	<a href="<c:url value="/vendor"/>" class="btn btn-default page__button">Back</a>
	
	<input type="hidden" id="vendor_id" value="${obj.id}">
    
    <div class="row">
    	<div class="col-md-4 col-sm-12">
      		<div class="box box-primary">
        		<div class="box-header with-border">
          			<h3 class="box-title">General Information</h3>
          			<div class="box-tools pull-right">
						<button id="btn_edit_general" class="btn btn-box-tool" onclick="btn_edit_general();">
						<i class="fa fa-pencil fa-fw"></i></button>
		  			</div>
        		</div>
        		<div class="box-body">
          			<div class="form-group">
            			<label class="control-label">Vendor Name</label>
            			<p class="form-control-static">${obj.name}</p>
          			</div>
          			<div class="form-group">
            			<label class="control-label">PIC</label>
            			<p class="form-control-static">${obj.picName} (${obj.picPhone})</p>
          			</div>
          			<div class="form-group">
            			<label class="control-label">Address</label>
            			<p class="form-control-static">${obj.address}</p>
          			</div>
          			<div class="form-group">
            			<label class="control-label">City - Province</label>
            			<p class="form-control-static">${obj.city} - ${obj.province}</p>
          			</div>
          			<div class="form-group">
            			<label class="control-label">Postal Code</label>
            			<p class="form-control-static">${obj.postalCode==''?'-':obj.postalCode}</p><input type="text" id="postalcode_in" class="form-control" value="${obj.postalCode}" style="display: none;">
          			</div>
          			<div class="form-group">
            			<label class="control-label">Status</label>
            			<p class="form-control-static">${obj.status}</p>
          			</div>
          			<div class="form-group">
            			<label class="control-label">Created Date</label>
            			<p class="form-control-static">${obj.createdDate}</p>
          			</div>
        		</div>
      		</div> 
    	</div>
    	<div class="col-md-8 col-sm-12">
      		<div class="box box-default">
        		<div class="box-header with-border">
          			<h3 class="box-title">Configuration</h3>
          			<div class="box-tools pull-right">
						<button id="btn_edit_config" class="btn btn-box-tool" onclick="btn_edit_config();">
							<i class="fa fa-pencil fa-fw"></i></button>
		  			</div>
        		</div>
        		<div class="box-body">
	          		<div class="form-group">
	            		<label class="control-label">Credit Limit</label>
	            		<p id="credit_limit_lbl" class="form-control-static">First: ${obj.creditLimitIdr}</p>
	            		<p id="credit_limit_remin_lbl" class="form-control-static">Remaining: ${obj.creditLimitRemainIdr}</p>
	          		</div>
	          		<div class="form-group">
	            		<label class="control-label">Delivery Capacity Limit</label>
	            		<p id="capacity_limit_lbl" class="form-control-static">First: ${obj.deliveryCapacity}</p>
	            		<p id="capacity_limit_remin_lbl" class="form-control-static">Remaining: ${obj.deliveryCapacityRemain}</p>
	          		</div>
        		</div>
      		</div>
      		<div class="box box-default">
        		<div class="box-header">
        			<h3 class="box-title">Coverage</h3>
        		</div>
        		<div class="box-body table-responsive">
        			<table id="table_vuser" class="table table-bordered table-striped">
		            <thead>
			        <tr>
		            	<th>Area</th>
		            	<th>SLA Time</th>
		              	<th>Action</th>
			        </tr>
		            </thead>
		            <tbody>
		            <c:forEach items="${obj.coverages}" var="cov">
		            <tr>
		                <td>
		                	<div id="cvr_area_lbl_${cov[0]}">${cov[1]}, ${cov[2]}</div>
		                	<select id="cvr_area_in_${cov[0]}" class="form-control" style="display: none;">
				            	<option value="3216" ${cov[4]==3216?'selected':''}>Kab. Bekasi</option>
				            	<option value="3201" ${cov[4]==3201?'selected':''}>Kab. Bogor</option>
				            	<option value="3275" ${cov[4]==3275?'selected':''}>Kota Bekasi</option>
				            	<option value="3271" ${cov[4]==3271?'selected':''}>Kota Bogor</option>
				            	<option value="3276" ${cov[4]==3276?'selected':''}>Kota Depok</option>
				            	<option value="3174" ${cov[4]==3174?'selected':''}>Kota Jakarta Barat</option>
				            	<option value="3173" ${cov[4]==3173?'selected':''}>Kota Jakarta Pusat</option>
				            	<option value="3171" ${cov[4]==3171?'selected':''}>Kota Jakarta Selatan</option>
				            	<option value="3172" ${cov[4]==3172?'selected':''}>Kota Jakarta Timur</option>
				            	<option value="3175" ${cov[4]==3175?'selected':''}>Kota Jakarta Utara</option>
				            	<option value="3674" ${cov[4]==3674?'selected':''}>Kota Tangerang Selatan</option>
				            </select>
		                </td>
		                <td>
		                	<div id="cvr_sla_lbl_${cov[0]}">${cov[3]}</div>
		                	<input type="text" id="cvr_sla_in_${cov[0]}" class="form-control" value="${cov[3]}" style="display: none;">
		                </td>
		                <td>
		                 	<button id="btn_edt_cvrg_${cov[0]}" class="btn btn-link btn-xs" onclick="btn_edit_coverage('${cov[0]}')">
			                  <i class="fa fa-pencil fa-fw"></i>
			                </button>
			                <button id="btn_sav_cvrg_${cov[0]}" class="btn btn-link btn-xs" style="display: none;" onclick="btn_update_coverage('${cov[0]}')">
			                  <i class="fa fa-save fa-fw"></i>
			                </button>
			                <button id="btn_rem_cvrg_${cov[0]}" class="btn btn-link btn-xs" onclick="btn_remove_coverage('${cov[0]}', this)"
			                	data-toggle="tooltip" title="Remove this, Are you sure?">
			                  <i class="fa fa-remove fa-fw"></i>
			                </button>	
		                </td>
		            </tr>
		            </c:forEach>
		            </tbody>
		            </table>
        		</div>
        	</div>
        	<div class="box box-default">
        		<div class="box-header with-border">
          			<h3 class="box-title">Vendor User</h3>
        		</div>
        		<div class="box-body">
          			<table id="tbl_vuser" class="table table-hover">
          			<thead>
		            <tr>
		              <th>Name</th>
		              <th>Email</th>
		              <th>Phone</th>
		              <th>Role</th>
		              <th>Action</th>
		            </tr>
		            </thead>
		            <tbody>
		            <c:forEach items="${obj.users}" var="user">
		       		<tr>
			           <td>
			           	<div id="us_name_lbl_${user.id}">${user.name}</div>
			           	<input type="text" id="us_name_in_${user.id}" class="form-control" value="${user.name}" style="display: none;">
			           </td>
			           <td>
			           	<div id="us_email_lbl_${user.id}">${user.email}</div>
			           	<input type="text" id="us_email_in_${user.id}" class="form-control" value="${user.email}" style="display: none;">
			           </td>
			           <td>
			           	<div id="us_phone_lbl_${user.id}">${user.phone}</div>
			           	<input type="text" id="us_phone_in_${user.id}" class="form-control" value="${user.phone}" style="display: none;">
			           </td>
			           <td>
			           	<div id="us_type_lbl_${user.id}">${user.typeName}</div>
			           	<select id="us_type_in_${user.id}" class="form-control" style="display: none;">
							<c:forEach var="role_" items="${userRoles}">
		                    	<option value="${role_[0]}" ${role_[0]==user.typeId?'selected':''}>${role_[1]}</option>
		                    </c:forEach>
						</select>
			           </td>
			           <td>
			       		<button id="btn_edt_vuser_${user.id}" class="btn btn-link btn-xs" onclick="btn_edit_vuser('${user.id}')">
		                  <i class="fa fa-pencil fa-fw"></i>
		                </button>
		                <button id="btn_sav_vuser_${user.id}" class="btn btn-link btn-xs" style="display: none;" onclick="btn_update_vuser('${user.id}')">
		                  <i class="fa fa-save fa-fw"></i>
		                </button>
		                <button id="btn_rem_vuser_${user.id}" class="btn btn-link btn-xs" onclick="btn_remove_vuser('${user.id}', this)"
		                	data-toggle="tooltip" title="Remove this, Are you sure?">
		                  <i class="fa fa-remove fa-fw"></i>
		                </button>
			           </td>
		       		</tr>
		       		</c:forEach>
		            </tbody>
          			</table>	
        		</div>
      		</div>
    	</div>
	</div>
</section>
</div>

<%@ include file="../general/footer.jsp" %>
<%-- <%@ include file="../general/sidebar-control.jsp" %> --%>  
</div>

<!--Delete Vendor-->
<div class="modal fade" tabindex="-1" role="dialog" id="deleteVendor">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Delete confirmation</h4>
      </div>
      <form method="post" action="<c:url value="/vendor/delete" />">
      <div class="modal-body">
        Sure to delete this vendor?
        <input type="hidden" name="vendId" value="${obj.id}">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        <button type="submit" class="btn btn-primary">OK</button>
      </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%-- <c:url value="/lte/" /> --%>
<script src="<c:url value="/lte/plugins/jQuery/jquery-2.2.3.min.js" />"></script>
<script src="<c:url value="/bootstrap/js/bootstrap.min.js" /> "></script>
<script src="<c:url value="/lte/dist/js/app.min.js" />"></script>
<script src="<c:url value="/lte/plugins/select2/select2.full.min.js" />"></script>
<script src="<c:url value="/biel/vendor/update.js" />"></script>

<script type="text/javascript">
$(document).ready(function(){
});
</script>

</body>
</html>
