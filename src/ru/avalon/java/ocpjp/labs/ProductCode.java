package ru.avalon.java.ocpjp.labs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Класс описывает представление о коде товара и отражает соответствующую
 * таблицу базы данных Sample (таблица PRODUCT_CODE).
 *
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
    private ProductCode(ResultSet set) throws SQLException {
        code = set.getString("prod_code");
        discountCode = set.getString("discount_code").charAt(0);
        description = set.getString("description");
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

    /**
     * Хеш-функция типа {@link ProductCode}.
     *
     * @return Значение хеш-кода объекта типа {@link ProductCode}
     */
    @Override
    public int hashCode() {

        return code.hashCode() + discountCode + description.hashCode();

    }

    /**
     * Сравнивает некоторый произвольный объект с текущим объектом типа
     * {@link ProductCode}
     *
     * @param obj Объект, скоторым сравнивается текущий объект.
     * @return true, если объект obj тождественен текущему объекту. В обратном
     * случае - false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        final ProductCode other = (ProductCode) obj;
        return code.equals(other.code)
                &Objects.equals(discountCode, other.discountCode)
                &description.equals(other.description);

        /*boolean result = code.equals(other.code) & discountCode
                == other.discountCode;
        if (description == null & other.description == null) {
            return result;
        }

        if (description != null & other.description != null) {
            return result & description.equals(other.description);
        }*/
    }

    /**
     * Возвращает строковое представление кода товара.
     *
     * @return Объект типа {@link String}
     */
    @Override
    public String toString() {
        return "Product with code : " + code
                + " have discount code : " + discountCode
                + " and description : " + description;
    }

    /**
     * Возвращает запрос на выбор всех записей из таблицы PRODUCT_CODE базы
     * данных Sample
     *
     * @param connection действительное соединение с базой данных
     * @return Запрос в виде объекта класса {@link PreparedStatement}
     */
    public static PreparedStatement getSelectQuery(Connection connection) throws SQLException {

        if (connection != null) {
            String selectQuery = "SELECT * FROM APP.product_code";
            return connection.prepareStatement(selectQuery);
        } else {
            return null;
        }

    }

    /**
     * Возвращает запрос на добавление записи в таблицу PRODUCT_CODE базы данных
     * Sample
     *
     * @param connection действительное соединение с базой данных
     * @return Запрос в виде объекта класса {@link PreparedStatement}
     */
    public PreparedStatement getInsertQuery(Connection connection) throws SQLException {
        if (connection != null) {
            String insertQuery = "INSERT INTO APP.product_code "
                    + "VALUES (?,?,?)";
            return connection.prepareStatement(insertQuery);
        } else {
            return null;
        }
    }

    /**
     * Выполняет запрос на добавление записи в таблицу PRODUCT_CODE базы данных
     * Sample
     *
     * @param connection действительное соединение с базой данных
     */
    private void insert(Connection connection) throws SQLException {
        try (PreparedStatement ps = getInsertQuery(connection)) {
            ps.setString(1, code);
            ps.setString(2, Character.toString(discountCode));
            ps.setString(3, description);
            ps.execute();
        }
    }

    /**
     * Возвращает запрос на обновление значений записи в таблице PRODUCT_CODE
     * базы данных Sample
     *
     * @param connection действительное соединение с базой данных
     * @return Запрос в виде объекта класса {@link PreparedStatement}
     * @throws java.sql.SQLException
     */
    public static PreparedStatement getUpdateQuery(Connection connection) throws SQLException {

        if (connection != null) {
            String updateQuery = "UPDATE APP.product_code "
                    + "SET discount_code = ?, description = ? "
                    + "WHERE prod_code = ?";
            return connection.prepareStatement(updateQuery);
        } else {
            return null;
        }

    }

    /**
     * Выполняет запрос на обновление значений записи в таблице PRODUCT_CODE
     * базы данных Sample
     *
     * @param connection действительное соединение с базой данных
     * @throws java.sql.SQLException
     */
    public void update(Connection connection) throws SQLException {
        try (PreparedStatement ps = getUpdateQuery(connection)) {
            ps.setString(1, Character.toString(discountCode));
            ps.setString(2, description);
            ps.setString(3, code);
            ps.execute();
        }
    }

    /**
     * Преобразует {@link ResultSet} в коллекцию объектов типа
     * {@link ProductCode}
     *
     * @param set {@link ResultSet}, полученный в результате запроса,
     * содержащего все поля таблицы PRODUCT_CODE базы данных Sample
     * @return Коллекция объектов типа {@link ProductCode}
     * @throws SQLException
     */
    public static Collection<ProductCode> convert(ResultSet set) throws SQLException {
        Collection<ProductCode> productList = new HashSet<>();
        while (set.next()) {
            productList.add(new ProductCode(set));
        }
        return productList;

    }

    /**
     * Сохраняет текущий объект в базе данных.
     * <p>
     * Если запись ещё не существует, то выполняется запрос типа INSERT.
     * <p>
     * Если запись уже существует в базе данных, то выполняется запрос типа
     * UPDATE.
     *
     * @param connection действительное соединение с базой данных
     */
    public void save(Connection connection) throws SQLException {
        if (connection != null) {
            Collection<ProductCode> productList = all(connection);
            for (ProductCode p : productList) {
                if (this.equals(p)) {
                    update(connection);
                } else {
                    insert(connection);
                }
            }

        }

    }

    /**
     * Возвращает все записи таблицы PRODUCT_CODE в виде коллекции объектов типа
     * {@link ProductCode}
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
