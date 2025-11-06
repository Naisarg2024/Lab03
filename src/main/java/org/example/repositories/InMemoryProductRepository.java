package org.example.repositories;

import org.example.entities.ProductEntity;

import java.util.*;

/**
 * An in-memory implementation of the Repository interface for ProductEntity.
 * This class uses a HashMap to store data and is primarily intended for
 * testing and development purposes, allowing the business logic to be tested
 * without a live database connection.
 */
public class InMemoryProductRepository implements Repository<ProductEntity> {

    private final Map<Long, ProductEntity> products = new HashMap<>();
    private Long nextId = 1L;

    /**
     * Retrieves a ProductEntity by its ID from the in-memory map.
     *
     * @param id The ID of the entity to find.
     * @return an Optional containing the found entity, or an empty Optional if not found.
     */
    @Override
    public Optional<ProductEntity> findById(Long id) {
        // The .ofNullable() factory method conveniently handles cases where the product might not exist.
        return Optional.ofNullable(products.get(id));
    }

    /**
     * Retrieves all ProductEntity objects from the in-memory map.
     *
     * @return a List of all stored entities.
     */
    @Override
    public List<ProductEntity> findAll() {
        return new ArrayList<>(products.values());
    }

    /**
     * Saves or updates a ProductEntity in the in-memory map.
     * If the entity's ID is null, a new ID is generated and assigned.
     *
     * @param entity The entity to save or update.
     * @return the saved entity with its ID assigned.
     */
    @Override
    public ProductEntity save(ProductEntity entity) {
        if (entity.getId() == null || entity.getId() == 0L) {
            // This is a new entity, so we generate and set its ID.
            entity.setId(nextId++);
        }
        // Add the new or updated entity to our map.
        products.put(entity.getId(), entity);
        return entity;
    }

    /**
     * Deletes a ProductEntity by its ID from the in-memory map.
     *
     * @param id The ID of the entity to delete.
     */
    @Override
    public void deleteById(Long id) {
        products.remove(id);
    }

    /**
     * Helper method for tests to clear all data from the repository.
     */
    public void clear() {
        products.clear();
        nextId = 1L;
    }
}