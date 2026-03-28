package memory;

import process.Process;

public class MemoryManager {
    private MemoryBlock[] memoryBlocks;
    private int totalSize;
    
    public MemoryManager(int totalSize) {
        this.totalSize = totalSize;
        this.memoryBlocks = new MemoryBlock[1];
        memoryBlocks[0] = new MemoryBlock(0, totalSize);
    }
    
    public boolean allocate(Process process) {
        for (MemoryBlock block : memoryBlocks) {
            if (block.isFree() && block.getSize() >= process.getMemorySize()) {
                if (block.getSize() == process.getMemorySize()) {
                    block.allocate("P" + process.getId());
                    process.setAllocated(true);
                    return true;
                } else {
                    splitBlock(block, process.getMemorySize(), process.getId());
                    process.setAllocated(true);
                    return true;
                }
            }
        }
        return false;
    }
    
    private void splitBlock(MemoryBlock block, int allocSize, String processId) {
        MemoryBlock allocBlock = new MemoryBlock(block.getStart(), allocSize);
        allocBlock.allocate(processId);
        
        MemoryBlock freeBlock = new MemoryBlock(block.getStart() + allocSize, 
                                              block.getSize() - allocSize);
        
        MemoryBlock[] newBlocks = new MemoryBlock[memoryBlocks.length + 1];
        int i = 0;
        for (; i < memoryBlocks.length; i++) {
            if (memoryBlocks[i] == block) {
                newBlocks[i] = allocBlock;
                newBlocks[i + 1] = freeBlock;
                i += 2;
                break;
            }
            newBlocks[i] = memoryBlocks[i];
        }
        for (; i < newBlocks.length; i++) {
            newBlocks[i] = memoryBlocks[i - 1];
        }
        memoryBlocks = newBlocks;
    }
    
    public void displayMemory() {
        System.out.println("\n MEMORY LAYOUT (" + totalSize + " KB):");
        System.out.println("Addr\tSize\tStatus");
        System.out.println("----\t----\t----------");
        
        for (MemoryBlock block : memoryBlocks) {
            String status = block.isFree() ? "FREE" : block.getProcessId();
            System.out.printf("%d-%d\t%d\t%s\n", 
                block.getStart(), block.getEnd(), block.getSize(), status);
        }
        System.out.println();
    }
}
