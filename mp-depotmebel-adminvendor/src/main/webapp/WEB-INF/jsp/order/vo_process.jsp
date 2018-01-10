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
      	   
      	  <form method="post" action="<c:url value="/order/voprocess" />" onsubmit="return btn_ready_toqc();" enctype="multipart/form-data"> 
      	  <input type="hidden" name="itemId" value="${bean.itemId}">
      	  <input type="hidden" name="statusItem" value="${lastStatusItem}">
      	   
      	  <button class="btn btn-default page__button">Back</button>
		  <c:if test="${not empty errorMsg}">
			<div class="alert alert-warning alert-dismissible">
         		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
         		<h4><i class="icon fa fa-warning"></i> Opps!</h4>
         		${errorMsg}
        	</div>
		  </c:if>
		
		  <div class="row">
		    <div class="col-md-12">
		      <!--General Information-->
		      <div class="box box-primary">
		        <div class="box-header with-border">
		          <h3 class="box-title">
		            General Information
		          </h3>
		        </div>
		        <div class="box-body">
		          <div class="form-group">
		            <label class="control-label">Order No.</label>
		            <p class="form-control-static">${bean.orderNo}</p>
		          </div>
		          <div class="form-group">
		            <label class="control-label">Customer</label>
		            <p class="form-control-static">${bean.customer[1]} <!-- <button class="btn btn-default btn-xs">View</button> --></p>
		          </div>
		          <div class="form-group">
		            <label class="control-label">Shipping Addess</label>
		            <p class="form-control-static">${bean.address}</p>
		          </div>
		        </div>
		      </div>
		    </div>
		    <div class="col-md-12">
		      <!--All orders: Need QC-->
		      <div class="box box-default">
		        <div class="box-header with-border">
		          <h3 class="box-title">Item Detail</h3>
		        </div>
		        <div class="box-body">
		          <div class="form-group">
		            <label class="control-label">Product Name</label>
		            <p class="form-control-static">${bean.product[1]}</p>
		          </div>
		          <div class="form-group">
		            <label class="control-label">Product Attribute</label>
		            <p class="form-control-static">${bean.product[3]} - ${bean.product[4]}</p>
		          </div>
		          <div class="form-group">
		            <label class="control-label">Specification</label>
		            <c:forEach items="${bean.prodSpecs}" var="spec">
		            	<p class="form-control-static">${spec[0]}: ${spec[1]}</p>
		            </c:forEach>
		          </div>
		          <div class="form-group">
		            <label class="control-label">Image</label>
		            <a href="${bean.productImg[0]}" target="_link"><div class="product__image" style="background-image: url('${bean.productImg[0]}'); max-width: 480px;"></div></a>
		          </div>
		          <div class="form-group">
		            <label class="control-label">Custom Message</label>
		            <p class="form-control-static">${bean.customMsg[0]}</p>
		            <a href="${bean.customMsg[1]}" target="_link"><div class="product__image" style="background-image: url('${bean.customMsg[1]}'); max-width: 480px;"></div></a>
		            <c:if test="${bean.customMsg[2]!=''}">
	            		<a href="${bean.customMsg[2]}" target="_link"><div class="product__image" style="background-image: url('${bean.customMsg[2]}'); max-width: 480px;"></div></a>
	            	</c:if>
		          </div>
		        </div>
		        <div class="box-footer">
		          <div class="form-group">
		            <label class="control-label">Finished Image</label>
		            <input id="img_qc" name="img_qc" type="file" class="form-control">
		          </div>
		          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#readyQc">Ready to QC</button>
		        </div>
		      </div>
		    </div>
		  </div>
      	
      	
      	<div class="modal fade" tabindex="-1" role="dialog" id="readyQc">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title">Ready to Quality Control Confirmation</h4>
		      </div>
		      <div class="modal-body">
		        <p>Ready to quality control?</p>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
		        <button type="submit" class="btn btn-primary">OK</button>
		      </div>
		    </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
      		
      	</form>
    	</section>
  	</div>
  	

<%@ include file="../general/footer.jsp" %>
<%-- <%@ include file="../general/sidebar-control.jsp" %> --%>
  
</div>


<%-- <c:url value="/lte/" /> --%>
<script src="<c:url value="/lte/plugins/jQuery/jquery-2.2.3.min.js" />"></script>
<script src="<c:url value="/bootstrap/js/bootstrap.min.js" /> "></script>
<script src="<c:url value="/lte/dist/js/app.min.js" />"></script>
<script src="<c:url value="/biel/order/vo_process.js" />"></script>

<script type="text/javascript">

</script>



</body>
</html>
