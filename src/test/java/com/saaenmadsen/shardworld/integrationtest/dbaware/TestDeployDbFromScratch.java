package com.saaenmadsen.shardworld.integrationtest.dbaware;

import org.h2.tools.RunScript;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbPoolDataSource;

import java.io.FileReader;
import java.net.URL;
import java.sql.Connection;

public class TestDeployDbFromScratch {

    private static MariaDbPoolDataSource mariaDbDataSource;

    @BeforeAll
    public static void setup() throws Exception {
        mariaDbDataSource = new MariaDbPoolDataSource("jdbc:mariadb://localhost:3306/shardworld");

        // Run SQL scripts
        try (Connection conn = mariaDbDataSource.getConnection("shardworld", "123456")) {
            RunScript.execute(conn, new FileReader(getResourceFileUrl("sql/h2unittest/dropall.sql").getPath()));
            RunScript.execute(conn, new FileReader(getResourceFileUrl("sql/worldmodel/001_create_stockkeepunits.sql").getPath()));
            RunScript.execute(conn, new FileReader(getResourceFileUrl("sql/worldmodel/002_create_recipes.sql").getPath()));
        }

    }

    private static @Nullable URL getResourceFileUrl(String pathFromResources) {
        URL path = TestDeployDbFromScratch.class.getClassLoader().getResource(pathFromResources);
        return path;
    }

    @Test
    public void testInsertAndQuery() throws Exception {
        try (Connection conn = mariaDbDataSource.getConnection("shardworld", "123456")) {
            conn.prepareStatement("INSERT INTO stockkeepunits (name, description) VALUES ('Wood', 'Dry wood')").execute();
//            conn.prepareStatement("INSERT INTO recipes (name) VALUES ('Build Table')").execute();
        }
    }
}
