package com.sgd.artlink;

import com.sgd.artlink.model.Role;
import com.sgd.artlink.model.User;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@AllArgsConstructor
public class ArtlinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtlinkApplication.class, args);
    }

    @Autowired
    private SessionFactory sessionFactory;

    @PostConstruct
    public void init(){
        Session session = sessionFactory.openSession();

       Transaction transaction = session.beginTransaction();

        Role role1 = new Role();
        role1.setName(Role.Name.ADMIN);

        Role role2 = new Role();
        role2.setName(Role.Name.USER);

        session.save(role1);
        session.save(role2);

        User user1 = new User();
        user1.setUsername("User");
        user1.setEmail("user@mail.ru");
        user1.setPassword("user123");
        User user2 = new User();
        user2.setUsername("Admin");
        user2.setEmail("admin@mail.com");
        user2.setPassword("admin123");

        session.save(user1);
        session.save(user2);

        transaction.commit();
        session.close();

    }

}
