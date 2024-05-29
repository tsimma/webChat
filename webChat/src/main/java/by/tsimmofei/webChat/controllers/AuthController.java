package by.tsimmofei.webChat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import by.tsimmofei.webChat.models.Person;
import by.tsimmofei.webChat.services.MessagesService;
import by.tsimmofei.webChat.services.PeopleService;
import by.tsimmofei.webChat.services.RegistrationService;
import by.tsimmofei.webChat.util.PersonValidator;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	private final PersonValidator personValidator;
	private final RegistrationService registrationService;
	private final PeopleService peopleService;
	private final MessagesService messageService;

	
	@Autowired
	public AuthController(PersonValidator personValidator, RegistrationService registrationService, PeopleService peopleService, MessagesService messageService) {
		this.personValidator = personValidator;
		this.registrationService = registrationService;
		this.peopleService = peopleService;
		this.messageService = messageService;	
	}

	@GetMapping("/login")
	public String loginPage() {	
		return "auth/login";
	}
	
	@GetMapping("/registration")
	public String registrationPage(@ModelAttribute("person") Person person) {	
		return "auth/registration";
	}
	
	@PostMapping("/registration")
	public String performRegistration(Model model, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {	
		
		personValidator.validate(person, bindingResult);
		
		if(bindingResult.hasErrors())
			return "/auth/registration";
		
		registrationService.register(person);
		model.addAttribute("IsResultSuccess", true);
		
		return "/auth/registration";
	}
	
	//
	   @GetMapping("/{id}/me")
	    public String edit(Model model, @PathVariable("id") int id) {
	        model.addAttribute("person", peopleService.findOne(id));
	        return "chat/me";
	    }

	    @PatchMapping("/{id}")
	    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
	                         @PathVariable("id") int id) {
	        if (bindingResult.hasErrors())
	            return "people/edit";

	        peopleService.update(id, person);
	        return "chat/me";
	    }

	    @DeleteMapping("/{id}")
	    public String delete(@PathVariable("id") int id) {
	        peopleService.delete(id);
	        return "auth/login";
	    }
}
