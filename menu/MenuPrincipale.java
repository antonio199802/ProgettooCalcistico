package ProgettoCalcistico.menu;

import ProgettoCalcistico.oggetti.Calciatore;
import ProgettoCalcistico.oggetti.Squadra;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe MenuPrincipale che rappresenta il menu principale dell'applicazione.
 * Permette di accedere ai menu di gestione Calciatori, Squadre e Leghe,
 * mantenendo le liste condivise di calciatori e squadre.
 */
public class MenuPrincipale {
    // Scanner per leggere input da tastiera
    private final Scanner scanner = new Scanner(System.in);

    // Liste che contengono tutti i calciatori e le squadre gestite
    private final List<Calciatore> calciatori = new ArrayList<>();
    private final List<Squadra> squadre = new ArrayList<>();

    // Menu dedicati alla gestione di calciatori, squadre e leghe,
    // inizializzati con le liste condivise per sincronizzare i dati
    private final MenuCalciatori menuCalciatori = new MenuCalciatori(calciatori, squadre);
    private final MenuSquadre menuSquadre = new MenuSquadre(squadre, calciatori);
    private final MenuLeghe menuLeghe = new MenuLeghe(squadre, calciatori);

    /**
     * Metodo principale per avviare il menu principale in loop,
     * mostrando le opzioni e gestendo la scelta dell'utente.
     */
    public void avvia() {
        while (true) {
            // Stampa il menu principale con le opzioni
            System.out.println("\n=== 📋 MENU PRINCIPALE 📋 ===");
            System.out.println("1️⃣➡️Gestione Calciatori");
            System.out.println("2️⃣➡️Gestione Squadre");
            System.out.println("3️⃣➡️Gestione Leghe");
            System.out.println("4️⃣➡️Esci");
            System.out.print("👉Scegli un'opzione:\n ");

            // Legge la scelta dell'utente
            String scelta = scanner.nextLine().trim();

            // Esegue l'azione corrispondente alla scelta
            switch (scelta) {
                case "1" -> menuCalciatori.avvia();  // Avvia il menu di gestione calciatori
                case "2" -> menuSquadre.avvia();     // Avvia il menu di gestione squadre
                case "3" -> menuLeghe.avvia();       // Avvia il menu di gestione leghe
                case "4" -> {
                    // Esce dal programma
                    System.out.println("👋Uscita dal programma. Arrivederci👋🏼");
                    return;
                }
                default -> System.out.println("❌Scelta non valida❌ Riprova 🏆↪️"); // Input non valido
            }
        }
    }
}