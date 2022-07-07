package com.sni.jdbc.dao;

import java.util.Optional;

public interface Dao<T, ID> {
    <S extends T> S save(S entity);
    <S extends T> Iterable<S> saveAll(Iterable<S> entity);
    Optional<T> findOne(ID id);
    Iterable<T> findAll();
    boolean delete(T entity);
    boolean deleteAll();
}
