package ProgettoCalcistico.menu;

// Import delle classi necessarie
import ProgettoCalcistico.oggetti.Calciatore;
import ProgettoCalcistico.oggetti.Squadra;
import ProgettoCalcistico.validatori.ValidatorSquadra;
import java.util.List;
import java.util.Scanner;

public class MenuSquadre {
    private final List<Squadra> squadre; // Elenco delle squadre esistenti
    private final List<Calciatore> calciatori; // Lista globale dei calciatori non assegnati
    private final Scanner scanner = new Scanner(System.in); // Per input utente

    // Costruttore: riceve le liste già esistenti
    public MenuSquadre(List<Squadra> squadre, List<Calciatore> calciatori) {
        this.squadre = squadre;
        this.calciatori = calciatori;
    }

    // Avvio del menu delle squadre
    public void avvia() {
        menuSquadre();
    }

    // Metodo principale del menu
    private void menuSquadre() {
        while (true) {
            System.out.println("\n=== 🏆 MENU SQUADRE 🏆 ===");
            System.out.println("1) 📋Visualizza elenco squadre📋");
            System.out.println("2) 🔡Crea squadra🔡");
            System.out.println("3) ❎Modifica squadra❎");
            System.out.println("4) ❌Elimina squadra❌");
            System.out.println("5) ✅Assegna calciatore a squadra🤾‍♀️");
            System.out.println("6) ❌Rimuovi calciatore da squadra🤾‍♂️");
            System.out.println("7) 👋🏼Torna indietro👋🏼");
            System.out.print("➡️Scegli un'opzione:\n");

            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1" -> visualizzaSquadre(); // Stampa elenco squadre
                case "2" -> creaSquadra(); // Crea nuova squadra
                case "3" -> modificaSquadra(); // Modifica nome squadra
                case "4" -> eliminaSquadra(); // Rimuove squadra
                case "5" -> assegnaCalciatoreASquadra(); // Assegna calciatore a squadra
                case "6" -> rimuoviCalciatoreDaSquadra(); // Rimuove calciatore
                case "7" -> { return; } // Torna al menu precedente
                default -> System.out.println("❌ Scelta non valida ❌");
            }
        }
    }

    // Visualizza ogni squadra e la sua rosa/panchina
    private void visualizzaSquadre() {
        if (squadre.isEmpty()) {
            System.out.println("❌ Nessuna squadra presente ❌");
            return;
        }
        System.out.println("=== Elenco Squadre ===");
        //TODO niente cicli for così, devi usare gli stream
        for (int i = 0; i < squadre.size(); i++) {
            Squadra s = squadre.get(i);
            System.out.println(i + ") " + s.getNome());

            // Rosa
            System.out.println("  Rosa:\n");
            if (s.getRosa().isEmpty()) {
                System.out.println("    [Vuota]");
            } else {
                for (Calciatore c : s.getRosa()) {
                    System.out.println("    - " + c);
                }
            }

            // Panchina
            System.out.println("  Panchina:\n");
            if (s.getPanchina().isEmpty()) {
                System.out.println("    [Vuota]");
            } else {
                for (Calciatore c : s.getPanchina()) {
                    System.out.println("    - " + c);
                }
            }
        }
    }

    // Crea una nuova squadra con validazione (gli spazi li ho ritenuti necessari)
    private void creaSquadra() {
        System.out.print("Nome squadra: \n ");
        String nome = scanner.nextLine().trim();

        //TODO stessa cosa che ti ho scritto per la classe menuCalciatori, fai un metodo per questo nella classe ValidatorSquadra e utilizzalo qui
        if ( nome.length() < 3 || nome.length() > 20 || !nome.matches("[a-zA-Z0-9 ]+")) {
            System.out.println("❌ Nome squadra non valido (3-20 caratteri alfanumerici e spazi ) ❌");
            return;
        }

        if (esisteSquadra(nome)) {
            System.out.println("❌Squadra già esistente❌");
            return;
        }

        Squadra squadra = new Squadra(nome);
        squadre.add(squadra);
        System.out.println("✅Squadra creata con successo✅");
    }

    // Controlla se una squadra esiste già per nome
    public boolean esisteSquadra(String nome) {
        return squadre.stream()
                .anyMatch(s -> s.getNome().equalsIgnoreCase(nome));
    }

    // Modifica nome di una squadra usando il Validator
    private void modificaSquadra() {
        if (squadre.isEmpty()) {
            System.out.println("❌Nessuna squadra da modificare❌");
            return;
        }

        visualizzaSquadre();
        System.out.print("Seleziona squadra da modificare (indice): \n ");
        int idx = leggiIndice(squadre.size());
        if (idx == -1) return;

        Squadra s = squadre.get(idx);
        try {
            ValidatorSquadra.modificaSquadra(s);
            System.out.println("✅Squadra modificata con successo✅");
        } catch (Exception e) {
            System.out.println("❌ Errore durante la modifica: " + e.getMessage() + " ❌");
        }
    }

    // Elimina una squadra, con opzione di salvare i calciatori nella lista globale
    private void eliminaSquadra() {
        if (squadre.isEmpty()) {
            System.out.println("❌Nessuna squadra da eliminare❌");
            return;
        }

        System.out.println("❎Seleziona la squadra da eliminare❌:\n");
        //TODO stream
        for (int i = 0; i < squadre.size(); i++) {
            System.out.println(i + ") " + squadre.get(i).getNome());
        }

        int scelta = leggiIndice(scanner, squadre.size());
        if (scelta == -1) return;

        Squadra squadraDaEliminare = squadre.get(scelta);

        // Gestione calciatori in rosa/panchina
        if (!squadraDaEliminare.getRosa().isEmpty() || !squadraDaEliminare.getPanchina().isEmpty()) {
            System.out.print("🤾‍♂️La squadra contiene calciatori🤾‍♂️. Vuoi spostarli nella lista globale 🌏(s) o eliminarli con la squadra❌(n)? (s/n):\n");
            String risposta = scanner.nextLine().trim().toLowerCase();
    //TODO testare, la logica non mi torna
            if (risposta.equals("s")) {
                for (Calciatore c : squadraDaEliminare.getRosa()) {
                    if (!calciatori.contains(c)) calciatori.add(c);
                }
                for (Calciatore c : squadraDaEliminare.getPanchina()) {
                    if (!calciatori.contains(c)) calciatori.add(c);
                }
                System.out.println("✅Calciatori spostati nella lista globale 🌏✅");
            } else {
                calciatori.removeAll(squadraDaEliminare.getRosa());
                calciatori.removeAll(squadraDaEliminare.getPanchina());
                System.out.println("✅👋🏼 Calciatori eliminati con la squadra 🤾‍♂️✅");
            }
        }

        squadre.remove(squadraDaEliminare);
        System.out.println("✅Squadra eliminata con successo✅");
    }

    // Legge un indice dall'utente
   //TODO dato che questo metodo è universale per tutte le classi menu, non ripeterlo in ogni classe, crea magari una classe Utils e mettilo li dentro, la classe Utils deve contenere tutti i metodi che vengono usati in tutte le classi menu per evitare che replichi il codice in ogni classe
    private int leggiIndice(Scanner scanner, int max) {
        System.out.print("🔢Inserisci indice🔢: \n ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim());
            if (idx >= 0 && idx < max) {
                return idx;
            }
        } catch (NumberFormatException ignored) {}
        System.out.println("❌Indice non valido❌");
        return -1;
    }

    // Assegna un calciatore a una squadra (gestendo spostamenti)
    private void assegnaCalciatoreASquadra() {
        if (calciatori.isEmpty()) {
            System.out.println("❌Nessun calciatore disponibile❌");
            return;
        }
        if (squadre.isEmpty()) {
            System.out.println("❌Nessuna squadra disponibile ❌");
            return;
        }

        // Mostra calciatori disponibili
        //TODO stream
        for (int i = 0; i < calciatori.size(); i++) {
            System.out.println(i + ") " + calciatori.get(i));
        }
        int calIdx = leggiIndice(calciatori.size());
        if (calIdx == -1) return;
        Calciatore c = calciatori.get(calIdx);

        System.out.println("💼Seleziona calciatore da assegnare 🔢(indice)🤾‍♂️:\n ");

        // Controlla se il calciatore è già assegnato a una squadra
        Squadra squadraCorrente = null;
        //TODO stream
        for (Squadra s : squadre) {
            if (s.getRosa().contains(c) || s.getPanchina().contains(c)) {
                squadraCorrente = s;
                break;
            }
        }

        // Se già assegnato, chiedi conferma per spostarlo
        //TODO il codice di questo if potresti sportarlo direttamente dentro l'if di riga 218 ed eliminare il check su squadraCorrente
        if (squadraCorrente != null) {
            System.out.println("⚠️ Calciatore attualmente assegnato a: 🫡" + squadraCorrente.getNome());
            System.out.print("💼Vuoi spostarlo in un'altra squadra?⚽️ (s/n): \n ");
            String risposta = scanner.nextLine().trim().toLowerCase();
            if (!risposta.equals("s")) {
                System.out.println("❌ Operazione annullata ❌");
                return;
            }
        }

        visualizzaSquadre();
        int sqIdx = leggiIndice(squadre.size());
        if (sqIdx == -1) return;
        Squadra nuovaSquadra = squadre.get(sqIdx);
        System.out.println("💼Seleziona squadra di destinazione 🔢(indice)🫡:\n");

        try {
            ValidatorSquadra.assegnaCalciatore(nuovaSquadra, c);
            // Rimuove il calciatore da eventuale squadra precedente
            if (squadraCorrente != null) {
                squadraCorrente.getRosa().remove(c);
                squadraCorrente.getPanchina().remove(c);
            }
            System.out.println("✅🤾‍♂️Calciatore assegnato con successo🤾‍♀️✅");
        } catch (Exception e) {
            System.out.println("❌Errore durante assegnazione: " + e.getMessage() + "❌");
        }
    }

    // Rimuove un calciatore da rosa o panchina
    private void rimuoviCalciatoreDaSquadra() {
        if (squadre.isEmpty()) {
            System.out.println("❌🤷‍♂️Nessuna squadra presente🤷‍♂️❌");
            return;
        }

        visualizzaSquadre();
        System.out.print("Seleziona squadra da cui rimuovere calciatore (indice):\n");
        int sqIdx = leggiIndice(squadre.size());
        if (sqIdx == -1) return;
        Squadra s = squadre.get(sqIdx);

        if (s.getRosa().isEmpty() && s.getPanchina().isEmpty()) {
            System.out.println("❌🤷‍♂️Squadra senza calciatori🤷‍♂️❌");
            return;
        }

        // Mostra calciatori con prefisso (R)osa o (P)anchina
        System.out.println("Calciatori in rosa🤾‍♂️:\n");
        //TODO stream
        for (int i = 0; i < s.getRosa().size(); i++) {
            System.out.println("R" + i + ") " + s.getRosa().get(i));
        }
        //TODO stream
        System.out.println("Calciatori in panchina 🐖:\n");
        for (int i = 0; i < s.getPanchina().size(); i++) {
            System.out.println("P" + i + ") " + s.getPanchina().get(i));
        }

        System.out.print("⚠️Digita Rindice o Pindice per rimuovere (es: R0 o P3):\n");
        String input = scanner.nextLine().trim().toUpperCase();
        if (input.length() < 2) {
            System.out.println("❌Input non valido❌");
            return;
        }

        char tipo = input.charAt(0); // R o P
        int idx;
        try {
            idx = Integer.parseInt(input.substring(1));
        } catch (NumberFormatException e) {
            System.out.println("❌👀Indice non valido👀❌");
            return;
        }

        boolean rimosso = false;
        if (tipo == 'R') {
            if (idx >= 0 && idx < s.getRosa().size()) {
                s.getRosa().remove(idx);
                rimosso = true;
            }
        } else if (tipo == 'P') {
            if (idx >= 0 && idx < s.getPanchina().size()) {
                s.getPanchina().remove(idx);
                rimosso = true;
            }
        } else {
            System.out.println("❌Tipo non valido❌");
            return;
        }

        if (rimosso) {
            System.out.println("✅Calciatore rimosso con successo✅");
        } else {
            System.out.println("❌😵‍💫Indice fuori range😵‍💫❌");
        }
    }

    // Versione alternativa per leggere indici (senza scanner esterno)
    private int leggiIndice(int max) {
        try {
            int idx = Integer.parseInt(scanner.nextLine());
            if (idx < 0 || idx >= max) {
                System.out.println("❌😵‍💫Indice fuori range😵‍💫❌");
                return -1;
            }
            return idx;
        } catch (NumberFormatException e) {
            System.out.println("❌Inserire un numero valido❌");
            return -1;
        }
    }
}