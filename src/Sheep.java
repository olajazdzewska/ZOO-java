import java.awt.*;

public class Sheep extends Animal{

    public Sheep(World world, int positionY, int positionX, int age){
        super(Organism.speciesEnum.sheep, world, 4, 4, positionY, positionX, age);
        setColor(new Color(64, 64, 64));
    }

    @Override
    protected String nameString() {
        return " sheep ";
    }

    @Override
    public String getSign() {
        return "S";
    }

    @Override
    public Organism breed(int y, int x) {
        return new Sheep(this.getWorld(), y, x, 1);
    }
}
