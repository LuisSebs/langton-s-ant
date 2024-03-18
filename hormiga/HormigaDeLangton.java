package hormiga;

import processing.core.PApplet;

public class HormigaDeLangton extends PApplet{
    /** Cantidad de celdas a lo ancho*/
    int ancho = 101;
    /** Cantidad de celdas a lo alto */
    int alto = 101;
    /** Tamaño de cada celda */
    int celda = 5;
    /** Posicion inicial de la hormiga en el eje X*/
    int posXHormiga = (int) (ancho/2);
    /** Posicion inicial de la hormiga en el eje Y*/
    int posYHormiga = (int) (alto/2);
    /** Orientacion de la hormiga
     *   -----------
     *  |   | 0 |   |
     *  |-----------|
     *  | 3 |   | 1 |
     *  |-----------|
     *  |   | 2 |   |
     *   -----------
     */
    int direccion = 1;
    /**
     * Modelo de la hormiga de langton
     */
    ModeloHormiga modelo;

    @Override
    public void setup(){
        /** cuadros por segundo */
        frameRate(60);
        /** color del fondo */
        background(200);
        /** inicializacion del modelo */
        this.modelo = new ModeloHormiga(ancho, alto, new Hormiga(posXHormiga, posYHormiga, direccion));
    }

    @Override
    public void settings(){
        /** tamaño del lienzo */
        size(ancho * celda, alto * celda);
    }

    @Override
    public void draw(){
        /**
         * Esta funcion se ejecuta siempre
         */
        for(int i = 0; i < ancho; i++){
            for(int j = 0; j < alto; j++){
                Celda c = this.modelo.mundo[i][j];
                /** Si en la celda esta la hormiga */
                if(c.hormiga){
                    fill(252,3,3);
                    stroke(252,3,3);
                }else{
                    /** Si el color de la celda es blanca */
                    if(c.color == 0){
                        fill(255,255,255);
                        stroke(255,255,255);
                    /** Si el color de la celda es negra */
                    }else{
                        fill(0,0,0);
                        stroke(0,0,0);
                    }
                }
                /** Dibujamos la celda */
                rect(i * celda, j*celda, celda, celda);
            }
        }
        /** Realizamos el siguiente paso del modelo */
        this.modelo.moverHormiga();
    }

    /**
     * Celda
     */
    class Celda{
        /** Indica si la hormiga esta en la celda */
        boolean hormiga = false;
        int color = 0; // 0: blanco, 1: negro

        /**
         * Modifica el atributo hormiga en true
         */
        public void ponerHormiga(){
            this.hormiga = true;
        }

        /**
         *  Modifica el atributo hormiga en false 
         */
        public void quitarHormiga(){
            this.hormiga = false;
        }

        /**
         * Cambia de color la celda
         * si es 0 lo cambia a 1, 
         * si es 1 lo cambia a 0
         */
        public void cambiarColor(){
            if (this.color == 0){
                this.color = 1;
            }else{
                this.color = 0;
            }
        }
    }

    /**
     * Hormiga
     */
    class Hormiga{
        /** Posicion de la hormiga en la coordenada x */
        int x;
        /** Posicion de la hormiga en la coordenada y */
        int y;
        /** Orientacion de la hormiga (hacia donde esta mirando)
         *   -----------
         *  |   | 0 |   |
         *  |-----------|
         *  | 3 |   | 1 |
         *  |-----------|
         *  |   | 2 |   |
         *   -----------
         */
        int direccion;

        /**
         * Constructor de la hormiga
         * @param x posicion inicial de la hormiga en x
         * @param y posicion inicial de la hormiga en y
         * @param direccion orientacion de la hormiga
         * (hacia donde esta mirando)
         */
        public Hormiga(int x, int y, int direccion){
            this.x = x;
            this.y = y;
            this.direccion = direccion;
        }

        /**
         * Orienta a la hormiga a la izquierda 90 grados
         */
        public void giraIzquierda(){
            switch (direccion) {
                case 0:
                    direccion = 3;
                    break;
                case 1:
                    direccion = 0;
                    break;
                case 2:
                    direccion = 1;
                    break;
                case 3:
                    direccion = 2;
                    break;                    
            }
        }

        /**
         * Orienta a la hormiga a la derecha 90 grados
         */
        public void giraDerecha(){
            switch (direccion) {
                case 0:
                    direccion = 1;
                    break;
                case 1:
                    direccion = 2;
                    break;
                case 2:
                    direccion = 3;
                    break;
                case 3:
                    direccion = 0;
                    break;                    
            }
        }

        /**
         * Avanza a la hormiga una casilla,
         * cambia la posicion de la hormiga.
         */
        public void avanza(){
            switch (direccion) {
                case 0:
                        y-=1;
                    break;
                case 1:
                        x+=1;             
                    break;
                case 2:
                        y+=1;
                    break;
                case 3:
                        x-=1;
                    break;
            }
        }
    }

    /**
     * Modelo de la Hormiga en el mundo
     */
    class ModeloHormiga extends Thread{
        /** Ancho del mundo */
        public int ancho;
        /** Alto del mundo */
        public int alto;
        /** Pasos ejecutados */
        public int pasos = 0;
        /** Mundo */
        public Celda[][] mundo;
        /** Hormiga del mundo */
        public Hormiga hormiga;

        /**
         * Constructor 
         * @param ancho ancho del mundo
         * @param alto alto del mundo
         * @param hormiga hormiga
         */
        public ModeloHormiga(int ancho, int alto, Hormiga hormiga){
            this.ancho = ancho;
            this.alto = alto;
            this.mundo = new Celda[ancho][alto];
            for (int i = 0; i < ancho; i++ ){
                for(int j = 0; j < alto; j++){
                    this.mundo[i][j] = new Celda();
                }
            }
            this.hormiga = hormiga;
            this.mundo[this.hormiga.x][this.hormiga.y].ponerHormiga();
        }

        /**
         * Mueve a la hormiga
         */
        public void moverHormiga(){
            if (pasos < 11000){
                int posX = this.hormiga.x;
                int posY = this.hormiga.y;
                Celda celdaHormiga = this.mundo[posX][posY];
                if (celdaHormiga.color == 0){
                    celdaHormiga.cambiarColor();
                    hormiga.giraIzquierda();
                    hormiga.avanza();
                }else{
                    celdaHormiga.cambiarColor();
                    hormiga.giraDerecha();
                    hormiga.avanza();
                }
                
                celdaHormiga.quitarHormiga();
                int newPosX = this.hormiga.x;
                int newPosY = this.hormiga.y;
                this.mundo[newPosX][newPosY].ponerHormiga();
                pasos++;
            }
        }
        
    }

    public static void main(String[] args) {
        PApplet.main(new String[] {"hormiga.HormigaDeLangton"});
    }
}