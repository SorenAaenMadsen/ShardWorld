package com.saaenmadsen.shardworld.actors.company.workers;

import akka.actor.typed.ActorRef;
import com.saaenmadsen.shardworld.actors.popgroup.A_PopGroup;

import java.util.Optional;

public class EmployeeGroup {
    private EmployeeCategory employeeCategory;
    private int count;
    private Optional<ActorRef<A_PopGroup.PopGroupCommand>> popGroupRef;

    private EmployeeGroup(EmployeeCategory employeeCategory, int count) {
        this.count = count;
        this.employeeCategory = employeeCategory;
        this.popGroupRef = Optional.empty();
    }

    public static EmployeeGroup of(EmployeeCategory employeeCategory, int employeeCount) {
        return new EmployeeGroup(employeeCategory, employeeCount);
    }

    public void setPopGroupRef(ActorRef<A_PopGroup.PopGroupCommand> popGroupRef) {
        this.popGroupRef = Optional.of(popGroupRef);
    }

    public EmployeeCategory getEmployeeCategory() {
        return employeeCategory;
    }

    public int getCount() {
        return count;
    }

    public Optional<ActorRef<A_PopGroup.PopGroupCommand>> getPopGroupRef() {
        return popGroupRef;
    }
}
