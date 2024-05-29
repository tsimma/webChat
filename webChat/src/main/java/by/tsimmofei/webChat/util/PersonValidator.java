package by.tsimmofei.webChat.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import by.tsimmofei.webChat.models.Person;
import by.tsimmofei.webChat.services.PeopleService;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        try {
        peopleService.loadUserByUsername(person.getUsername());
        }catch(UsernameNotFoundException e){
        	errors.rejectValue("username", "", "Пользователь с таким именем уже существует.");
        }
    }
}
