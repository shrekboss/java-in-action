package org.crayzer.err.coding.connPool.httpclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/httpclientnotreuse")
@Slf4j
public class HttpClientNotReuseController {


    private static CloseableHttpClient httpClient = null;

    static {
        // 当然，也可以把CloseableHttpClient定义为Bean，
        // 然后在@PreDestroy标记的方法内close这个HttpClient
        httpClient = HttpClients.custom()
                .setMaxConnPerRoute(1).setMaxConnTotal(1)
                .evictIdleConnections(60, TimeUnit.SECONDS).build();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                httpClient.close();
            } catch (IOException ignored) {
            }
        }));
    }

    /**
     * 访问这个接口几次后查看应用线程情况，可以看到有大量叫作 Connection evictor 的线程，且这些线程不会销毁
     * <p/>
     * lsof -nP -i4TCP:45678 | wc -l
     * <p/>
     * curl http://localhost:45678/httpclientnotreuse/wrong1
     * <p/>
     * jstack 76200 | grep evictor
     * <p/>
     * wrk -t 1 -c 1 -d 10s http://localhost:45678/httpclientnotreuse/wrong1
     * <p/>
     * lsof -nP -i4TCP:45678 | wc -l
     */
    @GetMapping("/wrong1")
    public String wrong1() {
        // 没有复用 CloseableHttpClient 连接池
        CloseableHttpClient client = HttpClients.custom()
                .setConnectionManager(new PoolingHttpClientConnectionManager())
                .evictIdleConnections(60, TimeUnit.SECONDS).build();
        try (CloseableHttpResponse response = client.execute(new HttpGet("http://localhost:45678/httpclientnotreuse/test"))) {
            return EntityUtils.toString(response.getEntity());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "wrong1";
    }

    /**
     * wrk -t 1 -c 1 -d 10s http://localhost:45678/httpclientnotreuse/wrong2
     */
    // 确保连接池使用完之后关闭
    @GetMapping("/wrong2")
    public String wrong2() {
        // 新建连接池方式应该是每次都会创建新的 TCP 连接
        try (CloseableHttpClient client = HttpClients.custom()
                .setConnectionManager(new PoolingHttpClientConnectionManager())
                .evictIdleConnections(60, TimeUnit.SECONDS).build();
             CloseableHttpResponse response = client.execute(new HttpGet("http://localhost:45678/httpclientnotreuse/test"))) {
            return EntityUtils.toString(response.getEntity());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "wrong2";
    }

    /**
     * wrk -t 1 -c 1 -d 10s http://localhost:45678/httpclientnotreuse/right
     */
    @GetMapping("/right")
    public String right() {
        // 复用连接池方式复用的始终应该是同一个连接
        try (CloseableHttpResponse response = httpClient.execute(new HttpGet("http://localhost:45678/httpclientnotreuse/test"))) {
            return EntityUtils.toString(response.getEntity());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "right";
    }

    @GetMapping("/test")
    public String test() {
        return "OK";
    }
}
