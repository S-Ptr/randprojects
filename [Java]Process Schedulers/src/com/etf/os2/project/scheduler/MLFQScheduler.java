package com.etf.os2.project.scheduler;

import com.etf.os2.project.process.Pcb;
import com.etf.os2.project.process.PcbData;

import java.util.ArrayDeque;
import java.util.Deque;

public class MLFQScheduler extends Scheduler {

    private Deque<Pcb>[] queues;
    private int qNum;
    private long[] timeSlices;

    public MLFQScheduler(int num, long[] slices) {
        this.qNum = num;
        this.timeSlices = slices;
        this.queues = new ArrayDeque[qNum];
        for (int i = 0; i < num; i++) {
            queues[i] = new ArrayDeque<Pcb>();
        }
    }


    @Override
    public Pcb get(int cpuId) {
        int i = 0;
        for (; i < qNum && queues[i].isEmpty(); i++) ;
        if (i == qNum) {
            return null;
        }
        Pcb asdf = queues[i].poll();
        asdf.setTimeslice(timeSlices[i]);
        return asdf;
    }

    @Override
    public void put(Pcb pcb) {
        if(pcb==null) return;
        if(qNum == 1){
            queues[0].addLast(pcb);
            return;
        }
        if(pcb.getPcbData() == null) pcb.setPcbData(new PcbData());
        if(pcb.getPreviousState() == Pcb.ProcessState.CREATED){
            pcb.getPcbData().setPrio((pcb.getPriority()<qNum)?pcb.getPriority():(qNum-1));
        }
        if (pcb.getPreviousState() == Pcb.ProcessState.BLOCKED) {
            pcb.getPcbData().setPrio(pcb.getPcbData().getPrio()-1);
        } else  if(pcb.getPcbData().getPrio() != (qNum-1)){
            pcb.getPcbData().setPrio(pcb.getPcbData().getPrio()+1);
        }
        queues[pcb.getPcbData().getPrio()].addLast(pcb);
    }
}

