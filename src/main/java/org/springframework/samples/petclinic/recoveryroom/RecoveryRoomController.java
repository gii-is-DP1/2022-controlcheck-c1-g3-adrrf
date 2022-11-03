package org.springframework.samples.petclinic.recoveryroom;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recoveryroom")
public class RecoveryRoomController {
    private static final String VIEW_ROOM_FORM = "recoveryroom/createOrUpdateRecoveryRoomForm";
    private static final String WELCOME = "welcome";
    
    private RecoveryRoomService rrservice;
    
    @Autowired
    public RecoveryRoomController(RecoveryRoomService service) {
    	this.rrservice = service;
    }
    
    @GetMapping("/create") 	
    public String initProduct(ModelMap map) {
    	map.addAttribute("recoveryRoom", new RecoveryRoom());
    	map.addAttribute("types", rrservice.getAllRecoveryRoomTypes());
    	return VIEW_ROOM_FORM;
    }
    
    @PostMapping(path = "/create")
    public String createProduct(@Valid RecoveryRoom rRoom, BindingResult br, ModelMap map){
        if(!br.hasErrors()){
        	try {
				rrservice.save(rRoom);
			} catch (DuplicatedRoomNameException e) {
				e.printStackTrace();
			}
            map.addAttribute("message", "Product succesfully save");
            return WELCOME;
        } else {
            map.addAttribute("recoveryRoom", rRoom);
            map.addAttribute("types", rrservice.getAllRecoveryRoomTypes());
        }
        return VIEW_ROOM_FORM;
    }
}
