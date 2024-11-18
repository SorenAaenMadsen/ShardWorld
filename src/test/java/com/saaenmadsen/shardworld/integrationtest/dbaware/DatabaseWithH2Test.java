//package com.saaenmadsen.shardworld.integrationtest.dbaware;
//
//import org.junit.jupiter.api.*;
//import org.testcontainers.containers.MariaDBContainer;
//import org.testcontainers.containers.JdbcDatabaseContainer;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//public class DatabaseWithH2Test {
//    static MariaDBContainer<?> mariaDBContainer = new MariaDBContainer<>("mariadb:10.11");
////    static JdbcDatabaseContainer<?> h2Container = new JdbcDatabaseContainer<>("h2:2.2.220").withDatabaseName("testdb");
//
//    @BeforeAll
//    static void setup() {
//        mariaDBContainer.start();
////        h2Container.start();
//    }
//
//    @Test
//    void testMariaDBConnection() throws Exception {
//        try (Connection conn = DriverManager.getConnection(
//                mariaDBContainer.getJdbcUrl(),
//                mariaDBContainer.getUsername(),
//                mariaDBContainer.getPassword())) {
//            Assertions.assertTrue(conn.isValid(2));
//        }
//    }
//
//    /**
//    @Test
//    void testH2Connection() throws Exception {
//        try (Connection conn = DriverManager.getConnection(
//                h2Container.getJdbcUrl(),
//                h2Container.getUsername(),
//                h2Container.getPassword())) {
//            Assertions.assertTrue(conn.isValid(2));
//        }
//    }
//     */
//
//    @AfterAll
//    static void teardown() {
//        mariaDBContainer.stop();
////        h2Container.stop();
//    }
//}
