<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Create Product</title>
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
     		Product Create
     		<small>Create new product</small>
   		</h1>
   		<ol class="breadcrumb">
     		<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
     		<li><a href="#">Product</a></li>
     	<li class="active">Create</li>
   		</ol>
 	</section>
 		
 	<input type="hidden" id="url_getsubcategory" value="<c:url value="/category/sub/listbypcategory/json" />">
 	<input type="hidden" id="url_getspeccategory" value="<c:url value="/category/spec/listbypcategory/json" />">
 	<input type="hidden" id="url_getattrcategory" value="<c:url value="/category/attr/listbypcategory/json" />">
 	<input type="hidden" id="url_generatesku" value="<c:url value="/product/sku/generate/json" />">

 	<section class="content">
 	<form id="form_" action="<c:url value="/product/create"/>" method="post" onsubmit="return form_submit();" enctype="multipart/form-data">
 		
	<div style="margin-bottom: 5px;">
		<button type="submit" class="btn btn-primary">Save</button>
 		<a href="#" onclick="javascript:history.back();" class="btn btn-default">Back</a>
	</div>
 			
	<c:if test="${not empty errorMsg}">
		<div class="alert alert-warning alert-dismissible">
    	  	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
    	  	<h4><i class="icon fa fa-warning"></i> Opps!</h4>
    	  	${errorMsg}
 		</div>
	</c:if>
			
	<div id="errmsg" class="alert alert-warning alert-dismissible" role="alert">
		<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		<span>Please fill in the required fields.</span>
 	</div>
 			
	<div class="row">
		<div class="col-md-3 col-sm-12">
			<div class="box box-primary">
				<div class="box-header">
			    	<h3 class="box-title">General Information</h3>
				</div>
			    <div class="box-body">
			    	<div id="pname_dv" class="form-group">
			        	<label class="control-label">Name</label>
			            <input id="pname_in" name="pname_in" type="text" class="form-control" value="" required="required">
					</div>
			        <div id="ppl_dv" class="form-group">
			            <label class="control-label">Category</label>
			            <select id="pcategory_in" name="pcategory_in" class="form-control" onchange="return pcategory_selected();" required="required">
			              <option value="">-</option>
			              <c:forEach items="${pcategoryList}" var="obj">
			              	<option value="${obj.id}">${obj.name}</option>
			              </c:forEach>
			            </select>
			        </div>
			        <div id="ppl_dv" class="form-group">
			            <label class="control-label">Sub-Category</label>
			            <select id="psubcategory_in" name="psubcategory_in" class="form-control">
			              <option value="">-</option>
			            </select>
					</div>          
			        <div id="pprice_dv" class="form-group">
			            <label class="control-label">Base Price</label>
			            <div class="input-group">
			              <span class="input-group-addon">Rp</span>
			              <input id="pprice_in" name="pprice_in" type="number" class="form-control" required="required">
			            </div>
			        </div>
			        <div id="pdesc_dv" class="form-group">
			            <label class="control-label">Description</label>
			            <textarea id="pdesc_in" name="pdesc_in" class="form-control" rows="5"></textarea>
			        </div>
			        <div id="pimages_dv" class="form-group">
			            <label class="control-label">Images</label>
			            <input type="file" id="pimages1_in" name="pimages1_in" style="padding-bottom: 5px;">
			            <input type="file" id="pimages2_in" name="pimages2_in" style="padding-bottom: 5px;">
			            <input type="file" id="pimages3_in" name="pimages3_in" style="padding-bottom: 5px;">
			        </div>
				</div>
			</div>
		</div>
		<div class="col-md-9 col-sm-12">
			<div class="box box-default">
				<div class="box-header with-border">
			    	<h3 class="box-title">Specification</h3>
			    </div>
			    <input type="hidden" id="spec_idcoll" name="spec_idcoll">
			    <div id="bb_spec" class="box-body">
			    	<%-- This form is based on <a><code>productLine.specification</code></a> --%>
			        <span id="spec_hb">This form is based on Category.</span>
			        <span id="spec_pw" class="text-red" style="display: none;"><strong>Please wait..</strong></span>
				</div>
			</div>
			<div class="box box-default">
				<div class="box-header with-border">
			    	<h3 class="box-title">Attribute</h3>
			    </div>
			    <input type="hidden" id="attr_idcoll" name="attr_idcoll">
			    <div id="bb_attr" class="box-body">
			  
			    	<span id="attr_hb">This form is based on Category.</span>
			        <span id="attr_pw" class="text-red" style="display: none;"><strong>Please wait..</strong></span>
			        <!-- <span id="attr_desc" class="text-orange" style="display: none;">Input values, separated by comma (,)</span> -->
				</div>
			</div>
			<div class="box box-default">
				<div class="box-header with-border">
			    	<h3 class="box-title">Product SKU</h3>
			    </div>
			    <div class="box-body">
			    	<span id="sku_hb">This form is based on Attribute.</span>
			        <span id="sku_pw" class="text-red" style="display: none;"><strong>Please wait..</strong></span>
			        <!-- <br /><br /> -->
			        <input type="hidden" id="sku_idcoll" name="sku_idcoll">
			        <div class="table-responsive">
			        	<table id="tbl_sku" class="table table-hover">
	              			<thead>
	              				<tr>
	                				<th>Label</th>
						            <th>Remarks</th>
						            <th>Stock</th>
						            <th>Add Price</th>
	              				</tr>
	              			</thead>
	              			<tbody>
	              			</tbody>
	              		</table>	
			        </div>
			    </div>
			</div>
		</div>
	</div>
	</form>
	</section>
</div>

<%@ include file="../general/footer.jsp" %>
 
</div>

<c:url var="url_pl_select" value="/product/prodline/childs"/>

<%-- <c:url value="/lte/" /> --%>
<script src="<c:url value="/lte/plugins/jQuery/jquery-2.2.3.min.js" />"></script>
<script src="<c:url value="/bootstrap/js/bootstrap.min.js" /> "></script>
<script src="<c:url value="/lte/dist/js/app.min.js" />"></script>
<script src="<c:url value="/biel/product/create.js" />"></script>

<script type="text/javascript">
$(document).ready(function(){
    $("#errmsg").hide();
});
</script>


</body>
</html>
