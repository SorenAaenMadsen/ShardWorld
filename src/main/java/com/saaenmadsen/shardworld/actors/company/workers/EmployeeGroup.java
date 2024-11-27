package com.saaenmadsen.shardworld.actors.company.workers;

import akka.actor.typed.ActorRef;
import com.saaenmadsen.shardworld.actors.shardcountry.A_ShardCountry;

import java.util.Optional;

public class EmployeeGroup {
    private EmployeeCategory employeeCategory;
    private int count;
    private Optional<ActorRef<A_ShardCountry.CountryMainActorCommand>> popGroupRef;

    private EmployeeGroup(EmployeeCategory employeeCategory, int count, Optional<ActorRef<A_ShardCountry.CountryMainActorCommand>> popGroupRef) {
        this.employeeCategory = employeeCategory;
        this.count = count;
        this.popGroupRef = popGroupRef;
    }

    private EmployeeGroup(int count, EmployeeCategory employeeCategory) {
        this.count = count;
        this.employeeCategory = employeeCategory;
    }

    public static EmployeeGroup of(EmployeeCategory employeeCategory, int i) {
        return new EmployeeGroup(employeeCategory, i, Optional.empty());
    }

    public void setPopGroupRef(Optional<ActorRef<A_ShardCountry.CountryMainActorCommand>> popGroupRef) {
        this.popGroupRef = popGroupRef;
    }

    public EmployeeCategory getEmployeeCategory() {
        return employeeCategory;
    }

    public int getCount() {
        return count;
    }

    public Optional<ActorRef<A_ShardCountry.CountryMainActorCommand>> getPopGroupRef() {
        return popGroupRef;
    }
}
