package com.sgd.artlink;

import com.sgd.artlink.model.Role;
import com.sgd.artlink.model.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class ArtlinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtlinkApplication.class, args);
    }

    private final SessionFactory sessionFactory;

    @PostConstruct
    public void init(){
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.beginTransaction();

        Role role1 = new Role();
        role1.setName(Role.Name.ADMIN);

        Role role2 = new Role();
        role2.setName(Role.Name.USER);

        session1.save(role1);
        session1.save(role2);

        transaction1.commit();
        session1.close();

        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.beginTransaction();

        User user1 = new User();
        user1.setUsername("Admin");
        user1.setEmail("admin@mail.com");
        user1.setPassword("12345");
        user1.setRole(role1);

        User user2 = new User();
        user2.setUsername("User");
        user2.setEmail("user@mail.ru");
        user2.setPassword("12345");
        user2.setRole(role2);

        session2.save(user1);
        session2.save(user2);

        transaction2.commit();
        session2.close();
    }

}
