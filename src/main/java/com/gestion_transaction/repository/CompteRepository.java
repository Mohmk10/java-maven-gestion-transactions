package com.gestion_transaction.repository;

import java.math.BigDecimal;
import java.util.List;

import com.gestion_transaction.entity.Compte;

public interface CompteRepository {

    Compte save(Compte compte);
    boolean existsByNumero(String numero);
    public void updateSolde(int compteId, BigDecimal nouveauSolde);
    Compte findById(int id);
    List<Compte> findAll();
}
