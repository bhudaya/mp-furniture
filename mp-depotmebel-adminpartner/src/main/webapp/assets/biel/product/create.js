
function pcategory_selected() {
	var url_sub = $("#url_getsubcategory").val();
	var url_spec = $("#url_getspeccategory").val();
	var url_attr = $("#url_getattrcategory").val();
	var pcategoryId = $("#pcategory_in").val();
	
	//console.log('url_sub: '+url_sub);
	//console.log('url_spec: '+url_spec);
	
	if(pcategoryId==''){
		$("#bb_spec").html("<span id=\"spec_hb\">No specification found.</span>"+
		"<span id=\"spec_pw\" class=\"text-red\" style=\"display: none;\"><strong>Please wait..</strong></span>");
	}else{
		$("#spec_hb").hide();
		$("#spec_pw").show();
		
		$("#attr_hb").hide();
		$("#attr_pw").show();
		
		$("#psubcategory_in option").remove();
		$('#psubcategory_in').append($("<option></option>").attr("value", "").text("-"));
		$.ajax({
			url: url_sub,
			dataType:'json',
	    	type:"POST",
	        data:"pcategoryId="+pcategoryId,
	        success: function(data) {
	        	//console.log('Success: '+data);
	        	$.each(data, function(index, value) {
	        		//console.log( index + ": " + value );
	        		 $('#psubcategory_in').append($("<option></option>").attr("value",value[0]).text(value[1]));
	        	});
	        }
		});
		
		$.ajax({
			url: url_spec,
			dataType:'json',
	    	type:"POST",
	        data:"pcategoryId="+pcategoryId,
	        success: function(data) {
	        	//console.log('Success: '+data);
	        	
	        	$("#spec_pw").hide();
	        	generate_specs(data);
	        }
		});
		
		$.ajax({
			url: url_attr,
			dataType:'json',
	    	type:"POST",
	        data:"pcategoryId="+pcategoryId,
	        success: function(data) {
	        	//console.log('Success: '+data);
	        	
	        	$("#attr_pw").hide();
	        	$("#attr_desc").show();
	        	generate_attr(data);
	        }
		});
	}
	
	return false;
}

function generate_specs(specs) {
	//console.log('specs-length: '+specs.length);
	//console.log('specs: '+JSON.stringify(specs));
	if(specs.length<1){
		$("#bb_spec").html("<span id=\"spec_hb\">No specification found.</span>"+
        	"<span id=\"spec_pw\" class=\"text-red\" style=\"display: none;\"><strong>Please wait..</strong></span>");
	}else{
		var idcoll = "";
		var new1 = "";
		for(var i=0;i<specs.length;i++){
			var spec = specs[i];
			var id_ = spec[2];
			var label = spec[1];
			//console.log('i-'+i+": "+cat.label);
			
			idcoll = idcoll + id_ + ";";
			var new_ = "<div id=\"spec_dv_"+id_+"\" class=\"form-group\">"+
							"<label class=\"control-label\">"+label+"</label>"+
							"<input id=\"spec_lbl_"+id_+"\" name=\"spec_lbl_"+id_+"\" class=\"form-control\">"+
	      	"</div>";
			new1 = new1 + new_;
		}
		$("#spec_idcoll").val(idcoll);
		$("#bb_spec").html(new1);
	}
}

function generate_attr(attrList) {
	//console.log('attrList-length: '+attrList.length);
	//console.log('attrList: '+JSON.stringify(attrList));
	if(attrList.length<1){
		$("#bb_attr").html("<span id=\"attr_hb\">No Attributes found.</span>"+
        	"<span id=\"attr_pw\" class=\"text-red\" style=\"display: none;\"><strong>Please wait..</strong></span>");
	}else{
		var idcoll = "";
		var new1 = "<span class=\"text-success\">Input values, separated by comma (,)</span><br />";
		new1 = new1 + "<span class=\"text-success\">You must be click 'Genereate' for Product SKU</span><br /><br />";
		for(var i=0;i<attrList.length;i++){
			var attr = attrList[i];
			var id_ = attr[2];
			var label = attr[1];
			//console.log('i-'+i+": "+cat.label);
			
			idcoll = idcoll + id_ + ";";
			var new_ = "<div id=\"attr_dv_"+id_+"\" class=\"form-group\">"+
							"<label class=\"control-label\">"+label+"</label>"+
							"<input id=\"attr_lbl_"+id_+"\" name=\"attr_lbl_"+id_+"\" class=\"form-control\">"+
	      	"</div>";
			new1 = new1 + new_;
		}
		
		new1 = new1 + "<button type=\"button\" class=\"btn btn-sm btn-default\" onclick=\"generate_sku();\" >Geneate</button>";
		
		$("#attr_idcoll").val(idcoll);
		$("#bb_attr").html(new1);
	}
}

function generate_sku() {
	$("#sku_hb").hide();
	$("#sku_pw").show();
	
	var url_generatesku = $("#url_generatesku").val();
	var idcoll = $("#attr_idcoll").val();
	var params = "attr_idcoll="+idcoll;
	
	var split_id = idcoll.split(";");
	for(var i=0;i<split_id.length;i++){
		var id = split_id[i].trim();
		if(id!=''){
			var value_ = $("#attr_lbl_"+id).val();
			params = params + "&attr_lbl_"+id+"="+value_.trim();
		}
	}
	//console.log('params: '+params);
	
	var rowCount = $('#tbl_sku tr').length;
	if(rowCount>1){
		$("#sku_idcoll").val('');
		$("#tbl_sku tbody").empty();
	}
	
	$.ajax({
		url: url_generatesku,
		dataType:'json',
    	type:"POST",
        data:params,
        success: function(data) {
        	//console.log('Success: '+data);
        	
        	$("#sku_pw").hide();
        	if(data.skuList.length<1){
        		$("#sku_hb").show();
        		$("#sku_hb").html("No Product SKU found.");
        		$("#sku_hb").attr("class","text-red")
        	}else{
        		$("#sku_hb").hide();
        		var row_ = "";
        		$.each(data.skuList, function(index, value) {
            		//console.log('value: '+value);
        			var new_row = "<tr>"+
	                				"<td>"+value[1]+"<input type='hidden' name='prodsku_label_"+value[0]+"' value='"+value[1]+"' class='form-control'></td>"+
						            "<td><input type='text' name='prodsku_remark_"+value[0]+"' class='form-control'></td>"+
						            "<td><input type='number' name='prodsku_stok_"+value[0]+"' class='form-control'></td>"+
						            "<td><input type='number' name='prodsku_price_"+value[0]+"' class='form-control'></td>"+
	              				"</tr>";
        			row_ = row_ + new_row;
            	});
        		
        		$("#sku_idcoll").val(data.desc);
        		$("#tbl_sku tbody").append(row_);
        	}
        }
	});
	
}

