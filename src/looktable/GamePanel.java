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
package looktable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import looktable.controls.Controller;
import looktable.models.Datamodel;
import looktable.models.GameModel;

/**
 *
 * @author schmaedech
 */
public class GamePanel extends javax.swing.JPanel {

    MainFrame aThis;
    HashMap<String, Integer> hashGrid = new HashMap<>();
    int counter = 1;
    int lastMoveX = 0;
    int lastMoveY = 0;
    int curValue = 0;
    JButton[] labels;
    int rxc = 0;
    long startTime = System.currentTimeMillis();
    long stopTime = System.currentTimeMillis();
    long lastElapsedTimeMillis = (long) ((stopTime - startTime));
    SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
    boolean inGame = false;
    Controller mo = new Controller("LookTable");
    private Color color1 = new Color(78,78,78);
   
    private Color color2 = color1.darker();
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize().getSize();

    public GamePanel(MainFrame aThis) {
        initComponents();
        this.aThis = aThis;
        TimeController timeController = new TimeController();
        timeController.start();

        mo.start();
        jScrollPane1.getViewport().setOpaque(false);
        // throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!isOpaque()) {
            super.paintComponent(g);
            return;
        }
        Graphics2D g2d = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();

        // Paint a gradient from top to bottom
        GradientPaint gp = new GradientPaint(
                0, 0, color1,
                0, h, color2);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        setOpaque(false);
        super.paintComponent(g);
        setOpaque(true);

    }

    public void updatePanel() {

        if (inGame) {
            if (GameModel.showTime) {

                stopTime = System.currentTimeMillis();
                long elapsedTimeMillis = (long) ((stopTime - startTime) / 1000F);

                // System.out.println(String.format("%02d:%02d", elapsedTimeMillis/60, elapsedTimeMillis%60));
                //  System.out.println("Tempo: " + String.format("%02d:%02d", elapsedTimeMillis / 60, elapsedTimeMillis % 60));
                aThis.setJlTime("Tempo: " + String.format("%02d:%02d", elapsedTimeMillis / 60, elapsedTimeMillis % 60));
            } else {
                aThis.setJlTime(" ");
            }
            if (GameModel.showLastNumber) {
                aThis.setJlLast("Última: " + (counter - 1));
                // System.out.println("Última: " + (counter - 1));
            } else {
                aThis.setJlLast(" ");
            }
//            if (GameModel.isRanMode) {
//            } else {
//            }
             
        }
      repaint();
         revalidate();
    }

    public void createTable() {

        clear();
        //define ArrayList to hold Integer objects         
        ArrayList<Integer> numbers = new ArrayList<>();
        int jsRows = Integer.parseInt(GameModel.jsRow.getModel().getValue().toString());
        int jsCols = Integer.parseInt(GameModel.jsCol.getModel().getValue().toString());
        rxc = jsRows * jsCols;
        for (int i = 0; i < rxc; i++) {
            numbers.add(i + 1);
        }
        Collections.shuffle(numbers);
        labels = new JButton[numbers.size()];
        GridLayout experimentLayout = new GridLayout(jsRows, jsCols);
        jpNumberGrid.setLayout(experimentLayout);

        for (int i = 0; i < numbers.size(); i++) {
            String strForm = String.format("%02d", numbers.get(i));
            labels[i] = new JButton(strForm);
            configureGrid(labels[i], strForm, jsRows);
            hashGrid.put(strForm, i);
        }

        counter = 1;
        startTime = System.currentTimeMillis();
        inGame = true;

        repaint();
        revalidate();
        mo.start();
    }

    public void reGrid() {
        hashGrid.clear();
        jpNumberGrid.removeAll();
        //define ArrayList to hold Integer objects         
        ArrayList<Integer> numbers = new ArrayList<>();
        int jsRows = Integer.parseInt(GameModel.jsRow.getModel().getValue().toString());
        int jsCols = Integer.parseInt(GameModel.jsCol.getModel().getValue().toString());
        rxc = jsRows * jsCols;
        for (int i = 0; i < rxc; i++) {
            numbers.add(i + 1);
        }
        Collections.shuffle(numbers);
        labels = new JButton[numbers.size()];
        GridLayout experimentLayout = new GridLayout(jsRows, jsCols);
        jpNumberGrid.setLayout(experimentLayout);

        for (int i = 0; i < numbers.size(); i++) {
            String strForm = String.format("%02d", numbers.get(i));
            labels[i] = new JButton(strForm);
            configureGrid(labels[i], strForm, jsRows);
            hashGrid.put(strForm, i);
        }
 
    }

    private void saveLogger() throws InterruptedException {

        inGame = false;
        jpNumberGrid.removeAll();
        jpNumberGrid.setLayout(new FlowLayout());
        jlLogo.setBackground(new java.awt.Color(255, 255, 255));
        jlLogo.setFont(new java.awt.Font("Myriad Web Pro", 0, 16)); // NOI18N
        jlLogo.setForeground(new java.awt.Color(0, 100, 0));
        jlLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ajax-loader.gif"))); // NOI18N
        jlLogo.setText("Gravando...!");
        jlLogo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jlLogo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jpNumberGrid.add(jlLogo);
        Thread worker = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            Datamodel.saveToFile();
                        }
                    });
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        };
        worker.start();

        jlLogo.setBackground(new java.awt.Color(255, 255, 255));
        jlLogo.setFont(new java.awt.Font("Myriad Web Pro", 0, 16)); // NOI18N
        jlLogo.setForeground(new java.awt.Color(0, 100, 0));
        jlLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/logo.png"))); // NOI18N
        jlLogo.setText("<html>Parabéns e <br> Obrigado pela sua participação!</html>");
        jlLogo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jlLogo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jpNumberGrid.add(jlLogo);
        jpNumberGrid.revalidate();
        jpNumberGrid.repaint();

    }

    private void clear() {
        Datamodel.clear();
        mo.clear();
        hashGrid.clear();
        jpNumberGrid.removeAll();
        counter = 1;
    }

    private void mouseClicked(java.awt.event.MouseEvent evt) {
        try {
            stopTime = System.currentTimeMillis();
            //long elapsedTimeMillis = (long) ((stopTime - startTime) / 1000F); 
            long elapsedTimeMillis = (long) ((stopTime - startTime));
             JButton tmp = (JButton) evt.getSource();
            String tmpStr = tmp.getBounds().x + "|" +tmp.getBounds().y+ "|" +tmp.getBounds().width+ "|" +tmp.getBounds().height;
            Datamodel.appendClick(new Timestamp(evt.getWhen()).toString() + "\t" + evt.getXOnScreen() + "\t" + evt.getYOnScreen() + "\t" + getCurValue() + "\t" + Datamodel.wrongMove + "\t" + evt.getClickCount() + "\t" + elapsedTimeMillis + "\t" + (elapsedTimeMillis - lastElapsedTimeMillis) + "\t" + tmpStr);
            lastElapsedTimeMillis = elapsedTimeMillis;
            // System.out.println(new Timestamp(evt.getWhen()).toString() + "\t" + evt.getXOnScreen() + "\t" + evt.getYOnScreen() + "\t" + getCurValue()+ "\t" + Datamodel.wrongMove + "\t" + evt.getClickCount() + "\t" + elapsedTimeMillis);
           
        } catch (Exception ex) {
            System.out.println("MouseClicked ERROR: " + ex.getMessage());
        }
        updatePanel();
        repaint();
         revalidate();
    }

    private void gridClicked(java.awt.event.MouseEvent evt) {

        //  System.out.println(new Timestamp(evt.getWhen()).toString() + "|click|" + evt.getXOnScreen() + "|" + evt.getYOnScreen());
        JButton tmp = (JButton) evt.getSource();
        setCurValue(Integer.parseInt(tmp.getText()));

        if (counter == getCurValue()) {
            Integer index = hashGrid.get(tmp.getText());
            if (!GameModel.isRanMode) {
                labels[index].setBackground(new java.awt.Color(255, 255, 255, 50));
                labels[index].setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                labels[index].setContentAreaFilled(false);
                labels[index].setOpaque(true);
                labels[index].setVerticalAlignment(javax.swing.SwingConstants.CENTER);
                labels[index].setVerticalTextPosition(javax.swing.SwingConstants.CENTER);

            } else {
                reGrid();
            }

            counter++;

            if (counter == rxc + 1) { //se o jogo acabou
                gameOver();
            }
           
        } else {
            Datamodel.wrongMove++;
        }
        if (GameModel.showLastNumber) {
            System.out.println("Última: " + (counter - 1));
        }
        //  System.out.println(tmp.getText());  
        repaint();
         revalidate();
        mouseClicked(evt);
         
    }

    private void gameOver() {
        stopTime = System.currentTimeMillis();
        long elapsedTimeMillis = stopTime - startTime;
        // Get elapsed time in seconds
        //  Datamodel.elapsedTime = (long) (elapsedTimeMillis / 1000F);
        // Get elapsed time in mili
        Datamodel.elapsedTime = (long) (elapsedTimeMillis);
        //  Datamodel.wrongMove++;

        Datamodel.screenResolution = dim.getWidth() + "," + dim.getHeight();
        dim = this.getSize();
        Datamodel.appModel = dim.getWidth() + "," + dim.getHeight();
        dim = jScrollPane1.getViewport().getSize();
        Datamodel.stimulusModel = dim.getWidth() + "," + dim.getHeight();
        Datamodel.mouseMoved = mo.getMouseMoved();
        Datamodel.distance = mo.getDistance();
        Datamodel.inativeCount = mo.getInativeCount();
        mo.stop();
        try {
            saveLogger();
        } catch (InterruptedException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        // jbCreateGame.setSelected(false);
    }

    private void configureGrid(JButton label, String value, int size) {
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setToolTipText(value);
        // label.setOpaque(true);
        label.setBackground(new java.awt.Color(255, 255, 255));
        //  label.setBackground(new java.awt.Color(0, 255, 0, 80));  
        label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        label.setContentAreaFilled(false);
        label.setOpaque(true);
        label.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        label.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        int fontSize = (int) Math.round((dim.getHeight() / (size * 2)));
        //System.out.println("MouseClicked ERROR: " + fontSize);
        label.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, fontSize)); // NOI18N
        label.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(150, 200, 255, 60)));

        label.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                gridClicked(evt);
            }
        });

        jpNumberGrid.add(label);
        repaint();
        revalidate();
    }

    /**
     * @return the curValue
     */
    public int getCurValue() {
        return curValue;
    }

    /**
     * @param curValue the curValue to set
     */
    public void setCurValue(int curValue) {
        this.curValue = curValue;
    }

    class TimeController implements Runnable {

        Thread thread;

        public TimeController() {
        }

        @Override
        public void run() {
            while (thread != null) {
               updatePanel();
//                try {
//                  //  Thread.sleep(1);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
//                }

            }
        }

        public void start() {
            try {
                thread = new Thread(this);
                thread.setPriority(1);
                thread.setDaemon(true);
                thread.setName("capture");
                thread.start();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        public void stop() {
            thread = null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jpNumberGrid = new javax.swing.JPanel();
        jlLogo = new javax.swing.JLabel();

        setBackground(new java.awt.Color(63, 63, 70));

        jScrollPane1.setBackground(new java.awt.Color(63, 63, 70));

        jpNumberGrid.setBackground(new java.awt.Color(53, 52, 51));

        jlLogo.setBackground(new java.awt.Color(231, 232, 233));
        jlLogo.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        jlLogo.setForeground(new java.awt.Color(231, 232, 233));
        jlLogo.setText("Clique em Começar para iniciar um novo treino!");
        jpNumberGrid.add(jlLogo);

        jScrollPane1.setViewportView(jpNumberGrid);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jlLogo;
    private javax.swing.JPanel jpNumberGrid;
    // End of variables declaration//GEN-END:variables
}
