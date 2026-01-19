package com.Deepbank.SistemaBancario.controllers;

import com.Deepbank.SistemaBancario.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Gerenciador de Token")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/gerarToken")
    @Operation(summary = "gerarToken", description = "Rota responsavel por gerar o Token!")
    public ResponseEntity<?> gerarToken(@RequestParam String email, @RequestParam String senha) {
        try {
            var lRetorno = tokenService.gerarToken(email);
            return ResponseEntity.ok(lRetorno);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

}
