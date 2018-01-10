<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Product Detail</title>
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
     			Product Detail
     			<small></small>
   			</h1>
   			<ol class="breadcrumb">
     			<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
     			<li><a href="#">Product</a></li>
     			<li class="active">Detail</li>
   			</ol>
 		</section>

 		<section class="content">
 			
 			<button type="button" class="btn btn-success page__button" onclick="return appr_status('1')">Approve</button>
 			<button type="button" class="btn btn-danger page__button" onclick="return appr_status('2')">Reject</button>
 			<a href="<c:url value="/tempproduct/"/>" class="btn btn-default page__button">Back</a>
 			
  			<div class="row">
    			<div class="col-md-3 col-sm-12">
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
            					<label class="control-label">Category</label>
            					<p class="form-control-static">${obj.pcategoryName}</p>
          					</div>
          					<div class="form-group">
            					<label class="control-label">Sub-Category</label>
            					<p class="form-control-static">${obj.pcategorySubName}</p>
          					</div>
          					<div class="form-group">
            					<label class="control-label">Base Price</label>
            					<p class="form-control-static">Rp ${obj.basePriceIdr}</p>
          					</div>
          					<div class="form-group">
            					<label class="control-label">Description</label>
            					<p class="form-control-static">${obj.desc}</p>
          					</div>
				          	<div class="form-group">
				            	<label class="control-label">Status</label>
				            	<p class="forom-control-static">
			            			<span class="bg-green">&nbsp;${obj.status}&nbsp;</span>
				            	</p>
				          	</div>
        				</div>
      				</div>
    			</div>
    			<div class="col-md-9 col-sm-12">
			      	<div class="box box-default">
	        			<div class="box-header with-border">
	          				<h3 class="box-title">Images</h3>
	        			</div>
	        			<div class="box-body">
	          				<div class="row">
	            				<div class="col-md-4 col-sm-12">
	              					<button class="btn btn-default page__button" onclick="modal_change_image('1')">Change</button>
	              					<div class="product__image" style="background-image: url('${obj.images[0][1]}');"></div>
	            				</div>
	            				<div class="col-md-4 col-sm-12">
	              					<button class="btn btn-default page__button" onclick="modal_change_image('2')">Change</button>
	              					<div class="product__image" style="background-image: url('${obj.images[1][1]}');"></div>
	            				</div>
				            	<div class="col-md-4 col-sm-12">
				              		<button class="btn btn-default page__button" onclick="modal_change_image('3')">Change</button>
				              		<div class="product__image" style="background-image: url('${obj.images[2][1]}');"></div>
				            	</div>
	          				</div>
	        			</div>
	      			</div>
	      			<div class="box box-default">
	        			<div class="box-header with-border">
	          				<h3 class="box-title">Specification</h3>
	        			</div>
	        			<div class="box-body">
	          				<div class="table-responsive">
            					<table class="table table-hover">
              					<thead>
						            <tr>
						            	<th>Specification</th>
						                <th>Value</th>
						                <th>Action</th>
						            </tr>
              					</thead>
              					<tbody>
              					<c:forEach items="${obj.specs}" var="spec_">
              						<tr>
						                <td>${spec_[1]}</td>
						                <td>
						                	<div>${spec_[2]}</div>
						                </td>
						                <td>
						                  <button class="btn btn-link btn-xs"><i class="fa fa-pencil fa-fw"></i></button>
						                </td>
					              	</tr>
              					</c:forEach>
              					</tbody>
              					</table>
              				</div>	
	        			</div>
	      			</div>
			      	<div class="box box-default">
			        	<div class="box-header with-border">
			          		<h3 class="box-title">Attribute</h3>
			        	</div>
				        <div class="box-body">
				          	<div class="table-responsive">
          						<table class="table table-hover">
              					<thead>
						            <tr>
						            	<th>Label</th>
						                <th>Value</th>
						            </tr>
              					</thead>
              					<tbody>
              					<c:forEach items="${obj.attrList}" var="attr">
              						<tr>
						                <td>${attr[1]}</td>
						                <td>
						                	<div>${attr[2]}</div>
						                </td>
					              	</tr>
              					</c:forEach>
              					</tbody>
              					</table>
          					</div>
	        			</div>
      				</div>
      				<div class="box box-default">
			        	<div class="box-header with-border">
			          		<h3 class="box-title">Product SKU</h3>
			        	</div>
				        <div class="box-body">
				          	<div class="table-responsive">
          						<table class="table table-hover">
              					<thead>
						            <tr>
						            	<th>Label</th>
						                <th>SKU</th>
						                <th>Remark</th>
						                <th>Stock</th>
						                <th>Addt. Price</th>
						                <th>Action</th>
						            </tr>
              					</thead>
              					<tbody>
              					<c:forEach items="${obj.skuList}" var="obj_">
              						<tr>
						                <td>${obj_[1]}</td>
						                <td>
						                	<div>${obj_[2]}</div>
						                </td>
						                <td>${not empty obj_[3]?obj_[3]:'-'}</td>
						                <td>${obj_[4]}</td>
						                <td>${obj_[5]}</td>
						                <td>
						                  <button class="btn btn-link btn-xs"><i class="fa fa-pencil fa-fw"></i></button>
						                </td>
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

<div class="modal fade" id="confModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog modal-sm" role="document">
  		<div class="modal-content">
  		<form action="<c:url value="/tempproduct/approval"/>" method="post">
      		<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
       			<h4 class="modal-title" id="myModalLabel">Confirm Approval</h4>
      		</div>
      		<div class="modal-body">
        		<div id="modal_question">Are you sure?</div>
        		<input type="hidden" id="modal_prodid" name="modal_prodid" value="${obj.id}" >
        		<input type="hidden" id="appr_status" name="appr_status" >
      		</div>
			<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        		<button type="submit" id="btn_smt_modal" class="btn btn-primary">Submit</button>
      		</div>
    	</form>  
    	</div>
	</div>
</div>

<%-- <c:url value="/lte/" /> --%>
<script src="<c:url value="/lte/plugins/jQuery/jquery-2.2.3.min.js" />"></script>
<script src="<c:url value="/bootstrap/js/bootstrap.min.js" /> "></script>
<script src="<c:url value="/lte/dist/js/app.min.js" />"></script>

<script type="text/javascript">
$(document).ready(function(){
    //$("#errmsg").hide();
});

function appr_status(val_) {
	
	var btn_val = "Submit";
	if(val_=='1'){
		btn_val = "Approve";
		$("#modal_question").html("Are you sure to approve?");
	}else if(val_=='2'){
		btn_val = "Reject";
		$("#modal_question").html("Are you sure to reject?");
	}
	
	$("#appr_status").val(val_);
	$("#btn_smt_modal").html(btn_val);
	$('#confModal').modal('show');
	
	return false;
}

</script>


</body>
</html>
