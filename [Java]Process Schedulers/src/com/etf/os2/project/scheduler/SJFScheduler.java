package com.etf.os2.project.scheduler;

import com.etf.os2.project.process.Pcb;
import com.etf.os2.project.process.PcbData;

import java.util.Comparator;
import java.util.PriorityQueue;

public class SJFScheduler extends Scheduler {

    private PriorityQueue<Pcb> pcbList;
    private Comparator<Pcb> comp = (Pcb x1, Pcb x2) -> {
        return (x1.getPcbData().getTau() > x2.getPcbData().getTau()) ? 1 : -1;
    };
    private double alpha;
    private boolean preempt;

    public SJFScheduler(double alpha, boolean preempt) {
        this.pcbList = new PriorityQueue<>(comp);
        this.alpha = alpha;
        this.preempt = preempt;
    }

    @Override
    public Pcb get(int cpuId) {
        if (pcbList.isEmpty()) return null;
        Pcb asdf = pcbList.poll();
        asdf.setTimeslice(0);
        return asdf;

    }

    @Override
    public void put(Pcb pcb) {
        if (pcb == null) return;
        if(pcb.getPcbData() == null) pcb.setPcbData(new PcbData());
        long newTau = (long) ((long) (pcb.getExecutionTime() * this.alpha) + ((double) 1 - this.alpha) * pcb.getPcbData().getTau());
        pcb.getPcbData().setTau(newTau);
        pcbList.add(pcb);
        if (preempt) {
            for (Pcb running : Pcb.RUNNING) {
                if (((Long) running.getPcbData().getTau() - running.getExecutionTime()) < newTau) {
                    running.preempt();
                    return;
                }
            }
        }
        return;

    }
}
