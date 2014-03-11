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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author schmaedech
 */
public class MainFrame extends javax.swing.JFrame {

    private ConfigurationPanel configurationPanel = new ConfigurationPanel(this);
    private GamePanel gamePanel = new GamePanel(this);
    private static final int W = 2;
    private static final Color borderColor = new Color(53, 52, 51);

    public enum Side {

        NW_SIDE, N_SIDE, NE_SIDE, L_SIDE, R_SIDE, SW_SIDE, S_SIDE, SE_SIDE;
    }
    private JLabel left, right, top, bottom, topleft, topright, bottomleft, bottomright;

    class DragWindowListener extends MouseAdapter {

        private MouseEvent start;
        //private Point  loc;
        private Window window;

        @Override
        public void mousePressed(MouseEvent me) {
            start = me;
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            if (window == null) {
                window = SwingUtilities.windowForComponent(me.getComponent());
            }
            Point eventLocationOnScreen = me.getLocationOnScreen();
            window.setLocation(eventLocationOnScreen.x - start.getX(), eventLocationOnScreen.y - start.getY());
            //loc = window.getLocation(loc);
            //int x = loc.x - start.getX() + me.getX();
            //int y = loc.y - start.getY() + me.getY();
            //window.setLocation(x, y);
        }

        @Override
        public void mouseClicked(MouseEvent me) {
            if (me.getClickCount() == 2) {
                rezise();
            }

        }
    }

    class ResizeWindowListener extends MouseAdapter {

        private Rectangle startSide = null;
        private final JFrame frame;

        public ResizeWindowListener(JFrame frame) {
            this.frame = frame;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            startSide = frame.getBounds();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (startSide == null) {
                return;
            }
            Component c = e.getComponent();
            if (c == topleft) {
                startSide.y += e.getY();
                startSide.height -= e.getY();
                startSide.x += e.getX();
                startSide.width -= e.getX();
            } else if (c == top) {
                startSide.y += e.getY();
                startSide.height -= e.getY();
            } else if (c == topright) {
                startSide.y += e.getY();
                startSide.height -= e.getY();
                startSide.width += e.getX();
            } else if (c == left) {
                startSide.x += e.getX();
                startSide.width -= e.getX();
            } else if (c == right) {
                startSide.width += e.getX();
            } else if (c == bottomleft) {
                startSide.height += e.getY();
                startSide.x += e.getX();
                startSide.width -= e.getX();
            } else if (c == bottom) {
                startSide.height += e.getY();
            } else if (c == bottomright) {
                startSide.height += e.getY();
                startSide.width += e.getX();
            }
            frame.setBounds(startSide);
        }
    }
    private JPanel resizePanel = new JPanel(new BorderLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            //super.paintComponent(g);
            //Graphics2D g2 = (Graphics2D)g;
            Graphics2D g2 = (Graphics2D) g.create();
            int w = getWidth();
            int h = getHeight();
            g2.setPaint(Color.WHITE);
            g2.fillRect(0, 0, w, h);
            g2.setPaint(borderColor); //g2.setPaint(Color.RED);
            g2.drawRect(0, 0, w - 1, h - 1);

            //g2.setPaint(Color.WHITE);
            //g2.setPaint(new Color(0,0,0,0));
//             g2.drawLine(0,0,0,0);
//             g2.drawLine(w-1,0,w-1,0);
            g2.drawLine(0, 2, 2, 0);
            g2.drawLine(w - 3, 0, w - 1, 2);

            g2.clearRect(0, 0, 2, 1);
            g2.clearRect(0, 0, 1, 2);
            g2.clearRect(w - 2, 0, 2, 1);
            g2.clearRect(w - 1, 0, 1, 2);

            g2.dispose();
        }
    };

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        jpStage.setLayout(new GridLayout(0, 1));
        jpStage.add(configurationPanel);
        jpStage.setOpaque(false);
//       String imageFile = "/resources/Arrow.png"; 
//         JPanel panel = new ImagePanel(imageFile);
//        panel.setOpaque(false);
//        panel.setSize(400, 400);
//        jPanel1.add(panel);
        //this.setExtendedState(MAXIMIZED_BOTH);
        jbRezise.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Application.png"))); // NOI18N

        DragWindowListener dwl = new DragWindowListener();
        jpTitleBar.addMouseListener(dwl);
        jpTitleBar.addMouseMotionListener(dwl);
        ResizeWindowListener rwl = new ResizeWindowListener(this);
        for (JLabel l : java.util.Arrays.asList(
                left = new JLabel(), right = new JLabel(),
                top = new JLabel(), bottom = new JLabel(),
                topleft = new JLabel(), topright = new JLabel(),
                bottomleft = new JLabel(), bottomright = new JLabel())) {
            l.addMouseListener(rwl);
            l.addMouseMotionListener(rwl);
            //l.setOpaque(true);
            //l.setBackground(Color.RED);
        }

        Dimension d = new Dimension(W, 0);
        left.setPreferredSize(d);
        left.setMinimumSize(d);
        right.setPreferredSize(d);
        right.setMinimumSize(d);

        d = new Dimension(0, W);
        top.setPreferredSize(d);
        top.setMinimumSize(d);
        bottom.setPreferredSize(d);
        bottom.setMinimumSize(d);

        d = new Dimension(W, W);
        topleft.setPreferredSize(d);
        topleft.setMinimumSize(d);
        topright.setPreferredSize(d);
        topright.setMinimumSize(d);
        bottomleft.setPreferredSize(d);
        bottomleft.setMinimumSize(d);
        bottomright.setPreferredSize(d);
        bottomright.setMinimumSize(d);

        left.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
        right.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
        top.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
        bottom.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
        topleft.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
        topright.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
        bottomleft.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
        bottomright.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));

        JPanel titlePanel = new JPanel(new BorderLayout(0, 0));
        titlePanel.add(top, BorderLayout.NORTH);
        titlePanel.add(jpTitleBar, BorderLayout.CENTER);

        JPanel northPanel = new JPanel(new BorderLayout(0, 0));
        northPanel.add(topleft, BorderLayout.WEST);
        northPanel.add(titlePanel, BorderLayout.CENTER);
        northPanel.add(topright, BorderLayout.EAST);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(bottomleft, BorderLayout.WEST);
        southPanel.add(bottom, BorderLayout.CENTER);
        southPanel.add(bottomright, BorderLayout.EAST);

        resizePanel.add(left, BorderLayout.WEST);
        resizePanel.add(right, BorderLayout.EAST);
        resizePanel.add(northPanel, BorderLayout.NORTH);
        resizePanel.add(southPanel, BorderLayout.SOUTH);
        resizePanel.add(mainPanel, BorderLayout.CENTER);

        titlePanel.setOpaque(false);
        northPanel.setOpaque(false);
        southPanel.setOpaque(false);

        resizePanel.setOpaque(false);


        this.setContentPane(resizePanel);
        gotoConf();
        this.setExtendedState(MAXIMIZED_BOTH);
    }

    public void gotoGame() {
        jpStage.remove(configurationPanel);
        jpStage.add(gamePanel);
        gamePanel.createTable();
        jlLast.setVisible(true);
        jlTime.setVisible(true);
        repaint();
        revalidate();
    }

    private void gotoConf() {
        jpStage.remove(gamePanel);
        jpStage.add(configurationPanel);
       jlLast.setVisible(false);
       jlTime.setVisible(false);
        repaint();
        revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jpTitleBar = new javax.swing.JPanel();
        jbRezise = new javax.swing.JButton();
        jbClose = new javax.swing.JButton();
        jbMinus = new javax.swing.JButton();
        jbGame = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jlTime = new javax.swing.JLabel();
        jlLast = new javax.swing.JLabel();
        jpStage = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Look Table - Grade de Números");
        setBackground(new java.awt.Color(63, 63, 70));
        setLocationByPlatform(true);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(800, 600));

        mainPanel.setBackground(new java.awt.Color(63, 63, 70));
        mainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(78, 78, 78)));
        mainPanel.setOpaque(false);

        jpTitleBar.setBackground(new java.awt.Color(215, 140, 48));
        jpTitleBar.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N

        jbRezise.setBackground(new java.awt.Color(215, 140, 48));
        jbRezise.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Application.png"))); // NOI18N
        jbRezise.setBorder(null);
        jbRezise.setBorderPainted(false);
        jbRezise.setPreferredSize(new java.awt.Dimension(32, 32));
        jbRezise.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbReziseActionPerformed(evt);
            }
        });

        jbClose.setBackground(new java.awt.Color(215, 140, 48));
        jbClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Cancel.png"))); // NOI18N
        jbClose.setBorder(null);
        jbClose.setBorderPainted(false);
        jbClose.setPreferredSize(new java.awt.Dimension(32, 32));
        jbClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCloseActionPerformed(evt);
            }
        });

        jbMinus.setBackground(new java.awt.Color(215, 140, 48));
        jbMinus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Minus.png"))); // NOI18N
        jbMinus.setBorder(null);
        jbMinus.setBorderPainted(false);
        jbMinus.setPreferredSize(new java.awt.Dimension(32, 32));
        jbMinus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbMinusActionPerformed(evt);
            }
        });

        jbGame.setBackground(new java.awt.Color(215, 140, 48));
        jbGame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Game.png"))); // NOI18N
        jbGame.setBorder(null);
        jbGame.setBorderPainted(false);
        jbGame.setPreferredSize(new java.awt.Dimension(32, 32));
        jbGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGameActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(231, 232, 233));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/loginho.png"))); // NOI18N
        jLabel1.setText("LookTable ");

        jlTime.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        jlTime.setForeground(new java.awt.Color(231, 232, 233));
        jlTime.setText("Tempo");

        jlLast.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        jlLast.setForeground(new java.awt.Color(231, 232, 233));
        jlLast.setText("Última");

        javax.swing.GroupLayout jpTitleBarLayout = new javax.swing.GroupLayout(jpTitleBar);
        jpTitleBar.setLayout(jpTitleBarLayout);
        jpTitleBarLayout.setHorizontalGroup(
            jpTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTitleBarLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 196, Short.MAX_VALUE)
                .addComponent(jlTime)
                .addGap(71, 71, 71)
                .addComponent(jlLast)
                .addGap(61, 61, 61)
                .addComponent(jbGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbMinus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbRezise, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpTitleBarLayout.setVerticalGroup(
            jpTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jpTitleBarLayout.createSequentialGroup()
                .addGroup(jpTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlTime, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlLast))
                    .addComponent(jbGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbMinus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbRezise, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jpStage.setBackground(new java.awt.Color(53, 52, 50));

        javax.swing.GroupLayout jpStageLayout = new javax.swing.GroupLayout(jpStage);
        jpStage.setLayout(jpStageLayout);
        jpStageLayout.setHorizontalGroup(
            jpStageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jpStageLayout.setVerticalGroup(
            jpStageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 481, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpTitleBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpStage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jpTitleBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpStage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCloseActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jbCloseActionPerformed

    private void rezise() {
        if (this.getExtendedState() == NORMAL) {
            this.setExtendedState(MAXIMIZED_BOTH);
            jbRezise.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Applications.png"))); // NOI18N
        } else {
            jbRezise.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Application.png"))); // NOI18N
            this.setExtendedState(NORMAL);
        }
    }
    private void jbReziseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbReziseActionPerformed

        rezise();
    }//GEN-LAST:event_jbReziseActionPerformed

    private void jbMinusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbMinusActionPerformed
       jbRezise.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Application.png"))); // NOI18N
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_jbMinusActionPerformed

    private void jbGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGameActionPerformed
        gotoConf();
    }//GEN-LAST:event_jbGameActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
       
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    try {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
         
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    /**
     * @param jlLast the jlLast to set
     */
    public void setJlLast(String jlLast) {
        this.jlLast.setText(jlLast);
    }

    /**
     * @param jlTime the jlTime to set
     */
    public void setJlTime(String jlTime) {
        this.jlTime.setText(jlTime);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jbClose;
    private javax.swing.JButton jbGame;
    private javax.swing.JButton jbMinus;
    private javax.swing.JButton jbRezise;
    private javax.swing.JLabel jlLast;
    private javax.swing.JLabel jlTime;
    private javax.swing.JPanel jpStage;
    private javax.swing.JPanel jpTitleBar;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
