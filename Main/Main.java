package ProgettoCalcistico.Main;

import ProgettoCalcistico.menu.MenuPrincipale;  // Importa la classe MenuPrincipale per avviare il menu principale

public class Main {
    // Metodo principale, punto di ingresso dell'applicazione
    public static void main(String[] args) {
        // Crea un'istanza del menu principale
        MenuPrincipale menu = new MenuPrincipale();
        // Avvia il menu principale, che gestisce l'interazione con l'utente
        menu.avvia();
    }
}