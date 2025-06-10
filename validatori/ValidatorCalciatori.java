package ProgettoCalcistico.validatori;

import ProgettoCalcistico.oggetti.Calciatore;
import java.util.Scanner;

public class ValidatorCalciatori {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Menu per la modifica interattiva di un oggetto Calciatore.
     */
    public static void modificaCalciatore(Calciatore c) {
        while (true) {
            System.out.println("\n=== ✏️ MODIFICA CALCIATORE ===");
            System.out.println("📌 Calciatore attuale: " + c);
            System.out.println("1️⃣  Modifica nome");
            System.out.println("2️⃣  Modifica cognome");
            System.out.println("3️⃣  Modifica età");
            System.out.println("4️⃣  Modifica ruolo");
            System.out.println("5️⃣  Torna indietro");
            System.out.print("👉 Scegli un'opzione: ");
            String scelta = scanner.nextLine().trim();

            switch (scelta) {
                case "1" -> {
                    System.out.print("Nuovo nome (3-15 caratteri): \n");
                    String nuovoNome = scanner.nextLine().trim();
                    if (!nuovoNome.isEmpty() && validaNomeOCognome(nuovoNome)) {
                        c.setNome(nuovoNome);
                        System.out.println("✅Nome aggiornato");
                    } else {
                        System.out.println("❌Nome non valido");
                    }
                }
                case "2" -> {
                    System.out.print("Nuovo cognome (3-15 caratteri): ");
                    String nuovoCognome = scanner.nextLine().trim();
                    if (!nuovoCognome.isEmpty() && validaNomeOCognome(nuovoCognome)) {
                        c.setCognome(nuovoCognome);
                        System.out.println("✅Cognome aggiornato");
                    } else {
                        System.out.println("❌Cognome non valido");
                    }
                }
                case "3" -> {
                    System.out.print("Nuova età (18-40): \n ");
                    String inputEta = scanner.nextLine().trim();
                    if (!inputEta.isEmpty()) {
                        try {
                            int eta = Integer.parseInt(inputEta);
                            if (eta >= 18 && eta <= 40) {
                                c.setEta(eta);
                                System.out.println("✅Età aggiornata");
                            } else {
                                System.out.println("❌Età fuori dal range");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("❌Valore non valido");
                        }
                    }
                }
                case "4" -> {
                    System.out.print("Nuovo ruolo (Portiere, Difensore, Centrocampista, Attaccante): ");
                    String ruolo = scanner.nextLine().trim();
                    if (!ruolo.isEmpty() && validaRuolo(ruolo)) {
                        c.setRuolo(ruolo);
                        System.out.println("✅Ruolo aggiornato");
                    } else {
                        System.out.println("❌Ruolo non valido");
                    }
                }
                case "5" -> {
                    System.out.println("🔙 Uscita dal menu modifica");
                    return;
                }
                default -> System.out.println("❌Scelta non valida. Riprova");
            }
        }
    }

    /**
     * Verifica che nome o cognome siano lunghi tra 3 e 15 caratteri e contengano solo lettere
     * (inclusi accenti italiani), apostrofi, trattini e spazi.
     */
    private static boolean validaNomeOCognome(String s) {
        return s.matches("[a-zA-Zàèìòù'\\-\\s]{3,15}");
    }

    /**
     * Verifica che il ruolo sia uno di quelli ammessi, case-insensitive.
     */
    private static boolean validaRuolo(String ruolo) {
        return ruolo.matches("(?i)portiere|difensore|centrocampista|attaccante");
    }
}