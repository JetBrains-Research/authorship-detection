package com.lz.center.entity;
import javax.persistence.*;
import java.util.Date;
/**
 * UserEntity: Teaey
 * Date: 13-7-8
 */
//@Entity
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
public class Persistance
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    protected int id;
    @Column(name = "add_time")
    protected Date addTime    = new Date();
    @Column(name = "modify_time")
    protected Date modifyTime = new Date();
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public Date getAddTime()
    {
        return addTime;
    }
    public void setAddTime(Date addTime)
    {
        this.addTime = addTime;
    }
    public Date getModifyTime()
    {
        return modifyTime;
    }
    public void setModifyTime(Date modifyTime)
    {
        this.modifyTime = modifyTime;
    }
}
