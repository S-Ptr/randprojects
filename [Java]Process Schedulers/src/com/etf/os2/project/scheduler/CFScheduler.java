package com.etf.os2.project.scheduler;

import com.etf.os2.project.process.Pcb;
import com.etf.os2.project.process.PcbData;

import java.util.Comparator;
import java.util.PriorityQueue;

public class CFScheduler extends Scheduler {
	
	long minTS;
	
    Comparator<Pcb> comp = (Pcb x1, Pcb x2) -> {
        return (x1.getPcbData().getExectime() > x2.getPcbData().getExectime()) ? 1 : -1;
    };

    private PriorityQueue<Pcb> pcbqueue;

    public CFScheduler(long minTS){
		this.minTS = minTS;
        pcbqueue = new PriorityQueue<>(comp);
    }

    @Override
    public Pcb get(int cpuId) {
        if(pcbqueue.isEmpty()) return null;
        Pcb asdf = pcbqueue.poll();
        long time = Pcb.getCurrentTime() - asdf.getPcbData().getWaittime();
		time /= Pcb.getProcessCount();
        asdf.setTimeslice((time > minTS)?time:minTS);
        return asdf;

    }

    @Override
    public void put(Pcb pcb) {
        if(pcb == null) return;
        if(pcb.getPcbData() == null) pcb.setPcbData(new PcbData());
        pcb.getPcbData().setWaittime(Pcb.getCurrentTime());
        pcb.getPcbData().setExectime(pcb.getPcbData().getExectime() + pcb.getExecutionTime());
        pcbqueue.add(pcb);


    }
}
