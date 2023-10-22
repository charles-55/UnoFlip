import java.util.ArrayList;
import java.util.Random;

public class Deck {

    private final ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        init();
    }

    private void init() {
        ArrayList<LightSide> allLightSides = getAllLightSides();
        ArrayList<DarkSide> allDarkSides = getAllDarkSides();

        while(!allLightSides.isEmpty()) {
            int lightSideIndex = (new Random()).nextInt(allLightSides.size());
            int darkSideIndex = (new Random()).nextInt(allDarkSides.size());

            cards.add(new Card(allLightSides.remove(lightSideIndex), allDarkSides.remove(darkSideIndex)));
        }
    }

    private ArrayList<LightSide> getAllLightSides() {
        ArrayList<LightSide> lightSides = new ArrayList<>();

        for(int i = 0; i < 4; i++) { // light side colors
            for(int j = 0; j < 9; j++) { // card values 1 to 9
                for(int k = 0; k < 2; k++)
                    lightSides.add(new LightSide(CardColor.values()[i], CardValue.values()[j]));
            }
            for(int j = 0; j < 2; j++) {
                lightSides.add(new LightSide(CardColor.values()[i], CardValue.SKIP));
                lightSides.add(new LightSide(CardColor.values()[i], CardValue.REVERSE));
                lightSides.add(new LightSide(CardColor.values()[i], CardValue.FLIP));
            }

            lightSides.add(new LightSide(CardColor.WILD, CardValue.WILD));
            lightSides.add(new LightSide(CardColor.WILD, CardValue.DRAW_TWO));
        }

        System.out.println("Light sides: " + lightSides.size());
        return lightSides;
    }

    private ArrayList<DarkSide> getAllDarkSides() {
        ArrayList<DarkSide> darkSides = new ArrayList<>();

        for(int i = 5; i < 9; i++) { // dark side colors
            for(int j = 0; j < 9; j++) { // card values 1 to 9
                for(int k = 0; k < 2; k++)
                    darkSides.add(new DarkSide(CardColor.values()[i], CardValue.values()[j]));
            }
            for(int j = 0; j < 2; j++) {
                darkSides.add(new DarkSide(CardColor.values()[i], CardValue.SKIP));
                darkSides.add(new DarkSide(CardColor.values()[i], CardValue.REVERSE));
                darkSides.add(new DarkSide(CardColor.values()[i], CardValue.FLIP));
            }

            darkSides.add(new DarkSide(CardColor.WILD, CardValue.WILD));
            darkSides.add(new DarkSide(CardColor.WILD, CardValue.DRAW_TWO));
        }

        System.out.println("Dark sides: " + darkSides.size());
        return darkSides;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card removeCard() {
        return cards.remove((new Random()).nextInt(cards.size()));
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
