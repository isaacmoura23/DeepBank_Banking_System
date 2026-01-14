package com.Deepbank.SistemaBancario.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@DiscriminatorValue("CORRENTE")
@AllArgsConstructor
public class CurrentAccount extends User{

    private static final BigDecimal WITHDRAWAL_FEE = new BigDecimal("1.00");
    private BigDecimal overdraftLimit = BigDecimal.ZERO;

    public CurrentAccount() {
        super();
    }

    public CurrentAccount(Long id, int agencia, String name, String CPF, String email, String senha, BigDecimal balance, BigDecimal overdraftLimit) {
        super(id, agencia, name, CPF, email, senha, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de saque inválido.");
        }

        BigDecimal totalDebit = amount.add(WITHDRAWAL_FEE);
        BigDecimal availableSpace = this.getBalance().add(this.overdraftLimit);

        if (availableSpace.compareTo(totalDebit) < 0) {
            throw new RuntimeException("Saldo insuficiente (incluindo limite e taxa).");
        }

        this.setBalance(this.getBalance().subtract(totalDebit));
    }

    public String apresentar() {
        return super.apresentar() + "| Conta: Corrente | Agencia: " + getAgencia();
    }
}
