public class DarkSide extends Side {

    public DarkSide(CardColor color, CardValue value) {
        super(color, value);
    }

    @Override
    public String getDescription() {
        return "| Side: Dark " + super.getDescription() + " |";
    }
}
