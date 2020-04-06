package pl.makuch.libraryspringsecuirtyh2thymeleaf.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.makuch.libraryspringsecuirtyh2thymeleaf.model.Book;
import pl.makuch.libraryspringsecuirtyh2thymeleaf.model.User;
import pl.makuch.libraryspringsecuirtyh2thymeleaf.repository.BookRepo;
import pl.makuch.libraryspringsecuirtyh2thymeleaf.repository.RoleRepo;
import pl.makuch.libraryspringsecuirtyh2thymeleaf.repository.UserRepo;

import java.util.List;

@Service
public class LibraryService {

    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private BookRepo bookRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public LibraryService(UserRepo userRepo, RoleRepo roleRepo, BookRepo bookRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.bookRepo = bookRepo;
    }

    public User saveUser(User user) {
        user.getRoleList().add(roleRepo.findByRolename("USER"));
        String passBCrypt = passwordEncoder.encode(user.getPassword());
        user.setPassword(passBCrypt);
        return userRepo.save(user);
    }

    public void deleteUser(Long id) throws NotFoundException {
        List<Book> bookList = userRepo.findById(id).orElseThrow(() -> new NotFoundException("User not found id: " + id)).getBookList();
        for (Book book : bookList) {
            book.setUser(null);
            book.setAvailable(true);
            bookRepo.save(book);
        }
        userRepo.deleteById(id);
    }

    public List<User> findAllUser() {
        return userRepo.findAll();
    }

    public boolean checkUniqueUsername(String username) {
        return userRepo.findByUsername(username).isPresent();
    }

    public List<Book> findAllBooks() {
        return bookRepo.findAll();
    }

    public List<Book> findBookByUser(String username) throws NotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> new NotFoundException("User: " + username + " not found")).getBookList();
    }
    public Book saveNewBook(Book book) {
        book.setAvailable(true);
        return bookRepo.save(book);
    }

    public Book saveEditedBook(Book book) {
        if(book.getUser()==null){
            return bookRepo.save(book);
        }
        else {
            book.setUser(bookRepo.findById(book.getId()).get().getUser());
            return bookRepo.save(book);
        }
    }

    public void deleteBook(Long id) {
        bookRepo.deleteById(id);
    }

    public Book getBookById(Long id) throws NotFoundException {
        return bookRepo.findById(id).orElseThrow(() -> new NotFoundException("Book not found: " + id));
    }

    public void returnBook(Long bookid) {
        Book book = bookRepo.findById(bookid).get();
        book.setUser(null);
        book.setAvailable(true);
        bookRepo.save(book);
    }

    public void borrowBook(String username, Long bookid) throws NotFoundException {
        Book book = bookRepo.findById(bookid).orElseThrow(() -> new NotFoundException("Book not found with: " + bookid));
        book.setAvailable(false);
        book.setUser(userRepo.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found with: " + username)));
        bookRepo.save(book);
    }
}
