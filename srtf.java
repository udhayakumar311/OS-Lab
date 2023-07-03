import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Process1 {
    int pid; // Process ID
    int bt; // Burst Time
    int art; // Arrival Time

    public Process1(int pid, int bt, int art) {
        this.pid = pid;
        this.bt = bt;
        this.art = art;
    }
}

public class srtf {
    // Method to find the waiting time for all processes
    static void findWaitingTime(List<Process1> processes, int n, int wt[], int ct[]) {
        int rt[] = new int[n];

        // Copy the burst time into rt[]
        for (int i = 0; i < n; i++)
            rt[i] = processes.get(i).bt;

        int complete = 0, t = 0, minm = Integer.MAX_VALUE;
        int shortest = -1, finishTime;
        boolean check = false;

        // Process until all processes get completed
        while (complete != n) {
            // Find the process with the minimum remaining time among the processes that have arrived till the current time
            for (int j = 0; j < n; j++) {
                if (processes.get(j).art <= t && rt[j] < minm && rt[j] > 0) {
                    minm = rt[j];
                    shortest = j;
                    check = true;
                }
            }

            if (!check) {
                t++;
                continue;
            }

            // Reduce the remaining time by one
            rt[shortest]--;

            // Update the minimum remaining time
            minm = rt[shortest];
            if (minm == 0)
                minm = Integer.MAX_VALUE;

            // If a process gets completely executed
            if (rt[shortest] == 0) {
                complete++;
                check = false;

                // Find the finish time of the current process
                finishTime = t + 1;

                // Calculate completion time
                ct[shortest] = finishTime;

                // Calculate waiting time
                wt[shortest] = finishTime - processes.get(shortest).bt - processes.get(shortest).art;
                if (wt[shortest] < 0)
                    wt[shortest] = 0;
            }

            // Increment time
            t++;
        }
    }

    // Method to calculate turnaround time
    static void findTurnaroundTime(List<Process1> processes, int n, int wt[], int tat[]) {
        // Calculate turnaround time by adding burst time and waiting time
        for (int i = 0; i < n; i++)
            tat[i] = processes.get(i).bt + wt[i];
    }

    // Method to calculate average time
    static void findAverageTime(List<Process1> processes, int n) {
        int wt[] = new int[n], tat[] = new int[n];
        int total_wt = 0, total_tat = 0;
        int ct[] = new int[n];

        // Find waiting time of all processes
        findWaitingTime(processes, n, wt, ct);

        // Find turnaround time for all processes
        findTurnaroundTime(processes, n, wt, tat);

        // Display process details
        System.out.println("Process\tArrival\tBurst\tCompletion\tWaiting\tTurnaround");
        for (int i = 0; i < n; i++) {
            System.out.println(
                    processes.get(i).pid + "\t" +
                            processes.get(i).art + "\t" +
                            processes.get(i).bt + "\t" +
                            ct[i] + "\t\t" +
                            wt[i] + "\t\t" +
                            tat[i]
            );

            total_wt += wt[i];
            total_tat += tat[i];
        }

        // Calculate average waiting time and average turnaround time
        float avg_wt = (float) total_wt / n;
        float avg_tat = (float) total_tat / n;

        System.out.println("\nAverage Waiting Time: " + avg_wt);
        System.out.println("Average Turnaround Time: " + avg_tat);

        // Calculate throughput
        float throughput = (float) n / ct[n - 1];
        System.out.println("Throughput: " + throughput+" per time unit");
    }

    // Driver Method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        List<Process1> processes = new ArrayList<>();

        System.out.println("Enter Arrival Time and Burst Time:");
        for (int i = 0; i < n; i++) {
            System.out.println("For process " + (i + 1) + ":");

            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();

            Process1 process = new Process1(i + 1, burstTime, arrivalTime);
            processes.add(process);
        }

        findAverageTime(processes, processes.size());
    }
}
