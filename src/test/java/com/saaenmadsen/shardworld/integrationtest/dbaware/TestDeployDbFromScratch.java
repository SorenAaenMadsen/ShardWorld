package com.saaenmadsen.shardworld.integrationtest.dbaware;

import com.saaenmadsen.shardworld.constants.Recipe;
import com.saaenmadsen.shardworld.constants.StockKeepUnit;
import com.saaenmadsen.shardworld.modeltypes.SkuAndCount;
import org.h2.tools.RunScript;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbPoolDataSource;

import java.io.FileReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

public class TestDeployDbFromScratch {

    private static MariaDbPoolDataSource mariaDbDataSource;

    @BeforeAll
    public static void setup() throws Exception {
        mariaDbDataSource = new MariaDbPoolDataSource("jdbc:mariadb://localhost:3306/shardworld");

        // Run SQL scripts
        try (Connection conn = mariaDbDataSource.getConnection("shardworld", "123456")) {
            RunScript.execute(conn, new FileReader(getResourceFileUrl("sql/h2unittest/dropall.sql").getPath()));
            System.out.println("dropall done");
            RunScript.execute(conn, new FileReader(getResourceFileUrl("sql/worldmodel/001_create_stockkeepunits.sql").getPath()));
            System.out.println("001_create_stockkeepunits done");
            RunScript.execute(conn, new FileReader(getResourceFileUrl("sql/worldmodel/002_create_recipes.sql").getPath()));
            System.out.println("002_create_recipes done");
        }

    }

    private static @Nullable URL getResourceFileUrl(String pathFromResources) {
        URL path = TestDeployDbFromScratch.class.getClassLoader().getResource(pathFromResources);
        return path;
    }

    @Test
    public void insertEnumValuesIntoDbTables() throws Exception {
        try (Connection conn = mariaDbDataSource.getConnection("shardworld", "123456")) {
            for (StockKeepUnit sku : StockKeepUnit.values()) {
                String sqlStatement = "INSERT INTO stockkeepunits (skuid, productname, initialprice, unit, description, usagecategory) VALUES ("
                        + String.format("'%s', '%s','%s','%s','%s','%s'", +sku.getArrayId(), sku.getProductName(), sku.getInitialPrice(), sku.getUnit(), sku.getDescription(), sku.getUsageCategory()) +
                        ")";
                runPreparedStatement(conn, sqlStatement);
            }
            for (Recipe recipe : Recipe.values()) {

                runPreparedStatement(conn, String.format("INSERT INTO recipes (recipeid, recipename, processdescription, calenderwaittimefromproductiontoavailable, workTime_times10Minutes) VALUES (" +
                        "'%s', '%s','%s','%s','%s'", +recipe.getRecipeId(), recipe.getRecipeName(), recipe.getProcessDescription(), recipe.getCalenderWaitDaysFromProductionToAvailable(), recipe.getWorkTimeTimes10Minutes()) +
                        ")"
                );
                for (SkuAndCount skuAndCount : recipe.getInputs().skuAndCounts) {
                    runPreparedStatement(conn, String.format("INSERT INTO recipe_input_sku (recipeid, skuid, amount) VALUES (" +
                            "'%s', '%s', '%s'", +recipe.getRecipeId(), skuAndCount.sku().getArrayId(), skuAndCount.amount()) +
                            ")"
                    );
                }
                for (SkuAndCount skuAndCount : recipe.getOutputs().skuAndCounts) {
                    runPreparedStatement(conn, String.format("INSERT INTO recipe_output_sku (recipeid, skuid, amount) VALUES (" +
                            "'%s', '%s', '%s'", +recipe.getRecipeId(), skuAndCount.sku().getArrayId(), skuAndCount.amount()) +
                            ")"
                    );
                }
                for (SkuAndCount skuAndCount : recipe.getToolRequirements().skuAndCounts) {
                    runPreparedStatement(conn, String.format("INSERT INTO recipe_toolreq_sku (recipeid, skuid, amount) VALUES (" +
                            "'%s', '%s', '%s'", +recipe.getRecipeId(), skuAndCount.sku().getArrayId(), skuAndCount.amount()) +
                            ")"
                    );
                }

            }

        }
    }

    private static void runPreparedStatement(Connection conn, String sqlStatement) {
        try {
            conn.prepareStatement(sqlStatement).execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failing SQL Statement: "+ sqlStatement);
        }
    }
}
