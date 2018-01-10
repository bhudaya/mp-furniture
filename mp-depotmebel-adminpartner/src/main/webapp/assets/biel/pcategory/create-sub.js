
function subcat_add() {
	var name = $("#sub_name_in").val();
	var lastid = $("#sub_last_id").val();
	var idcoll = $("#sub_idcoll").val();
	
	if(name!=''){
	
		var lastid_new = parseInt(lastid) + 1;
		var tbl = $("#tbl_sub tbody");
		
		var new_row = "<tr>" +
				"<td><div id=\"sub_name_"+(lastid_new)+"\">"+name+"</div>" +
					"<input type='hidden' id='sub_name_hd"+lastid_new+"' name='sub_name_hd"+lastid_new+"' value='"+name+"' class='form-control'>" +
				"</td>" +
				"<td>" +
				"<button type='button' id='sub_btnedt_"+lastid_new+"' onclick=\"return subcat_edit_show('"+lastid_new+"')\" class=\"btn btn-xs\">" +
					"<i class=\"fa fa-edit\"></i></button>&nbsp;"+
				"<button type='button' id='sub_btnupd_"+lastid_new+"' onclick=\"return subcat_edit_submit('"+lastid_new+"')\" style=\"display: none;\" class=\"btn btn-xs\">" +
					"<i class=\"fa fa-save\"></i></button>&nbsp;"+
				"<button type='button' id='sub_btndel_"+lastid_new+"' onclick=\"return subcat_delete(this, '"+lastid_new+"')\" class=\"btn btn-xs\" onclick=\"\">"+
					"<i class=\"fa fa-remove\"></i></button>" +
				"</td>" +
			"</tr>";
		
		tbl.append(new_row);
		$("#sub_last_id").val(lastid_new);
		$("#sub_idcoll").val(idcoll+lastid_new+";");
		$("#sub_name_in").val("");
	}
	
	$("#sub_name_in").focus();
	
	return false;
}

function subcat_edit_show(id_) {
	$("#sub_name_"+id_).hide();
	$("#sub_name_hd"+id_).attr("type","text");
	
	$("#sub_btnedt_"+id_).hide();
	$("#sub_btnupd_"+id_).show();
	$("#sub_btndel_"+id_).hide();
	
	return false;
} 

function subcat_edit_submit(id_) {
	var new_ = $("#sub_name_hd"+id_).val();
	$("#sub_name_"+id_).html(new_);
	$("#sub_name_"+id_).show();
	$("#sub_name_hd"+id_).attr("type","hidden");
	
	$("#sub_btnedt_"+id_).show();
	$("#sub_btnupd_"+id_).hide();
	$("#sub_btndel_"+id_).show();
	
	return false;
}

function subcat_delete(this_, id_) {
	var idcoll = $("#sub_idcoll").val();
	var split = idcoll.split(id_+";");
	
	$(this_).parents('tr').first().remove();
	$("#sub_idcoll").val(split[0]+split[1]);
}
