package com.saaenmadsen.shardworld.actors.company.workers;

public enum EmployeeCategory {
    WORKER(10, "workers"),
    MIDDLEMANAGEMENT(15, "middle management"),
    OWNER(20, "owners");

    private final int initialSalary;
    private final String printableCategoryName;

    EmployeeCategory(int initialSalary, String printableCategoryName) {
        this.initialSalary = initialSalary;
        this.printableCategoryName = printableCategoryName;
    }

    public int getInitialSalary() {
        return initialSalary;
    }

    public String getPrintableName() {
        return printableCategoryName;
    }
}
