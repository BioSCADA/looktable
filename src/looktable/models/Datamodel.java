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
 
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Protocolo mouseMoviment: posição do ponteiro do mouse na tela a cada 100 ms +
 * {3} a grade mouseClicked: posição do clic do mouse na tela + numero clicado +
 * {3} a grade elapsedTime: tempo total de jogo wrongMove: movimentos errados
 * completed: jogo completado distance: distância euclidiana percorrida pelo
 * ponteiro do mouse na tela a cada 100 ms
 *
 * @author schmaedech
 */
public class Datamodel {

    private static Datamodel instance = new Datamodel();
    static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
    public static ArrayList<String> mouseMoved = new ArrayList<>();
    public static ArrayList<String> mouseClicked = new ArrayList<>();
    public static long elapsedTime = 0;
    public static int wrongMove = 0;
    public static float distance = 0;
    public static float inativeCount = 0;
    public static String screenResolution;
    public static String appModel;
    public static String stimulusModel;

    public static Datamodel getInstance() {
        return instance;
    }

    public Datamodel() {
    }

    public static void clear() {
        mouseMoved.clear();
        mouseClicked.clear();
        elapsedTime = 0;
        wrongMove = 0;
        distance = 0;
        inativeCount = 0;
    }

    public static void appendMoved(String str) {
        Datamodel.mouseMoved.add(str);
        //System.out.println(str);
    }

    public static void appendClick(String str) {
        Datamodel.mouseClicked.add(str);
        // System.out.println(str);
    }

    public static void appendStats() {
    }

    public static void saveToFile() {
        saveMoved();
        saveClicked();
        saveStats();
    }

    private static boolean saveMoved() {
        FileWriter fWriter;
        BufferedWriter writer;
        try {
            String timeString = fmt.format(new Date());
            fWriter = new FileWriter("[move]" + timeString + ".txt", true);
            writer = new BufferedWriter(fWriter);
            writer.append("Timestamp\tX\tY\tLastEqual\tDistantance\tElapsedTimeMillis");
            writer.newLine();
            Iterator<String> itr = Datamodel.mouseMoved.iterator();
            while (itr.hasNext()) {
                String element = itr.next();
                writer.append(element);
                writer.newLine();
            }

            writer.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private static boolean saveClicked() {
        FileWriter fWriter;
        BufferedWriter writer;
        try {
            String timeString = fmt.format(new Date());
            fWriter = new FileWriter("[clicked]" + timeString + ".txt", true);
            writer = new BufferedWriter(fWriter);
            writer.append("Timestamp\tX\tY\tCurrentValue\tWrongMove\tMouseClickCount\tElapsedTimeMillis\tDiffElapsedTimeMillis\tClickBounds");
            writer.newLine();
            Iterator<String> itr = Datamodel.mouseClicked.iterator();
            while (itr.hasNext()) {
                String element = itr.next();
                writer.append(element);
                writer.newLine();
            }

            writer.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private static boolean saveStats() {
        FileWriter fWriter;
        BufferedWriter writer;
        try {
            String timeString = fmt.format(new Date());
            fWriter = new FileWriter("[stats]" + timeString + ".txt", true);
            writer = new BufferedWriter(fWriter);

            writer.append("WrongMove\tElapsedTime\tTotalMove\tTotalClicked\tDistance\tInativeCount\tScreenResolution\tAppModel\tStimulusModel");
            writer.newLine();
            writer.append(Datamodel.wrongMove + "\t" + Datamodel.elapsedTime + "\t" + Datamodel.mouseMoved.size() + "\t" + Datamodel.mouseClicked.size() + "\t" + Datamodel.distance + "\t" + Datamodel.inativeCount + "\t" + Datamodel.screenResolution + "\t" + Datamodel.appModel + "\t" + Datamodel.stimulusModel);
            writer.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
