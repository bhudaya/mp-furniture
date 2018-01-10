<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>New Category</title>
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
     		Create Category
     		<small></small>
   		</h1>
   		<ol class="breadcrumb">
     		<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
     		<li><a href="#">Category</a></li>
     		<li class="active">Create</li>
   		</ol>
	</section>

 	<section class="content">
 	<form id="form_" action="<c:url value="/category/create"/>" method="post">
 	
 		<button type="submit" class="btn btn-primary page__button">Save</button>
		<a href="#" class="btn btn-default page__button" onclick="javascript:history.back()">Back</a>
 		
 		<div class="row">
 			<div class="col-md-5 col-sm-12">
 				<div class="box box-primary">
        			<div class="box-header with-border">
          				<h3 class="box-title">General Information</h3>
        			</div>
        			<div class="box-body">
          				<div id="pname_dv" class="form-group">
				        	<label class="control-label">Name</label>
				            <input id="pname_in" name="pname_in" type="text" class="form-control" value="" required="required">
						</div>
        			</div>
      			</div>
      			<div class="box box-default">
        			<div class="box-header with-border">
          				<h3 class="box-title">Sub Category</h3>
        			</div>
        			<div class="box-body table-responsive">
          				<table id="tbl_sub" class="table table-hover">
          					<thead>
				            <tr>
				              <th>Name</th>
				              <th>Action</th>
				            </tr>
				            </thead>
				            <tbody>
				            <tr>
				              <td>
				                <div id="sub_lbl_dv" class="form-group">
				                  <input id="sub_name_in" class="form-control" type="text" placeholder="Input Sub Category Name">
				                </div>
				              </td>
				              <td>
				                <button type="button" class="btn btn-default" onclick="return subcat_add();">Add</button>
				                <input type="hidden" id="sub_last_id" name="sub_last_id" value="0"/>
			                	<input type="hidden" id="sub_idcoll" name="sub_idcoll" value=""/>
				              </td>
				        	</tr>     
          				</table>
        			</div>
      			</div>
 			</div>
 			<div class="col-md-7 col-sm-12">
 				<div class="box box-default">
        			<div class="box-header with-border">
          				<h3 class="box-title">Specification</h3>
        			</div>
        			<div class="box-body table-responsive">
          				<table id="tbl_spec" class="table table-hover">
          					<thead>
				            <tr>
				              <th>Name</th>
				              <th>Action</th>
				            </tr>
				            </thead>
				            <tbody>
				            <tr>
				              <td>
				                <div id="spec_lbl_dv" class="form-group">
				                  <input id="spec_name_in" class="form-control" type="text" placeholder="Input Specification Name">
				                </div>
				              </td>
				              <td>
				                <button type="button" class="btn btn-default" onclick="return speccat_add();">Add</button>
				                <input type="hidden" id="spec_last_id" name="spec_last_id" value="0"/>
			                	<input type="hidden" id="spec_idcoll" name="spec_idcoll" value=""/>
				              </td>
				        	</tr>      
          				</table>
        			</div>
      			</div>
      			<div class="box box-default">
        			<div class="box-header with-border">
          				<h3 class="box-title">Attribute</h3>
        			</div>
        			<div class="box-body table-responsive">
          				<table id="tbl_attr" class="table table-hover">
          					<thead>
				            <tr>
				              <th>Name</th>
				              <th>Action</th>
				            </tr>
				            </thead>
				            <tbody>
				            <tr>
				              <td>
				                <div id="attr_lbl_dv" class="form-group">
				                  <input id="attr_name_in" class="form-control" type="text" placeholder="Input Attribute Name">
				                </div>
				              </td>
				              <td>
				                <button type="button" class="btn btn-default" onclick="return attrcat_add();">Add</button>
				                <input type="hidden" id="attr_last_id" name="attr_last_id" value="0"/>
			                	<input type="hidden" id="attr_idcoll" name="attr_idcoll" value=""/>
				              </td>
				        	</tr>      
          				</table>
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
<script src="<c:url value="/biel/pcategory/create-sub.js" />"></script>
<script src="<c:url value="/biel/pcategory/create-spec.js" />"></script>
<script src="<c:url value="/biel/pcategory/create-attr.js" />"></script>

<script type="text/javascript">
$(document).ready(function(){
    //$("#errmsg").hide();
});

</script>


</body>
</html>
