package com.gestion_transaction.entity;

import java.time.LocalDate;
import java.math.BigDecimal;

public class CompteEpargne extends Compte{
    private LocalDate dateDebut;
    private int dureeBlocage;

    public CompteEpargne(int id, String numero, BigDecimal solde, LocalDate dateOuverture, LocalDate dateDebut, int dureeBlocage) {
        super(id, numero, solde, dateOuverture, TypeCompte.EPARGNE);
        this.dateDebut = dateDebut;
        this.dureeBlocage = dureeBlocage;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public int getDureeBlocage() {
        return dureeBlocage;
    }

    @Override
    public void depot(double montant) {
        this.solde = this.solde.add(BigDecimal.valueOf(montant));
    }

    @Override
    public boolean retrait(double montant) {
        LocalDate dateFinBlocage = dateDebut.plusMonths(dureeBlocage);
        if (LocalDate.now().isAfter(dateFinBlocage) && this.solde.doubleValue() >= montant) {
            this.solde = this.solde.subtract(BigDecimal.valueOf(montant));
            return true;
        }
        return false;
    }

}
