package com.lz.center.entity;
import com.lz.center.ToDtoable;
import com.lz.game.rpc.RpcDto;

import java.util.Date;
/**
 *
 * @author benkris
 *
 */
//@Entity
//@Table(name = "gm_cdkey")
public class CDKeyEntity implements ToDtoable<RpcDto.CdKeyDTO>
{             //extends Persistance
    //@Column(name = "gift_id")
    private int    giftId;
    //@Column(name = "name")
    private String cdName;
    //@Column(name = "legeal_count")
    private int    legealCount;
    private int    leftCount;
    //@Column(name = "date_limit")
    private Date   dateLimit;
    //@Column(name = "cd_key")
    private String cdKey;
    //@Column(name = "zone_id")
    private int    zoneId;
    public String getUuid()
    {
        return uuid;
    }
    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
    private String uuid;
    private Date addTime = new Date();
    public CDKeyEntity()
    {
    }
    public CDKeyEntity(RpcDto.CdKeyDTO dto)
    {
        this.leftCount = dto.getLeftCount();
        this.zoneId = dto.getZoneId();
        this.addTime = new Date(dto.getAddTime());
        this.cdKey = dto.getCdKey();
        this.cdName = dto.getCdName();
        this.dateLimit = new Date(dto.getDateLimit());
        this.giftId = dto.getGiftId();
        this.legealCount = dto.getLegealCount();
        this.addTime = new Date(dto.getAddTime());
    }
    public String getCdKey()
    {
        return cdKey;
    }
    public void setCdKey(String cdKey)
    {
        this.cdKey = cdKey;
    }
    public String getCdName()
    {
        return cdName;
    }
    public Date getDateLimit()
    {
        return dateLimit;
    }
    public int getGiftId()
    {
        return giftId;
    }
    public int getLegealCount()
    {
        return legealCount;
    }
    public void setCdName(String cdName)
    {
        this.cdName = cdName;
    }
    public void setDateLimit(Date dateLimit)
    {
        this.dateLimit = dateLimit;
    }
    public void setGiftId(int giftId)
    {
        this.giftId = giftId;
    }
    public void setLegealCount(int legealCount)
    {
        this.legealCount = legealCount;
    }
    public RpcDto.CdKeyDTO toDTO()
    {
        RpcDto.CdKeyDTO.Builder ret = RpcDto.CdKeyDTO.newBuilder();
        ret.setLeftCount(leftCount);
        ret.setZoneId(zoneId);
        ret.setAddTime(addTime.getTime());
        ret.setCdKey(cdKey);
        ret.setCdName(cdName);
        ret.setDateLimit(dateLimit.getTime());
        ret.setGiftId(giftId);
        ret.setLegealCount(legealCount);
        ret.setUuid(uuid);
        return ret.build();
    }
    public int getZoneId()
    {
        return zoneId;
    }
    public void setZoneId(int zoneId)
    {
        this.zoneId = zoneId;
    }
    public Date getAddTime()
    {
        return addTime;
    }
    public void setAddTime(Date addTime)
    {
        this.addTime = addTime;
    }
    public int getLeftCount()
    {
        return leftCount;
    }
    public void setLeftCount(int leftCount)
    {
        this.leftCount = leftCount;
    }
}
