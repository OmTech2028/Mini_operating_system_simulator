package scheduler;

import memory.MemoryManager;
import process.Process;

import java.util.LinkedList;
import java.util.Queue;

public class RoundRobin extends Scheduler {
    private int quantum;
    
    public RoundRobin(Process[] processes, int quantum) {
        super(processes);
        this.quantum = quantum;
    }
    
    @Override
    public void simulate(MemoryManager memory) {
        System.out.println("Algorithm: Round Robin (Quantum=" + quantum + ")");
        System.out.println("Process Queue: " + java.util.Arrays.toString(processes));
        
        Queue<Process> readyQueue = new LinkedList<>();
        int completed = 0;
        
        System.out.print("\n Gantt Chart: ");
        printGanttChartIntro();
        
        while (completed < processes.length) {
            for (Process p : processes) {
                if (p.getArrivalTime() <= currentTime && p.getRemainingTime() > 0 
                    && !readyQueue.contains(p)) {
                    readyQueue.offer(p);
                }
            }
            
            if (readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }
            
            Process current = readyQueue.poll();
            
            if (!current.isAllocated()) {
                boolean allocated = memory.allocate(current);
                if (!allocated) {
                    System.out.println(" Memory allocation failed for " + current);
                    current.setRemainingTime(0);
                    completed++;
                    continue;
                }
                current.setStartTime(currentTime);
            }
            
            int execTime = Math.min(quantum, current.getRemainingTime());
            printGanttChart(current, execTime);
            current.execute(execTime);
            currentTime += execTime;
            
            if (current.getRemainingTime() > 0) {
                readyQueue.offer(current);
            } else {
                current.setCompletionTime(currentTime);
                completed++;
            }
        }
        
        printMetrics();
        memory.displayMemory();
    }
}
