
function modal_show_general() {
	var name 	= $("#p_name_lbl").text();
	var active 	= $("#ph_isactive").val();
	var price 	= $("#p_baseprice").val();
	var desc 	= $("#p_desc_lbl").text();
	
	console.log('active: '+active);
	
	$("#p_name").val(name);
	$("#p_isactive").val(active);
	$("#p_price").val(price);
	$("#p_desc").val(desc);
	$("#modalFormGeneral").modal("show");
}
function product_update(url_, url_pl_change, url_rmv_catspec, url_insert_cat, url_insert_spec, 
		url_cat_getvalues, url_cat_update, url_spec_update) {
	var id = $("#p_id").val();
	var name = $("#p_name").val();
	var pl_before = $("#p_pl_id").val();
	var pl = $("#p_pl").val();
	var pl_name = $("#p_pl option:selected").text();
	var price = $("#p_price").val();
	var desc = $("#p_desc").val();
	var active 	= $("#p_isactive").val();
	
	//console.log('ID: '+id);
	
	var priceInt = parseInt(price);
	if(priceInt<1){
		alert('Harga tidak valid');
		return false;
	}
	
	$.ajax({
		url: url_,
    	type:"POST",
        data:"p_id="+id+"&p_name="+name+"&p_pl="+pl+"&p_price="+price+"&p_desc="+desc+"&p_isactive="+active,
        success: function(data) {
        	console.log('Success1: '+data);
        	//$('#pl_name_lbl').html(name);
        }
	});
	
	var activelbl = "Active";
	if(active=='N'){activelbl = "<span class=\"bg-orange\">&nbsp;Inactive&nbsp;</span>";}
	
	$("#p_name_lbl").html(name);
	$("#p_pl_id").val(pl);
	$("#p_pl_lbl").html(pl_name);
	$("#p_price_lbl").html(price);
	$("#p_desc_lbl").html(desc);
	$("#isactive_lbl").html(activelbl);
	$("#ph_isactive").val(active);
	$('#modalFormGeneral').modal('hide');
	
	if(pl_before!=pl){
		//change Category & Spec
		//Remove in DB
		console.log('url_rmv_catspec: '+url_rmv_catspec);
		
		//Remove List Current in DB
		$.ajax({
			url: url_rmv_catspec,
	  		//dataType:'json',
	    	type:"POST",
	        data:"prodId="+id,
	        success: function(data) {
	        	//console.log('Name: '+data.name);
	        }
		});
		
		$.ajax({
			url: url_pl_change,
	  		dataType:'json',
	    	type:"POST",
	        data:"k="+pl,
	        success: function(data) {
	        	console.log('Name: '+data.name);
	        	console.log('categoryList: '+data.categoryList);
	        	console.log('specs: '+data.specs);
	        	
	        	$("#tbl_cat tbody").empty();
	        	$("#tbl_spec tbody").empty();
	        	generate_pl_category(data.categoryList, url_insert_cat, url_cat_getvalues, url_cat_update);
	   			generate_pl_specs(data.specs, url_insert_spec, url_spec_update);
	        }
		});
	}
	
	return false;
}
function pl_change() {
	var pl_current = $("#p_pl_id").val();
	var pl_selected = $("#p_pl").val();
	//console.log("pl_current: "+pl_current);
	//console.log("pl_selected: "+pl_selected);
	if(pl_current==pl_selected || pl_selected==''){
		return;
	}
	$('#modalChangePl').modal('show');
	return false;
}
function btn_nochange_pl() {
	var pl_current = $("#p_pl_id").val();
	$("#p_pl").val(pl_current);
	$('#modalChangePl').modal('hide');
}
function btn_change_pl() {
	var pl_selected = $("#p_pl").val();
	$("#p_pl").val(pl_selected);
	$('#modalChangePl').modal('hide');
}

function edit_cat(url_, cat_id, pl_cat_id, pl_cat_val) {
	console.log("cat_id: "+cat_id);
	console.log("pl_cat_id: "+pl_cat_id);
	
	$("#cat_val_sel_"+cat_id+"").empty();
	$.ajax({
		url: url_,
		dataType:'json',
    	type:"POST",
        data:"plCatId="+pl_cat_id,
        success: function(data) {
        	//console.log('Success: '+data);
        	var valulist = data.valueList;
        	for(var i=0;i<valulist.length;i++){
        		var dt = valulist[i];var selected = "";if(dt[0]==pl_cat_val) selected = "selected";
        		$("#cat_val_sel_"+cat_id+"").append("<option value=\""+dt[0]+"\" "+selected+">"+dt[1]+"</option>");
        	}
        }
	});
	
	$("#cat_val_lbl_"+cat_id+"").hide();
	$("#cat_val_sel_"+cat_id+"").show();
	$("#btn_cat_edit_"+cat_id+"").hide();
	$("#btn_cat_save_"+cat_id+"").show();
}

function update_cat(url_,cat_id) {
	var pl_cat_val = $("#cat_val_sel_"+cat_id+" option:selected").val();
	var pl_cat_lbl = $("#cat_val_sel_"+cat_id+" option:selected").text();
	/*console.log("url_: "+url_);
	console.log("cat_id: "+cat_id);
	console.log("pl_cat_val: "+pl_cat_val);
	console.log("pl_cat_lbl: "+pl_cat_lbl);*/
	
	$("#cat_val_lbl_"+cat_id+"").html(pl_cat_lbl);
	$("#cat_val_lbl_"+cat_id+"").show();
	$("#cat_val_sel_"+cat_id+"").hide();
	$("#btn_cat_edit_"+cat_id+"").show();
	$("#btn_cat_save_"+cat_id+"").hide();
	
	$.ajax({
		url: url_,
		//dataType:'json',
    	type:"POST",
        data:"id="+cat_id+"&value="+pl_cat_val,
        success: function(data) {
        	console.log('Success: '+data);
        }
	});
}
function edit_spec(spec_id) {
	//console.log("url_: "+url_);
	console.log("spec_id: "+spec_id);
	
	$("#spec_lbl_"+spec_id+"").hide();
	$("#spec_val_"+spec_id+"").show();
	$("#btn_spec_edit_"+spec_id+"").hide();
	$("#btn_spec_save_"+spec_id+"").show();
}
function update_spec(url_, spec_id) {
	var value = $("#spec_val_"+spec_id+"").val();
	/*console.log("url_: "+url_);
	console.log("spec_id: "+spec_id);
	console.log("value: "+value);*/
	
	$("#spec_lbl_"+spec_id+"").html(value);
	$("#spec_lbl_"+spec_id+"").show();
	$("#spec_val_"+spec_id+"").hide();
	$("#btn_spec_edit_"+spec_id+"").show();
	$("#btn_spec_save_"+spec_id+"").hide();
	
	$.ajax({
		url: url_,
		//dataType:'json',
    	type:"POST",
        data:"id="+spec_id+"&value="+value,
        success: function(data) {
        	console.log('Success: '+data);
        }
	});
}
function generate_pl_category(categories, url_inst, url_cat_getvalues, url_cat_update) {
	var idcoll = "";
	for(var i=0;i<categories.length;i++){
		var cat = categories[i];
		var id = cat.id;
		var label = cat.label;
		var values = cat.valueList;
		idcoll = idcoll + id + ";";
		
		var opts = "";
		for(var p=0;p<values.length;p++){
			var v = values[p];
			opts = opts+ "<option value=\""+v[0]+"\">"+v[1]+"</option>";
		}
		
		var new_row = "<tr><td>"+label+"</td>"+
			"<td colspan=\"2\"><select id=\"cat_val_sel_"+id+"\" class=\"form-control\" >"+opts+"</select></td></tr>";
		$("#tbl_cat tbody").append(new_row);
	} 
	$("#tbl_cat tbody").append("<tr><td>&nbsp;</td><td colspan=\"2\"><input type=\"hidden\" id=\"catIdColl\" value=\""+idcoll+"\">" +
			"<button class=\"btn btn-primary btn-sm\" onclick=\"save_cat('"+url_inst+"', '"+url_cat_getvalues+"','"+url_cat_update+"');\">Save</button></td></tr>");
}
function generate_pl_specs(specs, url_inst, url_spec_update) {
	var idcoll = "";
	for(var i=0;i<specs.length;i++){
		var spec = specs[i];
		var id_ = spec[0];
		var label = spec[1];
		idcoll = idcoll + id_ + ";";
		var new_row = "<tr><td>"+label+"</td>"+
		                "<td colspan=\"2\">" +
		                "<input type=\"text\" id=\"spec_val_"+id_+"\" class=\"form-control\"></td>"+
		               "</tr>";
		$("#tbl_spec tbody").append(new_row);
	}
	$("#tbl_spec tbody").append("<tr><td>&nbsp;</td><td colspan=\"2\"><input type=\"hidden\" id=\"specIdColl\" value=\""+idcoll+"\">" +
			"<button class=\"btn btn-primary btn-sm\" onclick=\"save_spec('"+url_inst+"','"+url_spec_update+"');\">Save</button></td></tr>");
}
function save_cat(url_save, url_cat_getvalues, url_cat_update) {
	var p_id = $("#p_id").val();
	var idcoll = $("#catIdColl").val();
	
	//alert('Save Cat');
	//console.log("url_: "+url_save);
	//console.log("p_id: "+p_id);
	//console.log("idcoll: "+idcoll);
	
	var values = [];
	var split = idcoll.split(";");
	for(var i=0;i<split.length;i++){
		var v = split[i];
		if(v!=''){
			var val = $("#cat_val_sel_"+v+"").val();
			values.push(val);
		}
	}
	var obj = {'values':values}
	var catValues = $.param(obj, true);
	console.log("catValues: "+catValues);
	console.log("url_save: "+url_save);
	
	$.ajax({
		url: url_save,
		dataType:'json',
    	type:"POST",
        data:"prodId="+p_id+"&"+catValues,
        success: function(data) {
        	console.log('Success: '+data.categories);
        	$("#tbl_cat tbody").empty();
        	var catList = data.categories;
        	for(var c=0;c<catList.length;c++){
        		var dt = catList[c];
        		var new_row = "<tr><td>"+dt[3]+"</td>" +
					"<td><div id=\"cat_val_lbl_"+dt[0]+"\">"+dt[4]+"</div>" +
						"<select id=\"cat_val_sel_"+dt[0]+"\" class=\"form-control\" style=\"display: none;\">" +
						"<option value=\"\">-</option></select></td>" +
					"<td><button id=\"btn_cat_edit_"+dt[0]+"\" class=\"btn btn-link btn-xs\" onclick=\"edit_cat('"+url_cat_getvalues+"', '"+dt[0]+"', '"+dt[2]+"','"+dt[1]+"')\">"+
							"<i class=\"fa fa-pencil fa-fw\"></i></button>"+
						"<button id=\"btn_cat_save_"+dt[0]+"\" class=\"btn btn-link btn-sm\" onclick=\"update_cat('"+url_cat_update+"','"+dt[0]+"')\" style=\"display: none;\">"+
							"<i class=\"fa fa-save fa-fw\"></i></button>"+	
					"</td></tr>";
        		$("#tbl_cat tbody").append(new_row);
        	}
        }
	});
}
function save_spec(url_save, url_spec_update) {
	var p_id = $("#p_id").val();
	var idcoll = $("#specIdColl").val();
	
	//alert('Save Spec');
	console.log("url_: "+url_save);
	console.log("p_id: "+p_id);
	console.log("idcoll: "+idcoll);
	
	var values = [];
	var split = idcoll.split(";");
	for(var i=0;i<split.length;i++){
		var v = split[i];
		if(v!=''){
			var val = $("#spec_val_"+v+"").val();
			values.push(val);
		}
	}
	var obj = {'values':values}
	var specValues = $.param(obj, true);
	console.log("specValues: "+specValues);
	
	$.ajax({
		url: url_save,
		dataType:'json',
    	type:"POST",
        data:"prodId="+p_id+"&idcoll="+idcoll+"&"+specValues,
        success: function(data) {
        	console.log('Success: '+data);
        	$("#tbl_spec tbody").empty();
        	var specList = data.specs;
        	for(var c=0;c<specList.length;c++){
        		var dt = specList[c];
        		var new_row = "<tr><td>"+dt[1]+"</td>" +
					"<td><div id=\"spec_lbl_"+dt[0]+"\">"+dt[2]+"</div>" +
					"<input type=\"text\" id=\"spec_val_"+dt[0]+"\" value=\""+dt[2]+"\" class=\"form-control\" style=\"display: none;\"></td>" +
					"<td><button id=\"btn_spec_edit_"+dt[0]+"\" class=\"btn btn-link btn-xs\" onclick=\"edit_spec('"+dt[0]+"')\">"+
							"<i class=\"fa fa-pencil fa-fw\"></i></button>" +
						"<button id=\"btn_spec_save_"+dt[0]+"\" class=\"btn btn-link btn-xs\" onclick=\"update_spec('"+url_spec_update+"', '"+dt[0]+"');\" style=\"display: none;\">"+
							"<i class=\"fa fa-save fa-fw\"></i></button>" +
					"</td></tr>";
        		$("#tbl_spec tbody").append(new_row);
        	}
        }
	});
}

function attr_edit_btn(attr_id) {
	console.log("attr_id: "+attr_id);
	

	$("#patr_lbl_dv_"+attr_id+"").hide();
	$("#patr_sku_dv_"+attr_id+"").hide();
	$("#patr_rmrk_dv_"+attr_id+"").hide();
	$("#patr_stk_dv_"+attr_id+"").hide();
	$("#patr_prc_dv_"+attr_id+"").hide();
	
	$("#patr_lbl_in_"+attr_id+"").show();
	$("#patr_sku_in_"+attr_id+"").show();
	$("#patr_rmrk_in_"+attr_id+"").show();
	$("#patr_stk_in_"+attr_id+"").show();
	$("#patr_prc_in_"+attr_id+"").show();
	
	$("#btn_attr_edit_"+attr_id+"").hide();
	$("#btn_attr_save_"+attr_id+"").show();
	$("#btn_attr_del_"+attr_id+"").hide();
}
function attr_save_btn(attr_id, url_update) {
	//console.log("attr_id: "+attr_id);
	
	var lbl = $("#patr_lbl_in_"+attr_id+"").val();
	var sku = $("#patr_sku_in_"+attr_id+"").val();
	var remark = $("#patr_rmrk_in_"+attr_id+"").val();
	var stock = $("#patr_stk_in_"+attr_id+"").val();
	var price = $("#patr_prc_in_"+attr_id+"").val();
	
	$("#patr_lbl_dv_"+attr_id+"").html(lbl);
	$("#patr_sku_dv_"+attr_id+"").html(sku);
	$("#patr_rmrk_dv_"+attr_id+"").html(remark);
	$("#patr_stk_dv_"+attr_id+"").html(stock);
	$("#patr_prc_dv_"+attr_id+"").html(price);
	
	$("#patr_lbl_dv_"+attr_id+"").show();
	$("#patr_sku_dv_"+attr_id+"").show();
	$("#patr_rmrk_dv_"+attr_id+"").show();
	$("#patr_stk_dv_"+attr_id+"").show();
	$("#patr_prc_dv_"+attr_id+"").show();
	
	$("#patr_lbl_in_"+attr_id+"").hide();
	$("#patr_sku_in_"+attr_id+"").hide();
	$("#patr_rmrk_in_"+attr_id+"").hide();
	$("#patr_stk_in_"+attr_id+"").hide();
	$("#patr_prc_in_"+attr_id+"").hide();
	
	$("#btn_attr_edit_"+attr_id+"").show();
	$("#btn_attr_save_"+attr_id+"").hide();
	$("#btn_attr_del_"+attr_id+"").show();
	
	$.ajax({
		url: url_update,
		//dataType:'json',
    	type:"POST",
        data:"attrId="+attr_id+"&label="+lbl+"&sku="+sku+"&remark="+remark+"&stock="+stock+"&price="+price,
        success: function(data) {
        	console.log('Success: '+data);
        }
	});
}
function attr_add() {
	$("#attr_add_dv").toggle();
}
function attr_insert(url_insert) {
	var prodId = $("#p_id").val();
	var lbl = $("#patr_lbl_in_0").val();
	var sku = $("#patr_sku_in_0").val();
	var remark = $("#patr_rmrk_in_0").val();
	var stock = $("#patr_stck_in_0").val();
	var price = $("#patr_pric_in_0").val();
	
	console.log('lbl: '+lbl);
	console.log('sku: '+sku);
	console.log('remark: '+remark);
	console.log('stock: '+stock);
	console.log('price: '+price);
	
	$("#attr_add_dv").toggle();
	
	$.ajax({
		url: url_insert,
		//dataType:'json',
    	type:"POST",
        data:"prodId="+prodId+"&label="+lbl+"&sku="+sku+"&remark="+remark+"&stock="+stock+"&price="+price,
        success: function(data) {
        	console.log('Success: '+data);
        }
	});
}
function attr_delete(this_, attr_id, url_remove) {
	$(this_).parents('tr').first().remove();
	
	$.ajax({
		url: url_remove,
		//dataType:'json',
    	type:"POST",
        data:"attrId="+attr_id,
        success: function(data) {
        	console.log('Success: '+data);
        }
	});
}


//IMAGES
function modal_change_image(seq) {
	var prodId = $("#p_id").val();
	
	console.log('ProdId: '+prodId);
	
	$("#pimages_in_seq").val(seq);
	$("#pimages_in_pid").val(prodId);
	$("#pimages_in").val("").clone(true);
	$("#modalChangeImage").modal("show")
}
