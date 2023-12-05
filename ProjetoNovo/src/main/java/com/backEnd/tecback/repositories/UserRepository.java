package com.backEnd.tecback.repositories;

import  com.backEnd.tecback.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User,Long> {

    UserDetails findByLogin(String login);

}
