package producer;

import bl.Book;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import queue.FullException;
import queue.Queue;

public class BookProducer implements Runnable{

    private Queue<Book> bookQueue;

    public BookProducer(Queue<Book> bookQueue) {
        this.bookQueue = bookQueue;
    }
    
    @Override
    public void run() {
        while(true){
            String filename = JOptionPane.showInputDialog("Filename: ");
            
            synchronized(bookQueue){
                try {
                    bookQueue.put(new Book(filename, ""));
                    bookQueue.notifyAll();
                } catch (FullException ex) {
                    try {
                        bookQueue.wait();
                    } catch (InterruptedException ex1) {
                        Logger.getLogger(BookProducer.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
    }

}
