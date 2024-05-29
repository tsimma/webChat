package by.tsimmofei.webChat.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.tsimmofei.webChat.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer>{
	Optional<Person> findByUsername(String username);
}
