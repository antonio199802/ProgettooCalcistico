package ProgettoCalcistico.menu;

import ProgettoCalcistico.oggetti.Calciatore;
import ProgettoCalcistico.oggetti.Squadra;
import ProgettoCalcistico.oggetti.Lega;
import ProgettoCalcistico.validatori.ValidatorLeghe;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe MenuLeghe per la gestione interattiva delle leghe,
 * con operazioni di visualizzazione, creazione, modifica, eliminazione e assegnazione squadre.
 */
public class MenuLeghe {

    // Lista locale delle leghe gestite da questo menu
    private final List<Lega> leghe = new ArrayList<>();

    // Liste condivise di squadre e calciatori, passate dal menu principale
    private final List<Squadra> squadre;
    private final List<Calciatore> calciatori;

    // Scanner per input da tastiera
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Costruttore che riceve le liste condivise di squadre e calciatori
     * @param squadre lista delle squadre
     * @param calciatori lista dei calciatori
     */
    public MenuLeghe(List<Squadra> squadre, List<Calciatore> calciatori) {
        this.squadre = squadre;
        this.calciatori = calciatori;
    }

    /**
     * Metodo pubblico per avviare il menu leghe.
     * Invoca il menu interno in loop.
     */
    public void avvia() {
        menuLeghe();
    }

    /**
     * Menu principale delle leghe, con loop e gestione scelta.
     */
    private void menuLeghe() {
        while (true) {
            // Stampa opzioni menu
            System.out.println("\n=== MENU LEGA ===");
            System.out.println("1) Visualizza elenco leghe");
            System.out.println("2) Crea lega");
            System.out.println("3) Modifica lega");
            System.out.println("4) Elimina lega");
            System.out.println("5) Torna indietro");
            System.out.print("Scegli un'opzione: ");

            try {
                int scelta = scanner.nextInt();
                scanner.nextLine(); // Consuma newline residuo

                switch (scelta) {
                    case 1 -> visualizzaElencoLeghe(); // Visualizza tutte le leghe
                    case 2 -> creaLega();               // Crea una nuova lega
                    case 3 -> modificaNomeLega();       // Modifica nome di una lega esistente
                    case 4 -> eliminaLega();            // Elimina una lega esistente
                    case 5 -> { return; }               // Esce dal menu leghe (torna indietro)
                    default -> System.out.println("❌ Scelta non valida ❌"); // Input non valido
                }
            } catch (InputMismatchException e) {
                // Gestione input errato (non numero intero)
                System.out.println("❌ Input non valido. Inserisci un numero intero. ❌");
                scanner.nextLine(); // Pulisce input errato
            }
        }
    }

    /**
     * Visualizza l'elenco di tutte le leghe presenti.
     * Se non ci sono leghe, comunica all'utente.
     */
    private void visualizzaElencoLeghe() {
        if (leghe.isEmpty()) {
            System.out.println("Nessuna lega creata.");
            return;
        }

        System.out.println("=== Elenco Leghe ===");
        for (int i = 0; i < leghe.size(); i++) {
            System.out.println(i + ") " + leghe.get(i));
        }
    }

    /**
     * Crea una nuova lega usando il ValidatorLeghe,
     * aggiungendola alla lista locale delle leghe.
     */
    private void creaLega() {
        Lega nuovaLega = ValidatorLeghe.creaLega(scanner, leghe);
        leghe.add(nuovaLega);
        System.out.println("✅ Lega \"" + nuovaLega.getNome() + "\" creata con successo ✅");
    }

    /**
     * Modifica il nome di una lega selezionata dall'utente.
     * Mostra anche il numero di squadre e calciatori in quella lega.
     */
    private void modificaNomeLega() {
        if (leghe.isEmpty()) {
            System.out.println("❌ Nessuna lega da modificare ❌");
            return;
        }

        visualizzaElencoLeghe();
        System.out.print("Seleziona lega da modificare (indice): ");
        int idx = leggiIndice(leghe.size());
        if (idx == -1) return;

        Lega legaSelezionata = leghe.get(idx);

        // Usa Validator per modificare il nome lega in modo sicuro
        ValidatorLeghe.modificaNomeLega(legaSelezionata, leghe, scanner);

        // Conta squadre e calciatori assegnati a questa lega
        int numeroSquadre = 0;
        int totaleCalciatori = 0;
        for (Squadra s : squadre) {
            if (s.getLega() != null && s.getLega().equals(legaSelezionata)) {
                numeroSquadre++;
                totaleCalciatori += s.getRosa().size() + s.getPanchina().size();
            }
        }

        // Stampa informazioni di riepilogo
        System.out.println("ℹ️ Squadre nella lega: " + numeroSquadre);
        System.out.println("ℹ️ Calciatori totali nelle squadre della lega: " + totaleCalciatori);
    }

    /**
     * Elimina una lega selezionata dall'utente.
     */
    private void eliminaLega() {
        if (leghe.isEmpty()) {
            System.out.println("❌ Nessuna lega da eliminare ❌");
            return;
        }

        visualizzaElencoLeghe();
        System.out.print("Seleziona lega da eliminare (indice): ");
        int idx = leggiIndice(leghe.size());
        if (idx == -1) return;

        Lega rimossa = leghe.remove(idx);
        System.out.println("✅ Lega \"" + rimossa.getNome() + "\" eliminata ✅");
    }

    /**
     * Metodo di utilità per leggere un indice da tastiera,
     * assicurandosi che sia un intero valido e dentro il range.
     * @param max il numero massimo esclusivo dell'indice valido
     * @return indice letto oppure -1 se non valido
     */
    private int leggiIndice(int max) {
        try {
            int indice = scanner.nextInt();
            scanner.nextLine(); // Consuma newline residuo
            if (indice < 0 || indice >= max) {
                System.out.println("❌ Indice fuori dal range valido ❌");
                return -1;
            }
            return indice;
        } catch (InputMismatchException e) {
            System.out.println("❌ Inserisci un numero valido ❌");
            scanner.nextLine(); // Pulisce input errato
            return -1;
        }
    }
}