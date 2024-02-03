import java.awt.*;

public class Turtle extends Animal{

    public Turtle(World world, int positionY, int positionX, int age){
        super(Organism.speciesEnum.turtle, world, 2, 1, positionY, positionX, age);
        setColor(new Color(150, 150, 150));
    }
    @Override
    public String getSign() {
        return "T";
    }
    @Override
    public Organism breed(int y, int x) {
        return new Turtle(getWorld(), y, x, 1);
    }

    @Override
    public void action() {
        setAge(this.getAge() + 1);
        int x = this.getPositionX();
        int y = this.getPositionY();
        int probability = randomInt(4);
        this.setPrevX(x);
        this.setPrevY(y);
        if(probability>2){
            int random = randomInt(4);
            switch(random){
                case 0:
                    if(isCellValid(y, x+1)){
                        if(isCellOccupied(y, x+1)){
                            checkCollision(y, x+1);
                        }
                        else{
                            if(isSosnowskyNearby(y, x+1)){
                                getWorld().removeOrganism(this);
                                getWorld().getGrid()[y][x] = null;
                            }
                            else {
                                this.setPositionX(x + 1);
                                getWorld().getGrid()[y][x + 1] = getWorld().getGrid()[y][x];
                                getWorld().getGrid()[y][x] = null;
                            }
                        }
                    }
                    else{
                        this.action();
                    }
                    break;
                case 1:
                    if(isCellValid(y, x-1)){
                        if(isCellOccupied(y, x-1)){
                            checkCollision(y, x-1);
                        }
                        else{
                            if(isSosnowskyNearby(y, x-1)){
                                getWorld().removeOrganism(this);
                                getWorld().getGrid()[y][x] = null;
                            }
                            else{
                                this.setPositionX(x - 1);
                                getWorld().getGrid()[y][x - 1] = getWorld().getGrid()[y][x];
                                getWorld().getGrid()[y][x] = null;
                            }
                        }
                    }
                    else{
                        this.action();
                    }

                    break;
                case 2:
                    if(isCellValid(y+1, x)){
                        if(isCellOccupied(y+1, x)){
                            checkCollision(y+1, x);
                        }
                        else {
                            if(isSosnowskyNearby(y+1, x)){
                                getWorld().removeOrganism(this);
                                getWorld().getGrid()[y][x] = null;
                            }
                            else {
                                this.setPositionY(y + 1);
                                getWorld().getGrid()[y + 1][x] = getWorld().getGrid()[y][x];
                                getWorld().getGrid()[y][x] = null;
                            }
                        }
                    }
                    else
                        this.action();

                    break;
                case 3:
                    if(isCellValid(y-1, x)){
                        if(isCellOccupied(y-1, x)){
                            checkCollision(y-1, x);
                        }
                        else{
                            if(isSosnowskyNearby(y-1, x)){
                                getWorld().removeOrganism(this);
                                getWorld().getGrid()[y][x] = null;
                            }
                            else {
                                this.setPositionY(y - 1);
                                getWorld().getGrid()[y - 1][x] = getWorld().getGrid()[y][x];
                                getWorld().getGrid()[y][x] = null;
                            }
                        }
                    }
                    else
                        this.action();
                    break;
            }
        }
    }

    @Override
    protected String nameString() {
        return " turtle ";
    }

    @Override
    public void collision(Organism atacker, int y, int x) {
       Organism victim = this;
       if(atacker.getStrength() < 5){
           Comment.addComment("turtle reflects attack of " + atacker.nameString());

       }
       else {
           Comment.addComment("turtle is defeated by " + atacker.nameString());
           getWorld().removeOrganism(this);
           getWorld().getGrid()[y][x] = null;
           atacker.setPositionX(x);
           atacker.setPositionY(y);
           getWorld().getGrid()[y][x] = atacker;
           getWorld().getGrid()[atacker.getPrevY()][atacker.getPrevX()] = null;
       }
    }
}
