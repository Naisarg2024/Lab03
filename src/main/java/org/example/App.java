//package org.example;
//
//import java.util.Scanner;
//
//public class App {
//    private final Scanner scanner = new Scanner(System.in);
//
//    public void run() {
//        boolean running = true;
//        while (running) {
//            System.out.println("********************");
//            System.out.println(" 1. Add Items");
//            System.out.println(" 2. Edit Items");
//            System.out.println(" 3. Delete Items");
//            System.out.println(" 4. Sell item(s)");
//            System.out.println(" 5. List items");
//            System.out.println("99. Quit");
//            System.out.println("********************");
//            System.out.print("Enter choice: ");
//            String choice = scanner.nextLine().trim();
//            switch (choice) {
//                case "1" -> MenuItem();
//                case "2" -> System.out.println("... Edit Items functionality will be implemented here ...");
//                case "3" -> System.out.println("... Delete Items functionality will be implemented here ...");
//                case "4" -> System.out.println("... Sell items functionality will be implemented here ...");
//                case "5" -> System.out.println("... List items functionality will be implemented here ...");
//                case "99" -> {
//                    System.out.println("Exiting...");
//                    running = false;
//                }
//                default -> System.out.println("Invalid choice, (valid options are 1 to 5 and 99 to quit)");
//            }
//        }
//    }
//
//    private void MenuItem() {
//        boolean flag = false;
//        while (!flag) {
//            System.out.println("\nAdd an item");
//            System.out.println("1. Add a Book");
//            System.out.println("2. Add a Magazine");
//            System.out.println("3. Add a DiscMag");
//            System.out.println("4. Add a Ticket");
//            System.out.println("99. Exit app");
//            System.out.print("Enter choice: ");
//            String c = scanner.nextLine().trim();
//            switch (c) {
//                case "1" -> System.out.println("....Add Book functionality will be implemented here....");
//                case "2" -> System.out.println("....Add Magazine functionality will be implemented here....");
//                case "3" -> System.out.println("....Add DiscMag functionality will be implemented here....");
//                case "4" -> System.out.println("....Add Ticket functionality will be implemented here....");
//                case "99" -> {
//                    System.out.println("Redirecting to main menu...");
//                    flag = true;
//                }
//                default -> System.out.println("Invalid choice (valid options are 1 to 4 and 99 to exit)");
//            }
//        }
//    }
//}
package org.example;

import org.example.entities.BookEntity;
import org.example.entities.ProductEntity;
import org.example.entities.PublicationEntity;
import jakarta.persistence.*;

import java.util.List;

public class App {
    private EntityManagerFactory emf;

    public void run(){
        // Create the EntityManagerFactory using the persistence unit name from persistence.xml
        emf = Persistence.createEntityManagerFactory("product-pu");
        populateDatabase();
        listAllProducts();
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

            em.persist(b);

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