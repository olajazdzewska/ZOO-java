import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.KeyEventDispatcher.*;

public class World {
    private int width;
    private int height;
    private int ture;
    private boolean isHumanAlive = true;
    private boolean SAcanBeActivated = true;
    private boolean SAisActivated = false;
    private boolean humanSA = false;
    private int humanSature = 1;
    private int cooldown = 0;

    private Organism[][] grid;
    private ArrayList<Organism> humanArr;
    private ArrayList<Organism> organismArr;
    public JFrame frame;




    World(int height, int width){
        this.width = width;
        this.height = height;
        grid = new Organism[height][width];
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                grid[i][j] = null;
            }
        }
        organismArr = new ArrayList<>();
        humanArr = new ArrayList<>();

    }

    private void addOrganism(Organism specie){
        organismArr.add(specie);
        grid[specie.getPositionY()][specie.getPositionX()] = specie;
    }
    public void removeOrganism(Organism specie){
        grid[specie.getPositionY()][specie.getPositionX()] = null;
        specie.setIsAlive(false);
        if(specie.getSpeciesEnum() == Organism.speciesEnum.human) {
            humanArr.clear();
            isHumanAlive = false;
        }
    }
    public boolean isEmptyCellOnGrid(){
        //   to avoid infinite recursion
        for(int i=0; i<getHeight(); i++){
            for(int j=0; j<getWidth(); j++){
                if(grid[i][j] == null)
                    return true;
            }
        }
        return false;
    };


    private void createOrgAtPos(Organism specie, int y, int x){
           specie.setPositionXY(y, x);
        specie.setPrevX(x);
        specie.setPrevY(y);
        this.grid[y][x] = specie;
    };

    private void randomCellGenerator(Organism specie){
        Random rand = new Random();
        int x, y;
        x = rand.nextInt(width);
        y = rand.nextInt(height);
        if(grid[y][x] == null){
            createOrgAtPos(specie, y, x);
        }
        else{
            if(isEmptyCellOnGrid())
                randomCellGenerator(specie);
        }
    }
    public void collectOrgFromGrid(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(grid[i][j] != null){
                    if(grid[i][j].getSpeciesEnum() != Organism.speciesEnum.human){
                        organismArr.add(grid[i][j]);
                    }

                }
            }
        }
    }
    public void collectHuman(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(grid[i][j] != null){
                    if(grid[i][j].getSpeciesEnum() == Organism.speciesEnum.human){
                        humanArr.add(grid[i][j]);
                    }
                }
            }
        }
    }
    public void createOrganisms(){
        randomCellGenerator(new Sheep(this, -1, -1, 2));
        randomCellGenerator(new Sheep(this, -1, -1, 4));

        randomCellGenerator(new Fox(this, -1, -1, 1));
        randomCellGenerator(new Fox(this, -1, -1, 7));

        randomCellGenerator(new Turtle(this, -1, -1, 1));
        randomCellGenerator(new Turtle(this, -1, -1, 8));

        randomCellGenerator(new Antelope(this, -1, -1, 4));
        randomCellGenerator(new Antelope(this, -1, -1, 6));

        randomCellGenerator(new Wolf(this, -1, -1, 5));
        randomCellGenerator(new Wolf(this, -1, -1, 1));

        randomCellGenerator(new Grass(this, -1, -1, 2));

        randomCellGenerator(new Guarana(this, -1, -1, 6));

        randomCellGenerator(new sowThistle(this, -1, -1, 8));

        randomCellGenerator(new Belladonna(this, -1, -1, 3));

        randomCellGenerator(new Sosnowsky(this, -1, -1, 7));

        randomCellGenerator(new Human(this, -1, -1, 9));
    }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Organism[][] getGrid() {return grid; }
    public Organism getHuman() { return humanArr.get(0); }

    public ArrayList<Organism> getOrganismArr() {
        return organismArr;
    }
    private void sort(){
        Collections.sort(organismArr, new Comparator<Organism>() {
            @Override
            public int compare(Organism o1, Organism o2) {
                if( o1.getInitiative() != o2.getInitiative())
                    return Integer.valueOf(o2.getInitiative()).compareTo(o1.getInitiative());
                else
                    return Integer.valueOf(o1.getAge()).compareTo(o2.getAge());
            }
        });
    }
    public void makeTurn(){
        ture++;
       collectOrgFromGrid();
        sort();

        if(humanSA && SAcanBeActivated && isHumanAlive){
            SAisActivated = true;
            int prevStrength = prevHumanStrength;
            int newStrength = humanArr.get(0).getStrength();
            if( newStrength != prevStrength){
                Comment.addComment("Special ability activated. Human's strength equals: " + Integer.toString(newStrength));
                humanArr.get(0).setStrength(--newStrength);
                humanSature++;
            }
            if(newStrength == prevStrength){
                humanSA = false;
                SAcanBeActivated = false;
                SAisActivated = false;
                cooldown = 0;
                humanSature = 0;
            }
        }
        if(!SAcanBeActivated){
            cooldown++;
            if(cooldown == 5){
                SAcanBeActivated=true;
                humanSature=1;
            }
        }

        for(int i = 0; i < organismArr.size(); i++){
            if(organismArr.get(i).getIsAlive() == true){
                organismArr.get(i).action();
            }
        }
        for(int i = 0; i < organismArr.size(); i++){
            if(organismArr.get(i).getIsAlive() == false){
                organismArr.remove(i);
                i--;
            }
        }
        organismArr.clear();
        //humanArr.clear();
    }

    public void printWorld(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1,1,0,0));
        frame.setSize(1900, 1000);

    }

    public void save() throws IOException {
        try {
            File file = new File("save.txt");
            file.createNewFile();

            PrintWriter pw = new PrintWriter(file);
            pw.print(height + " ");
            pw.print(width + " ");

            pw.print(ture + " ");
            pw.print(isHumanAlive + " ");
            pw.print(prevHumanStrength + "\n");

            if(isHumanAlive == true) {
                pw.print(humanArr.get(0).getPositionY() + " ");
                pw.print(humanArr.get(0).getPositionX() + " ");
                pw.print(humanArr.get(0).getStrength() + " ");
                pw.print(humanArr.get(0).getAge() + " ");
                pw.print(humanSA + " ");
                pw.print(cooldown + " ");
                pw.print(humanSature + " ");
                pw.print(SAisActivated + " ");
                pw.print(SAcanBeActivated + " ");
                pw.println();
            }
            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                    if(grid[i][j] != null){
                        Organism tmp = grid[i][j];
                        if(tmp.getSpeciesEnum() != Organism.speciesEnum.human){
                            pw.print(tmp.getSign() + " ");
                            pw.print(tmp.getPositionY() + " ");
                            pw.print(tmp.getPositionX() + " ");
                            pw.print(tmp.getAge() + " ");
                            pw.println();
                        }
                    }
                }
            }



            pw.close();
            System.out.println("Successfully wrote to the file.");


        } catch (IOException e) {
            System.out.println("An error occurred.");

        }
    }

    public World loadWorld() throws FileNotFoundException {
        try {
            File file = new File("save.txt");

            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            String[] properties = line.split(" ");
            int height = Integer.parseInt(properties[0]);
            int width = Integer.parseInt(properties[1]);

            World tmpWorld = new World(height, width);

            int ture = Integer.parseInt(properties[2]);
            tmpWorld.ture = ture;
            boolean humanAlive = Boolean.parseBoolean(properties[3]);
            tmpWorld.isHumanAlive = humanAlive;
            int prevStrength = Integer.parseInt(properties[4]);
            tmpWorld.prevHumanStrength = prevStrength;



            //load human
            if(tmpWorld.isHumanAlive == true) {
                line = scanner.nextLine();
                properties = line.split(" ");
                int posY = Integer.parseInt(properties[0]);
                int posX = Integer.parseInt(properties[1]);
                int strength = Integer.parseInt(properties[2]);
                int age = Integer.parseInt(properties[3]);
                boolean humanSA = Boolean.parseBoolean(properties[4]);
                tmpWorld.humanSA = humanSA;
                tmpWorld.setHumanSA(humanSA);
                int cooldown = Integer.parseInt(properties[5]);
                tmpWorld.cooldown = cooldown;
                int humanSAture = Integer.parseInt(properties[6]);
                tmpWorld.humanSature = humanSAture;
                boolean saIsactivated = Boolean.parseBoolean(properties[7]);
                tmpWorld.SAisActivated = saIsactivated;
                boolean saCouldBeactivated = Boolean.parseBoolean(properties[8]);
                tmpWorld.SAcanBeActivated = saCouldBeactivated;
                //  humanArr.clear();
                createOrgAtPos(new Human(tmpWorld, posY, posX, age), posY, posX);
                tmpWorld.grid[posY][posX].setStrength(strength); // !!!
              }
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                properties = line.split(" ");
                switch (properties[0]) {
                    case "A":
                        createOrgAtPos(new Antelope(tmpWorld, Integer.parseInt(properties[1]),
                                Integer.parseInt(properties[2]),
                                Integer.parseInt(properties[3])), Integer.parseInt(properties[1]), Integer.parseInt(properties[2]));
                        break;
                    case "F":
                        createOrgAtPos(new Fox(tmpWorld, Integer.parseInt(properties[1]),
                                Integer.parseInt(properties[2]),
                                Integer.parseInt(properties[3])), Integer.parseInt(properties[1]), Integer.parseInt(properties[2]));
                        break;
                    case "S":
                        createOrgAtPos(new Sheep(tmpWorld, Integer.parseInt(properties[1]),
                                Integer.parseInt(properties[2]),
                                Integer.parseInt(properties[3])), Integer.parseInt(properties[1]), Integer.parseInt(properties[2]));
                        break;
                    case "W":
                        createOrgAtPos(new Wolf(tmpWorld, Integer.parseInt(properties[1]),
                                Integer.parseInt(properties[2]),
                                Integer.parseInt(properties[3])), Integer.parseInt(properties[1]), Integer.parseInt(properties[2]));
                        break;
                    case "T":
                        createOrgAtPos(new Turtle(tmpWorld, Integer.parseInt(properties[1]),
                                Integer.parseInt(properties[2]),
                                Integer.parseInt(properties[3])), Integer.parseInt(properties[1]), Integer.parseInt(properties[2]));
                        break;
                    case "~":
                        createOrgAtPos(new Grass(tmpWorld, Integer.parseInt(properties[1]),
                                Integer.parseInt(properties[2]),
                                Integer.parseInt(properties[3])), Integer.parseInt(properties[1]), Integer.parseInt(properties[2]));
                        break;
                    case "t":
                        createOrgAtPos(new sowThistle(tmpWorld, Integer.parseInt(properties[1]),
                                Integer.parseInt(properties[2]),
                                Integer.parseInt(properties[3])), Integer.parseInt(properties[1]), Integer.parseInt(properties[2]));
                        break;
                    case "s":
                        createOrgAtPos(new Sosnowsky(tmpWorld, Integer.parseInt(properties[1]),
                                Integer.parseInt(properties[2]),
                                Integer.parseInt(properties[3])), Integer.parseInt(properties[1]), Integer.parseInt(properties[2]));
                        break;
                    case "b":
                        createOrgAtPos(new Belladonna(tmpWorld, Integer.parseInt(properties[1]),
                                Integer.parseInt(properties[2]),
                                Integer.parseInt(properties[3])), Integer.parseInt(properties[1]), Integer.parseInt(properties[2]));
                        break;
                    case "g":
                        createOrgAtPos(new Guarana(tmpWorld, Integer.parseInt(properties[1]),
                                Integer.parseInt(properties[2]),
                                Integer.parseInt(properties[3])), Integer.parseInt(properties[1]), Integer.parseInt(properties[2]));
                        break;
                }


            }
            scanner.close();
            return tmpWorld;
        } catch (IOException e){
            System.out.println("Error:" + e);
        }
        return null;
    }




    public boolean isHumanAlive() {
        return isHumanAlive;
    }

    public boolean isSAcanBeActivated() {
        return SAcanBeActivated;
    }

    public boolean isSAisActivated() {
        return SAisActivated;
    }

    public void setHumanSA(boolean humanSA) {
        this.humanSA = humanSA;
    }

    public boolean isHumanSA() {
        return humanSA;
    }

    public void setSAisActivated(boolean SAisActivated) {
        this.SAisActivated = SAisActivated;
    }

    public int prevHumanStrength=5;
}
