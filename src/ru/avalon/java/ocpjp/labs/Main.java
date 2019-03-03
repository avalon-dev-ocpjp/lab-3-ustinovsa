package ru.avalon.java.ocpjp.labs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

/**
 * Лабораторная работа №3
 * <p>
 * Курс: "DEV-OCPJP. Подготовка к сдаче сертификационных экзаменов серии Oracle Certified Professional Java Programmer"
 * <p>
 * Тема: "JDBC - Java Database Connectivity" 
 *
 * @author Daniel Alpatov <danial.alpatov@gmail.com>
 */


public class Main {

    /**
     * Точка входа в приложение
     * 
     * @param args the command line arguments
     */

    private static String url = "jdbc:postgresql://localhost:5432/ulanov";
    private static String user = "postgres";
    private static String password = "password";
    private static Connection connection;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
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
        Properties properties = new Properties();
        properties.setProperty("user", user); //
        properties.setProperty("password", password);

        return properties;

    }
    /**
     * Возвращает соединение с базой данных Sample
     * 
     * @return объект типа {@link Connection}
     * @throws SQLException

     */
    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        /*
         * TODO #04 Реализуйте метод getConnection
         */
        if(connection == null) {
            Properties properties = getProperties();
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(getUrl(), properties.getProperty("user"), properties.getProperty("password"));
        }
        return connection;
    }
}
