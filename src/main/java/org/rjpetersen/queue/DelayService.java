package org.rjpetersen.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class DelayService {
    private final Logger logger = LoggerFactory.getLogger(DelayService.class);

    private final ScheduledThreadPoolExecutor threadPool;
    public DelayService() {
        threadPool = new ScheduledThreadPoolExecutor(1);
    }

    public void blockingDelayRandom() throws InterruptedException {
        blockingDelay(new Random().nextLong(1000, 3000));
    }

    public void blockingDelay(long delay) throws InterruptedException {
        logger.info("Delaying for {} ms.", delay);
        TimeUnit.MILLISECONDS.sleep(delay);
    }

    public void executeAfterRandom() {
        executeAfterDelay(new Random().nextLong(1000, 3000));
    }

    public void executeAfterDelay(long delay) {
        threadPool.schedule(() ->
            logger.info("Delayed for {} ms.", delay)
        , delay, TimeUnit.MILLISECONDS);
    }
}
