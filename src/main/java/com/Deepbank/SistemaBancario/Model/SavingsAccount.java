package com.Deepbank.SistemaBancario.Model;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@DiscriminatorValue("POUPANCA")
@AllArgsConstructor
public class SavingsAccount extends User {

    private BigDecimal interestRate = new BigDecimal("0.005"); // 0.5%

    public SavingsAccount() {
        super();
    }

    public SavingsAccount(Long id, int agencia, String name, String CPF, String email, String senha, BigDecimal balance, BigDecimal interestRate) {
        super(id, agencia, name, CPF, email, senha, balance);
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        BigDecimal interest = this.getBalance().multiply(interestRate);
        this.deposit(interest);
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de saque inválido.");
        }

        if (this.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Saldo insuficiente.");
        }

        this.setBalance(this.getBalance().subtract(amount));
    }


    public String apresentar() {
        return super.apresentar() + "| Conta: Poupanca | Agencia: " + getAgencia();
    }
}
