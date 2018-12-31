package com.benrkia.bank.dao;

import java.util.Collection;

public interface IDao<T> {

    T get(long id);

    Collection<T> getAll();

}
