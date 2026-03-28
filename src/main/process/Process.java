package process;

public class Process {
    private int id;
    private int arrivalTime;
    private int burstTime;
    private int remainingTime;
    private int memorySize;
    private int startTime;
    private int completionTime;
    private int waitingTime;
    private boolean isAllocated;
    
    public Process(int id, int arrivalTime, int burstTime) {
        this(id, arrivalTime, burstTime, 64 + (id * 16)); 
    }
    
    public Process(int id, int arrivalTime, int burstTime, int memorySize) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.memorySize = memorySize;
        this.isAllocated = false;
    }
    
    public int getId() { return id; }
    public int getArrivalTime() { return arrivalTime; }
    public int getBurstTime() { return burstTime; }
    public int getRemainingTime() { return remainingTime; }
    public void setRemainingTime(int time) { this.remainingTime = time; }
    public int getMemorySize() { return memorySize; }
    public boolean isAllocated() { return isAllocated; }
    public void setAllocated(boolean allocated) { isAllocated = allocated; }
    public int getStartTime() { return startTime; }
    public void setStartTime(int startTime) { this.startTime = startTime; }
    public int getCompletionTime() { return completionTime; }
    public void setCompletionTime(int completionTime) { this.completionTime = completionTime; }
    public int getWaitingTime() { return waitingTime; }
    public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime; }
    
    public void execute(int timeUnits) {
        remainingTime -= timeUnits;
        if (remainingTime < 0) remainingTime = 0;
    }
    
    @Override
    public String toString() {
        return String.format("P%d(A:%d,B:%d,M:%d)", id, arrivalTime, burstTime, memorySize);
    }
}
