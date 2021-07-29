package org.crayzer.java8.lambda.example.prodcut.cache;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ProductAndCacheLambda {

    private Map<Long, Product> cache = new ConcurrentHashMap<>();

    @Test
    public void coolCache() {
        getProductAndCacheCool(1L);
        getProductAndCacheCool(100L);
        System.out.println(cache);
        assertThat(cache.size(), is(1));
        assertTrue(cache.containsKey(1L));
    }

    private Product getProductAndCacheCool(Long id) {
        return cache.computeIfAbsent(id, i -> Product.getData().stream()
                .filter(p -> p.getId().equals(i))
                .findFirst()
                .orElse(null));
    }
}
