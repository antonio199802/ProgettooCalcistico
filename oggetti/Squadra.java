package ProgettoCalcistico.oggetti;

import java.util.ArrayList;
import java.util.List; // Importazione delle classi necessarie per la gestione di liste dinamiche.

/**
 * La classe Squadra rappresenta una squadra di calcio composta da una rosa di titolari,
 * una panchina di riserve e un'associazione opzionale a una lega.
 */
public class Squadra {
    private String nome; // Nome della squadra (es. "Juventus", "Milan", ecc.)
    private final List<Calciatore> rosa; // Lista dei calciatori titolari (massimo 11, con vincoli di ruolo)
    private final List<Calciatore> panchina; // Lista dei calciatori in panchina (massimo 22, senza vincoli di ruolo)
    private Lega lega; // Lega a cui la squadra è associata (può essere null se non ancora assegnata)

    /**
     * Costruttore della classe Squadra. Inizializza nome, rosa e panchina.
     * @param nome Il nome della squadra
     */
    public Squadra(String nome) {
        this.nome = nome;
        this.rosa = new ArrayList<>();
        this.panchina = new ArrayList<>();
    }

    /**
     * Restituisce il nome della squadra.
     * @return Il nome della squadra
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta un nuovo nome per la squadra.
     * @param nome Il nuovo nome da assegnare
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce la lista dei calciatori titolari (rosa).
     * @return Lista dei calciatori titolari
     */
    public List<Calciatore> getRosa() {
        return rosa;
    }

    /**
     * Restituisce la lista dei calciatori in panchina.
     * @return Lista dei calciatori in panchina
     */
    public List<Calciatore> getPanchina() {
        return panchina;
    }

    /**
     * Restituisce la lega a cui la squadra è associata.
     * @return L'oggetto Lega associato o null se non assegnato
     */
    public Lega getLega() {
        return lega;
    }

    /**
     * Imposta la lega a cui la squadra appartiene.
     * @param lega L'oggetto Lega da associare alla squadra
     */
    public void setLega(Lega lega) {
        this.lega = lega;
    }

    /**
     * Restituisce una rappresentazione testuale della squadra, utile per la stampa nei menu.
     * @return Una stringa con il nome della squadra e il numero di giocatori in rosa e panchina
     */
    @Override
    public String toString() {
        return nome + " [Rosa: " + rosa.size() + " giocatori, Panchina: " + panchina.size() + " giocatori]";
    }
}