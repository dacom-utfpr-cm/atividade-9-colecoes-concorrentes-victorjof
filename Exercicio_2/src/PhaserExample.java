import java.util.concurrent.Phaser;
/**
 * Resettable countdownLatch using phaser
 */
public class PhaserExample {
    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser(3); // you can use constructor hint or
        // register() or mixture of both
        // register self... so parties are incremented to 4 (3+1) now
//        phaser.register();
        //register is one time call for all the phases.
        //means no need to register for every phase


        int phasecount = phaser.getPhase();
        System.out.println("Phasecount is " + phasecount);
        new PhaserExample().testPhaser(phaser, 2000);
        new PhaserExample().testPhaser(phaser, 4000);
        new PhaserExample().testPhaser(phaser, 6000);

        // similar to await() in countDownLatch/CyclicBarrier
        // parties are decremented to 3 (4+1) now
        phaser.awaitAdvance(phaser.getPhase());
        // once all the thread arrived at same level, barrier opens
        System.out.println("Barrier has broken.");
        phasecount = phaser.getPhase();
        System.out.println("Phasecount is " + phasecount);

        //second phase
        new PhaserExample().testPhaser(phaser, 2000);
        new PhaserExample().testPhaser(phaser, 4000);
        new PhaserExample().testPhaser(phaser, 6000);
        phaser.awaitAdvance(phaser.getPhase());
        // once all the thread arrived at same level, barrier opens
        System.out.println("Barrier has broken.");
        phasecount = phaser.getPhase();
        System.out.println("Phasecount is " + phasecount);

    }

    private void testPhaser(Phaser phaser, final int sleepTime) {
        // phaser.register(); //Already constructor hint is given so not
        // required
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(sleepTime);
                    System.out.println(Thread.currentThread().getName() + " arrived");
                    phaser.arrive(); //similar to CountDownLatch#countDown()
//                    phaser.arriveAndAwaitAdvance();// thread will wait till Barrier opens
//                    // arriveAndAwaitAdvance is similar to CyclicBarrier#await()
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                System.out.println(Thread.currentThread().getName() + " after passing barrier");
            }
        }.start();
    }
}