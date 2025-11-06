package org.example;

import java.util.List;
import java.util.Optional;
import org.example.entities.BookEntity;
import org.example.entities.ProductEntity;
import org.example.entities.TicketEntity;
import org.example.repositories.H2ProductRepository;


public class App3 {
    public void run() {
        // 1. Instantiate the H2 repository.
        H2ProductRepository productRepository = new H2ProductRepository();

        try {
            System.out.println("\n--- CRUD Operations using H2ProductRepository (JPA In-Memory DB) ---");
            // You can see the Hibernate/H2 startup logs and SQL statements above this line.

            // CREATE Operations
            System.out.println("\n--- 1. CREATE ---");
            BookEntity book1 = new BookEntity("The Lord of the Rings", 29.99, 25, "978-0618640157", "The complete trilogy.", "J.R.R. Tolkien");
            TicketEntity ticket1 = new TicketEntity();
            ticket1.setDescription("Movie Ticket: Dune Part Two");
            ticket1.setPrice(22.50);

            // Save the entities using the repository
            ProductEntity savedBook = productRepository.save(book1);
            ProductEntity savedTicket = productRepository.save(ticket1);

            System.out.println("Saved Product to H2: " + savedBook);
            System.out.println("Saved Product to H2: " + savedTicket);
            System.out.println("\n");

            // READ Operations
            System.out.println("--- 2. READ ---");
            List<ProductEntity> allProducts = productRepository.findAll();
            System.out.println("Found " + allProducts.size() + " products in H2 database:");
            allProducts.forEach(System.out::println);

            // Read a single product by ID
            System.out.println("\nFinding product with ID: " + savedBook.getId());
            Optional<ProductEntity> foundProductOptional = productRepository.findById(savedBook.getId());
            foundProductOptional.ifPresent(p -> System.out.println("Found Product: " + p));
            System.out.println("\n");

            // UPDATE Operation
            System.out.println("--- 3. UPDATE ---");
            if (foundProductOptional.isPresent() && foundProductOptional.get() instanceof BookEntity) {
                BookEntity bookToUpdate = (BookEntity) foundProductOptional.get();
                System.out.println("Original book price: " + bookToUpdate.getPrice());
                bookToUpdate.setPrice(34.99); // Increase the price
                productRepository.save(bookToUpdate); // The save method handles updates
                System.out.println("Updated book details saved.");

                // Verify the update
                Optional<ProductEntity> updatedBookOptional = productRepository.findById(bookToUpdate.getId());
                updatedBookOptional.ifPresent(p -> System.out.println("Verified Updated Product: " + p));
            }
            System.out.println("\n");

            // DELETE Operation
            System.out.println("--- 4. DELETE ---");
            System.out.println("Deleting ticket with ID: " + savedTicket.getId());
            productRepository.deleteById(savedTicket.getId());

            // Verify the deletion
            System.out.println("Current product count after deletion: " + productRepository.findAll().size());
            Optional<ProductEntity> deletedTicketOptional = productRepository.findById(savedTicket.getId());
            if (deletedTicketOptional.isEmpty()) {
                System.out.println("Ticket successfully deleted, not found in H2.");
            } else {
                System.out.println("Error: Ticket was not deleted.");
            }

        } finally {
            // 5. IMPORTANT: Close the repository to release the database connection and resources.
            System.out.println("\n--- Closing H2 Repository ---");
            productRepository.close();
            System.out.println("Repository closed.");
        }
    }
}
