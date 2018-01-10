package centmp.depotmebel.adminpartner.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import centmp.depotmebel.adminpartner.service.NotifService;
import centmp.depotmebel.core.model.NotifTask;

@Controller
public class ParentController {

	@Autowired
	private NotifService notifService; 
	
	public ParentController() {
		// TODO Auto-generated constructor stub
	}
	
	public List<NotifTask> taskList(Model model){
		List<NotifTask> list = new ArrayList<>();
		try {
			list = notifService.listAll();
			
			model.addAttribute("taskNum", list.size());
			model.addAttribute("taskList", list);
			
		} catch (Exception e) {e.printStackTrace();}
		return list;
	}
}
