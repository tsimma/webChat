package by.tsimmofei.webChat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.tsimmofei.webChat.models.Person;
import by.tsimmofei.webChat.repositories.PeopleRepository;

@Service
public class RegistrationService {
	
	private final PeopleRepository peopleRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
		this.peopleRepository = peopleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	public void register(Person person) {
		person.setPassword(passwordEncoder.encode(person.getPassword()));
		
		peopleRepository.save(person);
	}
	
}
