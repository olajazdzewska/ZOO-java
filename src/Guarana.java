import java.awt.*;

public class Guarana extends Plant{
    public Guarana(World world, int positionY, int positionX, int age){
        super(speciesEnum.guarana, world, 0, positionY, positionX, age);
        setColor(new Color(12, 130, 134));
    }

    @Override
    protected String nameString() {
        return " guarana ";
    }

    @Override
    public String getSign() {
        return "g";
    }
    @Override
    public Organism breed(int y, int x) {
        return new Guarana(getWorld(), y, x, 1);
    }

    @Override
    public void collision(Organism atacker, int y, int x) {
       atacker.setStrength(atacker.getStrength() + 3);
       Comment.addComment(atacker.nameString() + "eats guarana and its strength has increased to " + Integer.toString(atacker.getStrength()));

        getWorld().removeOrganism(this);
        getWorld().getGrid()[y][x] = null;
        atacker.setPositionX(x);
        atacker.setPositionY(y);
        getWorld().getGrid()[y][x] = atacker;
        getWorld().getGrid()[atacker.getPrevY()][atacker.getPrevX()] = null;
    }
}
