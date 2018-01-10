<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
    	<h1>Order Management<small>Manage all transaction</small></h1>
		<ol class="breadcrumb">
   			<li><a href="<c:url value="/home"  />"><i class="fa fa-dashboard"></i> Home</a></li>
     		<li class="active">Vendor</li>
   		</ol>
    </section>
			
    <section class="content">
    	
    	<c:url value="/order/setvendor" var="url_set_vendor"/>
    	<c:url value="/vendor/list/json" var="url_vendors"/>
    	<input type="hidden" id="itemsNoVend" value="${fn:length(bean.itemsUnalloc)}"> 
    	<input type="hidden" id="url_getvendors" value="${url_vendors}">
    	<input type="hidden" id="url_this" value="<c:url value="/order/view?x="/>${x_}">
    	
    	<a href="<c:url value="/order/" />" class="btn btn-default page__button">Back</a>
      	<div class="row">
			<div class="col-md-4">
				<div class="box box-primary">
			    	<div class="box-header with-border">
			        	<h3 class="box-title">General Information</h3>
			        </div>
			        <div class="box-body">
			        	<div class="form-group">
			            	<label class="control-label">Order No.</label>
			            	<p class="form-control-static">${bean.no}</p>
			          	</div>
			          	<div class="form-group">
			            	<label class="control-label">Customer</label>
			            	<p class="form-control-static">
			            	${bean.customer[1]} <br /><!-- <button class="btn btn-default btn-xs">View</button> -->
			            	${bean.customer[2]} <br />
			            	${bean.customer[3]}
			            	</p>
			          	</div>
			          	<div class="form-group">
			            	<label class="control-label">Shipping Address</label>
			            	<p class="form-control-static">${bean.address}</p>
			          	</div>
			          	<div class="form-group">
			            	<label class="control-label">Notes</label>
			            	<p class="form-control-static">${bean.notes}</p>
			          	</div>
			        </div>
				</div>
				<ul class="timeline">
			    <c:forEach items="${bean.statusList}" var="ts">
					<li>
				    	<i class="fa fa-circle-o"></i>
				        <div class="timeline-item">
				        	<h3 class="timeline-header">${ts[1]}</h3>
				            <div class="timeline-body">
				              at ${ts[2]}<br>
				              by user-id.
				            </div>
				            <div class="timeline-footer">
				              <strong>Notes</strong>
				              <p>${ts[3]}</p>
				            </div>
				        </div>
				    </li> 
			    </c:forEach>
			    </ul>
			</div>
			<div class="col-md-8">
				<c:if test="${fn:length(bean.itemsUnalloc)>0}">
				<div class="box box-danger">
			    	<div class="box-header with-border">
			        	<h3 class="box-title">Unallocated Items</h3>
			        </div>
			        <div class="box-body">
			        	<div class="table-responsive">
			            <table class="table table-hover">
			            	<thead>
			              	<tr>
			                	<th>Item</th>
			                	<th>Vendor</th>
			                	<th>Qty</th>
			                	<th>Amount</th>
			                	<th>Action</th>
			              	</tr>
			              	</thead>
			              	<tbody>
			              	<c:forEach items="${bean.itemsUnalloc}" var="itmnove">
				              	<tr>
				                	<td>${itmnove[2]}</td>
				                	<td>
				                  		<div class="form-group">
					                    	<!--TODO: Change with available vendor-->
					                    	<select id="select_vendors_${itmnove[0]}" class="form-control class_vendors">
					                      		<option value="">-</option>
					                    	</select>
				                  		</div>
				                	</td>
				                	<td>${itmnove[5]}</td>
				                	<td>${itmnove[6]}</td>
				                	<td>
				                		<button type="button" class="btn btn-primary btn-xs" data-toggle="modal" 
				                			onclick="showModal('${itmnove[0]}', '${url_set_vendor}');" >Set Vendor</button>
				                	</td>
				              	</tr>
			              	</c:forEach>
			              	</tbody>
			            </table> 
			          	</div>
			        </div>
			    </div>
				</c:if>
				
			    <div class="box box-default">
			        <div class="box-header with-border">
			          <h3 class="box-title">Transaction Items</h3>
			        </div>
			        <div class="box-body">
			        	<div class="table-responsive">
				          	<table class="table table-hover">
			              	<thead>
				              	<tr>
				                	<th>#</th>
				                	<th>Item</th>
				                	<th>Vendor</th>
				                	<th>Status</th>
				              	</tr>
			              	</thead>
			              	<tbody>
			              	<c:forEach items="${bean.itemBeanList}" var="itemBean" varStatus="vs">
			              	<tr>
				                <td>${vs.count}</td>
				                <td>
				                  <p>
				                    ${itemBean.product[1]}<br />
				                    ${itemBean.prodAttr[1]} ${itemBean.prodAttr[2]} <br/>
				                    Rp ${itemBean.price}
				                  </p>
				                  <div class="form-group">
				                    <label class="control-label">Custom Message</label>
				                    <p class="form-control-static">${itemBean.customMsg[0]}</p>
				                    <a href="${itemBean.customMsg[1]}" target="_link"><div class="order__image" style="background-image: url('${itemBean.customMsg[1]}')"></div></a>
				                    <c:if test="${itemBean.customMsg[2]!=''}">
				                    	<a href="${itemBean.customMsg[2]}" target="_link"><div class="order__image" style="background-image: url('${itemBean.customMsg[2]}')"></div></a>
				                    </c:if>
				                  </div>
				                  <div class="form-group">
				                    <label class="control-label">Finished Image</label>
				                    <a href="${itemBean.imgQcUrl}" target="_link"><div class="order__image" style="background-image: url('${itemBean.imgQcUrl}')"></div></a>
				                  </div>
				                  <div class="form-group">
				                    <label class="control-label">Proof of Delivery</label>
				                    <a href="${itemBean.imgPodUrl}" target="_link"><div class="order__image" style="background-image: url('${itemBean.imgPodUrl}')"></div></a>
				                    <!-- <p class="form-control-static">Receiver name (phone)</p> -->
				                  </div>
				                </td>
				                <td width="100px">
				                  ${itemBean.vendor[1]}
				                </td>
				                <td width="250px">
				                  <%-- <code class="text-red">on_admin</code> @ DateTime by User<br>
				                  <code class="text-orange">on_vendor</code> @ DateTime by User<br>
				                  <code class="text-orange">acknowledged</code> @ DateTime by User<br>
				                  <code class="text-blue">ready_qc</code> @ DateTime by User<br>
				                  <code class="text-blue">pass_qc</code> @ DateTime by User<br>
				                  <code class="text-yellow">to_courier</code> @ DateTime by User<br>
				                  <code class="text-green">delivered</code> @ DateTime by User --%>
				                  
				                  <c:forEach items="${itemBean.statusHistory}" var="sh">
				                  	<code class="${sh[4]}">${sh[1]}</code> @ ${sh[2]} ${sh[3]} <br />
				                  </c:forEach>
				                  
				                </td>
			              	</tr>
			              	</c:forEach>
			              	</tbody>
			            	</table>
			        	</div>
			        </div>
				</div>
				<div class="box box-primary">
			    	<div class="box-header with-border">
			        	<h3 class="box-title">Amount</h3>
			        </div>
			        <div class="box-body no-padding">
			        	<div class="table-responsive">
				          	<table class="table table-hover">
			              	<tbody>
			            		<tr>
				                	<td>Item Amount: </td>
				                	<td class="text-right">Rp ${bean.amounts[0]}</td>
				              	</tr>
				              	<tr>
				                	<td>Voucher Discount: </td>
				                	<td class="text-right">- Rp ${bean.amounts[1]}</td>
				              	</tr>
				              	<tr>
				                	<td>Total Amount: </td>
				                	<td class="text-right">Rp ${bean.amounts[2]}</td>
				              	</tr>
			            	</tbody>
			            	</table>
			            </div>  	
			        	<!-- <div class="form-group">
			            	<label class="control-label">Item Amount.</label>
			            	<p class="form-control-static">Rp 2.000</p>
			          	</div>
			          	<div class="form-group">
			            	<label class="control-label">Voucher Discount</label>
			            	<p class="form-control-static">- Rp 500</p>
			          	</div>
			          	<div class="form-group">
			            	<label class="control-label">Total Amount</label>
			            	<p class="form-control-static">Rp 1.500</p>
			          	</div> -->
			        </div>
				</div>
			</div>	    
		</div>	
	</section>
</div>
  
<%@ include file="../general/footer.jsp" %>
<%-- <%@ include file="../general/sidebar-control.jsp" %> --%>
  
</div>

<div class="modal fade" tabindex="-1" role="dialog" id="setVendor">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Vendor Confirmation</h4>
      </div>
      <div class="modal-body">
        <p>Set this vendor to do the job?</p>
        <input type="hidden" id="itemId">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" onclick="btn_setvendor('${url_set_vendor}')">OK</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%-- <c:url value="/lte/" /> --%>
<script src="<c:url value="/lte/plugins/jQuery/jquery-2.2.3.min.js" />"></script>
<script src="<c:url value="/bootstrap/js/bootstrap.min.js" /> "></script>
<script src="<c:url value="/lte/dist/js/app.min.js" />"></script>

<script type="text/javascript">
$(document).ready(function(){
	var itemNoVend = $("#itemsNoVend").val();
	var url_ = $("#url_getvendors").val();
	console.log("url_: "+url_);
	console.log("itemNoVend: "+itemNoVend);
	
	//Get Vendor
	$.ajax({
		url: url_,
  		dataType:'json',
    	type:"POST",
        /* data:"prodId="+id, */
        success: function(data) {
        	console.log('Data: '+data);
        	//$("#select_vendors").empty();
        	$(".class_vendors").empty();
        	
        	for(var i=0;i<data.vendorList.length;i++){
        		var obj = data.vendorList[i];
        		console.log("obj: "+obj);
        		$(".class_vendors").append("<option value=\""+obj.id+"\">"+obj.name+"</option>");
        	}
        }
	});
});

function showModal(id_, url_) {
	console.log('URL: '+url_);
	$("#itemId").val(id_);
	$('#setVendor').modal('show');
}
function btn_setvendor(url_) {
	console.log('btn_setvendor');
	$('#setVendor').modal('hide');
	
	var itemId = $("#itemId").val();
	var vendor = $("#select_vendors_"+itemId+"").val();
	var url_this = $("#url_this").val();
	
	console.log('url_: '+url_);
	console.log('itemId: '+itemId);
	console.log('vendor: '+vendor);
	
	$.ajax({
		url: url_,
  		dataType:'json',
    	type:"POST",
        data:"itemId="+itemId+"&vendorId="+vendor,
        success: function(data) {
        	console.log('Data: '+data);
        	/* var new_row = "<tr><td>"+data[0]+"</td><td>"+data[1]+"</td>"+
            "<td>"+data[2]+"</td><td>"+data[3]+"</td><td><code class=\"text-yellow\">"+data[4]+"</code></td></tr>";
        	$("#tbl_orders tbody").append(new_row); */
        	//alert('Succes! Reload your page, Please');
        	alert('Sukses.');
        	window.location.replace(url_this);
        }
	});

	//$("#select_vendors_"+itemId+"").parents('tr').first().remove();
	//window.location.replace(url_this);
}
</script>



</body>
</html>
