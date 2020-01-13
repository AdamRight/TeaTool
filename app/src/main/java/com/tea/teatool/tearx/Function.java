package com.tea.teatool.tearx;

public interface Function<T,R> {
    R apply(T t) throws Exception;
}
