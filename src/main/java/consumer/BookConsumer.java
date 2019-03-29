package consumer;

import bl.Book;
import queue.EmptyException;
import queue.Queue;

public class BookConsumer implements Runnable{

    private Queue<Book> bookQueue;

    public BookConsumer(Queue<Book> bookQueue) {
        this.bookQueue = bookQueue;
    }
    
    
    @Override
    public void run() {
        while(true){
            Book currentB = null;
            synchronized(bookQueue){
                try {
                    currentB = bookQueue.get();
                    bookQueue.notifyAll();
                } catch (EmptyException ex) {
                    
                }
            }
        }
    }

}
