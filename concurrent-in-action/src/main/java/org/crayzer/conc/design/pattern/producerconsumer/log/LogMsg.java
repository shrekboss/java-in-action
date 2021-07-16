package org.crayzer.conc.design.pattern.producerconsumer.log;

public class LogMsg {
    LEVEL level;
    String msg;
    //省略构造函数实现
    LogMsg(LEVEL lvl, String msg){}
    //省略toString()实现
    @Override
    public String toString(){return "";}
}
