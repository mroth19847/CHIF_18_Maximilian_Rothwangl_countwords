package producer;

import bl.Book;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import queue.FullException;
import queue.Queue;

public class BookProducer implements Runnable {

    private Queue<Book> bookQueue;

    public BookProducer(Queue<Book> bookQueue) {
        this.bookQueue = bookQueue;
    }

    @Override
    public void run() {
        while (true) {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle(Thread.currentThread().getName() + ": Select File");
            fc.setCurrentDirectory(new File("./src/main/java/files"));
            BufferedReader reader;
            String text = "";
            String filename = "";
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                filename = fc.getSelectedFile().getAbsolutePath();
                try {
                    reader = new BufferedReader(new FileReader(filename));
                    String line = reader.readLine();
                    while (line != null) {
                        text += line;
                        line = reader.readLine();
                    }
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "File not found!");
                    continue;
                } catch (IOException ex) {
                    Logger.getLogger(BookProducer.class.getName()).log(Level.SEVERE, null, ex);
                }

                synchronized (bookQueue) {
                    try {
                        bookQueue.put(new Book(filename, text));
                        System.out.println("Book added to queue: " + filename);
                        bookQueue.notifyAll();
                    } catch (FullException ex) {
                        try {
                            bookQueue.wait();
                        } catch (InterruptedException ex1) {
                        }
                    }
                }
            }

        }
    }

}
