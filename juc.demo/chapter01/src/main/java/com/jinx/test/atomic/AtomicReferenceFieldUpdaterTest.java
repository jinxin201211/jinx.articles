package com.jinx.test.atomic;

import com.jinx.test.utils.JinxLogger;
import com.jinx.test.utils.LoggerFactory;
import com.jinx.test.utils.TestUtil;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicReferenceFieldUpdaterTest {
    private final static JinxLogger log = LoggerFactory.getLogger(AtomicReferenceFieldUpdaterTest.class);

    public static void main(String[] args) {
        UpdateReference updateReference = new UpdateReference();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                updateReference.init(updateReference);
            }, "thread-" + (i + 1)).start();
        }
    }
}

class UpdateReference {
    private final static JinxLogger log = LoggerFactory.getLogger(UpdateReference.class);

    public volatile Boolean inited = Boolean.FALSE;

    AtomicReferenceFieldUpdater<UpdateReference, Boolean> initedUpdater = AtomicReferenceFieldUpdater.newUpdater(UpdateReference.class, Boolean.class, "inited");

    public void init(UpdateReference updateReference) {
        if (initedUpdater.compareAndSet(updateReference, Boolean.FALSE, Boolean.TRUE)) {
            log.info("init begin, it will take around 2 seconds to initialize.");
            TestUtil.sleep();
            log.info("init end.");
        } else {
            log.info("this object is initializing in other thread now.");
        }
    }
}

