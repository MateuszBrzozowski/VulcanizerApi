package pl.mbrzozowski.vulcanizer.service;

import java.util.List;

/**
 * @param <R> The Response Body
 * @param <E> The Entity
 */
public interface ServiceLayer<T, R, E> {

    E save(T t);

    R update(T t);

    List<R> findAll();

    E findById(Long id);

    void deleteById(Long id);
}
