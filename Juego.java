import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

/**
 * Clase juego, donde esta el codigo que permite la existencia del juego
 */
public class Juego {
    Scanner inputfile;

    /**
     * Crea un nuevo juego
     *
     * @param path El nombre del archvio a procesar
     */
    public Juego(String path){
        System.out.println("Archivo "+path+" cargado");
        this.inputfile = null;

        try {
            inputfile = new Scanner(new File(path));
        } catch (IOException e) {
            System.out.println("Archivo no encontrado");
        }
    }

    /**
     * Metodo para jugar
     *
     * @throws InterruptedException Activado cuando una operacion esta esperando en segundos reales y es interrumpida abruptamente
     */
    public void jugar() throws InterruptedException {
        // ----- variables -------
        Scanner in;
        assert inputfile != null;
        Tablero tablero = new Tablero(inputfile);
        Pacman pacman = null;
        boolean gano = false;
        boolean salir = false;
        // ----- variables -------

        // ----- Procesamiento de los datos inciales -------
        String s = inputfile.nextLine();
        in = new Scanner(s);
        if(in.next().charAt(0) == 'P') { //Procesamiento de PacMan
            int x = in.nextInt();
            int y = in.nextInt();
            pacman = new Pacman(1, new Posicion(x, y), 0);
            tablero.setCelda(pacman.posicion.getX(), pacman.posicion.getY(), pacman);
        }
        // ----- Procesamiento de los datos inciales -------

        // ----- Creacion del BFS -----
        assert pacman != null;
        new BFS(tablero.numFilas, tablero.numCols, tablero);
        Stack<Posicion> mov =  BFS.bfs(tablero, pacman.posicion);
        BFS.imprimirPadre();
        BFS.imprimirDistancias();
        Stack<Posicion> moverse = BFS.pintarCamino(tablero, mov);
        // ----- Creacion del BFS -----

        // ----- Primer turno -----
        for(int hh = 0; hh<30; hh++) System.out.println();
        tablero.dibujarTablero(tablero, true);
        // ----- Primer turno -----

        // ----- Inicio del juego -------
        while(!salir){
            for(int hh = 0; hh<30; hh++) System.out.println();

            System.out.println();

            Posicion siguiente = moverse.pop();

            int nX = siguiente.getX();
            int nY = siguiente.getY();

            Celda next = tablero.getCelda(nX, nY);

            if(next.letra == 'O'){
                salir = true;
                gano = true;
            }

            tablero.setCelda(pacman.posicion.getX(), pacman.posicion.getY(), ' ');
            if(!(next.letra == 'O')) tablero.setCelda(pacman.posicion.getX(), pacman.posicion.getY(), (String) null);
            tablero.setCelda(nX, nY, pacman);
            pacman.posicion.setX(nX);
            pacman.posicion.setY(nY);

            int time = 500;

            if(gano) tablero.dibujarTablero(true);
            else tablero.dibujarTablero(tablero, true);

            TimeUnit.MILLISECONDS.sleep(time);

            System.out.println();

            if(gano) System.out.println(Colors.ANSI_PURPLE+Colors.ANSI_BOLD+"Ganaste "+Colors.ANSI_RESET+Colors.ANSI_GREEN+Colors.ANSI_BOLD+"el"+Colors.ANSI_RESET+Colors.ANSI_CYAN+Colors.ANSI_BOLD+" juego!"+Colors.ANSI_RESET);
        }

        System.out.println(Colors.ANSI_CYAN_BACKGROUND+Colors.ANSI_BLACK+"  Fin del juego  "+Colors.ANSI_RESET);
    }
}

/**
 * Clase color, para usar al imprimir
 */
//TODO COLOR A PERDER, GANAR, PERDER TURNO
class Colors{
//Colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

//Backgrounds
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

//Textures
    public static final String ANSI_BOLD = "\u001b[1m";
}