package ProgettoCalcistico.menu;
import ProgettoCalcistico.oggetti.Squadra;
import ProgettoCalcistico.oggetti.Lega;
import ProgettoCalcistico.validatori.ValidatorLeghe;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import static ProgettoCalcistico.Utils.Utils.leggiIndice;

/**
 * Classe MenuLeghe per la gestione interattiva delle leghe,
 * con operazioni di visualizzazione, creazione, modifica, eliminazione e assegnazione squadre.
 */
public class MenuLeghe {

    private final List<Lega> leghe = new ArrayList<>();
    private final List<Squadra> squadre;
    private final Scanner scanner = new Scanner(System.in);

    public MenuLeghe(List<Squadra> squadre) {
        this.squadre = squadre;
    }

    public void avvia() {
        menuLeghe();
    }

    private void menuLeghe() {
        while (true) {
            System.out.println("\n=== MENU LEGA ===");
            System.out.println("1) Visualizza elenco leghe");
            System.out.println("2) Crea lega");
            System.out.println("3) Modifica lega");
            System.out.println("4) Elimina lega");
            System.out.println("5) Torna indietro");
            System.out.print("Scegli un'opzione: ");

            try {
                int scelta = scanner.nextInt();
                scanner.nextLine();

                switch (scelta) {
                    case 1 -> visualizzaElencoLeghe();
                    case 2 -> creaLega();
                    case 3 -> modificaNomeLega();
                    case 4 -> eliminaLega();
                    case 5 -> {
                        return;
                    }
                    default -> System.out.println("❌ Scelta non valida ❌");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Input non valido. Inserisci un numero intero. ❌");
                scanner.nextLine();
            }
        }
    }

    private void visualizzaElencoLeghe() {
        if (leghe.isEmpty()) {
            System.out.println("Nessuna lega creata.");
            return;
        }

        System.out.println("=== Elenco Leghe ===");

        // TODO: Sostituito for con IntStream per stampare leghe con indice
        IntStream.range(0, leghe.size())
                .forEach(i -> System.out.println(i + ") " + leghe.get(i)));
    }

    private void creaLega() {
        Lega nuovaLega = ValidatorLeghe.creaLega(scanner, leghe);
        leghe.add(nuovaLega);
        System.out.println("✅ Lega \"" + nuovaLega.getNome() + "\" creata con successo ✅");
    }

    private void modificaNomeLega() {
        if (leghe.isEmpty()) {
            System.out.println("❌ Nessuna lega da modificare ❌");
            return;
        }

        visualizzaElencoLeghe();
        System.out.print("Seleziona lega da modificare (indice): ");
        int idx = leggiIndice(scanner,leghe.size());
        if (idx == -1) return;

        Lega legaSelezionata = leghe.get(idx);
        ValidatorLeghe.modificaNomeLega(legaSelezionata, leghe, scanner);

        // TODO: Stream per contare squadre nella lega
        long numeroSquadre = squadre.stream()
                .filter(s -> legaSelezionata.equals(s.getLega()))
                .count();

        // TODO: Stream per sommare calciatori in rosa e panchina
        int totalecalciatori = squadre.stream()
                .filter(s -> legaSelezionata.equals(s.getLega()))
                .mapToInt(s -> s.getRosa().size() + s.getPanchina().size())
                .sum();

        System.out.println("ℹ️ Squadre nella lega: " + numeroSquadre);
        System.out.println("ℹ️ Calciatori totali nelle squadre della lega: " + totalecalciatori);
    }

    private void eliminaLega() {
        if (leghe.isEmpty()) {
            System.out.println("❌ Nessuna lega da eliminare ❌");
            return;
        }

        visualizzaElencoLeghe();
        System.out.print("Seleziona lega da eliminare (indice): ");
        int idx = leggiIndice(scanner, leghe.size());
        if (idx == -1) return;

        Lega legaDaRimuovere = leghe.get(idx);

        // TODO: Rimuovere la lega dalle squadre che la contengono (stream)
        squadre.stream()
                .filter(s -> legaDaRimuovere.equals(s.getLega()))
                .forEach(s -> s.setLega(null));

        leghe.remove(idx);
        System.out.println("✅ Lega eliminata con successo ✅");
    }
}