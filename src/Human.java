import java.awt.*;

public class Human extends Animal{
    public Human(World world, int positionY, int positionX, int age){
        super(Organism.speciesEnum.human, world, 5, 4, positionY, positionX, age);

    }



    @Override
    public void action() {
       int x1 = this.getPositionX();
       int y1 = this.getPositionY();
       this.setPrevX(x1);
       this.setPrevY(y1);
       int y2 = this.getProposedY();
       int x2 = this.getProposedX();

       if(isCellValid(y2, x2)){
           if(isCellOccupied(y2, x2)){
               checkCollision(y2, x2);
           }
           else{
               if(isSosnowskyNearby(y2, x2)){
                   getWorld().removeOrganism(this);
                   Comment.addComment("human got too close to sosnowsky hogweed");
                   getWorld().getGrid()[y1][x1] = null;
               }
                else{
                   this.setPositionY(y2);
                   this.setPositionX(x2);
                   getWorld().getGrid()[y2][x2] = this;
                   getWorld().getGrid()[y1][x1] = null;
               }

           }
       }

    }



    @Override
    protected String nameString() {
        return " human ";
    }

    @Override
    public String getSign() {
        return "H";
    }

    @Override
    public Organism breed(int y, int x) {
        return null;
    }
}
