package ProgettoCalcistico.validatori;

import ProgettoCalcistico.oggetti.Lega;

import java.util.List;
import java.util.Scanner;

import static ProgettoCalcistico.validatori.ValidatorSquadra.CheckModify;
import static ProgettoCalcistico.validatori.ValidatorSquadra.ValidationCharacter;

public class ValidatorLeghe {

    /**
     * Metodo per creare una nuova lega con validazione del nome.
     * - Lunghezza: 3-20 caratteri
     * - Contenuti: solo lettere, numeri e spazi
     * - Unicità: non deve esistere già una lega con lo stesso nome (case-insensitive)
     */
    public static Lega creaLega(Scanner scanner, List<Lega> leghe) {

        while (true) {
            String nome;
            System.out.print("✅ Inserisci nome della lega (3-20 caratteri, lettere, numeri e spazi): \n> ");
            nome = scanner.nextLine().trim();

            if (!CheckModify(nome)) {
                System.out.println("❌ Il nome può contenere solo lettere, apostrofi, trattini e spazi (3-20 caratteri).");
                continue;
            }

            if (!ValidationCharacter(nome)) {
                System.out.println("❌ Il nome può contenere solo numeri tra 1 e 10.");
                continue;
            }
            boolean esisteGia = leghe.stream()
                    .anyMatch(l -> l.getNome().equalsIgnoreCase(nome));
            if (esisteGia) {
                System.out.println("❌ Esiste già una lega con questo nome ❌");
                continue;
            }
            return new Lega(nome);
        }
    }

    /**
     * Metodo per modificare il nome di una lega esistente.
     */
    public static void modificaNomeLega(Lega lega, List<Lega> leghe, Scanner scanner) {
        System.out.println("👀 Nome attuale: " + lega.getNome());
        System.out.print("Nuovo nome (INVIO per annullare): \n> ");
        String nuovoNome = scanner.nextLine().trim();

        if (nuovoNome.isEmpty()) {
            System.out.println("❎ Modifica annullata");
            return;
        }

        if (!checkModify(nuovoNome)) {
            System.out.println("❌ Il nome può contenere solo lettere, apostrofi, trattini e spazi (3-20 caratteri).");
            return;
        }

        if (!validationCharacter(nuovoNome)) {
            System.out.println("❌ Il nome può contenere solo numeri tra 1 e 10.");
            return;
        }

        boolean duplicato = leghe.stream()
                .anyMatch(l -> !l.equals(lega) && l.getNome().equalsIgnoreCase(nuovoNome));

        if (duplicato) {
            System.out.println("❌ Esiste già una lega con questo nome ❌");
            return;
        }

        lega.setNome(nuovoNome);
        System.out.println("✅ Nome lega aggiornato con successo ✅");
    }

    // Metodo per validare lettere, accenti, apostrofi, trattini e spazi
    public static boolean checkModify(String s) {
        return s.matches("[a-zA-Zàèìòù'\\-\\s]{3,20}");
    }

    // Metodo per accettare numeri da 1 a 10 (in mezzo a testo), evitando 0 o >10
    public static boolean validationCharacter(String s) {
        return s.matches("[a-zA-Zàèìòù0-9\\s]{3,20}") &&
                !s.matches(".*\\b(0|[1-9][1-9]|\\d{3,})\\b.*");
    }
}
