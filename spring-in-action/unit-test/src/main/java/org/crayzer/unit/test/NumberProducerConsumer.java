package org.crayzer.unit.test;

import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class NumberProducerConsumer extends ProducerConsumer<Integer> {

    private final Supplier<Integer> numberGenerator;

    private final Consumer<Integer> numberConsumer;

    public NumberProducerConsumer(Executor executor,
                                  Supplier<Integer> numberGenerator,
                                  Consumer<Integer> numberConsumer) {
        super(executor);
        this.numberGenerator = numberGenerator;
        this.numberConsumer = numberConsumer;
    }

    @Override
    void produce() {
        for (int i = 0; i < 10; i++) {
            produceInner(i + numberGenerator.get());
        }
    }

    @Override
    void consume() {
        while (true) {
            Integer result = consumeInner();
            numberConsumer.accept(result);
        }
    }
}
