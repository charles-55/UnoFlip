import java.util.ArrayList;
import java.util.Random;

public class AIPlayer extends Player {

    public AIPlayer(String name) {
        super(name);
    }

    public Card getAIPlay(Side playingCardSide, Deck deck) {
        ArrayList<Card> options = getPlayOptions(playingCardSide, playingCardSide instanceof LightSide);
        int choice = (new Random()).nextInt(options.size() + 1) + 1; // get AI's play choice

        if(choice <= options.size()) {
            /* set the color of a wild card */
            if(options.get(choice - 1).getSide(playingCardSide instanceof LightSide).getColor() == CardColor.WILD) {
                int newColorIndex = (new Random()).nextInt((playingCardSide instanceof LightSide ? 0 : 5) + 4);
                options.get(choice - 1).getSide(playingCardSide instanceof LightSide).setTempColor(CardColor.values()[newColorIndex]);
            }

            return removeCard(options.get(choice - 1));
        }
        else if(choice == (options.size() + 1)) {
            System.out.println(getName() + " drew a card.");
            addCard(deck.removeCard());
        }

        return null;
    }

    private ArrayList<Card> getPlayOptions(Side playingCardSide, boolean lightSide) {
        ArrayList<Card> options = new ArrayList<>();

        /* get all options that can be played */
        for(Card card : getCards()) {
            if((card.getSide(lightSide).getColor() == playingCardSide.getColor()) || (card.getSide(lightSide).getValue() == playingCardSide.getValue()) || (card.getSide(lightSide).getColor() == CardColor.WILD))
                options.add(card);
        }
        return options;
    }
}
