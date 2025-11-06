package org.example;
import org.example.entities.BookEntity;
import org.example.entities.ProductEntity;
import org.example.entities.TicketEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App1 {
    private EntityManagerFactory emf=null;
    private EntityManager em=null;
    public void run() {
        try{
            emf= Persistence.createEntityManagerFactory("default");
            createBook(emf);
            createTicket(emf);
            List<ProductEntity> products=getProducts();
            System.out.println("\n------------------\nList of ProductEntities\n------------------");
            for(Object product:products){
                System.out.println(product);
            }


        }catch(Exception e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            if(emf!=null)
                emf.close();
//            if(em!=null)
//                em.close();
        }
    }

    private List getProducts() {
        em=emf.createEntityManager();
        // confirm added
        return em.createQuery("SELECT c FROM ProductEntity c").getResultList();
    }
    private void createTicket(EntityManagerFactory emf) {
        em = emf.createEntityManager();
        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Entity Manager created (" + emf + ")");
        em.getTransaction().begin();
        TicketEntity t=new TicketEntity();
        t.setDescription("This is a ticket for a soo greyhound game");
        em.persist(t);
        em.getTransaction().commit();
    }
    private void createBook(EntityManagerFactory emf) {
        em=emf.createEntityManager();
        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Entity Manager created ("+emf+")");
        em.getTransaction().begin();
        BookEntity b=new BookEntity();
        b.setTitle("Title");
        b.setIsbn_10("isbn1234");
        b.setAuthor("Fred Carella");
        b.setDescription("Book Description");
        b.setCopies(10);
        b.setPrice(19.99);
        em.persist(b);
        em.getTransaction().commit();
    }
}
