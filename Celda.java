
/**
 * Esta clase define cada espacio del laberinto y sus propiedades
 */
public class Celda {
    protected boolean esMuro;
    protected boolean esSalida;
    protected char letra;
    protected Caracter personaje;
    protected String color = null;

    /**
     * Constructor de la celda, definimos atributos
     * @param esMuro este booleano indica si la celda es muro
     * @param esSalida  este booleano indica si la celda es una salida
     * @param letra este booleano indica el caracter que va dibujado en la celda
     */
    public Celda(boolean esMuro, boolean esSalida, char letra){
        this.esMuro = esMuro;
        this.esSalida = esSalida;
        this.letra = letra;
    }

    /**
     * Retorna la letra de la celda
     * @return retorna la letra o caracter que pertenece a la celda
     */
    public char caracterCelda(){
        return letra;
    }

    /**
     * Asigna al caracter de la celda el que le proporcionemos, y guarda su letra
     * @param cara Caracter que se le va a dar a la celda
     */
    public void setCara(Caracter cara) {
        this.personaje = cara;
        letra = personaje.representacion;
    }
}
