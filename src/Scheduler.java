import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

abstract class Scheduler {
    protected String algo;
    protected List<Process> completeQueue;
    protected int timeStamp = 0;
    abstract void addToReadyQueue(Process process);
    abstract boolean isEmpty();
    abstract Process getProcess();
    public String getAlgo(){ return algo; }
    public void addToCompleteQueue(Process process){
        if (completeQueue.size() == 0) {
            completeQueue.add(process);
        } else if (completeQueue.get(0).getProcessId() > process.getProcessId()) {
            completeQueue.add(0, process);
        } else if (completeQueue.get(completeQueue.size() - 1).getProcessId() < process.getProcessId()) {
            completeQueue.add(completeQueue.size(), process);
        } else {
            int i = 0;
            while (completeQueue.get(i).getProcessId() < process.getProcessId()) {
                i++;
            }
            completeQueue.add(i, process);
        }
    }
    public void printOutput(String filename){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" + filename + "_" + algo));
            for(Process p: completeQueue){
                String substr = p.getProcessId() + " " + p.getFinishTime() + " " + p.getWaitTime() + " " + p.getTurnaroundTime();
                out.write(substr);
                out.newLine();
            }
            out.close();
        }
        catch (IOException e) {
        }
    }
    abstract void schedule();
    abstract void setQuantum(int quantum);
}


