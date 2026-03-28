package scheduler;

import memory.MemoryManager;
import process.Process;

public class FCFS extends Scheduler {
    
    public FCFS(Process[] processes) {
        super(processes);
    }
    
    @Override
    public void simulate(MemoryManager memory) {
        System.out.println("Algorithm: First Come First Serve (FCFS)");
        System.out.println("Process Queue: " + java.util.Arrays.toString(processes));
        
        java.util.Arrays.sort(processes, (a, b) -> a.getArrivalTime() - b.getArrivalTime());
        
        System.out.print("\n Gantt Chart: ");
        printGanttChartIntro();
        
        while (true) {
            Process next = getNextReadyProcess();
            if (next == null) {
                int nextArrival = Integer.MAX_VALUE;
                for (Process p : processes) {
                    if (p.getRemainingTime() > 0) {
                        nextArrival = Math.min(nextArrival, p.getArrivalTime());
                    }
                }
                if (nextArrival == Integer.MAX_VALUE) break;
                currentTime = nextArrival;
                continue;
            }
            
            if (!next.isAllocated()) {
                boolean allocated = memory.allocate(next);
                if (!allocated) {
                    System.out.println("Memory allocation failed for " + next);
                    next.setRemainingTime(0);
                    currentTime++;
                    continue;
                }
            }
            
            next.setStartTime(currentTime);
            int execTime = next.getRemainingTime();
            printGanttChart(next, execTime);
            next.execute(execTime);
            currentTime += execTime;
            next.setCompletionTime(currentTime);
        }
        
        printMetrics();
        memory.displayMemory();
    }
    
    private void printGanttChartIntro() {
        System.out.println("Time: 0" + " ".repeat(50));
    }
}
