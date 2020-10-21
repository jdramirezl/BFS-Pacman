/**
 * Esta clase define objetos tipo pacman
 */
public class Pacman extends Caracter {
    private int puntosVida;

    /**
     * Constructor que define los atributos de pacman
     * @param tipo A traves de este entero diferenciamos si es pacman, arepa, etc
     * @param posicion Un objeto tipo posicion que nos dice las coordenadas X,Y de pacman
     * @param puntosVida Es un entero que asigna la cantidad de vida de pacman
     */
    public Pacman(int tipo, Posicion posicion, int puntosVida){
        super(tipo, posicion, '^');
        this.puntosVida = puntosVida;
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    public void setPuntosVida(int puntosVida) {
        this.puntosVida = puntosVida;
    }

}