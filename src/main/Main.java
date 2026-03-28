package main;

import scheduler.*;
import memory.MemoryManager;
import process.Process;

import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mini Operating System Simulator");
        System.out.println("=====================================\n");
        
        MemoryManager memoryManager = new MemoryManager(1024); 
        
        Random rand = new Random();
        Process[] processes = new Process[5];
        for (int i = 0; i < 5; i++) {
            processes[i] = new Process(i, 50 + rand.nextInt(100), 10 + rand.nextInt(20));
        }
        
        int choice;
        do {
            showMenu();
            choice = scanner.nextInt();
            
            switch (choice) {
                case 1 -> fcfsDemo(processes.clone(), memoryManager);
                case 2 -> roundRobinDemo(processes.clone(), memoryManager);
                case 3 -> customProcessDemo(scanner, memoryManager);
                case 4 -> memoryManager.displayMemory();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
        
        scanner.close();
    }
    
    private static void showMenu() {
        System.out.println("\n Choose Simulation:");
        System.out.println("1. FCFS Scheduling");
        System.out.println("2. Round Robin (Quantum=4)");
        System.out.println("3. Custom Process Input");
        System.out.println("4. Show Memory Layout");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }
    
    private static void fcfsDemo(Process[] processes, MemoryManager memory) {
        System.out.println("\n FCFS Scheduling Demo");
        Scheduler fcfs = new FCFS(processes);
        fcfs.simulate(memory);
    }
    
    private static void roundRobinDemo(Process[] processes, MemoryManager memory) {
        System.out.println("\n Round Robin Scheduling Demo");
        Scheduler rr = new RoundRobin(processes, 4);
        rr.simulate(memory);
    }
    
    private static void customProcessDemo(Scanner scanner, MemoryManager memory) {
        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();
        Process[] processes = new Process[n];
        
        for (int i = 0; i < n; i++) {
            System.out.print("Process " + i + " - Arrival: ");
            int arrival = scanner.nextInt();
            System.out.print("Burst: ");
            int burst = scanner.nextInt();
            System.out.print("Memory needed: ");
            int memorySize = scanner.nextInt();
            processes[i] = new Process(i, arrival, burst, memorySize);
        }
        
        System.out.print("Scheduling (1=FCFS, 2=RR): ");
        int type = scanner.nextInt();
        Scheduler scheduler = (type == 2) ? 
            new RoundRobin(processes, 4) : new FCFS(processes);
        scheduler.simulate(memory);
    }
}
