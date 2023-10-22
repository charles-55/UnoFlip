import java.util.ArrayList;

public abstract class Player {

    private final String name;
    private int score;
    private final ArrayList<Card> cards;
    private boolean quit;

    public Player(String name) {
        this.name = name;
        score = 0;
        cards = new ArrayList<>();
        quit = false;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void printCards() {
        System.out.println("Your cards: ");
        for(int i = 0; i < cards.size(); i++)
            System.out.println("[" + (i + 1) + "] " + cards.get(i));
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card removeCard(Card card) {
        if(cards.remove(card))
            return card;
        else {
            System.out.println("Error removing card from player " + name + "!");
            return null;
        }
    }

    public boolean hasQuit() {
        return quit;
    }

    public ArrayList<Card> quit() {
        quit = true;
        ArrayList<Card> returnCard = new ArrayList<>();

        for(int i = 0; i < cards.size(); ) {
            returnCard.add(cards.remove(i));
        }

        return returnCard;
    }
}
