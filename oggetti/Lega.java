package ProgettoCalcistico.oggetti;

/**
 * La classe Lega rappresenta una competizione o campionato a cui possono appartenere più squadre.
 * Ogni lega ha un nome univoco.
 */
public class Lega {

    // Variabile d'istanza per memorizzare il nome della lega
    public String nome;

    /**
     * Costruttore della classe Lega.
     * Inizializza il nome della lega al valore specificato.
     *
     * @param nome Il nome della lega (es. "Serie A", "Premier League", ecc.)
     */
    public Lega(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il nome della lega.
     *
     * @return Il nome della lega
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta un nuovo nome per la lega.
     *
     * @param nome Il nuovo nome da assegnare alla lega
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce una rappresentazione testuale della lega, utile per la stampa nei menu.
     *
     * @return Stringa descrittiva della lega
     */
    @Override
    public String toString() {
        return "Lega{" +
                "nome='" + nome + '\'' +
                '}';
    }
}