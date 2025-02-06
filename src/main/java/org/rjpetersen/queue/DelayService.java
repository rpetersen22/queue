package org.rjpetersen.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class DelayService {
    private final Logger logger = LoggerFactory.getLogger(DelayService.class);

    private final Timer timer;
    public DelayService() {
        this.timer = new Timer();
    }

    public void delayRandom() {
        delayFor(new Random().nextLong(0, 1000));
    }

    public void delayFor(long milliseconds) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                logger.info("Delaying for {} ms.", milliseconds);
            }
        };
        timer.schedule(task, milliseconds);
    }
}
