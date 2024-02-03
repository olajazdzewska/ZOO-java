import java.awt.*;

public class Sosnowsky extends Plant{
    public Sosnowsky(World world, int positionY, int positionX, int age){
        super(speciesEnum.sosnwsky, world, 0, positionY, positionX, age);
        setColor(new Color(22, 233, 230));
    }

    @Override
    protected String nameString() {
        return " sosnowsky hogweed ";
    }

    @Override
    public String getSign() {
        return "s";
    }
    @Override
    public Organism breed(int y, int x) {
        return new Sosnowsky(getWorld(), y, x, 1);
    }

    @Override
    public void collision(Organism atacker, int y, int x) {
        Comment.addComment(atacker.nameString() + "eats sosnowsky and dies");
        getWorld().removeOrganism(this);
        getWorld().getGrid()[y][x] = null;

        getWorld().removeOrganism(atacker);
        getWorld().getGrid()[atacker.getPositionY()][atacker.getPositionX()] = null;
    }
}
