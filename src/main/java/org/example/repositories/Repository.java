package org.example.repositories;

import java.util.List;
import java.util.Optional;

/**
 * A generic repository interface that defines standard CRUD (Create, Read, Update, Delete) operations.
 * This establishes a contract for data access layers, abstracting the underlying data source.
 *
 * @param <T> The entity type that this repository manages.
 */
public interface Repository<T> {

    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id The ID of the entity to find. Must not be null.
     * @return an Optional containing the found entity, or an empty Optional if no entity is found.
     */
    Optional<T> findById(Long id);

    /**
     * Retrieves all entities of the specified type.
     *
     * @return a List containing all entities.
     */
    List<T> findAll();

    /**
     * Saves or updates a given entity. If the entity is new (has no ID), it will be created.
     * If it has an ID, it will be updated.
     *
     * @param entity The entity to save or update. Must not be null.
     * @return the saved entity, which may be a new instance with a generated ID.
     */
    T save(T entity);

    /**
     * Deletes an entity by its unique identifier.
     *
     * @param id The ID of the entity to delete. Must not be null.
     */
    void deleteById(Long id);
}