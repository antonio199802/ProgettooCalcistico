package ProgettoCalcistico.menu;
import ProgettoCalcistico.oggetti.Calciatore;
import ProgettoCalcistico.oggetti.Squadra;
import ProgettoCalcistico.validatori.ValidatorCalciatori;
import ProgettoCalcistico.validatori.ValidatorSquadra;

import java.util.List;
import java.util.Scanner;

import static ProgettoCalcistico.Utils.Utils.leggiIndice;
import static ProgettoCalcistico.validatori.ValidatorCalciatori.validaNomeOCognome;
import static ProgettoCalcistico.validatori.ValidatorCalciatori.validaRuolo;

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
                case "5" -> {
                    return;
                }               // Esce dal menu
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
            // Definizione dei vincoli su nome, cognome(con metodo validaNomeOCognome()) e età
            int minEta = 18, maxEta = 40;

            System.out.print("Nome:\n ");
            //TODO dato che il controllo dei caratteri speciali è uguale sia per nome che per cognome, non replicare il codice, crea un metodo a parte chiamato per esempio checkNameValidation e che si prende in input una stringa e poi fai esattamente quell'if che stai facendo, e poi richiami il metodo due volte, una per il nome e una per il cognome
            // Controllo lunghezza e caratteri validi (solo lettere e spazi)
            /*
            //Modifica richiesta effettuata//
             */
            System.out.print("Inserisci il nome: ");
            String nome = scanner.nextLine();
            while (!validaNomeOCognome(nome)) {
                System.out.println("Nome non valido. Deve contenere solo lettere, spazi, apostrofi o trattini, lunghezza tra 3 e 15 caratteri.");
                nome = scanner.nextLine();
            }

            System.out.print("Inserisci il cognome: ");
            String cognome = scanner.nextLine();
            while (!validaNomeOCognome(cognome)) {
                System.out.println("Cognome non valido. Deve contenere solo lettere, spazi, apostrofi o trattini, lunghezza tra 3 e 15 caratteri.");
                cognome = scanner.nextLine();
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
            //TODO perchè non usi il metodo ValidatorRuolo nella classe validatorCalciatori? è meglio
            //modifica effettuata//
            while (!validaRuolo(ruolo)) {
                System.out.println("Ruolo non valido.Attieniti alle condizioni");
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
        //TODO utilizza lo stream invece che il forEach
        //modifica effettuata
        return calciatori.stream().anyMatch(calciatore -> calciatore.getNome().equalsIgnoreCase(nome) &&
                calciatore.getCognome().equalsIgnoreCase(cognome) &&
                calciatore.getRuolo().equalsIgnoreCase(ruolo));
    }


    // Metodo per modificare i dati di un calciatore selezionato
    private void modificaCalciatore() {
        if (calciatori.isEmpty()) {
            System.out.println("❌ Nessun calciatore da modificare ❌");
            return;
        }

        visualizzaCalciatori(); // Mostra elenco per scegliere
        System.out.print("⤴️ Seleziona calciatore da modificare (indice) 🤾‍♂️: \n ");
        int idx = leggiIndice(scanner,calciatori.size());
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
        int idx = leggiIndice(scanner,calciatori.size());
        if (idx == -1) return; // Indice non valido o uscita


        // Controlla se il calciatore è presente in almeno una squadra (rosa o panchina)
        //TODO crea un metodo a parte per questo check e usa gli stream
        //modifica effettuata-TODO
        while (true) {
            Calciatore player = calciatori.get(idx);
            boolean presente = ValidatorSquadra.calciatorePresenteInQualcheSquadra(player, squadre);
            if (presente) {
                System.out.println("Calciatore gia assegnato alla squadra: " + squadre.get(idx));
            }


            // Rimuove calciatore dalla lista globale
            calciatori.remove(idx);
            System.out.println("✅ Calciatore rimosso con successo ✅");
        }
    }
}

