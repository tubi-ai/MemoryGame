import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MemoryGame extends javax.swing.JFrame
        implements ActionListener {
public MemoryGame() {
        initialComponents();
        initialIcons();
        initialGame();
    }
    private void initialGame() {
        score = 0;
        int x = 0;
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Icons(icons[x], new ImageIcon(getClass().getResource("/images/logo.png")));
            tiles[i].addActionListener(this);
            gamePanel.add(tiles[i]);
            if ((i + 1) % 2 == 0) {
                x++;
            }
        }
        title.setText("Score: " + score);
        shuffle();
    }
    private void initialIcons() {
        Image img;
        for (int i = 0; i < icons.length; i++) {
            img = new ImageIcon(getClass().getResource("/images/img" + i + ".png")).getImage();
            icons[i] = createIcon(img);
        }
    }
    private ImageIcon createIcon(Image img) {
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        bi.createGraphics().drawImage(img, 0, 0, null);
        img = bi.getScaledInstance(80, 80, 1);
        return new ImageIcon(img);
    }
    private void showHelp() {
        if (tiles[0] != null) {
            for (int i = 0; i < tiles.length; i++) {
                if (!tiles[i].isNoIcon()) {
                    tiles[i].showIcons();
                    tiles[i].removeActionListener(this);
                }
            }
            score -= 50;
            title.setText("Score: " + score);
        }
    }
    private void hideHelp() {

        for (int i = 0; i < tiles.length; i++) {
            if (!tiles[i].isNoIcon()) {
                tiles[i].hideIcons();
                tiles[i].addActionListener(this);
            }
        }
    }
    private void check() {
        if (predict1 != predict2 && predict1.getImage() == predict2.getImage()) {
            new Thread() {
                @Override
                public void run() {
                }
            };
            new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < 3; i++) {
                        try {
                            predict1.hideIcons();
                            predict2.hideIcons();
                            Thread.sleep(100);
                            predict1.showIcons();
                            predict2.showIcons();
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            System.out.println(ex);
                        }
                    }
                    predict1.setNoIcon();
                    predict2.setNoIcon();
                    for (int i = 0; i < tiles.length; i++) {
                        if (!tiles[i].isNoIcon()) {
                            won = false;
                            break;
                        } else {
                            won = true;
                        }
                    }
                    if (won) {
                        if (score > 0) {
                            JOptionPane.showMessageDialog(gamePanel, "You Won! Your Score is " + score);
                        } else {
                            JOptionPane.showMessageDialog(gamePanel, "You Loose! Your Score is " + score);
                        }
                        initialGame();
                    }
                }
            };
            predict1.removeActionListener(this);
            predict2.removeActionListener(this);
            score += 100;
            title.setText("Score: " + score);

        } else {
            predict1.hideIcons();
            predict2.hideIcons();
            score -= 10;
            title.setText("Score: " + score);
        }
    }
    private void shuffle() {
        gamePanel.removeAll();
        ArrayList<Integer> al = new ArrayList<Integer>();
        for (int i = 0; i < 36;) {
            int x = (int) (Math.random() * 36);
            if (!al.contains(x)) {
                al.add(x);
                i++;
            }
        }
        for (int i = 0; i < 36; i++) {
            gamePanel.add(tiles[al.get(i)]);
            tiles[al.get(i)].hideIcons();
        }
    }
        private void initialComponents() {

        titlePanel = new javax.swing.JPanel();
        title = new javax.swing.JTextField();
        close = new javax.swing.JLabel();
        help = new javax.swing.JLabel();
        gamePanel = new javax.swing.JPanel();
        controlPanel = new javax.swing.JPanel();
        play = new javax.swing.JButton();
        load = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Matching Game");
        setBackground(new java.awt.Color(204, 204, 255));
        setIconImage(new ImageIcon(getClass().getResource("/images/logo.png")).getImage());
        setLocationByPlatform(true);
        setName("MainFrame");
        setUndecorated(true);

        titlePanel.setBackground(new java.awt.Color(153, 0, 153));
        titlePanel.setPreferredSize(new java.awt.Dimension(300, 25));
        titlePanel.setLayout(new java.awt.BorderLayout());

        title.setEditable(false);
        title.setBackground(new java.awt.Color(153, 153, 255));
        title.setFont(new java.awt.Font("Tahoma", 1, 11));
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        title.setText("Score: ");
        title.setToolTipText("After Clicking mouse here use arrow keys to move");
        title.setBorder(null);
        title.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        title.setSelectionColor(new java.awt.Color(153, 153, 255));
        title.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                titleMouseDragged(evt);
            }
        });
        title.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                titleKeyPressed(evt);
            }
        });
        titlePanel.add(title, java.awt.BorderLayout.CENTER);

        close.setBackground(new java.awt.Color(0, 153, 153));
        close.setFont(new java.awt.Font("Tahoma", 1, 14));
        close.setForeground(new java.awt.Color(255, 255, 255));
        close.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        close.setText("X");
        close.setToolTipText("Close");
        close.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        close.setPreferredSize(new java.awt.Dimension(25, 25));
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        titlePanel.add(close, java.awt.BorderLayout.LINE_END);

        help.setFont(new java.awt.Font("Tahoma", 1, 18));
        help.setForeground(new java.awt.Color(255, 255, 255));
        help.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        help.setText("?");
        help.setToolTipText("Right click to hide controls and Left click to see Images");
        help.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        help.setPreferredSize(new java.awt.Dimension(25, 25));
        help.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                helpMouseClicked(evt);
            }
        });
        titlePanel.add(help, java.awt.BorderLayout.LINE_START);

        getContentPane().add(titlePanel, java.awt.BorderLayout.NORTH);

        gamePanel.setBackground(new java.awt.Color(153, 0, 153));
        gamePanel.setPreferredSize(new java.awt.Dimension(630, 630));
        gamePanel.setLayout(new java.awt.GridLayout(6, 6, 5, 5));
        getContentPane().add(gamePanel, java.awt.BorderLayout.CENTER);

        controlPanel.setBackground(new java.awt.Color(153, 153, 255));
        controlPanel.setPreferredSize(new java.awt.Dimension(300, 40));
        controlPanel.setLayout(new java.awt.GridLayout(1, 2));

        play.setBackground(new java.awt.Color(153, 0, 153));
        play.setFont(new java.awt.Font("Tahoma", 0, 18));
        play.setForeground(new java.awt.Color(153, 153, 255));
        play.setText("PLAY");
        play.setToolTipText("Play new Game");
        play.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playActionPerformed(evt);
            }
        });
        controlPanel.add(play);
        getContentPane().add(controlPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }
    private void closeMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == MouseEvent.BUTTON1) {
            this.dispose();
        }
    }
    private void helpMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == MouseEvent.BUTTON1) {
            if (!helping) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            helping = true;
                            showHelp();
                            Thread.sleep(10000);
                            hideHelp();
                            helping = false;
                        } catch (InterruptedException ex) {
                            System.out.println(ex);
                        }
                    }
                };
            }
        }
        if (evt.getButton() == MouseEvent.BUTTON3) {
            if (controlPanel.isVisible()) {
                setSize(600, 625);
                controlPanel.setVisible(false);
            } else {
                setSize(600, 665);
                controlPanel.setVisible(true);
            }
        }
    }
    private void titleKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            setLocation(getX() - 5, getY());
        }
        if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            setLocation(getX() + 5, getY());
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            setLocation(getX(), getY() - 5);
        }
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            setLocation(getX(), getY() + 5);
        }
    }
    private void playActionPerformed(java.awt.event.ActionEvent evt) {
        initialGame();
    }

    private void titleMouseDragged(java.awt.event.MouseEvent evt) {
        setLocation(evt.getXOnScreen() - 300, evt.getYOnScreen());
    }
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MemoryGame().setVisible(true);
            }
        });
    }
    Icons[] tiles = new Icons[36];
    ImageIcon[] icons = new ImageIcon[18];
    int status, score;
    Icons predict1, predict2;
    private boolean won, helping;
    private javax.swing.JLabel close;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JLabel help;
    private javax.swing.JButton load;
    private javax.swing.JButton play;
    private javax.swing.JTextField title;
    private javax.swing.JPanel titlePanel;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (status == 0) {
            predict1 = (Icons) e.getSource();
            predict1.showIcons();
            status++;
        } else if (status == 1) {
            status++;
            predict2 = (Icons) e.getSource();
            new Thread() {
                @Override
                public void run() {
                    try {
                        predict2.showIcons();
                        Thread.sleep(500);
                        check();
                        Thread.sleep(600);
                        status = 0;
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }.start();
        }
    }
}
