package com.gestion_transaction.services;

import java.util.List;

import com.gestion_transaction.entity.Compte;

public interface CompteService {

    Compte addCompte(Compte compte);
    boolean numeroExiste(String numero);
    Compte getCompteById(int id);
    List<Compte> getAllComptes();
}
