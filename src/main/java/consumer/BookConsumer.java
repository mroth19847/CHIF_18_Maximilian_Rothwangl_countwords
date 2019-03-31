package consumer;

import bl.Book;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import queue.EmptyException;
import queue.Queue;

public class BookConsumer implements Runnable {

    private Queue<Book> bookQueue;

    public BookConsumer(Queue<Book> bookQueue) {
        this.bookQueue = bookQueue;
    }

    @Override
    public void run() {
        while (true) {
            Book currentB = null;
            synchronized (bookQueue) {
                try {
                    currentB = bookQueue.get();
                    bookQueue.notifyAll();
                } catch (EmptyException ex) {
                    try {
                        bookQueue.wait();
                    } catch (InterruptedException ex1) {
                        Logger.getLogger(BookConsumer.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    continue;
                }
            }

            outputResults(currentB, currentB.countWords());
        }
    }

    private void outputResults(Book b, HashMap<String, Integer> map) {
        System.out.println("Book: " + b.getInputfilename());
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(BookConsumer.class.getName()).log(Level.SEVERE, null, ex);
            }
            Map.Entry pair = (Map.Entry) it.next();
            if ((Integer) pair.getValue() >= 2 && !pair.getKey().equals("")) {
                System.out.println(pair.getKey() + " : " + pair.getValue());
            }
            it.remove();
        }
    }

}
