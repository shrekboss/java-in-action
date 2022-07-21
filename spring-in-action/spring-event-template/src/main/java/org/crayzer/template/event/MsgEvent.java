package org.crayzer.template.event;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 自定义事件
 *
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
public class MsgEvent {

    /** 该类型事件携带的信息 */
    public String orderId;
}
