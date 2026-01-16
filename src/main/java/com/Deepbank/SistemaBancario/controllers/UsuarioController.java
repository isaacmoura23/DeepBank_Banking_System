package com.Deepbank.SistemaBancario.controllers;

import com.Deepbank.SistemaBancario.Model.User;
import com.Deepbank.SistemaBancario.config.AccountConfig;
import com.Deepbank.SistemaBancario.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
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
        return userRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar usuario", description = "Cadastra um novo usuario que não tenha cadastrado na base de memoria e dura somente o tempo de execução!")
    public User addUser(@RequestBody User userRequest) {
        var user = userRepository.save(userRequest);
        return user;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> buscarUser(@PathVariable Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userRequest) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setName(userRequest.getName());
        user.setCPF(userRequest.getCPF());
        user.setEmail(userRequest.getEmail());
        user.setSenha(userRequest.getSenha());
        user.setBalance(userRequest.getBalance());

        userRepository.save(user);

        return ResponseEntity.ok(user);
    }
}
