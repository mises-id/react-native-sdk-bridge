package com.reactlibrary;

import androidx.core.util.Supplier;

@FunctionalInterface
public interface ExceptionSupplier<T> {
    T get() throws Exception;
}
