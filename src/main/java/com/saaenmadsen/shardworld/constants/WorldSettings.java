package com.saaenmadsen.shardworld.constants;

public record WorldSettings (
        int companyCount,
        int countryCount,
        int maxDaysToRun,
        boolean logAkkaMessages
)
{
}
