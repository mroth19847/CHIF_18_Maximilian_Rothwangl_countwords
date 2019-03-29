package queue;

import java.util.LinkedList;

public class Queue<T> {
private final LinkedList<T> files = new LinkedList<>();
    private final int maxSize;

    public Queue(int maxSize) {
        this.maxSize = maxSize;
    }
    
    public T get() throws EmptyException{
        if(files.isEmpty()){
            throw new EmptyException();
        }
        return files.poll();
    }
    
    public void put(T value) throws FullException{
        if(files.size() == maxSize){
            throw new FullException();
        }
        files.add(value);
    }
}
