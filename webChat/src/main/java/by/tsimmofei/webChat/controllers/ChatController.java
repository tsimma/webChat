package by.tsimmofei.webChat.controllers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import  by.tsimmofei.webChat.models.ChatLog;
import by.tsimmofei.webChat.models.Message;
import by.tsimmofei.webChat.models.Person;
//import by.tsimmofei.webChat.models.Book;
//import by.tsimmofei.webChat.services.BooksService;
import by.tsimmofei.webChat.security.PersonDetails;
import by.tsimmofei.webChat.services.MessagesService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/chat")
public class ChatController {

	private final MessagesService messagesService;	

    public ChatController(MessagesService messagesService) {
		this.messagesService = messagesService;
	}

	@GetMapping()
    public String index(Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    	
    	model.addAttribute("log", messagesService.findAll());
        model.addAttribute("me", personDetails.getPerson().getUsername());
        model.addAttribute("id", personDetails.getPerson().getId());

        return "chat/index";
    }

    /*@GetMapping("/showUserInfo")
    public String showUserInfo() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    	System.out.println(personDetails.getPerson());
    	return "books/index";
    }*/
    
    @GetMapping("/content-to-refresh")
    public String getContent(Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        // Логика получения новых данных
        model.addAttribute("log", messagesService.findAll());
        model.addAttribute("id", personDetails.getPerson().getId());

        return "chat/index";
    }
    
    @PostMapping()
    public String add(Model model, @RequestParam("message") String message) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
    	
    	Date current = new Date();	    	
    	
    	if(!messagesService.findAll().isEmpty()) {
    		Message lastMessage;
    		lastMessage = messagesService.findAll().getLast();	
        	Message newMessage = new Message(0, personDetails.getPerson(), message, current,0);
    	//
    	if(newMessage.getSend_at().getDate()!=lastMessage.getSend_at().getDate()) {
    		messagesService.save(new Message(0, personDetails.getPerson(), current.toString(), current,1));
			messagesService.save(newMessage);
    	}
    	else if(newMessage.getSend_at().getYear()!=lastMessage.getSend_at().getYear()) {
    		messagesService.save(new Message(0, personDetails.getPerson(), current.toString(), current,1));
    		messagesService.save(newMessage);
    	} else {
			messagesService.save(newMessage);
    	}
    	//
    	} else {
    		messagesService.save(new Message(0, personDetails.getPerson(), current.toString(), current,1));
    		messagesService.save(new Message(0, personDetails.getPerson(), message, current,0));
        	Message newMessage = messagesService.findAll().getLast();
    	}
    	
        model.addAttribute("me", personDetails.getPerson().getUsername());
    	getContent(model);
        return "chat/index";
    }
   
    
}
