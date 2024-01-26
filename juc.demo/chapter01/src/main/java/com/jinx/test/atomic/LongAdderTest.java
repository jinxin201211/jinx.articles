package com.jinx.test.atomic;

import com.jinx.test.utils.JinxLogger;
import com.jinx.test.utils.LoggerFactory;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderTest {
    private final static JinxLogger log = LoggerFactory.getLogger(LongAdderTest.class);

    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        log.info(String.valueOf(longAdder.intValue()));
        longAdder.add(1);
        log.info(String.valueOf(longAdder.intValue()));
        longAdder.add(2);
        log.info(String.valueOf(longAdder.intValue()));

        LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 100);
        log.info(String.valueOf(longAccumulator.intValue()));
        longAccumulator.accumulate(1);
        log.info(String.valueOf(longAccumulator.intValue()));
        longAccumulator.accumulate(1);
        log.info(String.valueOf(longAccumulator.intValue()));
    }
}
