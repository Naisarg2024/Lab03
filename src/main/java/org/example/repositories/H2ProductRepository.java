package org.example.repositories;

import org.example.entities.ProductEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

/**
 * A concrete implementation of the Repository interface that uses JPA and an H2 in-memory database.
 * This repository handles the lifecycle of the EntityManager and transactions for each operation.
 */
public class H2ProductRepository implements Repository<ProductEntity> {

    private final EntityManagerFactory emf;

    /**
     * Constructs the repository and initializes the EntityManagerFactory for the "h2" persistence unit.
     */
    public H2ProductRepository() {
        // The name "h2" must match the persistence-unit name in persistence.xml
        this.emf = Persistence.createEntityManagerFactory("h2");
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
            return em.createQuery("SELECT p FROM ProductEntity p", ProductEntity.class)
                    .getResultList();
        }
    }

    @Override
    public ProductEntity save(ProductEntity entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            // Use merge for both creating and updating. It returns a managed instance.
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
            // First, find the entity to ensure it's managed before trying to remove it.
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
     * Closes the EntityManagerFactory to release database connections and resources.
     * This should be called when the application is shutting down.
     */
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}