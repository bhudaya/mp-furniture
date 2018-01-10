
function get_cities() {
	var url_ = $("#url_getcity").val();
	var province = $("#prov_in").val();
	
	$("#city_in option").remove();
	$('#city_in').append($("<option></option>").attr("value", "").text("-"));
	
	if(province!=''){
		$.ajax({
			url: url_,
			dataType:'json',
	    	type:"POST",
	        data:"provId="+province,
	        success: function(data) {
	        	console.log('Success: '+data);
	        	//alert('Success: '+JSON.stringify(data));
	        	$.each(data, function(index, value) {
	        		//console.log( index + ": " + value );
	        		$('#city_in').append($("<option></option>").attr("value",value[0]).text(value[1]));
	        	});
	        }
		});
	}
}

$("#crdlmt_in").on('change', function() {
	var val = $("#crdlmt_in").val();
	var removeDot = val.split('.').join("");
	var replc = removeDot.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
	$("#crdlmt_in").val(replc);
});

function numberOnly(event) {
    var key = window.event ? event.keyCode : event.which;
    if (event.keyCode === 8 || event.keyCode === 46) {
        return true;
    } else if ( key < 48 ) {
        return false;
    } else if(key > 57){
    	return false;
    } else {
        return true;
    }
};

function coverage_add() {
	var cvr_id = $("#cvr_id").val();
	var cvr_idcoll = $("#cvr_idcoll").val();
	
	var city_id = $("#city_cvr_in").val();
	var city_label = $("#city_cvr_in option:selected").text();
	var vsla_in = $("#sla_in").val();
	
	if(city_id==''){
		alert("Please select city");
		$("#city_cvr_in").focus();
		return false;
	}
	if(vsla_in==''){
		alert("Please input SLA Time");
		$("#sla_in").focus();
		return false;
	}
	
	var id_ = parseInt(cvr_id) + 1;
	var idcoll = cvr_idcoll + id_ + ";";
	new_row_coverage(id_, city_id, city_label, vsla_in);
	$("#cvr_id").val(id_);
	$("#cvr_idcoll").val(idcoll);
	
	return false;
}

function new_row_coverage(id_, city_id, city_nm, sla) {
	var new_row = "<tr>"+
		    "<td>"+
		      	"<div id=\"pcvr_city_dv_"+id_+"\" class=\"form-group\">"+city_nm+"</div>"+
		        "<input id=\"pcvr_city_in_"+id_+"\" name=\"pcvr_city_in_"+id_+"\" class=\"form-control\" type=\"hidden\" value=\""+city_id+"\">"+
		    "</td>"+
		    "<td><div id=\"pcvr_sla_dv_"+id_+"\" class=\"form-group\">"+sla+"</div>"+
		        "<input id=\"pcvr_sla_in_"+id_+"\" name=\"pcvr_sla_in_"+id_+"\" class=\"form-control\" type=\"hidden\" value=\""+sla+"\">"+
		    "</td>"+
		    "<td>"+
		    	"<button type=\"button\" class=\"btn btn-default btn-xs\" onclick=\"return del_row_coverage(this, "+id_+");\">Remove</button>"+
		    "</td>"+
  		"</tr>";					
	$("#tbl_coverage tbody").append(new_row);
}

function del_row_coverage(this_, id_) {
	var idcoll = $("#cvr_idcoll").val();
	var split = idcoll.split(id_+";");
	
	$(this_).parents('tr').first().remove();
	$("#cvr_idcoll").val(split[0] + split[1]);
	
	return (false);
}