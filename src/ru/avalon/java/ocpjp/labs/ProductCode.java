package ru.avalon.java.ocpjp.labs;

import java.sql.*;
import java.util.*;

/**
 * Класс описывает представление о коде товара и отражает соответствующую 
 * таблицу базы данных Sample (таблица PRODUCT_CODE).
 * 
 * @author Daniel Alpatov <danial.alpatov@gmail.com>
 */
public class ProductCode {
    /**
     * Код товара
     */
    private String code;
    /**
     * Кода скидки
     */
    private char discountCode;
    /**
     * Описание
     */
    private String description;
    /**
     * Основной конструктор типа {@link ProductCode}
     * 
     * @param code код товара
     * @param discountCode код скидки
     * @param description описание 
     */
    public ProductCode(String code, char discountCode, String description) {
        this.code = code;
        this.discountCode = discountCode;
        this.description = description;
    }
    /**
     * Инициализирует объект значениями из переданного {@link ResultSet}
     * 
     * @param set {@link ResultSet}, полученный в результате запроса, 
     * содержащего все поля таблицы PRODUCT_CODE базы данных Sample.
     */
    private ProductCode(ResultSet set) {
        /*
         * TODO #05 реализуйте конструктор класса ProductCode
         */
        try {
            code = set.getString("code");
            String discountCode = set.getString("discountCode");
            if (discountCode.length() == 1) {
                this.discountCode = discountCode.charAt(0);
            }
            description = set.getString("description");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Возвращает код товара
     * 
     * @return Объект типа {@link String}
     */
    public String getCode() {
        return code;
    }
    /**
     * Устанавливает код товара
     * 
     * @param code код товара
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * Возвращает код скидки
     * 
     * @return Объект типа {@link String}
     */
    public char getDiscountCode() {
        return discountCode;
    }
    /**
     * Устанавливает код скидки
     * 
     * @param discountCode код скидки
     */
    public void setDiscountCode(char discountCode) {
        this.discountCode = discountCode;
    }
    /**
     * Возвращает описание
     * 
     * @return Объект типа {@link String}
     */
    public String getDescription() {
        return description;
    }
    /**
     * Устанавливает описание
     * 
     * @param description описание
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCode that = (ProductCode) o;
        return discountCode == that.discountCode &&
                Objects.equals(code, that.code) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, discountCode, description);
    }

    /**
     * Возвращает строковое представление кода товара.
     *
     * @return Объект типа {@link String}
     */
    @Override
    public String toString() {
        return "ProductCode{" +
                "code='" + code + '\'' +
                ", discountCode=" + discountCode +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Возвращает запрос на выбор всех записей из таблицы PRODUCT_CODE 
     * базы данных Sample
     * 
     * @param connection действительное соединение с базой данных
     * @return Запрос в виде объекта класса {@link PreparedStatement}
     */
    public static PreparedStatement getSelectQuery(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM \"ProductCode\"");
    }
    /**
     * Возвращает запрос на добавление записи в таблицу PRODUCT_CODE 
     * базы данных Sample
     * 
     * @param connection действительное соединение с базой данных
     * @return Запрос в виде объекта класса {@link PreparedStatement}
     */
    public static PreparedStatement getInsertQuery(Connection connection) throws SQLException {
        return connection.prepareStatement("INSERT INTO \"ProductCode\" " +
                "values (?, ?, ?)");
        /*
         * TODO #10 Реализуйте метод getInsertQuery
         */
    }
    /**
     * Возвращает запрос на обновление значений записи в таблице PRODUCT_CODE 
     * базы данных Sample
     * 
     * @param connection действительное соединение с базой данных
     * @return Запрос в виде объекта класса {@link PreparedStatement}
     */
    public static PreparedStatement getUpdateQuery(Connection connection) throws SQLException {
        /*
         * TODO #11 Реализуйте метод getUpdateQuery
         * //insert into "ProductCode" values ('mycode', 'a', 'description');
         * SELECT * FROM "ProductCode"
         */
        return connection.prepareStatement("UPDATE \"ProductCode\" SET \"discountCode\" = ?, description = ? WHERE code = ?");
    }
    /**
     * Преобразует {@link ResultSet} в коллекцию объектов типа {@link ProductCode}
     * 
     * @param set {@link ResultSet}, полученный в результате запроса, содержащего 
     * все поля таблицы PRODUCT_CODE базы данных Sample
     * @return Коллекция объектов типа {@link ProductCode}
     * @throws SQLException 
     */
    public static Collection<ProductCode> convert(ResultSet set) throws SQLException {
        /*
         * TODO #12 Реализуйте метод convert
         */
        List<ProductCode> productCodes = new ArrayList<>();

        while (set.next()) {
            ProductCode productCode = new ProductCode(set);
            productCodes.add(productCode);
        }

        return  productCodes;
    }
    /**
     * Сохраняет текущий объект в базе данных. 
     * <p>
     * Если запись ещё не существует, то выполняется запрос типа INSERT.
     * <p>
     * Если запись уже существует в базе данных, то выполняется запрос типа UPDATE.
     * 
     * @param connection действительное соединение с базой данных
     */
    public void save(Connection connection) throws SQLException {
        PreparedStatement selectQuery = getSelectQuery(connection);
        ResultSet selectResultSet = selectQuery.executeQuery();
        Collection<ProductCode> productCodes = convert(selectResultSet);
        boolean found = false;
        for (ProductCode productCode : productCodes) {
            if (productCode.getCode() != null && productCode.getCode().equals(this.code)) {
                found = true;
            }
        }

        if (!found) {
            PreparedStatement insertQuery = getInsertQuery(connection);
            insertQuery.setString(1, code);
            insertQuery.setString(2, String.valueOf(discountCode));
            insertQuery.setString(3, description);
            insertQuery.executeUpdate();
        } else {
            PreparedStatement updateQuery = getUpdateQuery(connection);
            updateQuery.setString(1, String.valueOf(discountCode));
            updateQuery.setString(2, description);
            updateQuery.setString(3, code);
            updateQuery.executeUpdate();
        }
        /*
         * TODO #13 Реализуйте метод convert
         */

    }
    /**
     * Возвращает все записи таблицы PRODUCT_CODE в виде коллекции объектов
     * типа {@link ProductCode}
     * 
     * @param connection действительное соединение с базой данных
     * @return коллекция объектов типа {@link ProductCode}
     * @throws SQLException 
     */
    public static Collection<ProductCode> all(Connection connection) throws SQLException {
        try (PreparedStatement statement = getSelectQuery(connection)) {
            try (ResultSet result = statement.executeQuery()) {
                return convert(result);
            }
        }
    }
}
