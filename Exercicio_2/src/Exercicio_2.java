/* 
Author: Victor Figueira
Date:  19/11/2019
Task: 2. Implemente o problema do produtor/consumidor para uma estrutura com
os seguintes campos: número, simbolo, naipe, que representa uma carta
de baralho. A implementação deve possibilitar que após 10 cartas
produzidas por dois produtores, outros dois consumidores pegarão
somente as maiores cartas. Os produtores somente devem produzir mais
cartas após os consumidores removerem 3 cartas cada um. As cartas
remanescentes podem continuar na estrutura. Use a ordenação do
baralho da menor para maior: A, 2, ..., 10, Q, J, K.
*/
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;

public class Exercicio_2 {
    private static int num_iterations = 100;
    private static Phaser full_cards;
    private static  int num_producers = 2;
    private static int capacity = 10;
    private static Semaphore semaphore_consumer1;
    private static Semaphore semaphore_consumer2;
    private static List<String> suits = Arrays.asList("diamonds", "hearts", "clubs", "spades");
    private static List<String> numbers = Arrays.asList("A", "2", "3", "4","5","6","7","8","9","10","J","Q","K");
    private static Deck deck;

    public static void main(String[] args) {
        deck = new Deck(capacity);
        full_cards = new Phaser(capacity);
        semaphore_consumer1 = new Semaphore(capacity);
        semaphore_consumer2 = new Semaphore(capacity);
        control_producing();
        control_consuming();
    }

    private static void control_producing(){

        for (int i = 0; i < num_producers; i++) {
                new Thread(() -> {
                    try {
                        producer();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
        }
    }

    private static void control_consuming(){
        //for a generic number(n) of consumers an array of semaphores(size n) would do the trick
        new Thread(() -> consumer(semaphore_consumer1)).start();
        new Thread(() -> consumer(semaphore_consumer2)).start();
    }


    private static void producer() throws InterruptedException {
        while(true) {
            semaphore_consumer1.acquire();//waits _each_ consumer to consume 3 cards
            semaphore_consumer2.acquire();
            Card card = get_random_card();
            deck.produce(card);
            System.out.println("Produced "+card.toString());
            full_cards.arrive();
            sleepMs(10);
        }
        }

    private static void consumer(Semaphore semaphore){
        while(true) {
            full_cards.awaitAdvance(full_cards.getPhase());//awaits deck with full capacity
            //new phase -> there's still 4 cards left in deck ->  2 for each consumer(generic version would be capacity/n_consumers)
            full_cards.arrive();//only needs to wait 6 new cards -> 10-(2*2)
            full_cards.arrive();
            System.out.println("Consumed  "+deck.consume().toString());
            System.out.println("Consumed  "+deck.consume().toString());
            System.out.println("Consumed  "+deck.consume().toString());
            semaphore.release(2 * 3);//each consumer consumes 3 cards (generic version would be num_consumers * num_cards_per_consumer)
            sleepMs(10);
        }
    }


    //generates non unique random cards
    private static Card get_random_card(){
        Card card = new Card(suits.get(new Random().nextInt(suits.size())),numbers.get(new Random().nextInt(numbers.size())));
        return card;
    }

    private static void sleepMs(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
