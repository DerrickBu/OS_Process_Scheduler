import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Scheduler scheduler;
        String filename = "";

        if(args.length > 0) {
            filename = args[0];
        }
        else{
            throw new IllegalArgumentException("The number of arguments is illegal");
        }

        for(int i = 0; i < 4; ++i){
            switch(i){
                case 0:
                    scheduler = new FCFSScheduler("FCFS");
                    scheduleAllAlgorithms(scheduler, filename);
                    break;
                case 1:
                    scheduler = new SJFScheduler("SJF");
                    scheduleAllAlgorithms(scheduler, filename);
                    break;
                case 2:
                    scheduler = new SRTFScheduler("SRTF");
                    scheduleAllAlgorithms(scheduler, filename);
                    break;

                case 3:
                    scheduler = new RRScheduler("RR");
                    scheduleAllAlgorithms(scheduler, filename);
                    break;
            }
        }
    }

    public static void scheduleAllAlgorithms(Scheduler scheduler, String filename){
        BufferedReader reader;
        try{
            String outputFile = "";
            String []strSplit = filename.split("/");
            outputFile = strSplit[strSplit.length - 1];
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            if(scheduler.getAlgo() == "RR")
                scheduler.setQuantum(Integer.parseInt(line));
            line = reader.readLine();
            while(line != null){
                String[] substr = line.split(" ");
                Process p = new Process(Integer.parseInt(substr[1]), Integer.parseInt(substr[0]),
                        Integer.parseInt(substr[2]));
                scheduler.addToReadyQueue(p);
                line = reader.readLine();
            }
            reader.close();
            scheduler.schedule();
            scheduler.printOutput(outputFile);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
