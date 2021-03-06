/*
 * 
 * 
 * Copyright (C) 2012 Diego Schmaedech & Laboratório de Educação Cerebral
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package looktable.models;
 


import javax.swing.JSpinner;

/**
 * Protocolo
 *  mouseMoviment: posição do ponteiro do mouse na tela a cada 100 ms + {3} a grade
 *  mouseClicked: posição do clic do mouse na tela + numero clicado + {3} a grade
 *  elapsedTime: tempo total de jogo
 *  wrongMove: movimentos errados
 *  completed: jogo completado
 *  distance: distância euclidiana percorrida pelo ponteiro do mouse na tela a cada 100 ms
 * @author schmaedech
 */
public class GameModel {
    
    private static GameModel instance = new GameModel();
    
    
    public static boolean showLastNumber;
    public static boolean showTime; 
    public static boolean isRanMode;
    public static JSpinner jsCol;
    public static JSpinner jsRow;
    
    public static GameModel getInstance() {
            return instance;
    }
 
    public GameModel (){
    
    }
    public static void clear(){
        
    }
      
     
     
}
