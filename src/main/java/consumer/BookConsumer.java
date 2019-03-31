package consumer;

import bl.Book;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import queue.EmptyException;
import queue.Queue;

public class BookConsumer extends JFrame implements Runnable {

    private Queue<Book> bookQueue;
    private JLabel statusLabel;
    private JTextArea outputArea;

    public BookConsumer(Queue<Book> bookQueue, String name) {
        this.bookQueue = bookQueue;
        this.setSize(200,500);
        this.setLayout(new BorderLayout());
        outputArea = new JTextArea();
        this.add(outputArea, BorderLayout.CENTER);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1));
        JLabel titleLabel = new JLabel();
        titleLabel.setText(name);
        topPanel.add(titleLabel);
        statusLabel = new JLabel();
        statusLabel.setText("Thread added");
        topPanel.add(statusLabel);
        this.add(topPanel, BorderLayout.NORTH);
    }

    @Override
    public void run() {
        while (true) {
            Book currentB = null;
            synchronized (bookQueue) {
                try {
                    statusLabel.setText("Running");
                    currentB = bookQueue.get();
                    bookQueue.notifyAll();
                } catch (EmptyException ex) {
                    try {
                        statusLabel.setText("Waiting");
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
//        System.out.println("Book: " + b.getInputfilename());
        outputArea.setText("");
        outputArea.append("Book: " + b.getInputfilename() + "\n");
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(BookConsumer.class.getName()).log(Level.SEVERE, null, ex);
            }
            Map.Entry pair = (Map.Entry) it.next();
            if ((Integer) pair.getValue() >= 2 && !pair.getKey().equals("")) {
//                System.out.println(pair.getKey() + " : " + pair.getValue());
                outputArea.append(pair.getKey() + " : " + pair.getValue() + "\n");
            }
            it.remove();
        }
    }

}
