package memory;

public class MemoryBlock {
    private int start;
    private int size;
    private String processId;
    private boolean isFree;
    
    public MemoryBlock(int start, int size) {
        this.start = start;
        this.size = size;
        this.isFree = true;
        this.processId = "FREE";
    }
    
    public int getStart() { return start; }
    public int getSize() { return size; }
    public int getEnd() { return start + size - 1; }
    public String getProcessId() { return processId; }
    public boolean isFree() { return isFree; }
    
    public boolean allocate(String processId) {
        if (isFree) {
            this.processId = processId;
            this.isFree = false;
            return true;
        }
        return false;
    }
    
    public void deallocate() {
        this.processId = "FREE";
        this.isFree = true;
    }
}
