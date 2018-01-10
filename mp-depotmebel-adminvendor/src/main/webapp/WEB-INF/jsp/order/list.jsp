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
      		
      		<input type="hidden" id="url_list" value="<c:url value="/order/" />">
      		<input type="hidden" id="url_acknowledged" value="<c:url value="/order/toacknowledged" />">
      			
      		<div class="row">
			    <div class="col-md-12">
			      <div class="box box-primary">
			        <div class="box-header with-border">
			          <h3 class="box-title">
			            <span class="fa fa-inbox fa-fw text-blue"></span> Incoming Orders
			          </h3>
			        </div>
			        <div class="box-body">
			          <!-- <div class="input-group box__item box__item--first">
			            <input type="text" class="form-control" placeholder="Search order">
			            <span class="input-group-btn">
			              <button type="button" class="btn btn-default btn-flat">Search</button>
			            </span>
			          </div> -->
			          <div class="box-item box-item__last table-responsive">
			            <table id="tbl_list" class="table table-hover">
			            <thead>
			            	<tr>
			            		<th>#</th>
			            		<th>Order No.</th>
			            		<th>Status</th>
			            		<th>Produk</th>
			            		<th>SLA Time</th>
			            		<th>Customer</th>
			            		<th>Alamat</th>
			            		<th>Action</th>
			            	</tr>
			            </thead>
              			<tbody>
              			<c:forEach items="${trxList}" var="obj" varStatus="vs">
              				<tr>
				                <td>${vs.count}</td>
				                <td>${obj.orderNo}</td>
				                <td>${obj.status[1]}</td>
				                <td>${obj.product[1]},<br />${obj.product[3]}</td>
				                <td>${obj.sla}</td>
				                <td>
				                	${obj.customer[1]},<br />
				                	${obj.customer[3]}
				                </td>
				                <td>${obj.address}</td>
				                <td width="80px">
			                    <c:choose>
				                	<c:when test="${sessionVendor_.user.vendors[3]==1 && obj.status[0]==1}">
				                		<a id="a_pros_${vs.count}" href="#" class="btn btn-default btn-xs" onclick="show_modal_process('${vs.count}', '${obj.itemId}')">Process</a>
				                		<div id="ps_wait_${vs.count}" style="display: none;">wait..</div>
				                		<a id="a_rc_${vs.count}" href="<c:url value="/order/voprocess?x=${obj.urlParam}" />" class="btn btn-default btn-xs" style="display: none;">Ready QC</a>
				                	</c:when>
				                	<c:when test="${sessionVendor_.user.vendors[3]==1 && obj.status[0]==6}">
				                		<a id="a_rc_${vs.count}" href="<c:url value="/order/voprocess?x=${obj.urlParam}" />" class="btn btn-default btn-xs">Ready QC</a>
				                	</c:when>
				                	<c:when test="${sessionVendor_.user.vendors[3]==1 && obj.status[0]==3}">
				                		<a href="<c:url value="/order/tocourier?x=${obj.urlParam}" />" class="btn btn-default btn-xs">To Courier</a>
				                	</c:when>
				                	<c:when test="${sessionVendor_.user.vendors[3]==2 && obj.status[0]==4}">
				                		<a href="<c:url value="/order/pod?x=${obj.urlParam}" />" class="btn btn-default btn-xs">POD</a>
				                	</c:when>
				                	<c:otherwise>
				                		<a href="<c:url value="/order/detail?x=${obj.urlParam}" />" class="btn btn-default btn-xs">View</a>
				                	</c:otherwise>
				                </c:choose>
			                  	</td>
	              			</tr>
              			</c:forEach>
              			</tbody>
            			</table>
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
  	
<div class="modal fade" tabindex="-1" role="dialog" id="startProgress">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Start Progress</h4>
      </div>
      <div class="modal-body">
        <p>Are you sure to proceed this order?</p>
        <input type="hidden" id="item_id" >
        <input type="hidden" id="my_count" >
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" onclick="proceed_action()">OK</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%@ include file="../general/footer.jsp" %>
<%-- <%@ include file="../general/sidebar-control.jsp" %> --%>  
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
function show_modal_process(id_, itemId) {
	$("#item_id").val(itemId);
	$("#my_count").val(id_);
	$('#startProgress').modal('show');
}
function proceed_action() {
	var id_ = $("#my_count").val();
	var item_id = $("#item_id").val();
	var url_ = $("#url_acknowledged").val();
	var url_list = $("#url_list").val();
	
	$('#startProgress').modal('hide');
	$("#a_pros_"+id_+"").hide();
	$("#ps_wait_"+id_+"").show();

	$.ajax({
		url: url_,
		//dataType:'json',
    	type:"POST",
        data:"itemId="+item_id,
        success: function(data) {
        	console.log('Success: '+data);
        	if(data=='F'){
        		$("#a_pros_"+id_+"").show();
        		$("#ps_wait_"+id_+"").hide();
            	$("#a_rc_"+id_+"").hide();
        	}else{
        		$("#ps_wait_"+id_+"").hide();
            	$("#a_rc_"+id_+"").show();
            	window.location.replace(url_list);
        	}
        	//alert('Success');
        }
	});
	
	
}
</script>



</body>
</html>
