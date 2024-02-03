import java.awt.*;

public class Antelope extends Animal{

    public Antelope(World world, int positionY, int positionX, int age){
        super(Organism.speciesEnum.antelope, world, 4, 4, positionY, positionX, age);
        setColor(new Color(100, 100, 100));
    }

    @Override
    public String getSign() {
        return "A";
    }

    @Override
    public Organism breed(int y, int x) {
        return new Antelope(getWorld(), y, x, 1);
    }

    @Override
    public void collision(Organism atacker, int y, int x) {
        int probability = randomInt(2);
        if(probability == 0){ //antelope escapes
            Comment.addComment(this.nameString() + "escapes");
            this.action();

            getWorld().getGrid()[y][x] = null;
            atacker.setPositionY(y);
            atacker.setPositionX(x);
            getWorld().getGrid()[y][x] = atacker;
            getWorld().getGrid()[atacker.getPrevY()][atacker.getPrevX()] = null;
        }
        else{ //antelope doesnt escape
            if(this.getStrength() > atacker.getStrength()){ //atacker is defeated
                Comment.addComment(atacker.nameString() + "is defeated");
                getWorld().getGrid()[atacker.getPositionY()][atacker.getPositionX()] = null;
                getWorld().removeOrganism(atacker);
            }
            else{
                Comment.addComment(atacker.nameString() + "wins over" + this.nameString());
                getWorld().removeOrganism(this);
                getWorld().getGrid()[y][x] = null;
                atacker.setPositionX(x);
                atacker.setPositionY(y);
                getWorld().getGrid()[y][x] = atacker;
                getWorld().getGrid()[atacker.getPrevY()][atacker.getPrevX()] = null;
            }
        }
    }

    @Override
    public void action() {
        this.setAge(this.getAge() + 1);
        int x = this.getPositionX();
        int y = this.getPositionY();
        int random = randomInt(8);
        this.setPrevX(x);
        this.setPrevY(y);

        switch (random){
            case 0:
                if(isCellValid(y, x+2)){
                    if(isCellOccupied(y, x+2)){
                        checkCollision(y, x+2);
                    }
                    else{
                        if(isSosnowskyNearby(y, x+2)){
                            getWorld().removeOrganism(this);
                            getWorld().getGrid()[y][x] = null;
                        }
                        else {
                            this.setPositionX(x + 2);
                            getWorld().getGrid()[y][x + 2] = getWorld().getGrid()[y][x];
                            getWorld().getGrid()[y][x] = null;
                        }
                    }
                }
                else{
                    this.action();
                }
                break;
            case 1:
                if(isCellValid(y, x-2)){
                    if(isCellOccupied(y, x-2)){
                        checkCollision(y, x-2);
                    }
                    else{
                        if(isSosnowskyNearby(y, x-2)){
                            getWorld().removeOrganism(this);
                            getWorld().getGrid()[y][x] = null;
                        }
                        else {
                            this.setPositionX(x -2);
                            getWorld().getGrid()[y][x -2] = getWorld().getGrid()[y][x];
                            getWorld().getGrid()[y][x] = null;
                        }
                    }
                }
                else{
                    this.action();
                }
                break;
            case 2:
                if(isCellValid(y+2, x)){
                    if(isCellOccupied(y+2, x)){
                        checkCollision(y+2, x);
                    }
                    else{
                        if(isSosnowskyNearby(y+2, x)){
                            getWorld().removeOrganism(this);
                            getWorld().getGrid()[y][x] = null;
                        }
                        else {
                            this.setPositionY(y + 2);
                            getWorld().getGrid()[y+2][x ] = getWorld().getGrid()[y][x];
                            getWorld().getGrid()[y][x] = null;
                        }
                    }
                }
                else{
                    this.action();
                }
                break;
            case 3:
                if(isCellValid(y-2, x)){
                    if(isCellOccupied(y-2, x)){
                        checkCollision(y-2, x);
                    }
                    else{
                        if(isSosnowskyNearby(y-2, x)){
                            getWorld().removeOrganism(this);
                            getWorld().getGrid()[y][x] = null;
                        }
                        else {
                            this.setPositionY(y - 2);
                            getWorld().getGrid()[y-2][x ] = getWorld().getGrid()[y][x];
                            getWorld().getGrid()[y][x] = null;
                        }
                    }
                }
                else{
                    this.action();
                }
                break;
            case 4:
                if(isCellValid(y+1, x+1)){
                    if(isCellOccupied(y+1, x+1)){
                        checkCollision(y+1, x+1);
                    }
                    else{
                        if(isSosnowskyNearby(y+1, x+1)){
                            getWorld().removeOrganism(this);
                            getWorld().getGrid()[y][x] = null;
                        }
                        else {
                            this.setPositionY(y + 1);
                            this.setPositionX(x + 1);
                            getWorld().getGrid()[y+1][x + 1] = getWorld().getGrid()[y][x];
                            getWorld().getGrid()[y][x] = null;
                        }
                    }
                }
                else{
                    this.action();
                }
                break;
            case 5:
                if(isCellValid(y+1, x-1)){
                    if(isCellOccupied(y+1, x-1)){
                        checkCollision(y+1, x-1);
                    }
                    else{
                        if(isSosnowskyNearby(y+1, x-1)){
                            getWorld().removeOrganism(this);
                            getWorld().getGrid()[y][x] = null;
                        }
                        else {
                            this.setPositionY(y + 1);
                            this.setPositionX(x - 1);
                            getWorld().getGrid()[y+1][x - 1] = getWorld().getGrid()[y][x];
                            getWorld().getGrid()[y][x] = null;
                        }
                    }
                }
                else{
                    this.action();
                }
                break;
            case 6:
                if(isCellValid(y-1, x-1)){
                    if(isCellOccupied(y-1, x-1)){
                        checkCollision(y-1, x-1);
                    }
                    else{
                        if(isSosnowskyNearby(y-1, x-1)){
                            getWorld().removeOrganism(this);
                            getWorld().getGrid()[y][x] = null;
                        }
                        else {
                            this.setPositionY(y - 1);
                            this.setPositionX(x - 1);
                            getWorld().getGrid()[y-1][x - 1] = getWorld().getGrid()[y][x];
                            getWorld().getGrid()[y][x] = null;
                        }
                    }
                }
                else{
                    this.action();
                }
                break;
            case 7:
                if(isCellValid(y-1, x+1)){
                    if(isCellOccupied(y-1, x+1)){
                        checkCollision(y-1, x+1);
                    }
                    else{
                        if(isSosnowskyNearby(y-1, x+1)){
                            getWorld().removeOrganism(this);
                            getWorld().getGrid()[y][x] = null;
                        }
                        else {
                            this.setPositionY(y - 1);
                            this.setPositionX(x + 1);
                            getWorld().getGrid()[y-1][x + 1] = getWorld().getGrid()[y][x];
                            getWorld().getGrid()[y][x] = null;
                        }
                    }
                }
                else{
                    this.action();
                }
                break;

        }
    }

    @Override
    protected String nameString() {
        return " Antelope ";
    }
}
