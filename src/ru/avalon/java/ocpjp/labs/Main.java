package ru.avalon.java.ocpjp.labs;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
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
    public static void main(String[] args) throws SQLException, IOException {
          /*
         * TODO #01 Подключите к проекту все библиотеки, необходимые для соединения с СУБД.
         */

        try ( Connection connection = getConnection()) {
            ProductCode code = new ProductCode("MO", 'N', "Movies");
            Collection<ProductCode> pcl = new HashSet<>();
            pcl = code.all(connection);
            
            code.save(connection);
            printAllCodes(connection);
            
            code.setDescription("Test");
            code.setDiscountCode('T');
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
        codes.forEach((code) -> {
            System.out.println(code);
        });
    }

    /**
     * Возвращает URL, описывающий месторасположение базы данных
     *
     * @return URL в виде объекта класса {@link String}
     */
    private static String getUrl() throws IOException {
        /*
         * TODO #02 Реализуйте метод getUrl
         */
        return getProperties().getProperty("url");
    }

    /**
     * Возвращает параметры соединения
     *
     * @return Объект класса {@link Properties}, содержащий параметры user и
     * password
     */
    private static Properties getProperties() throws IOException {

        Properties info = new Properties();
        try ( InputStream is = ClassLoader.getSystemResourceAsStream(
                "resources\\db.properties")) {
            info.load(is);
        }
        return info;
    }

    /**
     * Возвращает соединение с базой данных Sample
     *
     * @return объект типа {@link Connection}
     * @throws SQLException
     */
    private static Connection getConnection() throws SQLException, IOException {
        /*
         * TODO #04 Реализуйте метод getConnection
         */
        return DriverManager.getConnection(getUrl(), getProperties());
    }

}
