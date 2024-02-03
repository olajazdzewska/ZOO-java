import java.awt.*;

public class Belladonna extends Plant{
    public Belladonna(World world, int positionY, int positionX, int age){
        super(speciesEnum.belladonna, world, 0, positionY, positionX, age);
        setColor(new Color(24, 130, 24));
    }

    @Override
    protected String nameString() {
        return " belladonna ";
    }

    @Override
    public String getSign() {
        return "b";
    }
    @Override
    public Organism breed(int y, int x) {
        return new Belladonna(getWorld(), y, x, 1);
    }

    @Override
    public void collision(Organism atacker, int y, int x) {
        Comment.addComment(atacker.nameString() + "eats belladonna and dies");
        getWorld().removeOrganism(this); //beladonna is eaten
        getWorld().getGrid()[y][x] = null;

        getWorld().removeOrganism(atacker);
        getWorld().getGrid()[atacker.getPositionY()][atacker.getPositionX()] = null;
    }
}
