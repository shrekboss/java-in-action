package org.crayzer.spring.aop.overview.pattern;

/**
 * Echo 服务
 *
 * @author crayzer
 */
public interface EchoService {

    String echo(String message) throws NullPointerException;
}