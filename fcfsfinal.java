import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Process implements Comparable<Process> {
    int index;
    int arrTime;
    int burstTime;

    Process(int i, int a, int b) {
        this.arrTime = a;
        this.burstTime = b;
        this.index = i;
    }

    @Override
    public int compareTo(Process o) {
        return this.arrTime - o.arrTime;
    }
}

public class fcfsfinal {
    public static void avgWaitTime(ArrayList<Process> process) {
        int wt[] = new int[process.size()];
        int tat[] = new int[process.size()];
        int ct[] = new int[process.size()];
        int st[] = new int[process.size()];

        startingTime(process, st);
        compleitionTime(process, ct, st);
        tat(process, ct, tat);
        waitingTime(process, tat, wt);

        System.out.println("\nProcesses ||" + " Burst Time ||" + " Arrival Time || Starting Time ||" + " Waiting Time || Compleition Time ||" + " Turn-Around Time ");

        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        for (int i = 0; i < process.size(); i++) {
            System.out.println((process.get(i).index + 1) + "\t\t" + process.get(i).burstTime + "\t\t" + process.get(i).arrTime + "\t\t" + st[i] + "\t\t" + wt[i] + "\t\t" + ct[i] + "\t\t" + tat[i]);
            totalWaitingTime += wt[i];
            totalTurnaroundTime += tat[i];
        }

        double avgWaitingTime = (double) totalWaitingTime / process.size();
        double avgTurnaroundTime = (double) totalTurnaroundTime / process.size();
        double throughput = (double) process.size() / ct[process.size() - 1];

        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
        System.out.println("Throughput: " + throughput);
    }

    public static void startingTime(ArrayList<Process> process, int[] st) {
        st[0] = process.get(0).arrTime;
        for (int i = 1; i < process.size(); i++) {
            st[i] = st[i - 1] + process.get(i - 1).burstTime;
        }
    }

    public static void compleitionTime(ArrayList<Process> process, int[] ct, int[] st) {
        for (int i = 0; i < process.size(); i++) {
            ct[i] = st[i] + process.get(i).burstTime;
        }
    }

    public static void tat(ArrayList<Process> process, int[] ct, int[] tat) {
        for (int i = 0; i < process.size(); i++) {
            tat[i] = ct[i] - process.get(i).arrTime;
        }
    }

    public static void waitingTime(ArrayList<Process> process, int[] tat, int[] wt) {
        for (int i = 0; i < process.size(); i++) {
            wt[i] = tat[i] - process.get(i).burstTime;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of processes: ");
        int n = sc.nextInt();

        ArrayList<Process> process = new ArrayList<>();

        System.out.println("Enter Arrival and Burst time");
        for (int i = 0; i < n; i++) {
            System.out.print("For process " + (i + 1) + " : ");
            process.add(new Process(i, sc.nextInt(), sc.nextInt()));
        }

        Collections.sort(process);

        avgWaitTime(process);
    }
}
