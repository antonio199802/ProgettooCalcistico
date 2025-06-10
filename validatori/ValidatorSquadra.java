package ProgettoCalcistico.validatori;

import ProgettoCalcistico.oggetti.Calciatore;
import ProgettoCalcistico.oggetti.Squadra;
import java.util.Scanner;

public class ValidatorSquadra {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Metodo per modificare il nome della squadra.
     * Viene richiesto un nuovo nome, se fornito, viene validato e aggiornato.
     */
    public static void modificaSquadra(Squadra squadra) {
        System.out.println("🔡Modifica nome squadra attuale: " + squadra.getNome());
        System.out.print("Nuovo nome (lascia vuoto per non modificare):\n");
        String nuovoNome = scanner.nextLine().trim();

        if (!nuovoNome.isEmpty()) {
            // Validazione lunghezza e formato (solo alfanumerici e spazi)
            //TODO crea il metodo a parte
            if (nuovoNome.length() < 3 || nuovoNome.length() > 20 || !nuovoNome.matches("[a-zA-Z0-9 ]+")) {
                throw new IllegalArgumentException("❌Il nome deve contenere tra 3 e 20 caratteri alfanumerici e spazi❌");
            }
            squadra.setNome(nuovoNome);
        }
    }

    /**
     * Metodo per assegnare un calciatore a una squadra.
     * Controlla validità del calciatore, presenza nella squadra, numero maglia e posizione (rosa/panchina).
     */
    public static void assegnaCalciatore(Squadra squadra, Calciatore calciatore) {
        // Verifica dati obbligatori
        if (calciatore.getNome() == null || calciatore.getCognome() == null || calciatore.getRuolo() == null) {
            throw new IllegalArgumentException("❌Il calciatore non è valido❌");
        }

        // Verifica presenza nella squadra (evita duplicati)
        if (squadra.getRosa().contains(calciatore) || squadra.getPanchina().contains(calciatore)) {
            throw new IllegalArgumentException("👀❌Calciatore già presente in questa squadra❌👀");
        }

        // Richiesta numero maglia
        System.out.print("👕Inserisci numero maglia (1-99)🤾‍♂️:\n");
        int numeroMaglia;
        try {
            numeroMaglia = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("❌Numero di maglia non valido❌");
        }

        // Controllo range numero maglia
        if (numeroMaglia < 1 || numeroMaglia > 99) {
            throw new IllegalArgumentException("🫡Il numero maglia deve essere compreso tra 1 e 99🫡");
        }

        // Verifica se il numero è già usato nella squadra
        if (numeroMagliaGiaUsato(squadra, numeroMaglia)) {
            throw new IllegalArgumentException("👀❌Numero maglia già utilizzato nella squadra❌👀");
        }

        // Assegnazione numero maglia
        calciatore.setNumeroMaglia(numeroMaglia);

        // Scelta posizione (rosa o panchina)
        System.out.print("🌹🤾‍♂️Vuoi assegnarlo alla rosa (titolari) o panchina 🐖? (r/p): \n ");
        String scelta = scanner.nextLine().trim().toLowerCase();

        // Smistamento alla funzione corretta
        switch (scelta) {
            case "r" -> assegnaARosa(squadra, calciatore);
            case "p" -> assegnaAPanchina(squadra, calciatore);
            default -> throw new IllegalArgumentException("❌Scelta non valida❌");
        }
    }

    /**
     * Metodo per assegnare un calciatore alla rosa.
     * Verifica capienza massima e limiti per ruolo specifico.
     */
    private static void assegnaARosa(Squadra squadra, Calciatore calciatore) {
        if (squadra.getRosa().size() >= 11) {
            throw new IllegalArgumentException("😵‍💫❌La rosa ha già 11 titolari❌😵‍💫");
        }

        // Conta quanti calciatori ci sono già per quel ruolo
        long countRuolo = squadra.getRosa().stream()
                .filter(c -> c.getRuolo().equalsIgnoreCase(calciatore.getRuolo()))
                .count();

        switch (calciatore.getRuolo().toLowerCase()) {
            case "portiere" -> {
                if (countRuolo >= 1)
                    throw new IllegalArgumentException("❌La rosa ha già un portiere❌🤷‍♂️");
            }
            case "difensore", "centrocampista" -> {
                if (countRuolo >= 4)
                    throw new IllegalArgumentException("❌La rosa ha già 4 ❌" + calciatore.getRuolo() + "i.");
            }
            case "attaccante" -> {
                if (countRuolo >= 2)
                    throw new IllegalArgumentException("❌La rosa ha già 2 attaccanti❌🤷‍♂️");
            }
            default -> throw new IllegalArgumentException("❌Ruolo non valido❌😵‍💫");
        }

        squadra.getRosa().add(calciatore);
    }

    /**
     * Metodo per assegnare un calciatore alla panchina.
     * Massimo 22 calciatori ammessi.
     */
    private static void assegnaAPanchina(Squadra squadra, Calciatore calciatore) {
        if (squadra.getPanchina().size() >= 22) {
            throw new IllegalArgumentException("La panchina ha già 22 calciatori🤷‍♂️");
        }
        squadra.getPanchina().add(calciatore);
    }

    /**
     * Verifica se il numero maglia è già utilizzato nella squadra.
     */
    private static boolean numeroMagliaGiaUsato(Squadra squadra, int numero) {
        return squadra.getRosa().stream().anyMatch(c -> c.getNumeroMaglia() == numero)
                || squadra.getPanchina().stream().anyMatch(c -> c.getNumeroMaglia() == numero);
    }
}