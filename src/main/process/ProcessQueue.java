package process;

import java.util.LinkedList;
import java.util.Queue;


public class ProcessQueue {
    private Queue<Process> readyQueue;
    private int maxSize;
    
    public ProcessQueue(int maxSize) {
        this.readyQueue = new LinkedList<>();
        this.maxSize = maxSize;
    }
    
    
    public boolean enqueue(Process process) {
        if (readyQueue.size() < maxSize && process.getRemainingTime() > 0) {
            readyQueue.offer(process);
            System.out.println(" P" + process.getId() + " added to ready queue");
            return true;
        }
        System.out.println(" Queue full or process completed: P" + process.getId());
        return false;
    }
    

    public Process dequeue() {
        Process process = readyQueue.poll();
        if (process != null) {
            System.out.println(" Dispatching P" + process.getId());
        }
        return process;
    }
    

    public Process peek() {
        return readyQueue.peek();
    }
    

    public boolean isEmpty() {
        return readyQueue.isEmpty();
    }
    

    public int size() {
        return readyQueue.size();
    }
    

    public void displayQueue() {
        if (isEmpty()) {
            System.out.println(" Ready Queue: [EMPTY]");
        } else {
            System.out.print(" Ready Queue: [");
            for (Process p : readyQueue) {
                System.out.print("P" + p.getId() + "(" + p.getRemainingTime() + ") ");
            }
            System.out.println("]");
        }
    }
    

    public boolean removeProcess(int processId) {
        return readyQueue.removeIf(p -> p.getId() == processId);
    }
}
