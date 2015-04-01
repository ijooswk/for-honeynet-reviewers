package com.fan2fan.dao.impl;


import com.google.common.collect.Sets;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Table columns of each model
 * @author huangsz
 */
@Repository
public class TableColumns {

    private Map<String, Set<String>> updates = new HashMap<>();

    Set<String> userUpdates = Sets.newHashSet("email",
            "username", "activated", "type", "level", "notifiable", "fullName");

    Set<String> userDetailUpdates = Sets.newHashSet(
            "avatarUrl", "desc", "gender", "birthYear", "birthMonth", "birthDay",
            "phone", "address", "nation", "city", "occupation"
    );

    public TableColumns() {
        updates.put("user", userUpdates);
        updates.put("user_detail", userDetailUpdates);
    }

    public Set<String> getUpdateColumns(final String table) {
        return updates.get(table);
    }
}
