import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameModel {

    private final Deck deck;
    private Card playingCard;
    private int playingIndex;
    private boolean cwDirection;
    private boolean playingSide; // true for light, false for dark
    private final ArrayList<Player> players;
    private final Scanner scanner;

    public GameModel() {
        deck = new Deck();
        playingIndex = 0;
        cwDirection = true;
        playingSide = true;
        players = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    private void init() {
        System.out.println("Welcome to Uno Flip!");

        /* get the amount of players */
        System.out.print("Enter the number of players (2 - 10): ");
        int numOfPlayers = scanner.nextInt();
        System.out.println();

        /* get the player names */
        for(int i = 1; i <= numOfPlayers; i++) {
            System.out.print("Enter player " + i + "'s name: ");
            players.add(new HumanPlayer(scanner.next()));
        }
        System.out.println();

        /* share the starting cards */
        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < numOfPlayers; j++) {
                players.get(j).addCard(deck.removeCard());
            }
        }

        /* set the playing card */
        playingCard = deck.removeCard();

        if(playingCard.getSide(playingSide).getTempColor() != null) {
            int newColorIndex = (new Random()).nextInt((playingSide ? 0 : 5) + 4);
            playingCard.getSide(playingSide).setTempColor(CardColor.values()[newColorIndex]);
        }
    }

    private void nextTurn() {
        if(cwDirection)
            playingIndex = (playingIndex + 1) % players.size();
        else
            playingIndex = (playingIndex - 1) % players.size();
    }

    public void start() {
        init();

        do {
            /* player makes their play */
            System.out.println("Current Player: " + players.get(playingIndex).getName());
            System.out.println("Playing card: " + playingCard.getSide(playingSide).toString());
            Card playChoice;

            if (players.get(playingIndex) instanceof AIPlayer)
                playChoice = ((AIPlayer) players.get(playingIndex)).getAIPlay(playingCard.getSide(playingSide), deck);
            else
                playChoice = getHumanPlay();
            System.out.println();

            implementPlay(playChoice);

        } while(players.size() >= 2);

        System.out.println("Game is over!");
    }

    private Card getHumanPlay() {
        ArrayList<Card> options = getHumanPlayOptions();

        /* get player's play choice */
        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if((choice > 0) && (choice <= options.size())) { // if player played a card
            while(options.get(choice - 1).getSide(playingSide).getTempColor() ==  CardColor.WILD) { // if wild card was played
                System.out.println("Options to set wild card color:");
                for(int i = 0; i < 4; i++)
                    System.out.println("[" + (i + 1) + "] " + CardColor.values()[(playingSide ? 0 : 5) + i]);

                System.out.print("Enter your choice: ");
                int newColorIndex = scanner.nextInt();
                if((newColorIndex > 0) && (newColorIndex < 5))
                    options.get(choice - 1).getSide(playingSide).setTempColor(CardColor.values()[(playingSide ? -1 : 4) + newColorIndex]);
                else
                    System.out.println("Invalid input, try again!\n");
            }
            System.out.println();
            return players.get(playingIndex).removeCard(options.get(choice - 1));
        }
        else if(choice == (options.size() + 1)) { // if player drew a card
            System.out.println(players.get(playingIndex).getName() + " drew a card.");
            players.get(playingIndex).addCard(deck.removeCard());
            return null;
        }
        else if(choice == (options.size() + 2)) { // if player wants to view all their cards
            players.get(playingIndex).printCards();
            System.out.println();
            return getHumanPlay();
        }
        else if(choice == (options.size() + 3)) { // if the player quits
            ArrayList<Card> playersCards = players.get(playingIndex).quit();
            for(int i = 0; i < playersCards.size();)
                deck.addCard(playersCards.remove(i));
            return null;
        }

        System.out.println("Invalid input, try again!\n");
        return getHumanPlay();
    }

    private ArrayList<Card> getHumanPlayOptions() {
        ArrayList<Card> options = new ArrayList<>();

        /* get all options that can be played */
        System.out.println("These are your options to play:");
        for(Card card : players.get(playingIndex).getCards()) {
            if((card.getSide(playingSide).getColor() == playingCard.getSide(playingSide).getColor()) ||
                    (card.getSide(playingSide).getValue() == playingCard.getSide(playingSide).getValue()) ||
                    (card.getSide(playingSide).getColor() == CardColor.WILD)) {
                options.add(card);
                System.out.println("[" + options.size() + "] " + card.getSide(playingSide).toString());
            }
        }

        /* print other options to play */
        System.out.println("[" + (options.size() + 1) + "] Draw card");
        System.out.println("[" + (options.size() + 2) + "] View all cards");
        System.out.println("[" + (options.size() + 3) + "] Quit\n");

        return options;
    }

    private void implementPlay(Card selectedCard) {
        if(players.get(playingIndex).hasQuit()) { // player quit
            System.out.println(players.get(playingIndex).getName() + " left the game.");
            players.remove(players.get(playingIndex));
            if(!cwDirection)
                nextTurn();
            return;
        }

        if(selectedCard == null) { // drew card
            nextTurn();
            return;
        }

        /* update playing card */
        Card temp = playingCard;
        playingCard = selectedCard;
        if(temp.getSide(playingSide).getTempColor() != null) // reset the wild card
            temp.getSide(playingSide).setTempColor(null);
        deck.addCard(temp);

        System.out.println(players.get(playingIndex).getName() + " played " + playingCard.getSide(playingSide).toString());

        /* check if player has one more card */
        if(players.get(playingIndex).getCards().size() == 1)
            System.out.println("UNO!!! " + players.get(playingIndex).getName() + " has one more card!");

        /* check if player played last card */
        else if(players.get(playingIndex).getCards().isEmpty()) {
            playerUnoOut(players.get(playingIndex));
            return;
        }

        switch(playingCard.getSide(playingSide).getValue()) {
            case SKIP -> nextTurn();
            case REVERSE -> cwDirection = !cwDirection;
            case DRAW_TWO -> {
                nextTurn();
                players.get(playingIndex).addCard(deck.removeCard());
                players.get(playingIndex).addCard(deck.removeCard());
            }
            case FLIP -> {
                playingSide = !playingSide;
                System.out.println(players.get(playingIndex).getName() + " flipped the playing side to " + (playingSide ? "light!" : "dark!"));
            }
        }

        nextTurn();
    }

    private void playerUnoOut(Player player) {
        if(playingCard.getSide(playingSide).getValue() == CardValue.FLIP)
            playingSide = !playingSide;

        int score = player.getScore();

        for(Player p : players) {
            if(p == player)
                continue;

            for(Card card : p.getCards()) {
                switch(card.getSide(playingSide).getValue()) {
                    case ONE -> score += 1;
                    case TWO -> score += 2;
                    case THREE -> score += 3;
                    case FOUR -> score += 4;
                    case FIVE -> score += 5;
                    case SIX -> score += 6;
                    case SEVEN -> score += 7;
                    case EIGHT -> score += 8;
                    case NINE -> score += 9;
                    case SKIP, REVERSE, FLIP -> score += 20;
                    case WILD -> score += 40;
                    case DRAW_TWO -> score += 50;
                }
            }
        }

        player.setScore(score);
        System.out.println(player.getName() + " won with a score of " + score + "!");
        System.exit(0);
    }
}
