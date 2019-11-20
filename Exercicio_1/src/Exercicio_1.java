/* 
Author: Victor Figueira
Date:  19/11/2019
Task: 1. Implemente duas versões do problema do produtor/consumidor com M
produtores e N consumidores usando ArrayBlockingQueue e
LinkedBlockingQueue. Compare o desempenho das duas
implementações.
*/
import java.util.concurrent.CountDownLatch;


public class Exercicio_1 {
    private static int num_iterations = 317811;
    private static int num_producers = 987;
    private static int num_consumers = 610;

    public static void main(String[] args) throws InterruptedException {
        ProducerConsumer array_queue = new ProducerConsumerArrayQueue(10);
        ProducerConsumer linked_queue = new ProducerConsumerLinkedQueue(10);
        System.out.printf("LinkedQueue ProducerConsumer completed in: %s seconds%n",test_producer_consumer(linked_queue));
        System.out.printf("ArrayQueue ProducerConsumer completed in: %s seconds%n",test_producer_consumer(array_queue));

    }


    public static String test_producer_consumer(ProducerConsumer control){
        //Return elapsed time in seconds

        CountDownLatch done_signal = new CountDownLatch(num_producers+num_consumers);

        long startTime = System.nanoTime();

        for (int i = 0; i <num_producers ; i++) {
            //ensures that the same number of elements produced are consumed, even if num_producers!=num_consumers
            int num_iterations_interval = (i == num_producers-1) ? num_iterations/num_producers+(num_iterations%num_producers) : num_iterations/num_producers;
            new Thread(()-> producer(control,num_iterations_interval,done_signal)).start();
        }

        for (int i = 0; i <num_consumers ; i++) {
            int num_iterations_interval = (i == num_consumers-1) ? num_iterations/num_consumers+(num_iterations%num_consumers) : num_iterations/num_consumers;
            new Thread(()-> consumer(control,num_iterations_interval,done_signal)).start();
        }
        try {
            done_signal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return String.format("%.4f",((System.nanoTime() - startTime)/ 1_000_000_000.0));
    }

    private static void producer(ProducerConsumer control, int until,CountDownLatch done_signal){
        for (int i = 0; i < until ; i++) {
            control.produce(i);
        }
        done_signal.countDown();

    }

    private static void consumer(ProducerConsumer control, int until,CountDownLatch done_signal){
        for (int i = 0; i < until ; i++) {
              control.consume();
        }
        done_signal.countDown();
    }



}
