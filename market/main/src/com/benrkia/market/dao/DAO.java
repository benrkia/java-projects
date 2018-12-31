package com.benrkia.market.dao;

import java.util.Collection;

public interface DAO<T> {

    T get(long id);

    Collection<T> getAll();

    int add(T t);

    void update(long id, T t);

    void update(T t);

    void delete(long id);

    void delete(T t);

}
