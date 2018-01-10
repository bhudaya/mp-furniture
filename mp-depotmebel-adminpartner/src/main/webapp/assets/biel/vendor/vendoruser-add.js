
function venduser_add() {
	var user_name = $("#user_name").val();
	var user_email = $("#user_email").val();
	var user_phone = $("#user_phone").val();
	var user_role = $("#user_role").val();
	
	var vuser_no = $("#huser_no").val();
	var vuser_nocoll = $("#huser_nocoll").val();
	
	if(user_name=='' ){
		alert("Please input User name");
		$("#user_name").focus();
		return (false);
	}
	
	if(user_email=='' ){
		alert("Please input User email");
		$("#user_email").focus();
		return (false);
	}
	
	if(user_phone=='' ){
		alert("Please input User phone");
		$("#user_phone").focus();
		return (false);
	}
	
	var vuplus = parseInt(vuser_no) + 1;
	new_row_vendoruser(vuplus, user_name, user_email, user_phone, user_role);
	
	var new_user_nocoll = vuser_nocoll + vuplus+";"
	
	$("#huser_no").val(vuplus);
	$("#huser_nocoll").val(new_user_nocoll);
	
	$("#user_name").val("");
	$("#user_email").val("");
	$("#user_phone").val("");
	
	return false;
}

function new_row_vendoruser(user_no, user_name, user_email, user_phone, user_role) {
	//console.log('new_row_vendoruser');	
	
	var user_role_lbl = noaction_getvalue_role(user_role);
	var options = noaction_getselection_role(user_role);
	
	var new_row_user = "<tr>"+
		"<td><div id=\"dnr_name_"+user_no+"\">"+user_name+"</div>"+
			"<input type=\"hidden\" id=\"nr_name_"+user_no+"\" name=\"nr_name_"+user_no+"\" class=\"form-control\" value=\""+user_name+"\" /></td>"+
		"<td><div id=\"dnr_email_"+user_no+"\">"+user_email+"</div>"+
			"<input type=\"hidden\" id=\"nr_email_"+user_no+"\" name=\"nr_email_"+user_no+"\" class=\"form-control\" value=\""+user_email+"\" /></td>"+
		"<td><div id=\"dnr_phone_"+user_no+"\">"+user_phone+"</div>"+
			"<input type=\"hidden\" id=\"nr_phone_"+user_no+"\" name=\"nr_phone_"+user_no+"\" class=\"form-control\" value=\""+user_phone+"\" /></td>"+
		"<td><div id=\"dnr_role_"+user_no+"\">"+user_role_lbl+"</div>"+
			"<select id=\"nr_role_"+user_no+"\" name=\"nr_role_"+user_no+"\" class=\"form-control\" style=\"display: none;\">"+options+"</select></td>"+
		"<td><button id=\"nr_btnedit_"+user_no+"\" type=\"button\" class=\"btn btn-default btn-xs\" onclick=\"return edit_row("+user_no+");\"><i class=\"fa fa-edit\"></i></button>"+
		"<button id=\"nr_btnsave_"+user_no+"\" type=\"button\" style=\"display: none;\" class=\"btn btn-default btn-xs\" onclick=\"return update_row("+user_no+");\"><i class=\"fa fa-save\"></i></button>"+
		"<button type=\"button\" class=\"btn btn-default btn-xs\" "+
		"onclick=\"return del_row_vuser(this,'"+user_no+"','"+user_name+"','"+user_email+"','"+user_phone+"','"+user_role+"');\">"+
		"<i class=\"fa fa-remove\"></i></button></td></tr>";					
	$("#tbl_vuser tbody").append(new_row_user);
}

function noaction_getvalue_role(user_role) {
	var arrjs = $("#userRolesStr").val();
	var arrjs_split = arrjs.split(";");
	var user_role_lbl = "";
	//var options = "", selected="";
	for(var i=0;i<arrjs_split.length;i++){
		var arrjs_split_val = arrjs_split[i];
		var againsplit = arrjs_split_val.split("-");
		if(againsplit[0]==user_role){
			user_role_lbl = againsplit[1];
		}
	}
	return user_role_lbl;
}

function noaction_getselection_role(user_role) {
	var arrjs = $("#userRolesStr").val();
	var arrjs_split = arrjs.split(";");
	var user_role_lbl = "";
	var options = "", selected="";
	for(var i=0;i<arrjs_split.length;i++){
		var arrjs_split_val = arrjs_split[i];
		var againsplit = arrjs_split_val.split("-");
		if(againsplit[0]==user_role){
			user_role_lbl = againsplit[1];
			selected = "selected"
		}else{
			selected = ""
		}
		options = options + "<option value=\""+againsplit[0]+"\" "+selected+">"+againsplit[1]+"</option>";
	}
	return options;
}

function del_row_vuser(this_, user_no, user_name, user_email, user_phone, user_role) {
	var vuser_nocoll = $("#huser_nocoll").val();
	var vuser_no_split = vuser_nocoll.split(user_no+";");
	
	$(this_).parents('tr').first().remove();
	$("#huser_nocoll").val(vuser_no_split[0]+vuser_no_split[1]);
	
	return false;
}

function edit_row(id_) {
	
	$("#dnr_name_"+id_).hide();
	$("#nr_name_"+id_).attr("type","text");
	
	$("#dnr_email_"+id_).hide();
	$("#nr_email_"+id_).attr("type","text");
	
	$("#dnr_phone_"+id_).hide();
	$("#nr_phone_"+id_).attr("type","text");
	
	$("#dnr_role_"+id_).hide();
	$("#nr_role_"+id_).show();
	
	$("#nr_btnedit_"+id_).hide();
	$("#nr_btnsave_"+id_).show();
	
	return false;
}

function update_row(id_) {
	var name = $("#nr_name_"+id_).val();
	var email = $("#nr_email_"+id_).val();
	var phone = $("#nr_phone_"+id_).val();
	var role = $("#nr_role_"+id_).val();
	
	$("#dnr_name_"+id_).html(name);
	$("#dnr_name_"+id_).show();
	$("#nr_name_"+id_).attr("type","hidden");
	
	$("#dnr_email_"+id_).html(email);
	$("#dnr_email_"+id_).show();
	$("#nr_email_"+id_).attr("type","hidden");
	
	$("#dnr_phone_"+id_).html(phone);
	$("#dnr_phone_"+id_).show();
	$("#nr_phone_"+id_).attr("type","hidden");
	
	var role_lbl = noaction_getvalue_role(role);
	$("#dnr_role_"+id_).html(role_lbl);
	$("#dnr_role_"+id_).show();
	$("#nr_role_"+id_).hide();
	
	$("#nr_btnedit_"+id_).show();
	$("#nr_btnsave_"+id_).hide();
	
	return false;
}