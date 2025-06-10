package ProgettoCalcistico.validatori;

import ProgettoCalcistico.oggetti.Lega;
import java.util.List;
import java.util.Scanner;

public class ValidatorLeghe {

    /**
     * Metodo per creare una nuova lega con validazione del nome.
     * - Lunghezza: 3-20 caratteri
     * - Contenuti: solo lettere, numeri e spazi
     * - Unicità: non deve esistere già una lega con lo stesso nome (case-insensitive)
     */
    public static Lega creaLega(Scanner scanner, List<Lega> leghe) {
        String nome;
        while (true) {
            System.out.print("✅Inserisci nome della lega (3-20 caratteri, solo lettere, numeri e spazi)👀: \n ");
            nome = scanner.nextLine().trim();

            // Validazione lunghezza
            if (nome.length() < 3 || nome.length() > 20) {
                System.out.println("❌Nome deve avere tra 3 e 20 caratteri❌");
                continue;
            }

            // Validazione caratteri ammessi
            if (!nome.matches("[a-zA-Z0-9\\s]+")) {
                System.out.println("👀❌Il nome può contenere solo lettere, numeri e spazi❌👀");
                continue;
            }

            // Controllo se esiste già una lega con lo stesso nome (ignora maiuscole/minuscole)
            String finalNome = nome;
            boolean esisteGia = leghe.stream()
                    .anyMatch(l -> l.getNome().equalsIgnoreCase(finalNome));
            if (esisteGia) {
                System.out.println("👀❌Esiste già una lega con questo nome❌👀");
                continue;
            }

            // Se supera tutti i controlli, esce dal ciclo
            break;
        }

        // Crea e restituisce la nuova lega
        return new Lega(nome);
    }

    /**
     * Metodo per modificare il nome di una lega esistente con controlli simili alla creazione:
     * - Lunghezza valida
     * - Solo caratteri ammessi
     * - Nome non duplicato rispetto ad altre leghe
     */
    public static void modificaNomeLega(Lega lega, List<Lega> leghe, Scanner scanner) {
        System.out.println("👀Nome attuale: " + lega.getNome());
        System.out.print("Nuovo nome (INVIO per annullare): \n");
        String nuovoNome = scanner.nextLine().trim();

        // Se l'utente preme solo invio, annulla la modifica
        if (nuovoNome.isEmpty()) {
            System.out.println("Modifica annullata");
            return;
        }

        // Validazione lunghezza
        if (nuovoNome.length() < 3 || nuovoNome.length() > 20) {
            System.out.println("❌Il nome deve avere tra 3 e 20 caratteri❌");
            return;
        }

        // Validazione caratteri
        if (!nuovoNome.matches("[a-zA-Z0-9\\s]+")) {
            System.out.println("❌Il nome può contenere solo lettere, numeri e spazi❌");
            return;
        }

        // Controllo duplicato: il nuovo nome non deve coincidere con un altro già esistente (escludendo sé stesso)
        boolean duplicato = leghe.stream()
                .anyMatch(l -> !l.equals(lega) && l.getNome().equalsIgnoreCase(nuovoNome));

        if (duplicato) {
            System.out.println("❌Esiste già una lega con questo nome❌👀");
            return;
        }

        // Se tutto è valido, aggiorna il nome
        lega.setNome(nuovoNome);
        System.out.println("✅Nome lega aggiornato con successo✅");
    }
}