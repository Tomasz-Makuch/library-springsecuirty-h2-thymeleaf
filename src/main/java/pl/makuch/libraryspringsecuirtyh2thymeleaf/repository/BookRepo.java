package pl.makuch.libraryspringsecuirtyh2thymeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.makuch.libraryspringsecuirtyh2thymeleaf.model.Book;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
     List<Book> findAllByAvailable(Boolean available);
}
