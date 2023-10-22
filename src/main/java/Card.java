public class Card {

    private final LightSide lightSide;
    private final DarkSide darkSide;

    public Card(LightSide lightSide, DarkSide darkSide) {
        this.lightSide = lightSide;
        this.darkSide = darkSide;
    }

    public Side getSide(boolean lightSide) {
        if(lightSide)
            return this.lightSide;
        else return darkSide;
    }

    @Override
    public String toString() {
        return lightSide.getDescription() + " " + darkSide.getDescription();
    }
}
