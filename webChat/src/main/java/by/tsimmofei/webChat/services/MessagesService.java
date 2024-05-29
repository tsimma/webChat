package by.tsimmofei.webChat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.tsimmofei.webChat.models.Message;
import by.tsimmofei.webChat.models.Person;
import by.tsimmofei.webChat.repositories.MessagesRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MessagesService {

    private final MessagesRepository messagesRepository;

    @Autowired
    public MessagesService(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    public List<Message> findAll(){
            return messagesRepository.findAll(/*Sort.by("send_at")*/);
    }


    @Transactional
    public void save(Message message) {
        messagesRepository.save(message);
    }

    /*@Transactional
    public void update(int id, Book updatedBook) {
        Book bookToBeUpdated = messagesRepository.findById(id).get();

        // добавляем по сути новую книгу (которая не находится в Persistence context), поэтому нужен save()
        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner()); // чтобы не терялась связь при обновлении

        messagesRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        messagesRepository.deleteById(id);
    }*/
    

}
