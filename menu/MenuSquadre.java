package ProgettoCalcistico.menu;

import ProgettoCalcistico.oggetti.Calciatore;
import ProgettoCalcistico.oggetti.Squadra;
import ProgettoCalcistico.validatori.ValidatorSquadra;
import ProgettoCalcistico.Utils.Utils;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class MenuSquadre {
    private final List<Squadra> squadre;
    private final List<Calciatore> calciatori;
    private final Scanner scanner = new Scanner(System.in);

    public MenuSquadre(List<Squadra> squadre, List<Calciatore> calciatori) {
        this.squadre = squadre;
        this.calciatori = calciatori;
    }

    public void avvia() {
        while (true) {
            System.out.println("\n=== 🏆 MENU SQUADRE 🏆 ===");
            System.out.println("1) Visualizza squadre");
            System.out.println("2) Crea squadra");
            System.out.println("3) Modifica squadra");
            System.out.println("4) Elimina squadra");
            System.out.println("5) Assegna calciatore a squadra");
            System.out.println("6) Rimuovi calciatore da squadra");
            System.out.println("7) Torna indietro");
            System.out.print("Scegli opzione: ");
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1" -> visualizzaSquadre();
                case "2" -> creaSquadra();
                case "3" -> modificaSquadra();
                case "4" -> eliminaSquadra();
                case "5" -> assegnaCalciatoreASquadra();
                case "6" -> rimuoviCalciatoreDaSquadra();
                case "7" -> { return; }
                default -> System.out.println("❌ Scelta non valida ❌");
            }
        }
    }

    private void visualizzaSquadre() {
        if (squadre.isEmpty()) {
            System.out.println("❌ Nessuna squadra presente ❌");
            return;
        }
        System.out.println("=== ELENCO SQUADRE ===");

        // TODO niente cicli for così, devi usare gli stream
        IntStream.range(0, squadre.size()).forEach(i -> {
            Squadra s = squadre.get(i);
            System.out.println(i + ") " + s.getNome());
            System.out.println("  Rosa:");
            if (s.getRosa().isEmpty()) {
                System.out.println("    [Vuota]");
            } else {
                s.getRosa().stream()
                        .forEach(c -> System.out.println("    - " + c));
            }
            System.out.println("  Panchina:");
            if (s.getPanchina().isEmpty()) {
                System.out.println("    [Vuota]");
            } else {
                s.getPanchina().stream()
                        .forEach(c -> System.out.println("    - " + c));
            }
        });
    }

    private void creaSquadra() {
        System.out.print("Nome squadra: ");
        String nome = scanner.nextLine().trim();

        // TODO stessa cosa che ti ho scritto per la classe menuCalciatori, fai un metodo per questo nella classe ValidatorSquadra e utilizzalo qui
        if (!ValidatorSquadra.CheckModify(nome)) {
            System.out.println("❌ Nome squadra non valido (3-20 caratteri, alfanumerico e spazi) ❌");
            return;
        }
        if (existeSquadra(nome)) {
            System.out.println("❌ Squadra già esistente ❌");
            return;
        }

        squadre.add(new Squadra(nome));
        System.out.println("✅ Squadra creata ✅");
    }

    private boolean existeSquadra(String nome) {
        return squadre.stream()
                .anyMatch(s -> s.getNome().equalsIgnoreCase(nome));
    }

    private void modificaSquadra() {
        if (squadre.isEmpty()) {
            System.out.println("❌ Nessuna squadra da modificare ❌");
            return;
        }
        visualizzaSquadre();
        System.out.print("Seleziona squadra da modificare (indice): ");
        int idx = Utils.leggiIndice(scanner, squadre.size());
        if (idx == -1) return;

        Squadra s = squadre.get(idx);
        System.out.print("Nuovo nome: ");
        String nuovo = scanner.nextLine().trim();
        if (!ValidatorSquadra.CheckModify(nuovo)) {
            System.out.println("❌ Nome non valido ❌");
            return;
        }
        if (existeSquadra(nuovo)) {
            System.out.println("❌ Squadra con questo nome già esiste ❌");
            return;
        }
        s.setNome(nuovo);
        System.out.println("✅ Squadra modificata ✅");
    }

    private void eliminaSquadra() {
        if (squadre.isEmpty()) {
            System.out.println("❌ Nessuna squadra da eliminare ❌");
            return;
        }
        visualizzaSquadre();
        System.out.print("Seleziona squadra da eliminare (indice): ");
        int idx = Utils.leggiIndice(scanner, squadre.size());
        if (idx == -1) return;

        Squadra s = squadre.remove(idx);
        // TODO testare, la logica non mi torna
        if (!s.getRosa().isEmpty() || !s.getPanchina().isEmpty()) {
            System.out.print("Spostare calciatori nella lista globale (s) o eliminarli (n)? ");
            String r = scanner.nextLine().trim().toLowerCase();
            if (r.equals("s")) {
                s.getRosa().stream()
                        .filter(c -> !calciatori.contains(c))
                        .forEach(calciatori::add);
                s.getPanchina().stream()
                        .filter(c -> !calciatori.contains(c))
                        .forEach(calciatori::add);
                System.out.println("✅ Calciatori spostati ✅");
            } else {
                calciatori.removeAll(s.getRosa());
                calciatori.removeAll(s.getPanchina());
                System.out.println("✅ Calciatori eliminati ✅");
            }
        }
        System.out.println("✅ Squadra eliminata ✅");
    }

    private void assegnaCalciatoreASquadra() {
        if (calciatori.isEmpty()) {
            System.out.println("❌ Nessun calciatore disponibile ❌"); return;
        }
        if (squadre.isEmpty()) {
            System.out.println("❌ Nessuna squadra disponibile ❌"); return;
        }
        System.out.println("Elenco calciatori liberi:");
        // TODO stream
        IntStream.range(0, calciatori.size())
                .forEach(i -> System.out.println(i + ") " + calciatori.get(i)));
        int cidx = Utils.leggiIndice(scanner, calciatori.size());
        if (cidx == -1) return;
        final Calciatore c = calciatori.get(cidx);

        // TODO stream
        final Squadra corrente = squadre.stream()
                .filter(s -> s.getRosa().contains(c) || s.getPanchina().contains(c))
                .findFirst()
                .orElse(null);

        if (corrente != null) {
            System.out.print("Calciatore già in " + corrente.getNome() + ". Spostare? (s/n): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("s")) {
                System.out.println("Operazione annullata"); return;
            }
        }
        System.out.println("Elenco squadre:");
        // TODO stream
        IntStream.range(0, squadre.size())
                .forEach(i -> System.out.println(i + ") " + squadre.get(i).getNome()));
        int sidx = Utils.leggiIndice(scanner, squadre.size());
        if (sidx == -1) return;
        final Squadra dest = squadre.get(sidx);

        if (corrente != null) {
            corrente.getRosa().remove(c);
            corrente.getPanchina().remove(c);
        } else {
            calciatori.remove(c);
        }

        System.out.print("Inserirlo in: 1) Rosa 2) Panchina ? ");
        String scelta = scanner.nextLine().trim();
        if (scelta.equals("1")) {
            // TODO aggiungere controlli su ruolo e numero maglia
            dest.getRosa().add(c);
            System.out.println("✅ Calciatore aggiunto in rosa ✅");
        } else if (scelta.equals("2")) {
            // TODO aggiungere controllo su max panchina
            dest.getPanchina().add(c);
            System.out.println("✅ Calciatore aggiunto in panchina ✅");
        } else {
            System.out.println("❌ Scelta non valida ❌");
            if (corrente == null) calciatori.add(c);
            else {
                if (corrente.getRosa().contains(c)) corrente.getRosa().add(c);
                else corrente.getPanchina().add(c);
            }
        }
        // TODO completare gestione numero maglia e conteggio giocatori
    }

    private void rimuoviCalciatoreDaSquadra() {
        if (squadre.isEmpty()) {
            System.out.println("❌ Nessuna squadra disponibile ❌"); return;
        }
        System.out.println("Elenco squadre:");
        IntStream.range(0, squadre.size())
                .forEach(i -> System.out.println(i + ") " + squadre.get(i).getNome()));
        int sidx = Utils.leggiIndice(scanner, squadre.size());
        if (sidx == -1) return;
        Squadra s = squadre.get(sidx);

        // TODO stream
        if (s.getRosa().isEmpty() && s.getPanchina().isEmpty()) {
            System.out.println("❌ Squadra senza calciatori ❌"); return;
        }
        System.out.println("Calciatori in rosa:");
        IntStream.range(0, s.getRosa().size())
                .forEach(i -> System.out.println("R"+i + ") " + s.getRosa().get(i)));
        System.out.println("Calciatori in panchina:");
        IntStream.range(0, s.getPanchina().size())
                .forEach(i -> System.out.println("P"+i + ") " + s.getPanchina().get(i)));

        System.out.print("Digita Rindice o Pindice (es: R0, P1): ");
        String input = scanner.nextLine().trim().toUpperCase();
        if (!input.matches("[RP]\\d+")) {
            System.out.println("❌ Input non valido ❌"); return;
        }
        char tipo = input.charAt(0);
        int idx = Integer.parseInt(input.substring(1));
        boolean removed;
        if (tipo == 'R') removed = s.getRosa().removeIf(c -> s.getRosa().indexOf(c) == idx);
        else removed = s.getPanchina().removeIf(c -> s.getPanchina().indexOf(c) == idx);
        if (removed) {
            calciatori.add(tipo=='R' ? s.getRosa().get(idx) : s.getPanchina().get(idx));
            System.out.println("✅ Calciatore rimosso ✅");
        } else System.out.println("❌ Indice non valido ❌");
    }
}