package xefi.com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public enum Mouvement {
    CREDIT,
    DEBIT
}


class Operation {
    private double montant;
    private Mouvement type;


    public Operation() {}


    public Operation(double montant, Mouvement type) {
        this.montant = montant;
        this.type = type;
    }


    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Mouvement getType() {
        return type;
    }

    public void setType(Mouvement type) {
        this.type = type;
    }
}


abstract class Compte implements Comparable<Compte> {
    protected List<Operation> operations;
    protected String proprietaire;


    public Compte() {
        this.operations = new ArrayList<>();
    }

    public Compte(String proprietaire) {
        this.proprietaire = proprietaire;
        this.operations = new ArrayList<>();
    }


    public String getProprietaire() {
        return proprietaire;
    }

    public void crediter(double montant) {
        operations.add(new Operation(montant, Mouvement.CREDIT));
    }

    public void debiter(double montant) {
        operations.add(new Operation(montant, Mouvement.DEBIT));
    }

    public void crediter(double montant, Compte compteADebiter) {
        this.crediter(montant);
        compteADebiter.debiter(montant);
    }

    public void debiter(double montant, Compte compteACrediter) {
        this.debiter(montant);
        compteACrediter.crediter(montant);
    }

    public double calculSolde() {
        double solde = 0;
        for (Operation op : operations) {
            if (op.getType() == Mouvement.CREDIT) {
                solde += op.getMontant();
            } else {
                solde -= op.getMontant();
            }
        }
        return solde;
    }

    public abstract double calculBenefice();

    public double soldeFinal() {
        return calculSolde() + calculBenefice();
    }

    public void information() {
        System.out.println("*******************************************");
        System.out.println("Propriétaire : " + proprietaire);
        System.out.printf("Solde : %.2f%n", soldeFinal());
        System.out.println("Opérations :");
        for (Operation op : operations) {
            String signe = op.getType() == Mouvement.CREDIT ? "+" : "-";
            System.out.printf("%s%.2f%n", signe, op.getMontant());
        }
        System.out.println("*******************************************");
    }

    @Override
    public int compareTo(Compte autre) {
        return Double.compare(this.soldeFinal(), autre.soldeFinal());
    }
}

class CompteCourant extends Compte {
    private double decouvertAutorise;

    public CompteCourant() {}

    public CompteCourant(String proprietaire, double decouvertAutorise) {
        super(proprietaire);
        this.decouvertAutorise = decouvertAutorise;
    }

    @Override
    public double calculBenefice() {
        return 0;
    }

    @Override
    public void information() {
        System.out.println("*******************************************");
        System.out.println("Propriétaire : " + proprietaire);
        System.out.printf("Solde : %.2f%n", soldeFinal());
        System.out.printf("Découvert autorisé : %.2f%n", decouvertAutorise);
        System.out.println("Opérations :");
        for (Operation op : operations) {
            String signe = op.getType() == Mouvement.CREDIT ? "+" : "-";
            System.out.printf("%s%.2f%n", signe, op.getMontant());
        }
        System.out.println("*******************************************");
    }
}

class CompteEpargne extends Compte {
    private double tauxAbondement;

    public CompteEpargne() {}

    public CompteEpargne(String proprietaire, double tauxAbondement) {
        super(proprietaire);
        this.tauxAbondement = tauxAbondement;
    }

    @Override
    public double calculBenefice() {
        return calculSolde() * tauxAbondement;
    }

    @Override
    public void information() {
        System.out.println("*******************************************");
        System.out.println("Propriétaire : " + proprietaire);
        System.out.printf("Solde : %.2f%n", soldeFinal());
        System.out.printf("Taux d'abondement : %.2f %%%n", tauxAbondement * 100);
        System.out.println("Opérations :");
        for (Operation op : operations) {
            String signe = op.getType() == Mouvement.CREDIT ? "+" : "-";
            System.out.printf("%s%.2f%n", signe, op.getMontant());
        }
        System.out.println("*******************************************");
    }
}

class GestionDeComptes {
    private List<Compte> comptes;

    public GestionDeComptes() {
        comptes = new ArrayList<>();
    }

    public void ajouterCompte(Compte compte) {
        comptes.add(compte);
    }

    public void afficherComptes() {
        for (Compte compte : comptes) {
            compte.information();
        }
    }

    public void trierComptes() {
        Collections.sort(comptes);
    }

    public Compte trouverCompte(String proprietaire) {
        for (Compte compte : comptes) {
            if (compte.getProprietaire().equalsIgnoreCase(proprietaire)) {
                return compte;
            }
        }
        return null;
    }
}

