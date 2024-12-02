package xefi.com;

import java.util.Scanner;


public class TPGestionCompte {
    private static Scanner scanner = new Scanner(System.in);
    private static GestionDeComptes gestionComptes = new GestionDeComptes();

    public static void main(String[] args) {
        int choix;
        do {
            afficherMenu();
            choix = scanner.nextInt();
            scanner.nextLine();

            switch(choix) {
                case 1:
                    creerCompteCourant();
                    break;
                case 2:
                    creerCompteEpargne();
                    break;
                case 3:
                    crediterCompte();
                    break;
                case 4:
                    debiterCompte();
                    break;
                case 5:
                    effectuerVirement();
                    break;
                case 6:
                    gestionComptes.afficherComptes();
                    break;
                case 7:
                    gestionComptes.trierComptes();
                    gestionComptes.afficherComptes();
                    break;
                case 8:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide. Réessayez.");
            }
        } while (choix != 8);
    }

    private static void afficherMenu() {
        System.out.println("\n--- Menu Gestion de Comptes ---");
        System.out.println("1. Créer un compte courant");
        System.out.println("2. Créer un compte épargne");
        System.out.println("3. Créditer un compte");
        System.out.println("4. Débiter un compte");
        System.out.println("5. Effectuer un virement");
        System.out.println("6. Afficher la liste des comptes");
        System.out.println("7. Trier les comptes");
        System.out.println("8. Quitter");
        System.out.print("Votre choix : ");
    }

    private static void creerCompteCourant() {
        System.out.print("Nom du propriétaire : ");
        String nom = scanner.nextLine();
        System.out.print("Découvert autorisé : ");
        double decouvert = scanner.nextDouble();
        scanner.nextLine();
        CompteCourant compte = new CompteCourant(nom, decouvert);
        gestionComptes.ajouterCompte(compte);
        System.out.println("Compte courant créé avec succès.");
    }

    private static void creerCompteEpargne() {
        System.out.print("Nom du propriétaire : ");
        String nom = scanner.nextLine();
        System.out.print("Taux d'abondement (en %) : ");
        double taux = scanner.nextDouble();
        scanner.nextLine();
        CompteEpargne compte = new CompteEpargne(nom, taux / 100);
        gestionComptes.ajouterCompte(compte);
        System.out.println("Compte épargne créé avec succès.");
    }

    private static void crediterCompte() {
        System.out.print("Nom du propriétaire du compte à créditer : ");
        String nom = scanner.nextLine();
        Compte compte = gestionComptes.trouverCompte(nom);

        if (compte != null) {
            System.out.print("Montant à créditer : ");
            double montant = scanner.nextDouble();
            scanner.nextLine();
            compte.crediter(montant);
            System.out.println("Compte crédité avec succès.");
        } else {
            System.out.println("Compte non trouvé.");
        }
    }

    private static void debiterCompte() {
        System.out.print("Nom du propriétaire du compte à débiter : ");
        String nom = scanner.nextLine();
        Compte compte = gestionComptes.trouverCompte(nom);

        if (compte != null) {
            System.out.print("Montant à débiter : ");
            double montant = scanner.nextDouble();
            scanner.nextLine();
            compte.debiter(montant);
            System.out.println("Compte débité avec succès.");
        } else {
            System.out.println("Compte non trouvé.");
        }
    }

    private static void effectuerVirement() {
        System.out.print("Nom du propriétaire du compte source : ");
        String source = scanner.nextLine();
        Compte compteSource = gestionComptes.trouverCompte(source);

        System.out.print("Nom du propriétaire du compte destination : ");
        String destination = scanner.nextLine();
        Compte compteDestination = gestionComptes.trouverCompte(destination);

        if (compteSource != null && compteDestination != null) {
            System.out.print("Montant du virement : ");
            double montant = scanner.nextDouble();
            scanner.nextLine();

            compteSource.debiter(montant, compteDestination);
            System.out.println("Virement effectué avec succès.");
        } else {
            System.out.println("Un des comptes n'a pas été trouvé.");
        }
    }
}
