public abstract class Side {

    private final CardColor color;
    private CardColor tempColor; // for wild color only
    private final CardValue value;

    public Side(CardColor color, CardValue value) {
        this.color = color;
        this.value = value;
        if(color == CardColor.WILD)
            tempColor = CardColor.WILD;
        else
            tempColor = null;
    }

    public CardColor getColor() {
        return color;
    }

    public CardColor getTempColor() {
        return tempColor;
    }

    public void setTempColor(CardColor color) {
        if(this.color == CardColor.WILD)
            tempColor = color;
        else
            System.out.println("invalid command!");
    }

    public CardValue getValue() {
        return value;
    }

    public String getDescription() {
        return "Value: " + value + " Color: " + color;
    }

    @Override
    public String toString() {
        return value + " " + color;
    }
}
