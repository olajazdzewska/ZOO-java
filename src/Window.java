import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Window implements ActionListener, KeyListener {
    private final int sizeY = 15;
    private final int sizeX = 15;
  // private Toolkit toolkit;
  // private Dimension dimension;
   private JMenu menu;
    private int k, j;
    private JButton[][] buttonGrid;
    private boolean canMove = true;
   private JMenuItem newGame, load, save; //exit;
   private boolean isload = false;

   // private JButton newGame, load;
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel panelPink;
    private JPanel panelBlue;
    private World world;
    private CommentGraphics commentGraphics = null;

    public Window(){
        k=0;
        j=0;
        buttonGrid = null;


        panelPink = new JPanel();
        panelPink.setBackground(Color.PINK);
        panelPink.setBounds(100, 50, 800, 500);
        panelPink.setLayout(new GridLayout(sizeY, sizeX));
        buttonGrid = new JButton[sizeY][sizeX];

        panelBlue = new JPanel();
        panelBlue.setBackground(Color.BLUE);
        panelBlue.setBounds(800, 0, 250, 250);

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);



      JMenuBar menuBar = new JMenuBar();
      menu = new JMenu("menu");
      newGame = new JMenuItem("new game");
      load = new JMenuItem("load");
      save = new JMenuItem("save");

        newGame.addActionListener(this);
        load.addActionListener(this);
        save.addActionListener(this);

        menu.add(newGame);
        menu.add(load);
        menu.add(save);

        menuBar.add(menu);

        load.addActionListener(this);

       frame.setJMenuBar(menuBar);





        frame.addKeyListener(this);
        frame.setVisible(true);


        frame.add(panelPink); //add panel to frame
        frame.add(panelBlue);
        frame.revalidate();
        frame.repaint();
    }




    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == newGame){
            Comment.clearComment();

            world = new World(15, 15);
            world.createOrganisms();
            if(commentGraphics != null){
                frame.remove(commentGraphics);
            }

            if(k == 0) {
                printGrid(world);
            }
            else{
                refreshGrid();
            }

            world.collectOrgFromGrid();
            world.collectHuman();
            commentGraphics = new CommentGraphics();
            frame.add(commentGraphics);

            frame.add(panelPink);
            frame.add(panelBlue);
            frame.revalidate();
            frame.repaint();
            k++;


        }
        else if(e.getSource() == save){


            try {
                world.save();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } else if (e.getSource() == load) {
            isload = true;
            Comment.clearComment();

            world = new World(15, 15);
            try {
                world = world.loadWorld();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            if(commentGraphics != null){
                frame.remove(commentGraphics);
            }
            commentGraphics = new CommentGraphics();

            if(k==0){
                printGrid(world);
            }else{
                refreshGrid();
            }
            world.collectOrgFromGrid();
            world.collectHuman();
            if(world.isHumanAlive() && world.prevHumanStrength != world.getHuman().getStrength()){
                world.setSAisActivated(true);
                world.setHumanSA(true);
            }

            frame.add(commentGraphics);

            frame.add(panelPink);
            frame.add(panelBlue);
            frame.revalidate();
            frame.repaint();
            k++;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(world != null){
            int key = e.getKeyCode();
                if (key == KeyEvent.VK_UP && world.isHumanAlive() && canMove) {
                    world.getHuman().checkAction(Organism.direction.up);
                    world.getHuman().action();
                    refreshGrid();
                    canMove = false;
                } else if (key == KeyEvent.VK_DOWN && world.isHumanAlive() && canMove) {
                    world.getHuman().checkAction(Organism.direction.down);
                    world.getHuman().action();
                    refreshGrid();
                    canMove = false;
                } else if (key == KeyEvent.VK_LEFT && world.isHumanAlive() && canMove) {
                    world.getHuman().checkAction(Organism.direction.left);
                    world.getHuman().action();
                    refreshGrid();
                    canMove = false;
                } else if (key == KeyEvent.VK_RIGHT && world.isHumanAlive() && canMove) {
                    world.getHuman().checkAction(Organism.direction.right);
                    world.getHuman().action();
                    refreshGrid();
                    canMove = false;
                } else if (key == KeyEvent.VK_P && world.isHumanAlive()) {
                    if(world.isSAcanBeActivated() && !world.isSAisActivated()){
                        if(world.getHuman().getStrength() < 10) {
                            world.prevHumanStrength = world.getHuman().getStrength();
                            world.setHumanSA(true);
                            world.getHuman().specialAbility();
                            Comment.addComment("human's special ability activated");
                        }
                        else
                            Comment.addComment("human's strength is greater than 10");
                    } else if (world.isSAisActivated()) {
                        Comment.addComment("special ability is already activated");
                    }else {
                        Comment.addComment("you need to wait until human's cooldown ends to use special ability again");
                    }
                    refreshGrid();
                }

            else if( key == KeyEvent.VK_T){
                Comment.clearComment();
                world.makeTurn();
                refreshGrid();
                canMove = true;
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }




    protected void printGrid(World world){
        for (int i = 0; i < world.getHeight(); i++) {
            for (int j = 0; j < world.getWidth(); j++) {
                if (world.getGrid()[i][j] == null) {
                    buttonGrid[i][j] = new JButton(" ");
                    panelPink.add(buttonGrid[i][j]);
                } else {
                    String tmp = world.getGrid()[i][j].getSign();
                    buttonGrid[i][j] = new JButton(tmp);
                    panelPink.add(buttonGrid[i][j]);

                }
            }
        }

    }

    protected void refreshGrid(){
        for (int i = 0; i < world.getHeight(); i++) {
            for (int j = 0; j < world.getWidth(); j++) {
                if (world.getGrid()[i][j] == null) {
                    buttonGrid[i][j].setText(" ");

                } else {
                    String tmp = world.getGrid()[i][j].getSign();
                    buttonGrid[i][j].setText(tmp);

                }
            }
        }
        commentGraphics.refreshComments();
        SwingUtilities.updateComponentTreeUI(frame);
        frame.requestFocusInWindow();
    }

    private class CommentGraphics extends JPanel{
        private String text;
        private final String instruction = "Aleksandra Jazdzewska " + "\n" + "use arrows to control the moves of human" +
                "\n" + "click 't' to make next turn" + "\n" + "click 'p' to activate human's special ability" + "\n" ;
        private JTextArea textArea;

        public CommentGraphics(){
            super();
            setBounds(100, 600, panelPink.getWidth(), 150);
            text = Comment.getText();
            textArea = new JTextArea(text);
            textArea.setEditable(false);
            setLayout(new CardLayout());

            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setMargin(new Insets(5, 5, 5, 5));
            JScrollPane scrollPane = new JScrollPane(textArea);
            add(scrollPane);
        }

        public void refreshComments(){
            text = instruction + Comment.getText();
            textArea.setText(text);
        }
    }
}
