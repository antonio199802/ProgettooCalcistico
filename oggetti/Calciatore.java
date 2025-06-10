package ProgettoCalcistico.oggetti;

/**
 * La classe Calciatore rappresenta un singolo giocatore di calcio, con informazioni
 * personali e calcistiche come nome, età, ruolo e numero di maglia.
 */
public class Calciatore {
    // Attributi principali del calciatore
    private String nome;              // Nome del calciatore
    private String cognome;           // Cognome del calciatore
    private int eta;                  // Età del calciatore (tra 18 e 40 anni)
    private int numeroMaglia;         // Numero di maglia (tra 1 e 99), unico nella squadra
    private String ruolo;             // Ruolo del calciatore (Portiere, Difensore, Centrocampista, Attaccante)

    // =================== GETTER E SETTER ===================

    /**
     * Restituisce il nome del calciatore.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome del calciatore.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il cognome del calciatore.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il cognome del calciatore.
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Restituisce l'età del calciatore.
     */
    public int getEta() {
        return eta;
    }

    /**
     * Imposta l'età del calciatore.
     */
    public void setEta(int eta) {
        this.eta = eta;
    }

    /**
     * Restituisce il numero di maglia del calciatore.
     */
    public int getNumeroMaglia() {
        return numeroMaglia;
    }

    /**
     * Imposta il numero di maglia del calciatore.
     */
    public void setNumeroMaglia(int numeroMaglia) {
        this.numeroMaglia = numeroMaglia;
    }

    /**
     * Restituisce il ruolo del calciatore.
     */
    public String getRuolo() {
        return ruolo;
    }

    /**
     * Imposta il ruolo del calciatore.
     */
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    // =================== COSTRUTTORI ===================

    /**
     * Costruttore completo che inizializza tutti i campi, incluso il numero di maglia.
     * @param nome Nome del calciatore
     * @param cognome Cognome del calciatore
     * @param eta Età del calciatore
     * @param numeroMaglia Numero di maglia (da 1 a 99)
     * @param ruolo Ruolo del calciatore
     */
    public Calciatore(String nome, String cognome, int eta, int numeroMaglia, String ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
        this.numeroMaglia = numeroMaglia;
        this.ruolo = ruolo;
    }

    /**
     * Costruttore alternativo che non assegna subito il numero di maglia.
     * Utile quando il calciatore viene creato prima di essere assegnato a una squadra.
     * @param nome Nome del calciatore
     * @param cognome Cognome del calciatore
     * @param eta Età del calciatore
     * @param ruolo Ruolo del calciatore
     */
    public Calciatore(String nome, String cognome, int eta, String ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
        this.ruolo = ruolo;
        this.numeroMaglia = 0; // 0 indica che il numero non è ancora stato assegnato
    }

    // =================== TO STRING ===================

    /**
     * Restituisce una rappresentazione testuale del calciatore,
     * utile per la visualizzazione nei menu.
     */
    @Override
    public String toString() {
        return "Calciatore{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", eta=" + eta +
                ", numeroMaglia=" + numeroMaglia +
                ", ruolo='" + ruolo + '\'' +
                '}';
    }
}