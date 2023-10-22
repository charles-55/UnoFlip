public class LightSide extends Side {

    public LightSide(CardColor color, CardValue value) {
        super(color, value);
    }

    @Override
    public String getDescription() {
        return "| Side: Light " + super.getDescription() + " |";
    }
}
