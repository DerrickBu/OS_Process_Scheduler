import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SRTFScheduler extends Scheduler {
    private List<Process> readyQueue;
    SRTFScheduler(String algo){
        this.algo = algo;
        readyQueue = new ArrayList<>();
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
        int minRemainTime = Integer.MAX_VALUE;
        for(Process p : readyQueue){
            if(p.getRemainTime() > 0 && p.getRemainTime() <= minRemainTime && p.getArrivalTime() <= timeStamp) {
                if(p.getRemainTime() < minRemainTime) {
                    nextProcess = p;
                    minRemainTime = p.getRemainTime();
                }
                else{
                    if(p.getRemainTime() == minRemainTime && p.getProcessId() < nextProcess.getProcessId())
                        nextProcess = p;
                }
            }
        }
        return nextProcess;
    }

    public Process getMinProcess(){
        Process nextProcess = null;
        int minArrivalTime = Integer.MAX_VALUE;
        int minRemainTime = Integer.MAX_VALUE;
        int minPid = Integer.MAX_VALUE;
        for(Process p : readyQueue){
            if(p.getArrivalTime() < minArrivalTime && p.getArrivalTime() > timeStamp) {
                minArrivalTime = p.getArrivalTime();
            }
        }
        for(Process p : readyQueue){
            if(p.getRemainTime() < minRemainTime && p.getArrivalTime() == minArrivalTime) {
                minRemainTime = p.getRemainTime();
            }
        }
        for(Process p : readyQueue) {
            if (p.getProcessId() < minPid && p.getRemainTime() == minRemainTime && p.getArrivalTime() == minArrivalTime) {
                minPid = p.getProcessId();
                nextProcess = p;
            }
        }
        return nextProcess;
    }

    public Process getPreemptiveProcess(Process pro, int time){
        for(Process p : readyQueue) {
            if(p.getArrivalTime() > timeStamp && p.getArrivalTime() <= time && p.getRemainTime() > 0){
                int remainForPro = pro.getRemainTime() - p.getArrivalTime() + timeStamp;
                if(p.getRemainTime() < remainForPro || (p.getArrivalTime() == remainForPro && p.getProcessId() < pro.getProcessId()))
                    return p;
            }
        }
        return null;
    }

    public void schedule(){
        Process p = readyQueue.get(0);
        timeStamp = p.getArrivalTime();
        int completeProcess = 0;
        int processNum = readyQueue.size();
        while(completeProcess != processNum){
            int time = timeStamp + p.getRemainTime();
            Process nextProcess = getPreemptiveProcess(p, time);
            if(nextProcess == null){
                timeStamp = time;
                p.setFinishTime(timeStamp);
                p.setTurnaroundTime(timeStamp - p.getArrivalTime());
                p.setWaitTime(p.getTurnaroundTime() - p.getCPUBurstTime());
                p.setRemainTime(0);
                addToCompleteQueue(p);
                completeProcess++;
                if(completeProcess == processNum)
                    break;
                p = getProcess();
                if(p == null)
                    p = getMinProcess();
                if(p.getArrivalTime() > timeStamp)
                    timeStamp = p.getArrivalTime();
            }
            else{
                int burstTime = nextProcess.getArrivalTime() - timeStamp;
                p.setRemainTime(p.getRemainTime() - burstTime);
                p = nextProcess;
                timeStamp = p.getArrivalTime();
            }
        }
    }
    public void setQuantum(int quantum){

    }
}
