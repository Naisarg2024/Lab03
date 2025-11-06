package org.example.services;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entities.BookEntity;
import org.example.entities.ProductEntity;
import org.example.entities.TicketEntity;
import org.example.repositories.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ProductService.
 * These tests use an InMemoryProductRepository to test the service's business logic
 * in complete isolation from any database or external dependencies.
 */
class ProductServiceTest {

    private ProductService productService;
    private InMemoryProductRepository inMemoryRepository;

    // This method runs before each test, ensuring a clean state.
    @BeforeEach
    void setUp() {
        // Arrange: Create a fresh repository and service for each test.
        inMemoryRepository = new InMemoryProductRepository();
        productService = new ProductService(inMemoryRepository);
    }

//    @Test
//    @DisplayName("Should create and save a book successfully")
//    void testCreateBook() {
//        // Act: Call the service method to create a book.
//        BookEntity createdBook = productService.createBook(
//                "The Hobbit", 19.99, 50, "978-0345339683", "A classic fantasy novel.", "J.R.R. Tolkien"
//        );
//
//        // Assert: Verify the results.
//        assertNotNull(createdBook, "The created book should not be null.");
//        assertNotNull(createdBook.getId(), "The book's ID should be generated and not null.");
//        assertEquals("The Hobbit", createdBook.getTitle(), "The book title should match.");
//
//        // Verify it was actually saved in the repository
//        List<ProductEntity> products = productService.getAllProducts();
//        assertEquals(1, products.size(), "There should be exactly one product in the repository.");
//        assertTrue(products.get(0) instanceof BookEntity, "The saved product should be a BookEntity.");
//    }

    @Test
    @DisplayName("Should create and save a ticket successfully")
    void testCreateTicket() {
        // Act: Call the service method.
        TicketEntity createdTicket = productService.createTicket("Concert Ticket: The Rolling Stones", 150.75);

        // Assert: Verify the results.
        assertNotNull(createdTicket);
        assertNotNull(createdTicket.getId());
        assertEquals(150.75, createdTicket.getPrice());
        assertEquals("Concert Ticket: The Rolling Stones", createdTicket.getDescription());

        // Verify it was saved
        assertEquals(1, productService.getAllProducts().size(), "There should be one product after saving a ticket.");
    }

    @Test
    @DisplayName("Should retrieve all products correctly")
    void testGetAllProducts() {
        // Arrange: Create and save two different products.
        productService.createBook("Book 1", 10.0, 10, "1", "d1", "a1");
        productService.createTicket("Ticket 1", 20.0);

        // Act: Get all products.
        List<ProductEntity> allProducts = productService.getAllProducts();

        // Assert: Check the size of the returned list.
        assertNotNull(allProducts);
        assertEquals(2, allProducts.size(), "Should return a list containing two products.");
    }

    @Test
    @DisplayName("Should find a product by its correct ID")
    void getProductById_WhenFound() {
        // Arrange: Create a product to ensure it has an ID.
        BookEntity book = productService.createBook("Find Me", 1.0, 1, "1", "d1", "a1");

        // Act: Try to find the product by the ID that was just generated.
        Optional<ProductEntity> foundProduct = productService.getProductById(book.getId());

        // Assert
        assertTrue(foundProduct.isPresent(), "The Optional should contain a product.");
        assertEquals(book.getId(), foundProduct.get().getId(), "The ID of the found product should match.");
    }

    @Test
    @DisplayName("Should return an empty Optional for a non-existent ID")
    void getProductById_WhenNotFound() {
        // Act: Try to find a product with an ID that doesn't exist.
        Optional<ProductEntity> foundProduct = productService.getProductById(999L);

        // Assert
        assertTrue(foundProduct.isEmpty(), "The Optional should be empty for a non-existent ID.");
    }

    @Test
    @DisplayName("Should delete a product successfully")
    void testDeleteProduct() {
        // Arrange: Create two products so we can delete one.
        BookEntity bookToDelete = productService.createBook("Delete Me", 1.0, 1, "1", "d1", "a1");
        TicketEntity ticketToKeep = productService.createTicket("Keep Me", 2.0);

        // Pre-condition check
        assertEquals(2, productService.getAllProducts().size(), "Should have two products before deletion.");

        // Act: Delete the book.
        productService.deleteProduct(bookToDelete.getId());

        // Assert: Verify the final state.
        List<ProductEntity> remainingProducts = productService.getAllProducts();
        assertEquals(1, remainingProducts.size(), "Should have only one product remaining.");
        assertEquals(ticketToKeep.getId(), remainingProducts.get(0).getId(), "The remaining product should be the ticket.");

        // Also assert that the deleted product cannot be found anymore
        Optional<ProductEntity> deletedProduct = productService.getProductById(bookToDelete.getId());
        assertTrue(deletedProduct.isEmpty(), "The deleted book should not be findable.");
    }
}