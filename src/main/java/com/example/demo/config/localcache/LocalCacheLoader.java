package com.example.demo.config.localcache;

import java.util.function.Supplier;

public interface LocalCacheLoader<T> {
    /**
     * 类别
     */
    String category();

    Supplier<T> getValueSupplier();
}
