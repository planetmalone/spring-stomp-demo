package receiver

import java.util.concurrent.CountDownLatch

/**
 * Created by seanmalone on 11/18/16.
 */
class Receiver {
    private CountDownLatch latch = new CountDownLatch(1);

    void receiveMessage(String message) {
        System.out.println("Received <" + message + ">")
        latch.countDown()
    }

    CountDownLatch getLatch() {
        return latch;
    }
}
