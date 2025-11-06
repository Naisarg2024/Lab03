package org.example;

import java.util.List;
import java.util.Optional;
import java.util.List;
import java.util.Optional;
import org.example.entities.BookEntity;
import org.example.entities.ProductEntity;
import org.example.entities.TicketEntity;
import org.example.repositories.H2ProductRepository;
import org.example.repositories.MySQLProductRepository;

public class App4 {
    public void run() {
        // 1. Instantiate the MySQL repository.
        MySQLProductRepository productRepository = new MySQLProductRepository();

        try {
            System.out.println("\n--- CRUD Operations using MySQLProductRepository (Permanent Storage) ---");

            // CREATE Operations
            System.out.println("\n--- 1. CREATE ---");
            BookEntity book1 = new BookEntity("Dune", 25.50, 100, "978-0441013593", "A science fiction epic.", "Frank Herbert");
            TicketEntity ticket1 = new TicketEntity();
            ticket1.setDescription("Symphony Orchestra Admission");
            ticket1.setPrice(85.00);

            // Save the entities to the MySQL database
            ProductEntity savedBook = productRepository.save(book1);
            ProductEntity savedTicket = productRepository.save(ticket1);

            System.out.println("Permanently Saved to MySQL: " + savedBook);
            System.out.println("Permanently Saved to MySQL: " + savedTicket);
            System.out.println("\n");

            // READ Operations
            System.out.println("--- 2. READ ---");
            List<ProductEntity> allProducts = productRepository.findAll();
            System.out.println("Found " + allProducts.size() + " products in the MySQL database:");
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
                System.out.println("Original book copy count: " + bookToUpdate.getCopies());
                bookToUpdate.setCopies(99); // Simulate selling one copy
                productRepository.save(bookToUpdate);
                System.out.println("Updated book details saved to MySQL.");

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
                System.out.println("Ticket successfully deleted from MySQL.");
            } else {
                System.out.println("Error: Ticket was not deleted.");
            }

        } finally {
            // 5. IMPORTANT: Close the repository to release the database connection pool.
            System.out.println("\n--- Closing MySQL Repository ---");
            productRepository.close();
            System.out.println("Repository closed.");
        }
    }
}
