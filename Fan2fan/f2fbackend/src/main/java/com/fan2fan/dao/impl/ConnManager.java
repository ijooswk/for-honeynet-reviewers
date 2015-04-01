package com.fan2fan.dao.impl;

import com.fan2fan.util.EnumUtils;
import com.fan2fan.util.ReflectionUtils;
import com.google.common.collect.Sets;
import com.mysql.jdbc.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Maintain and manage the connections to the database,
 * in case that your jdbcTemplate may be changed or have more than one instance.
 * It would be convenient to declare them in a place.
 *
 * Created by huangsz on 2014/5/18.
 */
@Repository
public class ConnManager {

    public static final Logger logger = LoggerFactory.getLogger(ConnManager.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;  // created by spring boot

    @Autowired
    private TableColumns tableColumns;

    private static final Set<Class> basicTypes = Sets.newHashSet(
            String.class, Integer.class, Long.class,
            Boolean.class, Timestamp.class, Date.class
    );

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * Insert a table record. and return the newly created id.
     * Note that this function will ignore the null property contained in the attrs.
     * So please make sure for those are null, should have a default value in database.
     * @param obj: the obj containing information to be inserted
     * @param attrs: the fields to insert
     * @param table: the table name
     * @param <T>
     * @return
     */
    public <T> long insertReturnId(T obj, String[] attrs, final String table) {
        String sql = createInsertSql(attrs, table, obj);
        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            final BeanWrapper wrapper = new BeanWrapperImpl(obj);
            int i = 1;
            for (String attr : attrs) {
                Class<?> clazz = wrapper.getPropertyType(attr);
                Object value = wrapper.getPropertyValue(attr);
                if (value == null) {
                    continue;
                }
                if (clazz == Long.class) {
                    ps.setLong(i++, (Long) value);
                } else if (clazz.isEnum()) {
                    // attr is the enum index
                    ps.setInt(i++, EnumUtils.getIndexByEnum(value));
                } else if (clazz == Integer.class) {
                    ps.setInt(i++, (Integer) value);
                } else if (clazz == Timestamp.class) {
                    ps.setTimestamp(i++, (Timestamp) value);
                } else if (clazz == Date.class) {
                    Date d = (Date) value;
                    ps.setDate(i++, new java.sql.Date(d.getTime()));
                } else if (clazz == String.class) {
                    ps.setString(i++, (String) value);
                } else if (clazz == Boolean.class) {
                    ps.setBoolean(i++, (Boolean) value);
                } else {
                    throw new RuntimeException("Unexpected type to insert for attr: " + attr
                            + " with type:" + clazz);
                }
            }
            return ps;
        };
        return insertReturnId(psc);
    }

    /**
     * select * from table by the example
     * @param TABLE:
     *             the table to select from
     * @param example:
     *               the filter example, its not null attrs are the filters
     * @param isAnd:
     *             use 'and' or 'or'
     * @param mapper:
     *              the row mapper
     * @param <T>
     * @return
     */
    public<T> List<T> selectByExample(final String TABLE, final T example,
                                      final boolean isAnd, final RowMapper<T> mapper) {
        final String filterClause = createFilterClause(example, isAnd);
        final String sql = String.format("select * from %s %s", TABLE, filterClause);
        return jdbcTemplate.query(sql, mapper);
    }

    /**
     * update attrs what's not null
     * @param table
     * @param example
     * @param id
     * @param <T>
     */
    public<T> void updateExample(final String table, final T example, long id) {
        List<String> attrs = ReflectionUtils.getObjectNotNullPropertyNames(example);
        // we don't update those not basic types and columns not contained in database table
        Set<String> columns = tableColumns.getUpdateColumns(table);
        final BeanWrapper wrapper = new BeanWrapperImpl(example);
        logger.debug("attr before = {}", attrs);
        attrs = attrs.stream().filter(t -> columns.contains(t) && isUpdateCol(t, wrapper))
                .collect(Collectors.toList());
        logger.debug("attr after = {}", attrs);
        final String sql = createUpdateSql(attrs, table, id);
        Object[] values = attrs.stream().map(t -> ReflectionUtils.getValue(example, t)).toArray();
        jdbcTemplate.update(sql, values);
    }

    /**
     * select * from table by the example, and by limit
     * @param TABLE:
     *             the table to select from
     * @param example:
     *               the filter example, its not null attrs are the filters
     * @param isAnd:
     *             use 'and' or 'or'
     * @param mapper:
     *              the row mapper
     * @param offset:
     *              limit start
     * @param length:
     *              limit length
     * @param <T>
     * @return
     */
    public<T> List<T> selectByExample(final String TABLE, final T example, final boolean isAnd,
                                      final RowMapper<T> mapper, final int offset, final int length) {
        final String filterClause = createFilterClause(example, isAnd)
                .concat(String.format(" limit %d, %d", offset, length));
        final String sql = String.format("select * from %s %s", TABLE, filterClause);
        return jdbcTemplate.query(sql, mapper);
    }

    /**
     * should this column be updated in updateExample
     * @param col
     * @param srcWrapper
     * @return
     */
    private boolean isUpdateCol(String col, BeanWrapper srcWrapper) {
        return basicTypes.contains(srcWrapper.getPropertyType(col));
    }

    /**
     * create the where xx = xx clause.
     * @param example
     * @param isAnd
     * @param <T>
     * @return
     */
    private<T> String createFilterClause(final T example, final boolean isAnd) {
        List<String> filters =ReflectionUtils.getObjectNotNullPropertyNames(example);
        if (filters.size() == 0) {
            return "";
        }
        final String con = isAnd ? "and" : "or";
        StringBuilder filterClause = new StringBuilder();
        final BeanWrapper wrapper = new BeanWrapperImpl(example);
        filters.forEach(filter ->
                filterClause.append(String.format("%s %s = %s ", con, filter,
                        wrapFilterValue(wrapper.getPropertyValue(filter), wrapper.getPropertyType(filter)))));
        return "where".concat(filterClause.toString().substring(con.length()));
    }

    /**
     * wrap the value in a filter clause. such as "'a string'", "22" a integer
     * @param value
     * @param type
     * @return
     */
    private String wrapFilterValue(Object value, Class<?> type) {
        String result;
        if (type == Timestamp.class) {
            result = "" + ((Timestamp)value).getTime();
        } else if (type.isEnum()) {
            // filter is the enum index
            result = "" + EnumUtils.getIndexByEnum(value);
        } else if (type == Integer.class || type == Long.class || type == Boolean.class) {
            result = value.toString();
        } else if (type == String.class) {
            result = String.format("'%s'", value);
        } else {
            throw new RuntimeException("Unexpected type to create filter. Type:" + type);
        }
        return result;
    }

    /**
     * Create the update sql command.
     * Of cause we can use reflection to update to database directly like the insert,
     * but in case we are going to use '?' in jdbcTemplate to prevent sql injection, so
     * we just create an update command.
     * @param attrs
     * @param table
     * @param id
     * @return
     */
    public String createUpdateSql(String[] attrs, final String table, final long id) {
        String command = String.format("update `%s` set ", table);
        StringBuilder sb = new StringBuilder();
        for (String attr : attrs) {
            sb.append(String.format(",`%s`=?", attr));
        }
        return command.concat(sb.toString().substring(1)).concat(String.format(" where id=%d", id));
    }

    /**
     * create update sql
     * @param attrs
     * @param table
     * @param id
     * @return
     */
    public String createUpdateSql(List<String> attrs, final String table, final long id) {
        String[] attrsArray = new String[attrs.size()];
        attrs.toArray(attrsArray);
        return createUpdateSql(attrsArray, table, id);
    }

    /**
     * create the insert sql command
     * @param attrs
     * @param table
     * @return
     */
    public<T> String createInsertSql(String[] attrs, final String table, T obj) {
        StringBuilder fb = new StringBuilder();  // fields
        StringBuilder vb = new StringBuilder();  // values
        List<String> attrList = new ArrayList<>(Arrays.asList(attrs));
        attrList.stream()
            .filter(t -> ReflectionUtils.getValue(obj, t) != null)
            .forEach(attr -> {
                fb.append(String.format(", `%s`", attr));
                vb.append(",?");
            });
        String fields = fb.toString().substring(1); // trim the first ','
        String values = vb.toString().substring(1); // trim the first ','
        return String.format("insert into `%s` (%s) values (%s)", table, fields, values);
    }

    /**
     * insert a new object, and return its newly created id.
     * @param psc
     * @return
     */
    private long insertReturnId(PreparedStatementCreator psc){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
