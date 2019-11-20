import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerLinkedQueue implements ProducerConsumer {
    private int num_elements;
    private LinkedBlockingQueue<Integer> queue;

    public ProducerConsumerLinkedQueue(int num_elements) {
        this.num_elements = num_elements;
        queue = new LinkedBlockingQueue<Integer>(num_elements);
    }

    public void produce(int value) {
        try {
            this.queue.put(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int consume(){
        try {
            return this.queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return  -1;
        }
    }
}
