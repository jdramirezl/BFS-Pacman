

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Clase Main para ejecutar el codigo
 */
public class Main {
    /**
     * El metodo main, donde se corre inicialmente el codigo
     *
     * @param args Argumentos estandar
     * @throws InterruptedException Activado cuando una operacion esta esperando en segundos reales y es interrumpida abruptamente
     */
    public static void main(String[] args) throws InterruptedException{
        String path = "laberinto.txt";
        Juego play = new Juego(path);
        play.jugar();
    }
}
