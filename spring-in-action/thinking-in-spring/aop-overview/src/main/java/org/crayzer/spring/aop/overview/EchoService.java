package org.crayzer.spring.aop.overview;

/**
 * Echo 服务
 *
 * @author crayzer
 */
public interface EchoService {

    String echo(String message) throws NullPointerException;
}