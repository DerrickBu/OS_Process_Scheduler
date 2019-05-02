import java.util.LinkedList;
import java.util.Queue;

public class RRScheduler extends Scheduler{
    private Queue<Process> readyQueue;
    private Queue<Process> runningQueue;
    public int quantum = 0;
    RRScheduler(String algo){
        this.algo = algo;
        readyQueue = new LinkedList<>();
        runningQueue = new LinkedList<>();
        completeQueue = new LinkedList<>();
    }
    public void setQuantum(int quantum){
        this.quantum = quantum;
    }
    public void addToReadyQueue(Process process){
        readyQueue.offer(process);
    }
    public boolean isEmpty(){
        return readyQueue.size() == 0;
    }
    public Process getProcess(){
        return runningQueue.poll();
    }
    public void schedule(){
        int processNum = readyQueue.size();
        int completeProcess = 0;
        while(completeProcess != processNum){
            if(runningQueue.isEmpty()){
                runningQueue.offer(readyQueue.poll());
            }
            Process p = runningQueue.poll();
            if(timeStamp < p.getArrivalTime())
                timeStamp = p.getArrivalTime();
            if(p.getRemainTime() <= quantum){
                while(!readyQueue.isEmpty() && readyQueue.peek().getArrivalTime() <= timeStamp + p.getRemainTime()){
                    runningQueue.offer(readyQueue.poll());
                }
                timeStamp = timeStamp + p.getRemainTime();
                p.setRemainTime(0);
                p.setFinishTime(timeStamp);
                p.setTurnaroundTime(timeStamp - p.getArrivalTime());
                p.setWaitTime(p.getTurnaroundTime() - p.getCPUBurstTime());
                addToCompleteQueue(p);
                completeProcess++;
            }
            else{
                while(!readyQueue.isEmpty() && readyQueue.peek().getArrivalTime() <= timeStamp + quantum){
                    if(readyQueue.peek().getArrivalTime() < timeStamp + quantum || (readyQueue.peek().getArrivalTime() == timeStamp + quantum
                            && readyQueue.peek().getProcessId() < p.getProcessId()))
                        runningQueue.offer(readyQueue.poll());
                    else break;
                }
                timeStamp = timeStamp + quantum;
                p.setRemainTime(p.getRemainTime() - quantum);
                runningQueue.offer(p);
                while(!readyQueue.isEmpty() && readyQueue.peek().getArrivalTime() == timeStamp){
                    runningQueue.offer(readyQueue.poll());
                }
            }
        }

    }
}
