<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Create vendor</title>
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
        	Create Vendor
        	<small>Create new vendor</small>
      	</h1>
      	<ol class="breadcrumb">
        	<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        	<li><a href="#">Vendor</a></li>
			<li class="active">Create</li>
      	</ol>
    </section>

    <section class="content">
	<form id="form_" action="<c:url value="add"/>" method="post" onsubmit="return form_submit();">
	
		<input type="hidden" id="url_getcity" value="<c:url value="/city/load/byprov" />">
		<input type="hidden" id="userRolesStr" value="${userRolesStr}">
      			
    	<div style="margin-bottom: 5px;">
      		<button type="submit" class="btn btn-primary">Save</button>
  			<a href="<c:url value="/vendor/"/>" class="btn btn-default">Back</a>
      	</div>
      			
		<c:if test="${not empty errorMsg}">
			<div class="alert alert-warning alert-dismissible">
        		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        		<h4><i class="icon fa fa-warning"></i> Opps!</h4>
        		${errorMsg}
       		</div>
		</c:if>
      			
  		<div class="row">
			<div class="col-md-4 col-sm-12">
				<div class="box box-primary">
					<div class="box-header with-border">
				    	<h3 class="box-title">General Information</h3>
					</div>
				    <div class="box-body">
				    	<div class="form-group">
				        	<label class="control-label">Vendor Name*</label>
				            <input id="name_in" name="name_in" class="form-control" type="text" required="required">
						</div>
				        <div class="form-group">
				            <label class="control-label">PIC Name*</label>
				            <input id="pic_name_in" name="pic_name_in" class="form-control" type="text" required="required">
						</div>
						<div class="form-group">
				            <label class="control-label">PIC Phone*</label>
				            <input id="pic_phone_in" name="pic_phone_in" class="form-control" type="text" required="required">
						</div>
				        <div class="form-group">
				            <label class="control-label">Address*</label>
				            <textarea id="address_in" name="address_in" class="form-control" required="required"></textarea>
				        </div>
				        <div class="form-group">
				            <label class="control-label">Province*</label>
				            <select id="prov_in" name="prov_in" class="form-control" onchange="get_cities();" required="required">
								<option value="">-</option>
								<c:forEach items="${provinceList}" var="prov">
									<option value="${prov[0]}">${prov[1]}</option>
								</c:forEach>
				            </select>
				        </div>
				        <div class="form-group">
				            <label class="control-label">City*</label>
				            <select id="city_in" name="city_in" class="form-control" required="required">
								<option value="">-</option>
				            </select>
				        </div>
				        <div class="form-group">
				            <label class="control-label">Postal Code*</label>
				            <input id="postal_in" name="postal_in" class="form-control" type="number" maxlength="6" required="required">
				        </div>
					</div>
				</div>
			</div>
			<div class="col-md-8 col-sm-12">
				<div class="box box-default">
					<div class="box-header with-border">
				    	<h3 class="box-title">Configuration</h3>
					</div>
					<div class="box-body">
						<div id="vcrdlmt_dv" class="form-group">
				        	<label class="control-label">Credit Limit*</label>
				            <div class="input-group">
				            	<span class="input-group-addon">Rp</span>
				            	<input id="crdlmt_in" name="crdlmt_in" type="text" class="form-control" required="required">
				            </div>
						</div>
						<div id="vcrdlmt_dv" class="form-group">
				            <label class="control-label">Delivery Capacity Limit*</label>
				            <input id="delvr_in" name="delvr_in" type="number" class="form-control" required="required">
						</div>
				    </div>
				</div>
				<div class="box box-default">
					<div class="box-header with-border">
				    	<h3 class="box-title">Coverage</h3>
					</div>
					<div class="box-body">
						<table id="tbl_coverage" class="table table-hover">
				            <thead>
					            <tr>
					              <th>Area</th>
					              <th>SLA Time</th>
					              <th>Action</th>
					            </tr>
				            </thead>
				            <tbody>
				            <tr>
								<td>
					            	<div class="form-group">
						              	<select id="city_cvr_in" name="city_cvr_in" class="form-control">
							            	<option value="3216">Kab. Bekasi</option>
							            	<option value="3201">Kab. Bogor</option>
							            	<option value="3275">Kota Bekasi</option>
							            	<option value="3271">Kota Bogor</option>
							            	<option value="3276">Kota Depok</option>
							            	<option value="3174">Kota Jakarta Barat</option>
							            	<option value="3173" selected="selected">Kota Jakarta Pusat</option>
							            	<option value="3171">Kota Jakarta Selatan</option>
							            	<option value="3172">Kota Jakarta Timur</option>
							            	<option value="3175">Kota Jakarta Utara</option>
							            	<option value="3674">Kota Tangerang Selatan</option>
							            </select>
					            	</div>
								</td>
								<td>
					              	<div class="form-group">
										<input id="sla_in" name="sla_in" type="number" class="form-control">
					              	</div>
								</td>
					            <td>
					              	<button class="btn btn-default" onclick="return coverage_add();">Add</button>
					              	<input type="hidden" id="cvr_id" name="cvr_id" value="0"/>
		                			<input type="hidden" id="cvr_idcoll" name="cvr_idcoll" value=""/>
					            </td>
							</tr>
							</tbody>
						</table>	           
				    </div>
				</div>
				<div class="box box-default">
					<div class="box-header with-border">
				    	<h3 class="box-title">Vendor User</h3>
					</div>
				    <div class="box-body table-responsive">
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
						<tr>
				        	<td>
				            	<div id="vuname_dv" class="form-group">
				                	<input id="user_name" class="form-control" type="text" placeholder="Input name here">
				                </div>
							</td>
							<td>
				                <div id="vuemail_dv" class="form-group">
				                  <input id="user_email" class="form-control" type="email" placeholder="Input email here">
				            	</div>
							</td>
				            <td>
				                <div id="vuphone_dv" class="form-group">
				                  <input id="user_phone" class="form-control" type="text" placeholder="Input phone here">
				                </div>
							</td>
							<td>
								<div class="form-group">
				                	<select id="user_role" class="form-control">
				                    <c:forEach var="obj" items="${userRoles}">
				                    	<option value="${obj[0]}">${obj[1]}</option>
				                    </c:forEach>
				                  	</select>
				                </div>
							</td>
				            <td>
				            	<button class="btn btn-default" onclick="return venduser_add();">Add</button>
				            </td>
						</tr>	
				        </tbody>
						</table>
						<input type="hidden" id="huser_no" name="huser_no" value="0" />
						<input type="hidden" id="huser_nocoll" name="huser_nocoll" value="" />
				    </div>
				</div>
			</div>
		</div>
	</form>
    </section>
</div>

<%@ include file="../general/footer.jsp" %>
</div>

<%-- <c:url value="/lte/" /> --%>
<script src="<c:url value="/lte/plugins/jQuery/jquery-2.2.3.min.js" />"></script>
<script src="<c:url value="/bootstrap/js/bootstrap.min.js" /> "></script>
<script src="<c:url value="/lte/dist/js/app.min.js" />"></script>
<script src="<c:url value="/lte/plugins/select2/select2.full.min.js" />"></script>
<script src="<c:url value="/biel/vendor/vendor-create.js" />"></script>
<script src="<c:url value="/biel/vendor/vendoruser-add.js" />"></script>

<script type="text/javascript">
$(document).ready(function(){
    $(".select2").select2();
    $('#postal_in').keypress(numberOnly);
    $('#crdlmt_in').keypress(numberOnly);
    $('#delvr_in').keypress(numberOnly);
    $('#sla_in').keypress(numberOnly);
});
</script>

</body>
</html>
