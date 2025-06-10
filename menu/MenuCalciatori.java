package ProgettoCalcistico.menu;

import ProgettoCalcistico.oggetti.Calciatore;
import ProgettoCalcistico.oggetti.Squadra;
import ProgettoCalcistico.validatori.ValidatorCalciatori;

import java.util.List;
import java.util.Scanner;

public class MenuCalciatori {
    // Lista globale di calciatori disponibili
    private final List<Calciatore> calciatori;
    // Lista delle squadre, utile per controllare se un calciatore è assegnato
    private final List<Squadra> squadre;
    // Scanner per leggere input da console
    private final Scanner scanner = new Scanner(System.in);

    // Costruttore con liste di calciatori e squadre
    public MenuCalciatori(List<Calciatore> calciatori, List<Squadra> squadre) {
        this.calciatori = calciatori;
        this.squadre = squadre;
    }

    // Metodo per avviare il menu calciatori
    public void avvia() {
        menuCalciatori();
    }

    // Menu principale del modulo calciatori
    private void menuCalciatori() {
        while (true) {
            System.out.println("\n=== MENU CALCIATORI ===");
            System.out.println("1) Visualizza elenco calciatori");
            System.out.println("2) Crea calciatore");
            System.out.println("3) Modifica calciatore");
            System.out.println("4) Elimina calciatore");
            System.out.println("5) Torna indietro");
            System.out.print("Scegli un'opzione: ");

            String scelta = scanner.nextLine();
            switch (scelta) {
                case "1" -> visualizzaCalciatori();  // Visualizza la lista
                case "2" -> creaCalciatore();        // Crea un nuovo calciatore
                case "3" -> modificaCalciatore();    // Modifica calciatore esistente
                case "4" -> rimuoviCalciatore();     // Rimuove un calciatore
                case "5" -> { return; }               // Esce dal menu
                default -> System.out.println("❌ Scelta non valida ❌");
            }
        }
    }

    // Metodo per visualizzare tutti i calciatori nella lista
    private void visualizzaCalciatori() {
        if (calciatori.isEmpty()) {
            System.out.println("❌ Nessun calciatore presente ❌");
            return;
        }
        System.out.println("=== Elenco Calciatori ===");
        for (int i = 0; i < calciatori.size(); i++) {
            System.out.println(i + ") " + calciatori.get(i)); // Stampa indice e info calciatore
        }
    }

    // Metodo per creare un nuovo calciatore con controlli di validità
    private void creaCalciatore() {
        try {
            // Definizione dei vincoli su nome, cognome e età
            int minNome = 3, maxNome = 15;
            int minCognome = 3, maxCognome = 15;
            int minEta = 18, maxEta = 40;

            System.out.print("Nome:\n ");
            String nome = scanner.nextLine().trim();
            //TODO dato che il controllo dei caratteri speciali è uguale sia per nome che per cognome, non replicare il codice, crea un metodo a parte chiamato per esempio checkNameValidation e che si prende in input una stringa e poi fai esattamente quell'if che stai facendo, e poi richiami il metodo due volte, una per il nome e una per il cognome
            // Controllo lunghezza e caratteri validi (solo lettere e spazi)
            if (nome.length() < minNome || nome.length() > maxNome || !nome.matches("[a-zA-Z ]+")) {
                System.out.println("❌ Il nome deve contenere solo lettere (e spazi) e avere tra " + minNome + " e " + maxNome + " caratteri ❌");
                return;
            }

            System.out.print("Cognome:\n ");
            String cognome = scanner.nextLine().trim();
            // Controllo lunghezza e caratteri validi (solo lettere e spazi)
            if (cognome.length() < minCognome || cognome.length() > maxCognome || !cognome.matches("[a-zA-Z ]+")) {
                System.out.println("❌ Il cognome deve contenere solo lettere (e spazi) e avere tra " + minCognome + " e " + maxCognome + " caratteri ❌");
                return;
            }

            System.out.print("Età (18-40): \n ");
            String etaStr = scanner.nextLine().trim();
            int eta;
            try {
                eta = Integer.parseInt(etaStr);
            } catch (NumberFormatException e) {
                // Se non è un numero valido, segnala errore
                System.out.println("❌ Età non valida (inserire solo numeri) ❌");
                return;
            }
            // Controllo range età
            if (eta < minEta || eta > maxEta) {
                System.out.println("❌ Età non valida, deve essere tra " + minEta + " e " + maxEta + " ❌");
                return;
            }

            System.out.print("Ruolo (portiere, difensore, centrocampista, attaccante):\n ");
            String ruolo = scanner.nextLine().trim().toLowerCase();
            // Controllo che il ruolo sia uno di quelli consentiti
            if (!ruolo.equals("portiere") && !ruolo.equals("difensore") &&
                    !ruolo.equals("centrocampista") && !ruolo.equals("attaccante")) {
                System.out.println("❌ Ruolo non valido ❌");
                return;
            }

            // Verifica se calciatore già presente con stesso nome, cognome e ruolo
            if (esisteCalciatore(nome, cognome, ruolo)) {
                System.out.println("❌ Calciatore già presente nella lista globale ❌");
                return;
            }

            // Se tutto ok, crea e aggiunge il nuovo calciatore alla lista
            Calciatore nuovo = new Calciatore(nome, cognome, eta, ruolo);
            calciatori.add(nuovo);
            System.out.println("✅ Calciatore aggiunto con successo ✅");

        } catch (Exception e) {
            // Gestione generale di eventuali errori imprevisti
            System.out.println("❌ Errore imprevisto: " + e.getMessage() + " ❌");
        }
    }

    // Metodo che verifica se esiste già un calciatore con nome, cognome e ruolo uguali
    private boolean esisteCalciatore(String nome, String cognome, String ruolo) {
        for (Calciatore c : calciatori) {
            if (c.getNome().equalsIgnoreCase(nome)
                    && c.getCognome().equalsIgnoreCase(cognome)
                    && c.getRuolo().equalsIgnoreCase(ruolo)) {
                return true; // Trovato doppione
            }
        }
        return false;
    }

    // Metodo per modificare i dati di un calciatore selezionato
    private void modificaCalciatore() {
        if (calciatori.isEmpty()) {
            System.out.println("❌ Nessun calciatore da modificare ❌");
            return;
        }

        visualizzaCalciatori(); // Mostra elenco per scegliere
        System.out.print("⤴️ Seleziona calciatore da modificare (indice) 🤾‍♂️: \n ");
        int idx = leggiIndice(calciatori.size());
        if (idx == -1) return; // Indice non valido o uscita

        try {
            Calciatore c = calciatori.get(idx);
            // Chiamata a metodo esterno che gestisce interattivamente la modifica del calciatore
            ValidatorCalciatori.modificaCalciatore(c);
            System.out.println("✅ Calciatore modificato con successo ✅");
        } catch (Exception e) {
            System.out.println("❌ Errore durante la modifica: " + e.getMessage() + " ❌");
        }
    }

    // Metodo per rimuovere un calciatore, se non è assegnato a nessuna squadra
    private void rimuoviCalciatore() {
        if (calciatori.isEmpty()) {
            System.out.println("❌ Nessun calciatore da rimuovere ❌");
            return;
        }

        visualizzaCalciatori(); // Mostra elenco
        System.out.print("⤴️ Seleziona calciatore da rimuovere (indice) 🤾‍♂️:\n ");
        int idx = leggiIndice(calciatori.size());
        if (idx == -1) return; // Indice non valido o uscita

        Calciatore c = calciatori.get(idx);
        boolean presente = false;
        // Controlla se il calciatore è presente in almeno una squadra (rosa o panchina)
        for (Squadra s : squadre) {
            if (s.getRosa().contains(c) || s.getPanchina().contains(c)) {
                presente = true;
                break;
            }
        }

        if (presente) {
            System.out.println("❌ Impossibile rimuovere; calciatore presente in una squadra ❌");
            return;
        }

        // Rimuove calciatore dalla lista globale
        calciatori.remove(idx);
        System.out.println("✅ Calciatore rimosso con successo ✅");
    }

    // Metodo helper per leggere e validare l'indice selezionato da input utente
    private int leggiIndice(int max) {
        try {
            int idx = Integer.parseInt(scanner.nextLine());
            if (idx < 0 || idx >= max) {
                System.out.println("❌ 😵‍💫 Indice fuori range 😵‍💫 ❌");
                return -1;
            }
            return idx;
        } catch (NumberFormatException e) {
            System.out.println("❌ 😵‍💫 Inserire un numero valido 😵‍💫 ❌");
            return -1;
        }
    }
}