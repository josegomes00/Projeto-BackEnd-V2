package com.backEnd.tecback.controller;

import com.backEnd.tecback.models.User;
import com.backEnd.tecback.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class apiControllers {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/")
    public String getPage(){

        return "<h1>API BackEnd Vendas - Projeto Java: José Gomes & Alexander Dore</h1>";

    }

    @GetMapping(value = "/usuarios")
    public List<User> obterUsuarios(){
        return userRepository.findAll();
    }

}
