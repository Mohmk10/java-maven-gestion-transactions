package com.gestion_transaction.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CompteCheque extends Compte{
    private static final BigDecimal FRAIS = BigDecimal.valueOf(0.08);

    public CompteCheque(int id, String numero, BigDecimal solde, LocalDate dateOuverture) {
        super(id, numero, solde, dateOuverture, TypeCompte.CHEQUE);
    }

    @Override
    public void depot(double montant) {
        BigDecimal fact = BigDecimal.valueOf(montant).multiply(FRAIS);
        this.solde = this.solde.add(BigDecimal.valueOf(montant).subtract(fact));
    }

    @Override
    public boolean retrait(double montant) {
        BigDecimal fact = BigDecimal.valueOf(montant).multiply(FRAIS);
        BigDecimal montantTotal = BigDecimal.valueOf(montant).add(fact);
        if (this.solde.compareTo(montantTotal) >= 0) {
            this.solde = this.solde.subtract(montantTotal);
            return true;
        }
        return false;
    }

    
}
