package com.Deepbank.SistemaBancario.controllers.dto;

import java.math.BigDecimal;

public record UserDTO (
        Long id,
        String agencia,
        String name,
        String cpf,
        String email,
        String senha,
        BigDecimal balance,
        String typeAccount
)
{}
