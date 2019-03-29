package consumer;

import bl.Book;
import queue.Queue;

public class BookConsumer implements Runnable{

    private Queue<Book> bookQueue;

    public BookConsumer(Queue<Book> bookQueue) {
        this.bookQueue = bookQueue;
    }
    
    
    @Override
    public void run() {

    }

}
