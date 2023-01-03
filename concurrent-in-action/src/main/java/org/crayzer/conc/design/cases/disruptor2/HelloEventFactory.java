package org.crayzer.conc.design.cases.disruptor2;

import com.lmax.disruptor.EventFactory;

/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class HelloEventFactory  implements EventFactory {
    @Override
    public Object newInstance() {
        return new MessageModel();
    }
}
