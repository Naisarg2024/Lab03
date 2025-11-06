package org.example;

import org.example.entities.BookEntity;
import org.example.entities.MagazineEntity;
import org.example.entities.ProductEntity;
import jakarta.persistence.*;
import org.example.entities.TicketEntity;

import java.time.LocalDate;
import java.util.List;

public class App {
    private EntityManagerFactory emf;

    public void run(){
        // Create the EntityManagerFactory using the persistence unit name from persistence.xml
        emf = Persistence.createEntityManagerFactory("product-pu");
        // CRUD operations testing
        populateDatabase();
        listAllProducts();
        listAllBooks();
        listAllTickets();
        updateMagazine(4L,
                "Tech Monthly (Updated)",
                10.99,
                "Updated issue for November",
                30,
                LocalDate.of(2025, 11, 5)
        );
        deleteProduct(4L);
    }

    public void populateDatabase() {
        // EntityManager is used to interact with the persistence context
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); // Start a transaction
//
            System.out.println("Populating database with products...");

            BookEntity b = new BookEntity(
                    "String title",
                    29.99,
                    10,
                    "String isbn_10",
                    "String description",
                    "String author");
            BookEntity b2 = new BookEntity(
                    "NJP",
                    19.99,
                    100,
                    "ISBN440832",
                    "A book written by Naisarg",
                    "Naisarg Patel"
            );
            TicketEntity t=new TicketEntity("NJP",
                    19.99,
                    100,
                    440832,
                    "A book written by Naisarg",
                    "Naisarg Patel");
            MagazineEntity m = new  MagazineEntity(2L,
                    "NJP Magazine",
                    5.99,
                    10,
                    "A magazine by NJP",1, LocalDate.of(2025,11,5)
                    );
            em.persist(b);
            em.persist(b2);
            em.persist(t);
            em.persist(m);
            tx.commit(); // Commit the transaction
            System.out.println("Population complete.");
        } catch (Exception e) {
            // If anything goes wrong, roll back the transaction
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            // Always close the EntityManager
            em.close();
        }
    }

    // list all types of products
    private void listAllProducts() {
        EntityManager em = emf.createEntityManager();
        try {
            List<ProductEntity> results = getAllProducts(em);
            System.out.println("Found " + results.size() + " items.");
            for (ProductEntity p : results) {
                System.out.println(p);
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }finally {
            em.close();
        }
    }

    // listing all books
    private void listAllBooks() {
        EntityManager em = emf.createEntityManager();
        try {
            List<BookEntity> books =
                    em.createQuery("SELECT b FROM BookEntity b", BookEntity.class)
                            .getResultList();
            System.out.println("\nList of Books:");
            System.out.println("---------------------");
            for (BookEntity b : books) {
                System.out.println(b);
            }
        } finally {
            em.close();
        }
    }

    // listing all tickets
    private void listAllTickets() {
        EntityManager em = emf.createEntityManager();
        try {
            List<TicketEntity> tickets =
                    em.createQuery("SELECT t FROM TicketEntity t", TicketEntity.class)
                            .getResultList();
            System.out.println("\nList of Tickets:");
            System.out.println("-------------------");
            for (TicketEntity t : tickets) {
                System.out.println(t);
            }
        } finally {
            em.close();
        }
    }

    // update operation
    public void updateMagazine(Long id, String newTitle, Double newPrice, String newDescription, Integer newOrderQty, LocalDate newCurrentIssue) {

        if (id == null) return;
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            MagazineEntity m = em.find(MagazineEntity.class, id);
            if (m != null) {
                if (newTitle != null) m.setTitle(newTitle);
                if (newPrice != null) m.setPrice(newPrice);
                if (newDescription != null) m.setDescription(newDescription);
                if (newOrderQty != null) m.setOrderQty(newOrderQty);
                if (newCurrentIssue != null) m.setCurrentIssue(newCurrentIssue);
            } else {
                System.out.println("Magazine with id " + id + " not found.");
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // delete operation
    public void deleteProduct(Long id) {
        if (id == null) return;

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            ProductEntity p = em.find(ProductEntity.class, id);
            if (p != null) {
                em.remove(p);
                System.out.println("Deleted product with id " + id);
            } else {
                System.out.println("Product with id " + id + " not found.");
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Queries the database for all entities that are part of the PublicationEntity
     * inheritance hierarchy. Because PublicationEntity extends EditableEntity, this
     * effectively retrieves all editable items.
     *
     * @param em The EntityManager to use for the query.
     * @return A list of all PublicationEntity instances (including Books, Magazines, etc.).
     */
    public List<ProductEntity> getAllProducts(EntityManager em) {
        System.out.println("\n--- Querying for all editable publications ---");

        // 1. The JPQL query is written against the root @Entity class, not the @MappedSuperclass.
        //    JPA will automatically fetch all subclasses (like BookEntity).
        String jpql = "SELECT p FROM ProductEntity p";

        // 2. We create a TypedQuery to ensure the results are cast to the correct base type.
        TypedQuery<ProductEntity> query = em.createQuery(jpql, ProductEntity.class);

        // 3. Execute the query to get the list of results.
        return query.getResultList();
    }
}