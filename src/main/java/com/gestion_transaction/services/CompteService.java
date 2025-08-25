package com.gestion_transaction.services;

import com.gestion_transaction.entity.Compte;

public interface CompteService extends Service<Compte> {

    public Compte findById(int id);
    public boolean numeroExiste(String numero);
    
}
