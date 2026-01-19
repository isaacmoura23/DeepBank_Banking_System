package com.Deepbank.SistemaBancario.repository;

import com.Deepbank.SistemaBancario.Model.Token;
import com.Deepbank.SistemaBancario.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    boolean existsByToken(String token);

}
