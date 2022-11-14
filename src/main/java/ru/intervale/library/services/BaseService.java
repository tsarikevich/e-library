package ru.intervale.library.services;

public interface BaseService<T> {
    T create(T dto);

    T update(T dto);

    boolean delete(Long id);
}
