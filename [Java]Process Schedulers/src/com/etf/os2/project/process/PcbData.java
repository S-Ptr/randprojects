package com.etf.os2.project.process;

public class PcbData {
	//TODO: Add implementation

    private long tau;
    private int priority;

    long waittime;

    long exectime;


    public long getTau() {
        return tau;
    }

    public void setTau(long tau) {
        this.tau = tau;
    }



    public long getWaittime() {
        return waittime;
    }

    public void setWaittime(long waittime) {
        this.waittime = waittime;
    }

    public long getExectime() {
        return exectime;
    }

    public void setExectime(long exectime) {
        this.exectime = exectime;
    }

    public int getPrio() {
        return priority;
    }

    public void setPrio(int val){
        priority = val;
    }



}
