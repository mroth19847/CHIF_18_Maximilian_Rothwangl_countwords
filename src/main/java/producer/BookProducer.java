package producer;

import bl.Book;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import queue.FullException;
import queue.Queue;

public class BookProducer extends JFrame implements Runnable {

    private Queue<Book> bookQueue;
    private JLabel statusLabel;
    private JLabel informationLabel;

    public BookProducer(Queue<Book> bookQueue, String name) {
        this.bookQueue = bookQueue;
        this.setSize(200, 200);
        this.setLayout(new GridLayout(3, 1));
        JLabel title = new JLabel();
        title.setText(name);
        title.setFont(new Font("Carstellar", Font.BOLD, 16));
        this.add(title);
        statusLabel = new JLabel();
        statusLabel.setText("Thread added");
        this.add(statusLabel);
        informationLabel = new JLabel();
        informationLabel.setText(" - ");
        this.add(informationLabel);
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
                        statusLabel.setText("Running");
                        bookQueue.put(new Book(filename, text));
//                        System.out.println("Book added to queue: " + filename);
                        informationLabel.setText("Book added to queue: " + filename);
                        bookQueue.notifyAll();
                    } catch (FullException ex) {
                        try {
                            statusLabel.setText("Waiting");
                            informationLabel.setText(" - ");
                            bookQueue.wait();
                        } catch (InterruptedException ex1) {
                        }
                    }
                }
            }

        }
    }

}
