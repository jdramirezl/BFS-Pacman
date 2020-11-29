/**
 * Clase padre para crear objetos caracter
 */
public class Caracter{
    protected int tipo;
    protected Posicion posicion;
    protected char representacion;

    /**
     * Crea un nuevo caracter
     *
     * @param tipo           El tipo numerico del caracter
     * @param posicion       La posicion del caracter
     * @param representacion La letra que representa al caracter
     */
    public Caracter(int tipo, Posicion posicion, char representacion){
        this.tipo = tipo;
        this.posicion = posicion;
        this.representacion = representacion;
    }

}

/**
 * Clase para crear posiciones
 */
class Posicion{
    private int x;
    private int y;

    /**
     * Crea una nueva posicion
     *
     * @param x Coordenada x
     * @param y Coordenada y
     */
    public Posicion(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Devuelve la coordenada x
     *
     * @return La coordenada x
     */
    public int getX() {
        return x;
    }

    /**
     * Cambia la coordenada x
     *
     * @param x La nueva coordenada x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Devuelve la coordenada y
     *
     * @return La coordenada y
     */
    public int getY() {
        return y;
    }

    /**
     * Cambia la coordenada y
     *
     * @param y La nueva coordenada y
     */
    public void setY(int y) {
        this.y = y;
    }
}


