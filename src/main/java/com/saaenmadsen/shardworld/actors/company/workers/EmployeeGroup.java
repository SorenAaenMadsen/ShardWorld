package com.saaenmadsen.shardworld.actors.company.workers;

import akka.actor.typed.ActorRef;
import com.saaenmadsen.shardworld.actors.popgroup.A_PopGroup;
import com.saaenmadsen.shardworld.modeltypes.MoneyBox;

import java.util.Optional;

public class EmployeeGroup {
    private EmployeeCategory employeeCategory;
    private int popCount;
    private int salaryPrWorker;
    private Optional<ActorRef<A_PopGroup.PopGroupCommand>> popGroupRef;

    private EmployeeGroup(EmployeeCategory employeeCategory, int count, int salaryPrWorker) {
        this.popCount = count;
        this.employeeCategory = employeeCategory;
        this.salaryPrWorker = salaryPrWorker;
        this.popGroupRef = Optional.empty();
    }

    public static EmployeeGroup of(EmployeeCategory employeeCategory, int employeeCount) {
        return new EmployeeGroup(employeeCategory, employeeCount, employeeCategory.getInitialSalary());
    }

    public void setPopGroupRef(ActorRef<A_PopGroup.PopGroupCommand> popGroupRef) {
        this.popGroupRef = Optional.of(popGroupRef);
    }

    public EmployeeCategory getEmployeeCategory() {
        return employeeCategory;
    }

    public int getPopCount() {
        return popCount;
    }

    public Optional<ActorRef<A_PopGroup.PopGroupCommand>> getPopGroupRef() {
        return popGroupRef;
    }

    public int getTotalSalary() {
        return salaryPrWorker * popCount;
    }
}
