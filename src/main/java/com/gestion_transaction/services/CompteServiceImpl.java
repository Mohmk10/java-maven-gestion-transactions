package com.gestion_transaction.services;

import java.util.List;

import com.gestion_transaction.entity.Compte;
import com.gestion_transaction.repository.CompteRepository;
import com.gestion_transaction.repository.CompteRepositoryImpl;

public class CompteServiceImpl implements CompteService{

    private final CompteRepository compteRepository = new CompteRepositoryImpl();

    @Override
    public Compte addCompte(Compte compte) {
        return compteRepository.save(compte);
    }

    @Override
    public boolean numeroExiste(String numero) {
        return compteRepository.existsByNumero(numero);
    }

    @Override
    public Compte getCompteById(int id) {
        return compteRepository.findById(id);
    }

    @Override
    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }
}
