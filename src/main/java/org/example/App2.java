package org.example;

import org.example.entities.BookEntity;
import org.example.entities.ProductEntity;
import org.example.entities.TicketEntity;
import org.example.repositories.InMemoryProductRepository;
import org.example.repositories.Repository;

import java.util.List;
import java.util.Optional;

/**
 * A demonstration application that performs CRUD (Create, Read, Update, Delete)
 * operations on Book and Ticket entities using an in-memory repository.
 * This class showcases how the business logic interacts with the Repository abstraction,
 * decoupled from the actual data storage mechanism.
 */
public class App2 {

    public void run() {
        // 1. Instantiate the in-memory repository.
        // Notice we code to the interface (Repository), not the implementation (InMemoryProductRepository).
        Repository<ProductEntity> productRepository = new InMemoryProductRepository();

        System.out.println("--- CRUD Operations using InMemoryProductRepository ---\n");

        // CREATE Operations
        System.out.println("--- 1. CREATE ---");
        BookEntity book1 = new BookEntity("The Hobbit", 19.99, 50, "978-0345339683", "A classic fantasy novel.", "J.R.R. Tolkien");
        TicketEntity ticket1 = new TicketEntity();
        ticket1.setDescription("Concert Ticket: The Rolling Stones");
        ticket1.setPrice(150.75);

        // Save the entities using the repository
        ProductEntity savedBook = productRepository.save(book1);
        ProductEntity savedTicket = productRepository.save(ticket1);

        System.out.println("Saved Product: " + savedBook);
        System.out.println("Saved Product: " + savedTicket);
        System.out.println("\n");


        // READ Operations
        System.out.println("--- 2. READ ---");
        // Read all products
        List<ProductEntity> allProducts = productRepository.findAll();
        System.out.println("Found " + allProducts.size() + " products in total:");
        allProducts.forEach(System.out::println);

        // Read a single product by ID
        System.out.println("\nFinding product with ID: " + savedBook.getId());
        Optional<ProductEntity> foundProductOptional = productRepository.findById(savedBook.getId());
        if (foundProductOptional.isPresent()) {
            ProductEntity foundProduct = foundProductOptional.get();
            System.out.println("Found Product: " + foundProduct);
        } else {
            System.out.println("Product not found!");
        }
        System.out.println("\n");


        // UPDATE Operation
        System.out.println("--- 3. UPDATE ---");
        // Let's update the book's price and copy count
        if (foundProductOptional.isPresent() && foundProductOptional.get() instanceof BookEntity) {
            BookEntity bookToUpdate = (BookEntity) foundProductOptional.get();
            System.out.println("Original book price: " + bookToUpdate.getPrice());
            bookToUpdate.setPrice(24.99); // Increase the price
            bookToUpdate.setCopies(49);   // One copy was sold
            productRepository.save(bookToUpdate); // The save method handles updates too
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
        System.out.println("Attempting to find deleted ticket (ID: " + savedTicket.getId() + ")");
        Optional<ProductEntity> deletedTicketOptional = productRepository.findById(savedTicket.getId());
        if (deletedTicketOptional.isEmpty()) {
            System.out.println("Ticket successfully deleted, not found.");
        } else {
            System.out.println("Error: Ticket was not deleted.");
        }
    }

    public static void main(String[] args) {
        new App2().run();
    }
}