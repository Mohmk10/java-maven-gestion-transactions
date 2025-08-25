package com.gestion_transaction.repository;

import java.math.BigDecimal;
import java.util.List;

import com.gestion_transaction.entity.Compte;


public interface CompteRepository extends Repository<Compte>{

    public Compte save(Compte compte);
    public Compte findById(int id);
    public List<Compte> findAll();
    public boolean existsByNumero(String numero);
    public void updateSolde(int compteId, BigDecimal nouveauSolde);
    
}
