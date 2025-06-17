package ProgettoCalcistico.Utils;

import java.util.Scanner;

public class Utils {
    public static int leggiIndice(Scanner scanner, int max) {
        System.out.print("🔢Inserisci indice🔢: \n ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim());
            if (idx >= 0 && idx < max) return idx;
        } catch (NumberFormatException ignored) {}
        System.out.println("❌Indice non valido❌");
        return -1;
    }
}