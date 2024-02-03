import java.awt.*;

public class Wolf extends Animal{

    public Wolf(World world, int positionY, int positionX, int age){
        super(Organism.speciesEnum.wolf, world, 9, 5, positionY, positionX, age);
        setColor(new Color(100, 100, 100));
    }

    @Override
    protected String nameString() {
        return " wolf ";
    }

    @Override
    public String getSign() {
        return "W";
    }
    @Override
    public Organism breed(int y, int x) {
        return new Wolf(getWorld(), y, x, 1);
    }
}

