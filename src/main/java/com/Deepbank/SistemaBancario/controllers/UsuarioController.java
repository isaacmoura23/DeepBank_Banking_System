package com.Deepbank.SistemaBancario.controllers;

import com.Deepbank.SistemaBancario.Model.User;
import com.Deepbank.SistemaBancario.config.AccountConfig;
import com.Deepbank.SistemaBancario.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "usuarios", description = "Gerenciador de usuarios")
public class UsuarioController {

    @Autowired
    private UserRepository userRepository;


   @GetMapping
    public Collection<User> getUsers() {

       var users = userRepository.findAll();

       for (var user : users) {
           System.out.println(user.apresentar());
       }

       return users;
   }

}
