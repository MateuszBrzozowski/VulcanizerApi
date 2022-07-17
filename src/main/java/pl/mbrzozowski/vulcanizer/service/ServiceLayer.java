package pl.mbrzozowski.vulcanizer.service;

import java.util.List;

/**
 * @param <T> The Request Body
 * @param <R> The Response Body
 */
public interface ServiceLayer<T, R> {

    void save(T t);

    R update(T t);

    List<R> findAll();

    R findById(Long id);

    void delete(T t);
}
