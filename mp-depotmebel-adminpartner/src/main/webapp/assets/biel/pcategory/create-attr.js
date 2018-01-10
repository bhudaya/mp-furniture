
function attrcat_add() {
	var name = $("#attr_name_in").val();
	var lastid = $("#attr_last_id").val();
	var idcoll = $("#attr_idcoll").val();
	
	if(name!=''){
	
		var lastid_new = parseInt(lastid) + 1;
		var tbl = $("#tbl_attr tbody");
		
		var new_row = "<tr>" +
				"<td><div id=\"attr_name_"+(lastid_new)+"\">"+name+"</div>" +
					"<input type='hidden' id='attr_name_hd"+lastid_new+"' name='attr_name_hd"+lastid_new+"' value='"+name+"' class='form-control'>" +
				"</td>" +
				"<td>" +
				"<button type='button' id='attr_btnedt_"+lastid_new+"' onclick=\"return attrcat_edit_show('"+lastid_new+"')\" class=\"btn btn-xs\">" +
					"<i class=\"fa fa-edit\"></i></button>&nbsp;"+
				"<button type='button' id='attr_btnupd_"+lastid_new+"' onclick=\"return attrcat_edit_submit('"+lastid_new+"')\" style=\"display: none;\" class=\"btn btn-xs\">" +
					"<i class=\"fa fa-save\"></i></button>&nbsp;"+
				"<button type='button' id='attr_btndel_"+lastid_new+"' onclick=\"return attrcat_delete(this, '"+lastid_new+"')\" class=\"btn btn-xs\" onclick=\"\">"+
					"<i class=\"fa fa-remove\"></i></button>" +
				"</td>" +
			"</tr>";
		
		tbl.append(new_row);
		$("#attr_last_id").val(lastid_new);
		$("#attr_idcoll").val(idcoll+lastid_new+";");
		$("#attr_name_in").val("");
	}
	
	$("#attr_name_in").focus();
	
	return false;
}

function attrcat_edit_show(id_) {
	$("#attr_name_"+id_).hide();
	$("#attr_name_hd"+id_).attr("type","text");
	
	$("#attr_btnedt_"+id_).hide();
	$("#attr_btnupd_"+id_).show();
	$("#attr_btndel_"+id_).hide();
	
	return false;
} 

function attrcat_edit_submit(id_) {
	var new_ = $("#attr_name_hd"+id_).val();
	$("#attr_name_"+id_).html(new_);
	$("#attr_name_"+id_).show();
	$("#attr_name_hd"+id_).attr("type","hidden");
	
	$("#attr_btnedt_"+id_).show();
	$("#attr_btnupd_"+id_).hide();
	$("#attr_btndel_"+id_).show();
	
	return false;
}

function attrcat_delete(this_, id_) {
	var idcoll = $("#attr_idcoll").val();
	var split = idcoll.split(id_+";");
	
	$(this_).parents('tr').first().remove();
	$("#attr_idcoll").val(split[0]+split[1]);
}
