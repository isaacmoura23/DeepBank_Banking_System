package com.Deepbank.SistemaBancario.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@AllArgsConstructor
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Long id;

    private int agencia;
    private String  name;
    private String CPF;
    private String email;
    private String senha;

    private BigDecimal balance = BigDecimal.ZERO;

    public User() {
    }

    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser positivo.");
        }
        this.balance = this.balance.add(amount);
    }


    public abstract void withdraw(BigDecimal amount);

    public void transfer(BigDecimal amount, User destination) {
        this.withdraw(amount);
        destination.deposit(amount);
    }

    public String apresentar() {
        return "Nome: " + name + " | Email:" + email;
    }
}
