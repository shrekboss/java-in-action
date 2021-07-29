package org.crayzer.java8.lambda.example.prodcut.cache;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ProductAndCache {

    private Map<Long, Product> cache = new ConcurrentHashMap<>();

    @Test
    public void notcoolCache() {
        getProductAndCache(1L);
        getProductAndCache(100L);
        System.out.println(cache);
        assertThat(cache.size(), is(1));
        assertTrue(cache.containsKey(1L));
    }

    private Product getProductAndCache(Long id) {
        Product product = null;
        if (cache.containsKey(id)) {
            product = cache.get(id);
        } else {
            for (Product p : Product.getData()) {
                if (p.getId().equals(id)) {
                    product = p;
                    break;
                }
            }
        }

        if (product != null) {
            cache.put(id, product);
        }
        return product;
    }
}
