package org.example.repositories;

import org.example.entities.ProductEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

/**
 * A concrete implementation of the Repository interface that uses JPA and a MySQL database.
 * This repository is intended for production use, persisting data to a permanent database store.
 * It handles the lifecycle of the EntityManager and transactions for each operation.
 */
public class MySQLProductRepository implements Repository<ProductEntity> {

    private final EntityManagerFactory emf;

    /**
     * Constructs the repository and initializes the EntityManagerFactory for the "default"
     * persistence unit, which is configured for the MySQL database.
     */
    public MySQLProductRepository() {
        // The name "default" must match the persistence-unit name for MySQL in persistence.xml
        this.emf = Persistence.createEntityManagerFactory("default");
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            ProductEntity entity = em.find(ProductEntity.class, id);
            return Optional.ofNullable(entity);
        }
    }

    @Override
    public List<ProductEntity> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            // This JPQL query is identical to the one used in the H2 repository.
            return em.createQuery("SELECT p FROM ProductEntity p", ProductEntity.class)
                    .getResultList();
        }
    }

    @Override
    public ProductEntity save(ProductEntity entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            // Merge handles both new entities (INSERT) and existing entities (UPDATE).
            ProductEntity savedEntity = em.merge(entity);
            em.getTransaction().commit();
            return savedEntity;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public void deleteById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            // Find the managed entity first before attempting to remove it.
            ProductEntity entityToRemove = em.find(ProductEntity.class, id);
            if (entityToRemove != null) {
                em.remove(entityToRemove);
            }
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    /**
     * Closes the EntityManagerFactory to release all database connections and resources.
     * This is a crucial step for shutting down the application cleanly.
     */
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}