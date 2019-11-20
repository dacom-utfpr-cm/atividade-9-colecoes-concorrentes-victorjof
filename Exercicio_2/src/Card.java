
public class Card {


    private String number;
    private String suit;
    private int value;


    public Card(String suit, String number) {
        this.number = number;
        this.suit = suit;
    }


    public String getSuit() {
        return suit;
    }

    public String getNumber() {
        return number;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String toString(){
        return String.format("%s:%s",getNumber(),getSuit());
    }

}



