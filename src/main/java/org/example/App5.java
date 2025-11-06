package org.example;

import java.util.List;
import org.example.entities.BookEntity;
import org.example.entities.ProductEntity;
import org.example.repositories.H2ProductRepository;
import org.example.repositories.MySQLProductRepository;
import org.example.services.ProductService;

public class App5 {
    public void run() {
        System.out.println("======================================================================");
        System.out.println("  DEMONSTRATING THE SERVICE LAYER WITH H2 (IN-MEMORY DATABASE)        ");
        System.out.println("======================================================================");

        // --- Step 1: Use the Service with the H2 Repository ---
        H2ProductRepository h2Repository = new H2ProductRepository();
        // Inject the H2 repository into our service.
        ProductService h2ProductService = new ProductService(h2Repository);

        // Use the service to perform business operations
        System.out.println("\n[SERVICE] Creating a book in the H2 database...");
        BookEntity h2Book = h2ProductService.createBook("1984", 15.99, 30, "978-0451524935", "A dystopian novel.", "George Orwell");
        System.out.println("[H2 REPO] Saved book with ID: " + h2Book.getId());

        System.out.println("\n[SERVICE] Getting all products from the H2 database...");
        List<ProductEntity> h2Products = h2ProductService.getAllProducts();
        System.out.println("[H2 REPO] Found " + h2Products.size() + " product(s).");
        h2Products.forEach(p -> System.out.println("  -> " + p.toString()));

        // Clean up the H2 repository
        h2Repository.close();
        System.out.println("\n[H2 REPO] H2 repository has been closed.");


        System.out.println("\n\n======================================================================");
        System.out.println("  DEMONSTRATING THE SERVICE LAYER WITH MYSQL (PRODUCTION DATABASE)    ");
        System.out.println("======================================================================");

        // --- Step 2: Use the exact SAME Service logic with the MySQL Repository ---
        MySQLProductRepository mySQLRepository = new MySQLProductRepository();
        // Inject the MySQL repository into a new service instance.
        ProductService mySQLProductService = new ProductService(mySQLRepository);

        // Use the service to perform the same business operations
        System.out.println("\n[SERVICE] Creating a book in the MySQL database...");
        BookEntity mySQLBook = mySQLProductService.createBook("The Martian", 18.50, 75, "978-0804139021", "A man stranded on Mars.", "Andy Weir");
        System.out.println("[MySQL REPO] Permanently saved book with ID: " + mySQLBook.getId());

        System.out.println("\n[SERVICE] Getting all products from the MySQL database...");
        List<ProductEntity> mySQLProducts = mySQLProductService.getAllProducts();
        System.out.println("[MySQL REPO] Found " + mySQLProducts.size() + " product(s).");
        mySQLProducts.forEach(p -> System.out.println("  -> " + p.toString()));

        System.out.println("\n[SERVICE] Deleting the book we just created from MySQL...");
        mySQLProductService.deleteProduct(mySQLBook.getId());
        System.out.println("[MySQL REPO] Book with ID " + mySQLBook.getId() + " deleted.");

        // Clean up the MySQL repository
        mySQLRepository.close();
        System.out.println("\n[MySQL REPO] MySQL repository has been closed.");
    }
}
