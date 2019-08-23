package com.lz.center.entity;
import com.lz.center.ToDtoable;
import com.lz.game.rpc.RpcDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
/**
 * UserEntity: Teaey
 * Date: 13-7-8
 */
@Entity
@Table(name = "gm_notice")
public class NoticeEntity extends Persistance  implements ToDtoable<RpcDto.SysNoticeDTO>
{
    @Column(name = "content")
    private String content;
    @Column(name = "start_time")
    private Date   startTime;
    @Column(name = "stop_time")
    private Date   stopTime;
    @Column(name = "zone_id")
    private int    zoneId;
    @Column(name = "round")
    private int    round;
    @Column(name = "level")
    private int    level;
    @Column(name = "remark")
    private String remark;
    @Column(name = "every_interval")
    private int    everyInterval;
    @Column(name = "type")
    private int    type;
    @Override
    public RpcDto.SysNoticeDTO toDTO()
    {
        RpcDto.SysNoticeDTO.Builder ret = RpcDto.SysNoticeDTO.newBuilder();
        ret.setId(id);
        ret.setContent(content);
        ret.setStopTime(stopTime.getTime());
        ret.setStartTime(startTime.getTime());
        ret.setEveryInterval(everyInterval);
        ret.setRemark(remark);
        ret.setRound(round);
        ret.setZoneId(zoneId);
        ret.setLevel(level);
        ret.setType(type);
        return ret.build();
    }
    public String getContent()
    {
        return content;
    }
    public void setContent(String content)
    {
        this.content = content;
    }
    public Date getStartTime()
    {
        return startTime;
    }
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    public int getZoneId()
    {
        return zoneId;
    }
    public void setZoneId(int zoneId)
    {
        this.zoneId = zoneId;
    }
    public int getRound()
    {
        return round;
    }
    public void setRound(int round)
    {
        this.round = round;
    }
    public String getRemark()
    {
        return remark;
    }
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    public int getEveryInterval()
    {
        return everyInterval;
    }
    public void setEveryInterval(int everyInterval)
    {
        this.everyInterval = everyInterval;
    }
    public int getLevel()
    {
        return level;
    }
    public void setLevel(int level)
    {
        this.level = level;
    }
    public void setStopTime(Date stopTime)
    {
        this.stopTime = stopTime;
    }
    public Date getStopTime()
    {
        return this.stopTime;
    }
    public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
}
