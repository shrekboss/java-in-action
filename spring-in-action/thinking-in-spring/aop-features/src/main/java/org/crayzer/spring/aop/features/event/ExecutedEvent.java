
package org.crayzer.spring.aop.features.event;

import org.springframework.context.ApplicationEvent;

/**
 * 动作已执行的事件
 *
 * @author crayzer
 */
public class ExecutedEvent extends ApplicationEvent {

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public ExecutedEvent(Object source) {
        super(source);
    }
}
