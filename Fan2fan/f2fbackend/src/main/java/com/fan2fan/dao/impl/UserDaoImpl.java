package com.fan2fan.dao.impl;

import com.fan2fan.dao.UserDao;
import com.fan2fan.dao.impl.mapper.RowMapperFactory;
import com.fan2fan.dao.impl.mapper.UserDetailRowMapper;
import com.fan2fan.dao.impl.mapper.UserRowMapper;
import com.fan2fan.model.User;
import com.fan2fan.model.UserDetail;
import com.fan2fan.model.UserRecord;
import com.fan2fan.model.UserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

/**
 * Implement of UserDao
 * Created by huangsz on 2014/5/18.
 */
@Repository
public class UserDaoImpl implements UserDao {

    public static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    private ConnManager connManager;

    @Autowired
    private DataSource dataSource;//Provided by spring boot.

    @Autowired
    private RowMapperFactory mapperFactory;

    private static final String USER_TABLE = "user";

    private static final String DETAIL_TABLE = "user_detail";

    private static final String RECORD_TABLE = "user_record";

    @Override
    public long insertBasicTables(final User user) {
        long userId = insertUserTable(user);
        insertUserDetail(userId);
        insertUserRecord(userId);
        return userId;
    }

    @Override
    public void insertUserToken(long userId, String token, Timestamp expires) {
        final String sql = "insert into user_token(userId, token, expires) values (?, ?, ?)";
        connManager.getJdbcTemplate().update(sql, userId, token, expires);
    }

    @Override
    public boolean emailRegistered(String email) {
        final String sql = "select count(*) from `user` where email = ?";
        return connManager.getJdbcTemplate().queryForObject(sql, new Object[]{email}, Integer.class) > 0;
    }

    @Override
    public boolean userNameRegistered(String userName) {
        final String sql = "select count(*) from user where userName = ?";
        return connManager.getJdbcTemplate().queryForObject(sql, new Object[]{userName}, Integer.class) > 0;
    }

    @Override
    public UserToken getUserToken(String token, long userId) {
        final String sql = "select * from user_token where userId = ? and token = ?";
        return connManager.getJdbcTemplate().queryForObject
                (sql, new Object[]{userId, token}, mapperFactory.getUserTokenMapper());
    }

    @Override
    public void activateUser(UserToken userToken) {
        connManager.getJdbcTemplate().update("update user set activated = 1 where id = ?",
                userToken.getUserId()
        );
        markTokenUsed(userToken.getId());
    }

    @Override
    public User getUserByEmail(String email) {
        List<User> users = connManager.getJdbcTemplate().query(
                "select * from user where email=?", new Object[]{email}, new UserRowMapper());
        if (users.size() > 1) {
            throw new IncorrectResultSizeDataAccessException("More than one users sharing email: " + email, 1);
        }
        return users.size() == 0 ? null : users.get(0);
    }

    @Override
    public User getUserById(long id) {
        return connManager.getJdbcTemplate().queryForObject(
                "select * from user where id=?", new Object[]{id}, new UserRowMapper()
        );
    }

    @Override
    public void updatePassword(long id, String newPwd) {
        connManager.getJdbcTemplate().update(
                "update `user` set password = ? where id = ?", newPwd, id
        );
    }

    @Override
    public UserDetail getUserDetailById(long userId) {
        return connManager.getJdbcTemplate().queryForObject(
                "select * from `user_detail` where id = ?",
                new Object[]{userId}, new UserDetailRowMapper());
    }

    @Override
    public void markTokenUsed(long tokenId) {
        connManager.getJdbcTemplate().update(
                "update user_token set used = 1 where id = ?", tokenId
        );
    }

    @Override
    public void updateUser(User user) {
        user.setOperatorId(null); // now user acts as an example
        connManager.updateExample(USER_TABLE, user, user.getId());
//        User original = getUserById(user.getId());
//        ReflectionUtils.copyPropertiesIgnoreNull(user, original);
//        final String sql = "update `user` set email=?, firstname=?, lastname=?, username=?, notifiable=? " +
//                        "where id = ?";
//        connManager.getJdbcTemplate().update(sql, original.getEmail(), original.getFirstName(),
//                original.getLastName(), original.getUserName(), original.isNotifiable(), user.getId());
    }

    @Override
    public void updateUserDetail(UserDetail detail) {
        detail.setOperatorId(null);
        connManager.updateExample(DETAIL_TABLE, detail, detail.getId());
//        UserDetail original = getUserDetailById(detail.getId());
//        ReflectionUtils.copyPropertiesIgnoreNull(detail, original);
//        final String sql = "update user_detail " +
//                "set avatarUrl=?,`desc`=?,gender=?,phone=?,nation=?,city=?,province=?,address=? where id = ?";
//        connManager.getJdbcTemplate().update(sql, original.getAvatarUrl(), original.getDesc(),
//                original.getGender().ordinal(), original.getPhone(), original.getNation(),
//                original.getCity(), original.getProvince(), original.getAddress(), detail.getId());
    }

    @Override
    public String getPassword(long userId) {
        return connManager.getJdbcTemplate().queryForObject(
                "select password from user where id=?",
                new Object[]{userId}, String.class);
    }

    @Override
    public void changePoints(long userId, int points) {
        connManager.getJdbcTemplate().update(
                String.format("update %s set points = points + ? where id = ?", DETAIL_TABLE),
                points, userId
        );
    }

    @Override
    public UserRecord getUserRecordById(long userId) {
        return connManager.getJdbcTemplate().queryForObject(
                String.format("select * from %s where id = ?", RECORD_TABLE),
                new Object[]{userId}, mapperFactory.getUserRecordRowMapper()
        );
    }

    @Override
    public void updateUserRecord(UserRecord record) {
        String[] attrs = new String[]{"stories", "packs", "views", "invites",
                "contribStories", "sellPacks", "buyPacks", "reviews"};
        final String sql = connManager.createUpdateSql(attrs, RECORD_TABLE, record.getId());
        connManager.getJdbcTemplate().update(sql, record.getStories(), record.getPacks(),
                record.getViews(), record.getInvites(), record.getContribStories(), record.getSellPacks(),
                record.getBuyPacks(), record.getReviews());
    }

    @Override
    public void updateAvatarUrl(long userId, String fileName) {
        final String sql = String.format("update `%s` set avatarUrl = ? where id = ?", DETAIL_TABLE);
        connManager.getJdbcTemplate().update(sql, fileName, userId);
    }

    private void insertUserDetail(long userId) {
        final String sql = "insert into user_detail(id) values(?)";
        connManager.getJdbcTemplate().update(sql, userId);
    }

    private void insertUserRecord(long userId) {
        final String sql = "insert into user_record(id) values(?)";
        connManager.getJdbcTemplate().update(sql, userId);
    }

    private long insertUserTable(final User user) {
        final String userSql =
                "insert into user(email, username, password, createTime, notifiable, `type`) " +
                "values(?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        connManager.getJdbcTemplate().update(connection -> {
            PreparedStatement ps =
                    connection.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            ps.setString(i++, user.getEmail());
            ps.setString(i++, user.getUserName());
            ps.setString(i++, user.getPassword());
            ps.setTimestamp(i++, user.getCreateTime());
            ps.setBoolean(i++, user.isNotifiable());
            ps.setInt(i++, user.getType().ordinal());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

}
