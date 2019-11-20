import java.util.concurrent.ArrayBlockingQueue;

public class ProducerConsumerArrayQueue implements ProducerConsumer{
    private int num_elements;
    private ArrayBlockingQueue<Integer> queue;

    public ProducerConsumerArrayQueue(int num_elements) {
        this.num_elements = num_elements;
        queue = new ArrayBlockingQueue<Integer>(num_elements);
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
