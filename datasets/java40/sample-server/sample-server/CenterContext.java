package com.lz.center;
import com.lz.center.entity.UserEntity;
import com.lz.center.entity.ZoneEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * UserEntity: Teaey
 * Date: 13-7-6
 */
public class CenterContext
{
    public static final CenterContext newContext(UserEntity user)
    {
        return new CenterContext(user);
    }
    private CenterContext(UserEntity user)
    {
        if (null == user)
            throw new NullPointerException("user");
        this.user = user;
    }
    private volatile UserEntity user;
    private volatile ZoneEntity targetZone;
    public UserEntity getUser()
    {
        return user;
    }
    public void setUser(UserEntity user)
    {
        this.user = user;
    }
    public boolean isAuth()
    {
        return null == user;
    }
    public ZoneEntity getTargetZone()
    {
        return targetZone;
    }
    public void setTargetZone(ZoneEntity targetZone)
    {
        this.targetZone = targetZone;
    }
}
