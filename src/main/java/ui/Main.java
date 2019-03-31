package ui;

import bl.Book;
import consumer.BookConsumer;
import producer.BookProducer;
import queue.Queue;

public class Main {

    public static void main(String[] args) {
        
        Queue<Book> queue = new Queue<>(3);
        
        BookProducer prod1 = new BookProducer(queue, "Producer 1");
        new Thread(prod1, "Producer 1").start();
        
        BookConsumer con1 = new BookConsumer(queue, "Consumer 1");
        new Thread(con1, "Consumer 1").start();
    }

}
