package com.Deepbank.SistemaBancario.config;

import com.Deepbank.SistemaBancario.Model.User;
import com.Deepbank.SistemaBancario.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional // Define que todos os métodos aqui são transacionais
public class AccountConfig {

    private final UserRepository userRepository;

    public AccountConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(){
    }

    @Transactional()
    public Optional<User> getAccount(@NonNull Long id) {
        return userRepository.findById(id);
    }

    public void deposit(Long accountId, BigDecimal amount) {
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada."));

        user.deposit(amount);
    }

    public void withdraw(Long accountId, BigDecimal amount) {
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada."));

        user.withdraw(amount);
    }

    public void executeTransfer(@NonNull Long fromId, @NonNull Long toId, BigDecimal amount) {
        if (fromId.equals(toId)) {
            throw new RuntimeException("A conta de origem não pode ser a mesma da conta de destino.");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
        }

        User source = userRepository.findById(fromId)
                .orElseThrow(() -> new RuntimeException("Origem não encontrada"));

        User destination = userRepository.findById(toId)
                .orElseThrow(() -> new RuntimeException("Destino não encontrado"));

        source.transfer(amount, destination);
    }
}
