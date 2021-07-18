package org.crayzer.conc.apm;

import lombok.Data;

/**
 * 主子线程间传递
*/
@Data
public class AsyncTaskHolder {

    // 用于发送调用信息的 Kafka 客户端
    // private CallableInfoSender callableInfoSender = new CallInfoKafkaSender(...);
    private long traceID = -1;
    private long spanID = -1;
    private long parentSpanID= -1;
    // 临时保存当前线程的 traceID 和 spanID、parentSpanID
    private long prevTraceID = -1;
    private long prevSpanID = -1;
    private long prevParentSpanID = -1;
    // 原生异步线程任务
    private volatile Runnable task;

    public AsyncTaskHolder(Runnable runnable) {
        this.task = runnable;
    }

    final public void run() {
        try {
            // 进入异步线程处理，讲 SpanID 进一步传递下去，并保存原来的 SpanID
            beforeRun();
        } catch (Throwable t) {
            // 打印异常日志，吃掉异常，不能让新增的功能影响原来的功能
        }
        try {
            task.run();
        } finally {
            try {
                // 退出异步线程处理，回复原来的 SpanID
                afterRun();
            } catch (Throwable t) {
                // 打印异常日志，吃掉异常，不能让新增的功能影响原来的功能
            }
        }
    }

    public void beforeSubmit() {
        // 当前线程的 TraceID、SpanID 和 parentSpanID 传递给即将要执行的任务线程
        traceID = getTraceID();
        spanID = getSpanID();
        parentSpanID = getParentSpanID();
    }

    private void afterRun() {
        traceID = prevTraceID;
        spanID = prevSpanID;
        parentSpanID = prevParentSpanID;
    }

    public Runnable getTask() {
        return task;
    }

    public void setTaSK(Runnable task) {
        this.task = task;
    }

    public void beforeRun() {
        // 在子线程执行任务前，在任务对象中保存父线程的 tranceID、spanID 和 parentSpanID
        prevTraceID = traceID;
        prevSpanID = spanID;
        prevParentSpanID = parentSpanID;
        // 在子线程执行任务前，生成新的 traceID、spanID 和 parentSpanID，其中 traceID 不变， parentSpanID 为父线程的 spanID，生成新的 spanID
        setTraceID(traceID);
        setSpanID(getNewSpanID(spanID));
        setParentSpanID(spanID);
        // long ip = getHostIp();
        // callInfoSender.sender(Thread.currentThread().getId(), RPCPhase.SIB, traceID, spanID, parentSpanID, ip);
    }

    private long getNewSpanID(long spanID) {
        // todo
        return 0;
    }

    public void beforeInvoke() {

    }
}
