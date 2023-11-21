package com.constraintPreprocess;

public abstract class Filter {
    private Filter next;
    public void setNext(Filter next){
        this.next = next;
    }
    public Filter getNext(){
        return next;
    }

    //过滤具体某一条约束
    public abstract boolean ConstraintFilter(String constraint);
}
