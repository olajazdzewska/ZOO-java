import java.awt.*;

public class Fox extends Animal{
    public Fox(World world, int positionY, int positionX, int age){
        super(Organism.speciesEnum.fox, world, 3, 7, positionY, positionX, age);
        setColor(new Color(140, 64, 64));
    }
    @Override
    public String getSign() {
        return "F";
    }

    @Override
    public Organism breed(int y, int x) {
        return new Fox(getWorld(), y, x, 1);
    }

    private boolean chechIfStronger(int x, int y){
        if(getWorld().getGrid()[y][x] == null)
            return false;
        if(getWorld().getGrid()[y][x] != null && getWorld().getGrid()[y][x].getStrength() <= this.getStrength())
            return false;
        else
            return true;
    }

    @Override
    public void action() {
        setAge(this.getAge() + 1);
        int x = this.getPositionX();
        int y = this.getPositionY();
        int random = randomInt(4);
        this.setPrevX(x);
        this.setPrevY(y);


        if((isCellValid(y, x+1) && !chechIfStronger(x+1, y)) ||( isCellValid(y, x-1) && !chechIfStronger(x-1, y)) ||
                (isCellValid(y+1, x) && !chechIfStronger(x, y+1) ) || (isCellValid(y-1, x) && !chechIfStronger(x, y-1) )){
            switch (random){
                case 0:
                   if(isCellValid(y, x+1) && !chechIfStronger(x+1, y)){
                       if(isCellOccupied(y, x+1))
                           checkCollision(y, x+1);
                       else{
                           if(isSosnowskyNearby(y, x+1)){
                               getWorld().removeOrganism(this);
                               getWorld().getGrid()[y][x] = null;
                           }
                           else{
                               this.setPositionX(x+1);
                               getWorld().getGrid()[y][x+1] = this;
                               getWorld().getGrid()[this.getPrevY()][this.getPrevX()] = null;
                           }
                       }
                   }
                   else
                       this.action();
                   break;
                case 1:
                    if(isCellValid(y, x-1) && !chechIfStronger(x-1, y)){
                        if(isCellOccupied(y, x-1))
                            checkCollision(y, x-1);
                        else{
                            if(isSosnowskyNearby(y, x-1)){
                                getWorld().removeOrganism(this);
                                getWorld().getGrid()[y][x] = null;
                            }
                            else{
                                this.setPositionX(x-1);
                                getWorld().getGrid()[y][x-1] = this;
                                getWorld().getGrid()[this.getPrevY()][this.getPrevX()] = null;
                            }
                        }
                    }
                    else
                        this.action();
                    break;
                case 2:
                    if(isCellValid(y+1, x) && !chechIfStronger(x, y+1)){
                        if(isCellOccupied(y+1, x))
                            checkCollision(y+1, x);
                        else{
                            if(isSosnowskyNearby(y+1, x)){
                                getWorld().removeOrganism(this);
                                getWorld().getGrid()[y][x] = null;
                            }
                            else{
                                this.setPositionY(y+1);
                                getWorld().getGrid()[y+1][x] = this;
                                getWorld().getGrid()[this.getPrevY()][this.getPrevX()] = null;
                            }
                        }
                    }
                    else
                        this.action();
                    break;
                case 3:
                    if(isCellValid(y-1, x) && !chechIfStronger(x, y-1)){
                        if(isCellOccupied(y-1, x))
                            checkCollision(y-1, x);
                        else{
                            if(isSosnowskyNearby(y-1, x)){
                                getWorld().removeOrganism(this);
                                getWorld().getGrid()[y][x] = null;
                            }
                            else{
                                this.setPositionY(y-1);
                                getWorld().getGrid()[y-1][x] = this;
                                getWorld().getGrid()[this.getPrevY()][this.getPrevX()] = null;
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
        return " fox ";
    }
}
