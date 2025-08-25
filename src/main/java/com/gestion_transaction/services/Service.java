package com.gestion_transaction.services;

import java.util.List;

public interface Service<T> {
    public T add (T entity);
    public List<T> findAll();
}
