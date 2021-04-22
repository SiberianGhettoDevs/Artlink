package com.sgd.artlink;

import com.sgd.artlink.model.Role;
import com.sgd.artlink.model.User;
import com.sgd.artlink.repository.RoleRepository;
import com.sgd.artlink.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ArtlinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtlinkApplication.class, args);
    }


}
