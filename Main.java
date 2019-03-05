package ru.avalon.java.ocpjp.labs;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Лабораторная работа №3
 * <p>
 * Курс: "DEV-OCPJP. Подготовка к сдаче сертификационных экзаменов серии Oracle
 * Certified Professional Java Programmer"
 * <p>
 * Тема: "JDBC - Java Database Connectivity"
 *
 * @author Daniel Alpatov <danial.alpatov@gmail.com>
 */
public class Main {

    private static String url = "jdbc:derby://localhost:1527/sample";
    private static Properties properties = new Properties();

    static {
        properties.setProperty("user", "app");
        properties.setProperty("password", "app");
    }

    /**
     * Точка входа в приложение
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        /*
         * TODO #01 Подключите к проекту все библиотеки, необходимые для соединения с СУБД.
         */
        try (Connection connection = getConnection()) {
            ProductCode code = new ProductCode("MO", 'N', "Movies");
            code.save(connection);
            printAllCodes(connection);

            code.setCode("MV");
            code.save(connection);
            printAllCodes(connection);
            
            code.setDiscountCode('M');
            code.setDescription("MyValues");
            code.save(connection);
            printAllCodes(connection);
        }
        /*
         * TODO #14 Средствами отладчика проверьте корректность работы программы
         */
    }

    /**
     * Выводит в кодсоль все коды товаров
     *
     * @param connection действительное соединение с базой данных
     * @throws SQLException
     */
    private static void printAllCodes(Connection connection) throws SQLException {
        Collection<ProductCode> codes = ProductCode.all(connection);
        for (ProductCode code : codes) {
            System.out.println(code);
        }
    }

    /**
     * Возвращает URL, описывающий месторасположение базы данных
     *
     * @return URL в виде объекта класса {@link String}
     */
    private static String getUrl() {
        /*
         * TODO #02 Реализуйте метод getUrl
         */
        return url;
    }

    /**
     * Возвращает параметры соединения
     *
     * @return Объект класса {@link Properties}, содержащий параметры user и
     * password
     */
    private static Properties getProperties() {
        /*
         * TODO #03 Реализуйте метод getProperties
         */
        return properties;
    }

    /**
     * Возвращает соединение с базой данных Sample
     *
     * @return объект типа {@link Connection}
     * @throws SQLException
     */
    private static Connection getConnection() throws SQLException {
        /*
         * TODO #04 Реализуйте метод getConnection
         */
//        if (checkDriver(url)) {

            Connection con = DriverManager.getConnection(url, properties);
            return con;
//        } else {
//            return null;
//        }

    }

    private static boolean checkDriver(String url) {
        System.out.println("Driver list:");
        Enumeration<Driver> e
                = DriverManager.getDrivers();
        while (e.hasMoreElements()) {
            System.out.println(e.nextElement());
        }
        Driver d = null;
        try {
            d = DriverManager.getDriver(url);
        } catch (SQLException ex) {
            System.out.println("Error: "
                    + ex.getMessage());
        }
        return d != null;
    }

}
