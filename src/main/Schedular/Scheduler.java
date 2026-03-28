package scheduler;

import memory.MemoryManager;
import process.Process;

public abstract class Scheduler {
    protected Process[] processes;
    protected int currentTime = 0;
    
    public Scheduler(Process[] processes) {
        this.processes = processes.clone();
    }
    
    public abstract void simulate(MemoryManager memory);
    
    protected Process getNextReadyProcess() {
        for (Process p : processes) {
            if (p.getArrivalTime() <= currentTime && p.getRemainingTime() > 0) {
                return p;
            }
        }
        return null;
    }
    
    protected void printGanttChart(Process p, int duration) {
        System.out.printf("%s |", "─".repeat(3));
        for (int i = 0; i < duration; i++) {
            System.out.print(" P" + p.getId() + " ");
        }
        System.out.println("|");
    }
    
    protected void printMetrics() {
        System.out.println("\n SCHEDULING METRICS:");
        double avgWait = 0, avgTurnaround = 0;
        
        for (Process p : processes) {
            p.setWaitingTime(p.getStartTime() - p.getArrivalTime());
            p.setCompletionTime(currentTime);
            int turnaround = p.getCompletionTime() - p.getArrivalTime();
            avgWait += p.getWaitingTime();
            avgTurnaround += turnaround;
            
            System.out.printf("P%d: Wait=%d, Turnaround=%d\n", 
                p.getId(), p.getWaitingTime(), turnaround);
        }
        
        System.out.printf("Average Waiting Time: %.2f\n", avgWait / processes.length);
        System.out.printf("Average Turnaround Time: %.2f\n", avgTurnaround / processes.length);
    }
}
