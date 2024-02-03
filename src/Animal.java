public abstract class Animal extends Organism{
    public Animal(speciesEnum speciesEnum, World world, int strength, int initiative,
                  int positionY, int positionX, int age){
        super(speciesEnum, world, strength, initiative, positionY, positionX, age);

    }
    @Override
    public void action() {
        this.setAge(this.getAge() + 1);
        int random = randomInt(4);
        int x = this.getPositionX();
        int y = this.getPositionY();
        this.setPrevX(x);
        this.setPrevY(y);

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
