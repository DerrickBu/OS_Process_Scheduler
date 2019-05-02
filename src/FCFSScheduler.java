import java.util.*;

public class FCFSScheduler extends Scheduler{
    private Queue<Process> readyQueue;
    FCFSScheduler(String algo){
        this.algo = algo;
        readyQueue = new LinkedList<>();
        completeQueue = new LinkedList<>();
    }
    public void addToReadyQueue(Process process){
        readyQueue.offer(process);
    }
    public boolean isEmpty(){
        return readyQueue.size() == 0;
    }
    public Process getProcess(){
        return readyQueue.poll();
    }

    public void schedule(){
        while (!readyQueue.isEmpty()){
            Process p = getProcess();
            if(p.getArrivalTime() > timeStamp){
                timeStamp = p.getArrivalTime();
            }
            p.setWaitTime(timeStamp - p.getArrivalTime());
            timeStamp += p.getCPUBurstTime();
            p.setFinishTime(timeStamp);
            p.setTurnaroundTime(timeStamp - p.getArrivalTime());
            p.setRemainTime(0);
            addToCompleteQueue(p);
        }
    }

    @Override
    public void setQuantum(int quantum) {

    }
}
