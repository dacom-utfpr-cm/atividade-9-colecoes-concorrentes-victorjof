import java.util.Comparator;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.PriorityBlockingQueue;

public class Deck {
    private int num_elements;
    private PriorityBlockingQueue<Card> cards;
    Dictionary<String,Integer> number_value = new Hashtable<String,Integer>();
    Dictionary<String,Integer> suit_value = new Hashtable<String,Integer>();

    public Deck(int num_elements) {

        Comparator<Card> value_field = (c1, c2) -> {
            if(c1.getValue() > c2.getValue()){
                return -1;
            }
            else if(c1.getValue() < c2.getValue()){
                return 1;
            }
            else{
                return 0;
            }
        };
        cards = new PriorityBlockingQueue<>(num_elements,value_field);
        fill_suit_value();
        fill_number_value();
    }

    public void produce(Card card){
        card.setValue(number_value.get(card.getNumber()) + suit_value.get(card.getSuit()));
        cards.put(card);

    }

    public Card consume(){
        try {
            return cards.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void fill_suit_value(){
        suit_value.put("spades",100);
        suit_value.put("hearts",200);
        suit_value.put("clubs",300);
        suit_value.put("diamonds",400);
    }

    private void fill_number_value(){
        number_value.put("A",1);
        number_value.put("2",2);
        number_value.put("3",3);
        number_value.put("4",4);
        number_value.put("5",5);
        number_value.put("6",6);
        number_value.put("7",7);
        number_value.put("8",8);
        number_value.put("9",9);
        number_value.put("10",10);
        number_value.put("J",11);
        number_value.put("Q",12);
        number_value.put("K",13);
    }

    public String toString(){
        String aux ="[ ";
        for (Card card:cards) {
            aux+=(" "+card.toString());
        }
        aux+=" ]";
        return aux;
    }
}
