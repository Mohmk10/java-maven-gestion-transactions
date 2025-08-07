package com.gestion_transaction.views;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.gestion_transaction.entity.Compte;
import com.gestion_transaction.entity.CompteCheque;
import com.gestion_transaction.entity.CompteEpargne;
import com.gestion_transaction.services.CompteService;

public class CompteView {

    private Scanner sc = new Scanner(System.in);

    public int saisie(String message) {
        while (true) {
            try {
                System.out.print("\n" + message + ": ");
                int valeur = sc.nextInt();
                sc.nextLine();
                return valeur;
            } catch (java.util.InputMismatchException e) {
                System.out.println("\nEntrée invalide. Veuillez saisir un nombre entier.\n");
                sc.next();
            }
        }
    }


    public Compte saisieCompte(CompteService compteService) {

        System.out.print("Entrez le Numéro: ");
        String numero = sc.nextLine();

        if (compteService.numeroExiste(numero)) {
            return null;
        }

        System.out.println("1 - Ajout de compte de Chèque");
        System.out.println("2 - Ajout de compte d'Épargne");

        int type;

        do {
            type = saisie("Choisissez");

            if (type != 1 && type != 2) {
                System.out.println("\n Veuillez choisir entre 1 & 2");
            }
            
        } while (type != 1 && type != 2);

        Compte compte;

        if (type == 1) {
            compte = new CompteCheque(0, numero, BigDecimal.ZERO, LocalDate.now());
        } else {
            int dureeBlocage = saisie("Entrez la durée de blocage (en mois)");
            compte = new CompteEpargne(0, numero, BigDecimal.ZERO, LocalDate.now(), LocalDate.now(), dureeBlocage);
        }

        return compte;        
    }


    public void afficherCompteParId(Compte compte) {
        if (compte == null) {
            System.out.println("\nAucun compte trouvé avec cet ID\n");
        } else {
            System.out.println(compte.toString());
        }
    }


    public void afficherComptes(List<Compte> comptes) {
        if (comptes == null || comptes.isEmpty()) {
            System.out.println("\nLe tableau de Comptes est vide\n");
        } else {
            for (int i = 0; i < comptes.size(); i++) {
                System.out.println(comptes.get(i).toString());
            }
        }
    }

}
