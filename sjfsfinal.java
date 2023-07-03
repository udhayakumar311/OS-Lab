import java.util.*;

class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int startTime;
    int completionTime;
    int waitingTime;
    int turnAroundTime;
}

public class sjfsfinal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        // Create an array of processes
        Process[] processes = new Process[n];

        System.out.println("Enter Arrival and Burst time :");
        // Get arrival time and burst time for each process
        for (int i = 0; i < n; i++) {
            processes[i] = new Process();
            processes[i].id = i + 1;

            System.out.println("For process " + (i + 1) + " : ");
            processes[i].arrivalTime = scanner.nextInt();
            processes[i].burstTime = scanner.nextInt();
        }

        // Sort the processes based on arrival time and burst time
        Arrays.sort(processes, Comparator.comparingInt((Process p) -> p.arrivalTime)
                .thenComparingInt(p -> p.burstTime));

        int currentTime = 0; // Current time
        float totalTurnAroundTime = 0;
        float totalWaitingTime = 0;

        System.out.println("\nProcess\tArrival Time\tBurst Time\tStart Time\tCompletion Time\tWaiting Time\tTurnaround Time");

        // Calculate scheduling metrics for each process
        for (int i = 0; i < n; i++) {
            Process currentProcess = processes[i];

            // Calculate waiting time
            currentProcess.waitingTime = currentTime - currentProcess.arrivalTime;

            if (currentProcess.waitingTime < 0) {
                currentProcess.waitingTime = 0;
                currentTime = currentProcess.arrivalTime;
            }

            // Calculate starting time and completion time
            currentProcess.startTime = currentTime;
            currentProcess.completionTime = currentTime + currentProcess.burstTime;

            // Calculate turnaround time
            currentProcess.turnAroundTime = currentProcess.completionTime - currentProcess.arrivalTime;

            // Update current time
            currentTime = currentProcess.completionTime;

            // Calculate total turnaround time and total completion time
            totalTurnAroundTime += currentProcess.turnAroundTime;
            totalWaitingTime += currentProcess.waitingTime;

            // Print process details
            System.out.printf("%-8d%-16d%-16d%-16d%-20d%-16d%-16d\n", currentProcess.id, currentProcess.arrivalTime,
                    currentProcess.burstTime, currentProcess.startTime, currentProcess.completionTime,
                    currentProcess.waitingTime, currentProcess.turnAroundTime);
        }

        // Calculate average turnaround time, average waiting time, and throughput
        float avgTurnAroundTime = totalTurnAroundTime / processes.length;
        float avgWaitingTime = totalWaitingTime / processes.length;
        float throughput = (float) processes.length / currentTime;

        System.out.println("\nAverage Turnaround Time: " + avgTurnAroundTime);
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println("Throughput: " + throughput +" per time unit");

        scanner.close();
    }
}
