package com.lz.center.entity;
import com.lz.game.rpc.RpcDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * UserEntity: Teaey
 * Date: 13-7-16
 */
@Entity
@Table(name = "gm_online_info")
public class OnlineInfoEntity extends Persistance
{
    @Column(name = "num")
    private int    num;
    @Column(name = "zone_id")
    private int    zoneId;
    @Column(name = "zone_name")
    private String zoneName;
    public int getNum()
    {
        return num;
    }
    public void setNum(int num)
    {
        this.num = num;
    }
    public int getZoneId()
    {
        return zoneId;
    }
    public void setZoneId(int zoneId)
    {
        this.zoneId = zoneId;
    }
    public String getZoneName()
    {
        return zoneName;
    }
    public void setZoneName(String zoneName)
    {
        this.zoneName = zoneName;
    }
    public static OnlineInfoEntity fromDTO(RpcDto.OnlineInfoDTO dto)
    {
        OnlineInfoEntity ret = new OnlineInfoEntity();
        ret.zoneId = dto.getZoneId();
        ret.num = dto.getNum();
        ret.zoneName = dto.getZoneName();
        return ret;
    }
}
