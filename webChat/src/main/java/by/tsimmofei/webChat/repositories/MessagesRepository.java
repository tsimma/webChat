package by.tsimmofei.webChat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.tsimmofei.webChat.models.Message;
import by.tsimmofei.webChat.models.Person;


@Repository
public interface MessagesRepository extends JpaRepository<Message, Integer> {
}
