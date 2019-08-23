package com.lz.center.entity;
import com.lz.center.acl.Limit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
/**
 * UserEntity: Teaey
 * Date: 13-7-10
 */
@Entity
@Table(name = "gm_user")
public class UserEntity extends Persistance
{
    @Column(name = "account")
    private String account;
    @Column(name = "passwd")
    private String passwd;
    @Column(name = "username")
    private String username;
    @Column(name = "limits")
    private long   limits;
    @Column(name = "created_by")
    private String createdBy = "";
    public List<Limit> limits()
    {
        List<Limit> ret = new ArrayList<>();
        for (Limit each : Limit.values())
        {
            if ((each.getMark() & limits) != 0)
            {
                ret.add(each);
            }
        }
        return ret;
    }
    public void addLimit(Limit limit)
    {
        limits |= limit.getMark();
    }
    public void removeLimit(Limit limit)
    {
        limits &= ~limit.getMark();
    }
    public static void main(String[] args)
    {
        System.out.println(32 & 33);
    }
    public boolean haveLimit(Limit limit)
    {
        return haveLimit(limit.getMark());
    }
    public boolean haveLimit(long mark)
    {
        return (this.limits & mark) != 0;
    }
    public String getAccount()
    {
        return account;
    }
    public void setAccount(String account)
    {
        this.account = account;
    }
    public String getPasswd()
    {
        return passwd;
    }
    public void setPasswd(String passwd)
    {
        this.passwd = passwd;
    }
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }
    public long getLimits()
    {
        return limits;
    }
    public void setLimits(long limits)
    {
        this.limits = limits;
    }
    //    public UserGroup getUserGroup()
    //    {
    //        return userGroup;
    //    }
    //    public void setUserGroup(UserGroup userGroup)
    //    {
    //        this.userGroup = userGroup;
    //    }
    //    public String getUserGroupStr()
    //    {
    //        return userGroupStr;
    //    }
    //    public void setUserGroupStr(String userGroupStr)
    //    {
    //        this.userGroupStr = userGroupStr;
    //    }
}
