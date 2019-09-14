package com.etf.os2.project.scheduler;

import com.etf.os2.project.process.Pcb;

public abstract class Scheduler {
    public abstract Pcb get(int cpuId);

    public abstract void put(Pcb pcb);

    public static Scheduler createScheduler(String[] args) {
        String alg  = args[0];
        alg = alg.toLowerCase();
        if(alg.equals("sjf")){
            double alph = Double.parseDouble(args[1]);
            if(alph < 0 || alph > 1){
                System.out.println("");
                System.out.println("**************************");
                System.out.println("ERROR: Alpha must be a number between 0 and 1");
                System.out.println("**************************");
                System.exit(0xDEAD);
            }
            String preemptArg = args[2].toLowerCase();
            boolean isPreempt = (preemptArg == "y") ? true:false;
            return new SJFScheduler(alph, isPreempt);
        }else if(alg.equals("cfs")){
			long minTS = Long.parseLong(args[1]);
			if(parseLong < 0){
				System.out.println("");
                System.out.println("**************************");
                System.out.println("ERROR: Please give a non-negative value for the minimum time slice");
                System.out.println("**************************");
                System.exit(0xDEAD);
			}
            return new CFScheduler(minTS);
        }else if (alg.equals("mfq")){
            int num = Integer.parseInt(args[1]);
            if(num < 1){
                System.out.println("");
                System.out.println("**************************");
                System.out.println("ERROR: Not enough queues. Please enter a positive number of queues.");
                System.out.println("**************************");
                System.exit(0xDEAD);
            }
            long[] slices = new long[num];
            for(int i  = 0; i < num; i++){
                slices[i] = Long.parseLong(args[2+i]);
            }
            return new MLFQScheduler(num,slices);
        }else{
            System.out.println("");
            System.out.println("**************************");
            System.out.println("BAD ARGUMENTS");
            System.out.println("Arg Format:'<cpus> <procs> <algo> <algoparameters>'");
            System.out.println("Available Algorithms: CFS; SJF <alpha> <preemptive?(y/n)>; MFQ <numrows> <timeslice1..n>");
            System.out.println("**************************");
            System.exit(0xDEAD);
            return null;
        }
    }
}
