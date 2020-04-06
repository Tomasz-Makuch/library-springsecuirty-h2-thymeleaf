package pl.makuch.libraryspringsecuirtyh2thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.makuch.libraryspringsecuirtyh2thymeleaf.model.Book;
import pl.makuch.libraryspringsecuirtyh2thymeleaf.model.Role;
import pl.makuch.libraryspringsecuirtyh2thymeleaf.model.User;
import pl.makuch.libraryspringsecuirtyh2thymeleaf.repository.BookRepo;
import pl.makuch.libraryspringsecuirtyh2thymeleaf.repository.RoleRepo;
import pl.makuch.libraryspringsecuirtyh2thymeleaf.repository.UserRepo;
import java.util.List;

@Service
public class Library {

    private RoleRepo roleRepo;
    private UserRepo userRepo;
    private BookRepo bookRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public Library(RoleRepo roleRepo, UserRepo userRepo, PasswordEncoder passwordEncoder, BookRepo bookRepo) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public void testOneToOne() {

        Role roleUser = new Role("USER");
        Role roleAdmin = new Role("ADMIN");

        User admin = new User("admin", passwordEncoder.encode("admin"), "tomotomato@rt.de");
        User user2 = new User("Dymitr",passwordEncoder.encode("1234"), "Dymitr@xx.eu");
        User user3 = new User("Emilly",passwordEncoder.encode("5678"), "emilly@ww.uk");

        Book book1 = new Book ("Nineteen Eighty-Four,", "George Orwell", "978-83-900210-1-0 ");
        Book book2 = new Book ("Diary of a Nobody", "George and Weedon Grossmith", "788-54-450210-2-0 ");
        Book book3 = new Book ("His Dark Materials", "Philip Pullman", "345-83-903450-3-0 ");
        Book book4 = new Book ("Great Expectations", "Charles Dickens", "668-83-900210-6-0 ");

        Book book5 = new Book ("Robinson Crusoe", "Daniel Defoe", "0-234131-041-3 ");
        Book book6 = new Book ("Gulliver’s Travels", "Jonathan Swift", "60-821231-041-7 ");
        Book book7 = new Book ("Frankenstein", "Mary Shelley", "60-99551-041-1 ");
        Book book8 = new Book ("Alice’s Adventures in Wonderland", "Lewis Carrolle", "0-564131-041-3 ");
        Book book9 = new Book ("Ulysses", "James Joyce", "60-824331-051-0 ");
        Book book10 = new Book ("War and Peace", "Leo Tolstoy", "60-89051-041-1 ");
        Book book11 = new Book ("Hamlet", "William Shakespeare", "0-588131-041-3 ");
        Book book12 = new Book ("The Odyssey ", "Homer", "60-824991-051-0 ");
        Book book13 = new Book ("The Lord of the Rings", "J. R. R. Tolkien", "60-81151-041-1 ");
        Book book14 = new Book ("The Old Man and the Sea", "Ernest Hemingway", "60-81151-771-1 ");

        admin.getRoleList().add(roleAdmin);
        admin.getRoleList().add(roleUser);
        user2.getRoleList().add(roleUser);
        user3.getRoleList().add(roleUser);

        book11.setUser(user2);
        book8.setUser(user2);
        book3.setUser(user3);

        roleRepo.save(roleUser);
        roleRepo.save(roleAdmin);

        userRepo.save(admin);
        userRepo.save(user2);
        userRepo.save(user3);

        bookRepo.save(book1);
        bookRepo.save(book2);
        bookRepo.save(book3);
        bookRepo.save(book4);

        bookRepo.save(book5);
        bookRepo.save(book6);
        bookRepo.save(book7);
        bookRepo.save(book8);
        bookRepo.save(book9);
        bookRepo.save(book10);
        bookRepo.save(book11);
        bookRepo.save(book12);
        bookRepo.save(book13);
        bookRepo.save(book14);

    }

    public void printDatabase(){
        //users
        List<User> allUser = userRepo.findAll();
        System.out.println("====================== USER ===================");
        for (User user: allUser){
            System.out.println(user);
        }

//        //roles
//        System.out.println("====================== ROLE ===================");
//        List<Role> allRole = roleRepo.findAll();
//        for (Role role: allRole){
//            System.out.println(role);
//        }

    }

    @EventListener(ApplicationReadyEvent.class)
    public void runTest() {
        testOneToOne();
      //  printDatabase();
    }

}
