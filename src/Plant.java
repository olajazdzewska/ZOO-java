public abstract class Plant extends Organism{
    private double chanceOfSow;

    protected Plant(speciesEnum speciesEnum, World world, int strength, int positionY, int positionX, int age){
        super(speciesEnum, world, strength, 0, positionY, positionX, age);
        setChanceOfSow(0.3);
    }

    @Override
    public void action() {
        int x = this.getPositionX();
        int y = this.getPositionY();
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



   // public double getChanceOfSow() {return chanceOfSow; }
    public void setChanceOfSow(double x) { chanceOfSow = x; }
}
