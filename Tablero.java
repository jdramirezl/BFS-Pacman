import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * CLase para crear los objetos Tablero, es decir para crear un tablero de celdas.
 */
public class Tablero {
    final int numFilas;
    final int numCols;
    private Celda[][] tablero;
    Posicion meta;

    /**
     * Crea un nuevo tablero.
     *
     * @param inputfile Un scanner que contiene en su constructor el archivo a procesar
     */
    public Tablero(Scanner inputfile) {
        assert inputfile != null;

        this.numFilas = inputfile.nextInt();
        this.numCols = inputfile.nextInt();

        System.out.println("Tablero creado");
        this.tablero = new Celda[numFilas][numCols];

        inputfile.nextLine();

        for (int i = 0; i < numFilas; i++) {
            String line = inputfile.nextLine();
            for (int j = 0; j < numCols; j++) {
                char x = line.charAt(j);
                if (x == '*') {
                    tablero[i][j] = new Celda(true, false, '*');
                } else if (x == ' ' ) {
                    tablero[i][j] = new Celda(false, false, x);
                } else if (x == 'O') {
                    tablero[i][j] = new Celda(false, true, 'O');
                    meta = new Posicion(i, j);
                }
            }
        }
    }

    /**
     * Metodo para poner un Caracter en una celda de coordenadas x, y
     *
     * @param x   Coordenada x
     * @param y   Coordenada y
     * @param per El personaje/caracter a posicionar
     */
    public void setCelda(int x, int y, Caracter per){
        this.tablero[x][y].personaje = per;
        this.tablero[x][y].setCara(per);
    }

    /**
     * Metodo para poner una representacion (Es decir, una letra) en una celda de coordenadas x, y
     *
     * @param x              the x
     * @param y              the y
     * @param representacion El caracter/letra a poner
     */
    public void setCelda(int x, int y, char representacion){
        this.tablero[x][y].letra = representacion;
    }

    /**
     * Set celda.
     *
     * @param x     the x
     * @param y     the y
     * @param color the color
     */
    public void setCelda(int x, int y, String color){
        this.tablero[x][y].color = color;
    }

    /**
     * Devuelve un objeto celda de una coordenada del laberinto
     *
     * @param x Coordenada x
     * @param y Coordenada y
     * @return La celda de la posicion proporcionada
     */
    public Celda getCelda(int x, int y){
        return tablero[x][y];
    }

    /**
     * Metodo para imprimir el tablero con ciertos colores de acuerdo a la letra
     */
    public void dibujarTablero(){
        for(int i = 0; i<numFilas; i++){
            for (int j = 0; j<numCols; j++){
                char x = tablero[i][j].caracterCelda();

                if (x == '^') System.out.print(Colors.ANSI_YELLOW+Colors.ANSI_BOLD+x+Colors.ANSI_RESET);

                else PrintExtras(x);
            }
            System.out.println();
        }
    }

    /**
     * Metodo para dibujar el tablero, especial pues es para imprimir en rojo si pierde o azul si gana
     *
     * @param gano Verdadero o falso si gano
     */
    public void dibujarTablero(boolean gano){
        for(int i = 0; i<numFilas; i++){
            for (int j = 0; j<numCols; j++){
                char x = tablero[i][j].caracterCelda();

                if (x == '^'){
                    if(gano) System.out.print(Colors.ANSI_YELLOW_BACKGROUND+Colors.ANSI_BLACK+Colors.ANSI_BOLD+x+Colors.ANSI_RESET);
                    else System.out.print(Colors.ANSI_RED_BACKGROUND+Colors.ANSI_WHITE+Colors.ANSI_BOLD+x+Colors.ANSI_RESET);
                }
                else PrintExtras(x);
            }
            System.out.println();
        }
    }

    /**
     * Dibujar tablero.
     *
     * @param tablero the tablero
     * @param camino  the camino
     */
    public void dibujarTablero(Tablero tablero, boolean camino){
        for(int i = 0; i<numFilas; i++){
            for (int j = 0; j<numCols; j++){
                char x = tablero.getCelda(i, j).caracterCelda();

                if (x == '^' && tablero.getCelda(i, j).color != null){
                    if(camino) System.out.print(Colors.ANSI_YELLOW+Colors.ANSI_BOLD+x+Colors.ANSI_RESET);
                    else System.out.print(tablero.getCelda(i, j).color);
                }
                else PrintExtras2(x, tablero.getCelda(i, j));
            }
            System.out.println();
        }
    }

    /**
     * Metodo auxiliar para la impresion del tablero
     *
     * @param x La letra a imprimir
     */
    public void PrintExtras(char x) {
        if(x == '*') System.out.print(Colors.ANSI_BLUE+x+Colors.ANSI_RESET);
        else if(x == 'O') System.out.print(Colors.ANSI_RED+Colors.ANSI_BOLD+x+Colors.ANSI_RESET);
        else if(x == ' ')  System.out.print(x);
    }

    /**
     * Metodo auxiliar para la impresion del tablero
     *
     * @param x La letra a imprimir
     * @param cel La celda a pintar
     */
    public void PrintExtras2(char x, Celda cel) {
        if(x == '*') System.out.print(Colors.ANSI_BLUE+x+Colors.ANSI_RESET);
        else if(x == 'O') System.out.print(Colors.ANSI_RED+Colors.ANSI_BOLD+x+Colors.ANSI_RESET);
        else if(x == ' '){
            if(cel.color != null) System.out.print(cel.color);
            else System.out.print(x);
        }
    }
}

/**
 * Clase para realizar el algoritmo de BFS
 */
class BFS {
    static Tablero tablero;
    private static int[][] d;
    private static Posicion[][] padre;
    static int[] xCambio = {1, 0, 0, -1};
    static int[] yCambio = {0, 1, -1, -0};

    /**
     * Crea un nuevo objeto BFS que realiza el algoritmo sobre un tablero
     *
     * @param n       Numero de filas
     * @param s       Numero de columnas
     * @param tablero El tablero a procesar
     */
    public BFS(int n, int s, Tablero tablero){
        BFS.d = new int[n][s];
        BFS.padre = new Posicion[n][s];
        BFS.tablero = tablero;
    }

    /**
     * Metodo que realizar el bfs sobre un tablero y devuelve en un Stack los pasos que debe seguir el pacman para ganar el juego
     *
     * @param a      El tablero
     * @param fuente La posicion donde esta pacman
     * @return Un stack con los pasos que debe seguir el pacman para ganar el juego
     * @throws InterruptedException Activado cuando una operacion esta esperando en segundos reales y es interrumpida abruptamente
     */
    public static Stack<Posicion> bfs(Tablero a, Posicion fuente) throws InterruptedException {
        for (int i = 0; i < d.length; ++i){
            for(int j = 0; j<d[0].length; j++){
                d[i][j] = -1;
                padre[i][j] = null;
            }
        }

        Queue<Posicion> q = new LinkedList<>();
        q.add(new Posicion(fuente.getX(), fuente.getY()));
        d[fuente.getX()][fuente.getY()] = 0;
        padre[fuente.getX()][fuente.getY()] = new Posicion(0, 0);

        //Primera Pintada
        tablero.dibujarTablero();
        TimeUnit.SECONDS.sleep(1);


        while(q.size() > 0){
            Posicion cur = q.element();

            // ----- Pintar cada casilla e imprimirla -----
            char ini = tablero.getCelda(cur.getX(), cur.getY()).letra;
            String ini2 = Colors.ANSI_RED_BACKGROUND+Colors.ANSI_BLACK+ini+Colors.ANSI_RESET;
            tablero.setCelda(cur.getX(), cur.getY(), ini2);
            tablero.dibujarTablero(tablero, false);
            TimeUnit.MILLISECONDS.sleep(100);
            // ----- Pintar cada casilla e imprimirla -----

            q.remove();
            for(int i = 0; i<4; i++){
                int nX = cur.getX() + xCambio[i];
                int nY = cur.getY() + yCambio[i];
                char next = a.getCelda(nX, nY).letra;

                if(next == '*') continue;

                // ----- Pintar cada casilla -----
                ini2 = Colors.ANSI_RED_BACKGROUND+Colors.ANSI_BLACK+next+Colors.ANSI_RESET;
                tablero.setCelda(nX, nY, ini2);
                // ----- Pintar cada casilla -----


                if(d[nX][nY] == -1){
                    d[nX][nY] = d[cur.getX()][cur.getY()]+1;
                    padre[nX][nY] = new Posicion(cur.getX(), cur.getY());
                    q.add(new Posicion(nX, nY));
                }
                else if (d[nX][nY] > d[cur.getX()][cur.getY()]+1){
                    d[nX][nY] = d[cur.getX()][cur.getY()]+1;
                    padre[nX][nY] = new Posicion(cur.getX(), cur.getY());
                    q.add(new Posicion(nX, nY));
                }
            }
        }

        Stack<Posicion> moverse = new Stack<>();
        Posicion meta = tablero.meta, actual = meta;


        for(int i = 0; i<d[meta.getX()][meta.getY()]; i++){
            moverse.push(actual);
            actual = padre[actual.getX()][actual.getY()];
        }

        System.out.println("BFS terminado!");
        TimeUnit.SECONDS.sleep(5);

        return moverse;
    }

    /**
     * Dado un stack de pasos a seguir (Posiciones) pinta este camino
     *
     * @param tablero El tablero a pintar
     * @param pos     Los pasos/posiciones a pintar
     * @return the stack
     * @throws InterruptedException Activado cuando una operacion esta esperando en segundos reales y es interrumpida abruptamente
     */
    public static Stack<Posicion> pintarCamino(Tablero tablero, Stack<Posicion> pos) throws InterruptedException {
        Stack<Posicion> movimientos = new Stack<>();
        Posicion[] a = new Posicion[pos.size()];

        int hh= 0;
        while(!pos.empty()){
            a[hh] = pos.pop();
            hh++;
        }

        for(Posicion x : a){
            String color = Colors.ANSI_GREEN_BACKGROUND+" "+Colors.ANSI_RESET;
            tablero.setCelda(x.getX(), x.getY(), color);
            tablero.dibujarTablero(tablero, true);
            TimeUnit.MILLISECONDS.sleep(250);
        }

        for(int i = 0; i<tablero.numFilas; i++){
            for(int j = 0; j<tablero.numCols; j++){
                tablero.setCelda(i, j, (String) null);
            }
        }

        for(Posicion x : a){
            String color = Colors.ANSI_GREEN_BACKGROUND+" "+Colors.ANSI_RESET;
            tablero.setCelda(x.getX(), x.getY(), color);
        }

        for(int i = a.length-1; i>=0; i--) movimientos.push(a[i]);

        System.out.println("Recorrido elegido terminado!");
        TimeUnit.SECONDS.sleep(5);

        return movimientos;

    }

    /**
     * Metodo que imprime todas las distancias del tablero desde el pacman
     */
    public static void imprimirDistancias(){
        System.out.println("distancias");
        for (int[] ints : d) {
            for (int j = 0; j < d[0].length; j++) {
                String s;

                if(ints[j] == -1) s = (Colors.ANSI_BLUE+ints[j]+Colors.ANSI_RESET);
                else s = Colors.ANSI_BLACK + ints[j] + Colors.ANSI_RESET;

                System.out.printf("%12s",s);
            }
            System.out.println();
        }
    }

    /**
     * Metodo para imprimir todas las coordenadas padres del mejor camino
     */
    public static void imprimirPadre(){
        System.out.println("padre");
        for(int i = 0; i<d.length; i++){
            for(int j = 0; j<d[0].length; j++){
                String s;

                if(padre[i][j] == null)  s = ("[--]");
                else if(padre[i][j].getX() == 0) s = Colors.ANSI_BLUE+"   [" + padre[i][j].getX() + "," + padre[i][j].getY() + "]"+Colors.ANSI_RESET;
                else s = "[" + padre[i][j].getX() + "," + padre[i][j].getY() + "]";

                System.out.printf("%8s", s);
            }
            System.out.println();
        }
    }
}

