package com.fan2fan.dao;

public interface UserTeamDao {

    public void updateUserFavoriteClub(long userId, long teamId);

    public Long getUserFavoriteTeamId(long userId);
}
