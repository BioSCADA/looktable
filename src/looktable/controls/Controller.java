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
package looktable.controls;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.Timer;
 
 
public class Controller {
    private ArrayList<String> mouseMoved = new ArrayList<>();

    /**
     * @return the mouseMoved
     */
    public ArrayList<String> getMouseMoved() {
        return mouseMoved;
    }
    private int delay = 10; 
    private String component;
    private Timer timer;
    private float distance = 0; 
    private float inativeCount = 0; 
    long startTime = System.currentTimeMillis();
    long stopTime = System.currentTimeMillis();
    public Controller(){
    
    }
    
    public Controller(String component) {
        if (component == null) {  
        } else {
 
            timer = new Timer(delay, new ActionListener() {

                private Point lastPoint = MouseInfo.getPointerInfo().getLocation();
 
                @Override
                public synchronized void actionPerformed(ActionEvent evt) {

                    Point point = MouseInfo.getPointerInfo().getLocation();
                     
                    if (!point.equals(lastPoint)) {
                        setDistance((float) (getDistance() + Math.sqrt(Math.pow((point.getX() - lastPoint.getX()), 2) + Math.pow((point.getY() - lastPoint.getY()), 2))));            
                    } else {
                        setInativeCount(getInativeCount() + 1);
                    } 
                     try {
                         stopTime = System.currentTimeMillis();
                         long elapsedTimeMillis = stopTime-startTime; 
                         appendMoved(new Timestamp(new Date().getTime()).toString() + "\t" + (int)point.getX() + "\t" + (int)point.getY() + "\t" + point.equals(lastPoint) + "\t" +getDistance()+ "\t" +elapsedTimeMillis);
           
                       // System.out.println(new Timestamp(evt.getWhen()).toString() + "\t"  + point.getX() + "\t" + point.getY() + "\t" +getDistance());

                    } catch (Exception ex) {
                        System.out.println("Controller ERROR: "+ex.getMessage());
                    }
                    lastPoint = point;
                }

                private void appendMoved(String string) {
                     getMouseMoved().add(string);
                }
            });
        }
    }
 
    public String getComponent() {
        return component;
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    /**
     * @return the distance
     */
    public float getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(float distance) {
        this.distance = distance;
    }

    /**
     * @return the inativeCount
     */
    public float getInativeCount() {
        return inativeCount;
    }

    /**
     * @param inativeCount the inativeCount to set
     */
    public void setInativeCount(float inativeCount) {
        this.inativeCount = inativeCount;
    }

    public void clear() {
        mouseMoved.clear();
        setInativeCount(0);
        setDistance(0);
        
    }
   

    
}
