package com.gestion_transaction.services.Impl;

import java.util.List;

import com.gestion_transaction.entity.Compte;
import com.gestion_transaction.repository.CompteRepository;
import com.gestion_transaction.services.CompteService;

public class CompteServiceImpl implements CompteService{

    private final CompteRepository compteRepository;

    public CompteServiceImpl(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Override
    public Compte add(Compte compte) {
        return compteRepository.save(compte);
    }

    @Override
    public Compte findById(int id) {
        return compteRepository.findById(id);
    }

    @Override
    public List<Compte> findAll() {
        return compteRepository.findAll();
    }

    @Override
    public boolean numeroExiste(String numero) {
        return compteRepository.existsByNumero(numero);
    }

}
