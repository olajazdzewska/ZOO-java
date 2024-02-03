import java.awt.*;

public class Grass extends Plant{
    public Grass(World world, int positionY, int positionX, int age){
        super(speciesEnum.grass, world, 0, positionY, positionX, age);
        setColor(new Color(242, 23, 234));
    }

    @Override
    protected String nameString() {
        return " grass ";
    }

    @Override
    public String getSign() {
        return "~";
    }
    @Override
    public Organism breed(int y, int x) {
        return new Grass(getWorld(), y, x, 1);
    }
}
