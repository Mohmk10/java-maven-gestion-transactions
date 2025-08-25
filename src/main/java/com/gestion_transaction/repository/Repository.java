package com.gestion_transaction.repository;

import java.util.List;

public interface Repository<T> {
    public T save(T entity);
    public T findById(int id);
    public List<T> findAll();
}
