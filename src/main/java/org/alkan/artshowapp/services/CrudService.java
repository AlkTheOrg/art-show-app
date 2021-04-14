package org.alkan.artshowapp.services;

import java.util.Set;

public interface CrudService<T, ID> {

    Set<T> findAll();

    T findById(ID id);

    T findByName(String name);

    T save(T object);

    void delete(T object);

    void deleteById(ID id);
}
