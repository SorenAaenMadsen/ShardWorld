package com.saaenmadsen.shardworld.actors.company;

public class DailyReport {
    StringBuilder dailyReport;
    public DailyReport() {
        dailyReport = new StringBuilder();
    }

    public void appendToDailyReport(String section){
        dailyReport.append(" | "+section);
    }
}
