
public class Process {

    private int processId;
    private int arrivalTime = 0;
    private int finishTime = 0;
    private int waitTime = 0;
    private int turnaroundTime = 0;
    private  int CPUBurstTime = 0;
    private int remainTime = 0;
    Process(int processId, int arrivalTime, int CPUBurstTime){
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.CPUBurstTime = CPUBurstTime;
        this.remainTime = CPUBurstTime;
    }
    int getProcessId(){
        return processId;
    }
    int getArrivalTime(){
        return arrivalTime;
    }
    int getFinishTime(){
        return finishTime;
    }
    int getWaitTime(){
        return waitTime;
    }
    int getTurnaroundTime(){
        return turnaroundTime;
    }
    int getCPUBurstTime(){
        return CPUBurstTime;
    }
    int getRemainTime(){
        return remainTime;
    }
    void setRemainTime(int remainTime){
        this.remainTime = remainTime;
    }
    void setFinishTime(int finishTime){
        this.finishTime = finishTime;
    }
    void setTurnaroundTime(int turnaroundTime){
        this.turnaroundTime = turnaroundTime;
    }
    void setWaitTime(int waitTime){
        this.waitTime = waitTime;
    }
}
