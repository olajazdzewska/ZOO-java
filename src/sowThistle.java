import java.awt.*;

public class sowThistle extends Plant{
    public sowThistle(World world, int positionY, int positionX, int age){
        super(Organism.speciesEnum.sowThistle, world, 0, positionY, positionX, age);
        setColor(new Color(200, 230, 234));
    }
    @Override
    public String getSign() {
        return "t";
    }
    @Override
    public Organism breed(int y, int x) {
        return new sowThistle(getWorld(), y, x, 1);
    }

    @Override
    public void action() {
        int x = this.getPositionX();
        int y = this.getPositionY();
        for(int i = 0; i < 3; i++){
            int rand = randomInt(4);
            int probOfSow = randomInt(4);
            if(probOfSow < 1){
                switch (rand){
                    case 0:
                        if(isCellValid(y, x+1)){
                            if(isCellOccupied(y,x+1))
                                break;
                            else{
                                breed(y, x+1);
                                getWorld().getGrid()[y][x+1] = breed(y, x+1);
                            }
                        }
                        else
                            this.action();
                        break;
                    case 1:
                        if(isCellValid(y, x-1)){
                            if(isCellOccupied(y,x-1))
                                break;
                            else{
                                breed(y, x-1);
                                getWorld().getGrid()[y][x-1] = breed(y, x-1);
                            }
                        }
                        else
                            this.action();
                        break;
                    case 2:
                        if(isCellValid(y+1, x)){
                            if(isCellOccupied(y+1,x))
                                break;
                            else{
                                breed(y+1, x);
                                getWorld().getGrid()[y+1][x] = breed(y+1, x);
                            }
                        }
                        else
                            this.action();
                        break;
                    case 3:
                        if(isCellValid(y-1, x)){
                            if(isCellOccupied(y-1,x))
                                break;
                            else{
                                breed(y-1, x);
                                getWorld().getGrid()[y-1][x] = breed(y-1, x);
                            }
                        }
                        else
                            this.action();
                        break;
                }
            }
        }
    }

    @Override
    protected String nameString() {
        return " sow thistle ";
    }
}
