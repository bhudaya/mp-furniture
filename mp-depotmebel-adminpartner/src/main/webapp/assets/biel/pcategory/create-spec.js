
function speccat_add() {
	var name = $("#spec_name_in").val();
	var lastid = $("#spec_last_id").val();
	var idcoll = $("#spec_idcoll").val();
	
	if(name!=''){
	
		var lastid_new = parseInt(lastid) + 1;
		var tbl = $("#tbl_spec tbody");
		
		var new_row = "<tr>" +
				"<td><div id=\"spec_name_"+(lastid_new)+"\">"+name+"</div>" +
					"<input type='hidden' id='spec_name_hd"+lastid_new+"' name='spec_name_hd"+lastid_new+"' value='"+name+"' class='form-control'>" +
				"</td>" +
				"<td>" +
				"<button type='button' id='spec_btnedt_"+lastid_new+"' onclick=\"return speccat_edit_show('"+lastid_new+"')\" class=\"btn btn-xs\">" +
					"<i class=\"fa fa-edit\"></i></button>&nbsp;"+
				"<button type='button' id='spec_btnupd_"+lastid_new+"' onclick=\"return speccat_edit_submit('"+lastid_new+"')\" style=\"display: none;\" class=\"btn btn-xs\">" +
					"<i class=\"fa fa-save\"></i></button>&nbsp;"+
				"<button type='button' id='spec_btndel_"+lastid_new+"' onclick=\"return speccat_delete(this, '"+lastid_new+"')\" class=\"btn btn-xs\" onclick=\"\">"+
					"<i class=\"fa fa-remove\"></i></button>" +
				"</td>" +
			"</tr>";
		
		tbl.append(new_row);
		$("#spec_last_id").val(lastid_new);
		$("#spec_idcoll").val(idcoll+lastid_new+";");
		$("#spec_name_in").val("");
	}
	
	$("#spec_name_in").focus();
	
	return false;
}

function speccat_edit_show(id_) {
	$("#spec_name_"+id_).hide();
	$("#spec_name_hd"+id_).attr("type","text");
	
	$("#spec_btnedt_"+id_).hide();
	$("#spec_btnupd_"+id_).show();
	$("#spec_btndel_"+id_).hide();
	
	return false;
} 

function speccat_edit_submit(id_) {
	var new_ = $("#spec_name_hd"+id_).val();
	$("#spec_name_"+id_).html(new_);
	$("#spec_name_"+id_).show();
	$("#spec_name_hd"+id_).attr("type","hidden");
	
	$("#spec_btnedt_"+id_).show();
	$("#spec_btnupd_"+id_).hide();
	$("#spec_btndel_"+id_).show();
	
	return false;
}

function speccat_delete(this_, id_) {
	var idcoll = $("#spec_idcoll").val();
	var split = idcoll.split(id_+";");
	
	$(this_).parents('tr').first().remove();
	$("#spec_idcoll").val(split[0]+split[1]);
}
