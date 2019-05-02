import java.util.LinkedList;
import java.util.List;

public class SJFScheduler extends Scheduler{
    private List<Process> readyQueue;
    SJFScheduler(String algo){
        this.algo = algo;
        readyQueue = new LinkedList<>();
        completeQueue = new LinkedList<>();
    }
    public void addToReadyQueue(Process process){
        if(readyQueue.size() == 0)
            readyQueue.add(process);
        else{
            int i = readyQueue.size() - 1;
            while(i >= 0 && readyQueue.get(i).getArrivalTime() == process.getArrivalTime()
                    && readyQueue.get(i).getCPUBurstTime() > process.getCPUBurstTime()){
                i -= 1;
            }
            readyQueue.add(i + 1, process);

        }
    }
    public boolean isEmpty(){
        return readyQueue.size() == 0;
    }

    public Process getProcess(){
        Process nextProcess = null;
        int minCPUBurstTime = Integer.MAX_VALUE;
        int idx = -1;
        for(int i = 0; i < readyQueue.size(); ++i){
            Process p = readyQueue.get(i);
            if(p.getArrivalTime() > timeStamp)
                break;
            if(p.getCPUBurstTime() <= minCPUBurstTime && p.getArrivalTime() <= timeStamp) {
                if (p.getCPUBurstTime() < minCPUBurstTime){
                    nextProcess = p;
                    minCPUBurstTime = p.getCPUBurstTime();
                    idx = i;
                }
                else{
                    if(nextProcess != null && p.getProcessId() < nextProcess.getProcessId()) {
                        nextProcess = p;
                        idx = i;
                    }
                }
            }
        }
        if(nextProcess != null){
            readyQueue.remove(idx);
        }
        else{
            nextProcess = readyQueue.get(0);
            readyQueue.remove(0);
        }
        return nextProcess;
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
