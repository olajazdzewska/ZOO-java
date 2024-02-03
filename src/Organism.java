import java.awt.*;
import java.util.Random;

public abstract class Organism {
    public enum speciesEnum{
        human,
        wolf,
        sheep,
        fox,
        turtle,
        antelope,
        grass,
        sowThistle,
        sosnwsky,
        belladonna,
        guarana,
    }

    protected enum direction{
        up,
        down,
        left,
        right
    }
    private World world;

     public Color Color() { return Color.ORANGE; };
     private int positionX;
     private int positionY;
     private int proposedX;
     private int proposedY;
     private int prevX;
     private int prevY;
     private int strength;
     private int initiative;
     private int age;
     private speciesEnum speciesEnum;
     private boolean isAlive;
     private Color color;


     public Organism(speciesEnum speciesEnum, World world, int strength,
                     int initiative, int positionY, int positionX, int age){
         this.speciesEnum = speciesEnum;
         this.world = world;
         this.strength = strength;
         this.initiative = initiative;
         this.positionY = positionY;
         this.positionX = positionX;
         this.age = age;
         this.setIsAlive(true);
     }

    public void checkAction(direction dir){
        int x = this.getPositionX();
        int y = this.getPositionY();
        switch(dir){
            case up:
                this.setProposedX(x);
                this.setProposedY(y-1);
                break;
            case left:
                this.setProposedX(x-1);
                this.setProposedY(y);
                break;
            case right:
                this.setProposedX(x+1);
                this.setProposedY(y);
                break;
            case down:
                this.setProposedX(x);
                this.setProposedY(y+1);
                break;
        }
    }

    public abstract void action();
    protected abstract String nameString();

    public abstract String getSign();
    public abstract Organism breed(int y, int x);

    public void setPositionX(int x) {this.positionX = x; }
    public int getPositionX() {return positionX;}

    public void setPositionY(int y) {this.positionY = y; }
    public int getPositionY() {return positionY;}
    public void setProposedX(int proposedX) {
        this.proposedX = proposedX;
    }

    public int getProposedX() {
        return proposedX;
    }

    public void setProposedY(int proposedY) {
        this.proposedY = proposedY;
    }

    public int getProposedY() {
        return proposedY;
    }

    public void setPositionXY(int y, int x){
        positionX = x;
        positionY = y;
        world.getGrid()[positionY][positionX] = this;
    }

    public int getInitiative() {
        return initiative;
    }

    public int getAge() {
        return age;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int randomInt(int range){
        Random rand = new Random();
        int x;
        x = rand.nextInt(range);
        return x;
    }
    protected boolean isCellValid(int y, int x){
        if(x >= world.getWidth() ||  x<0 || y >= world.getHeight() || y<0)
        return false;
    else
        return true;
    }

    protected boolean isCellOccupied( int y, int x){
        if(world.getGrid()[y][x] == null){
            return false;
        }
        else
            return true;
    }
    public void collision(Organism atacker, int y, int x){
       // Organism victim = this;
       // Organism atacker = atacker;
        if(this.getStrength() > atacker.getStrength()){ //atacker defeated
            Comment.addComment(atacker.nameString() + "is defeated");
            world.getGrid()[atacker.getPositionY()][getPositionX()] = null;
            world.removeOrganism(atacker);
        }
        else{//atacker wins
            Comment.addComment(atacker.nameString() + "wins over " + this.nameString());
            world.removeOrganism(this);
            world.getGrid()[y][x] = null;
            atacker.setPositionY(y);
            atacker.setPositionX(x);
            world.getGrid()[y][x] = atacker;
            world.getGrid()[atacker.getPrevY()][atacker.getPrevX()] = null;
        }

    }

    public void checkCollision(int y, int x){
        Organism victim = world.getGrid()[y][x];
        Organism atacker = this;

        if(victim != null && atacker != null && victim.getIsAlive()==true && atacker.getIsAlive()==true){

            if(atacker.getSpeciesEnum() != victim.getSpeciesEnum()) { //if they are different species
                Comment.addComment(atacker.nameString() + "attacked " +  victim.nameString());
                victim.collision(atacker, y, x);


            }
            else { //if the same species then breed
                if (world.isEmptyCellOnGrid()) {
                    Comment.addComment( "breed of" +  victim.nameString());
                    putNewborn(victim.positionX, victim.positionY, atacker.positionX, atacker.positionY);
                }
                else {
                    Comment.addComment("there is no empty place for newborn to be put");
                    //no empty place on grid for newborn to  be put
                }
            }

        }
    }

    protected int[] nearestFreeCell(int x, int y){
        int[] position = {-1, -1};

        if(isCellValid(y, x+1) && world.getGrid()[y][x+1] == null){
            position[0] = y;
            position[1] = x+1;
            return position;
        } else if (isCellValid(y, x-1) && world.getGrid()[y][x-1] == null) {
            position[0] = y;
            position[1] = x-1;
            return position;
        } else if (isCellValid(y+1, x) && world.getGrid()[y+1][x] == null) {
            position[0] = y+1;
            position[1] = x;
            return position;
        }else if (isCellValid(y-1, x) && world.getGrid()[y-1][x] == null) {
            position[0] = y-1;
            position[1] = x;
            return position;
        }else{
            position[0] = -1;
            position[1] = -1;
            return position;
        }

    }

    private void putNewborn(int x1, int y1, int x2, int y2){
        int[] newPosition = nearestFreeCell(x1, y1);
        if(newPosition[0] == -1 && newPosition[1] == -1){
            newPosition = nearestFreeCell(x2, y2);
            if(newPosition[0] == -1 && newPosition[1] == -1){
                Comment.addComment("there is no empty place for newborn to be put");
            }
            else{
                breed(newPosition[0], newPosition[1]);
                world.getGrid()[newPosition[0]][newPosition[1]] = breed(newPosition[0], newPosition[1]); //1, 1
            }
        }
        else{
            breed(newPosition[0], newPosition[1]);
            world.getGrid()[newPosition[0]][newPosition[1]] = breed(newPosition[0], newPosition[1]);
        }
    }
    protected boolean isSosnowskyNearby(int y, int x){
        if(isCellValid(y, x+1) && world.getGrid()[y][x+1] != null && world.getGrid()[y][x+1].getSpeciesEnum() == speciesEnum.sosnwsky)
            return true;
        else if (isCellValid(y, x-1) && world.getGrid()[y][x-1] != null && world.getGrid()[y][x-1].getSpeciesEnum() == speciesEnum.sosnwsky) {
            return true;
        } else if (isCellValid(y+1, x) && world.getGrid()[y+1][x] != null && world.getGrid()[y+1][x].getSpeciesEnum() == speciesEnum.sosnwsky) {
            return true;
        } else if (isCellValid(y-1, x) && world.getGrid()[y-1][x] != null && world.getGrid()[y-1][x].getSpeciesEnum() == speciesEnum.sosnwsky) {
            return true;
        }else
            return false;
    }
    public void specialAbility(){
        int newStrength = 10;
        this.setStrength(newStrength);
    }
    public void setPrevX(int x) { this.prevX = x;}
    public int getPrevX() { return this.prevX; }
    public void setPrevY(int y) {this.prevY = y; }
    public int getPrevY() {return this.prevY; }

    public Color getColor() { return color; }
    public void setColor(Color color) { color = color; }
    public World getWorld() { return this.world; }
    public void setIsAlive(boolean value) { this.isAlive = value; }

    public Organism.speciesEnum getSpeciesEnum() {
        return speciesEnum;
    }

    public boolean getIsAlive() { return isAlive; }

    public void setAge(int age) {
        this.age = age;
    }


}
