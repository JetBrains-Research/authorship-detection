package com.lz.center.entity;
import com.lz.center.ToDtoable;
import com.lz.game.rpc.RpcDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * UserEntity: Teaey
 * Date: 13-7-17
 */
@Entity
@Table(name = "gm_bulletin")
public class BulletinEntity extends Persistance implements ToDtoable<RpcDto.BulletinDTO>
{
    @Column(name = "sort_flag")
    private Integer sortFlag = Integer.valueOf(0);
    @Column(name = "link_id")
    private Integer linkId   = Integer.valueOf(0);
    @Column(name = "title")
    private String  title;
    @Column(name = "content")
    private String  content;//每一行用\n\r隔开
    @Column(name = "zone_id")
    private Integer zoneId;
    public int getLinkId()
    {
        return linkId;
    }
    public void setLinkId(int linkId)
    {
        this.linkId = linkId;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getContent()
    {
        return content;
    }
    public void setContent(String content)
    {
        this.content = content;
    }
    public RpcDto.BulletinDTO toDTO()
    {
        RpcDto.BulletinDTO.Builder dto = RpcDto.BulletinDTO.newBuilder();
        dto.setId(id);
        dto.setSortFlag(sortFlag);
        dto.setLinkId(linkId);
        dto.setTitle(title);
        dto.setContent(content);
        return dto.build();
    }
    public int getSortFlag()
    {
        return sortFlag;
    }
    public void setSortFlag(int sortFlag)
    {
        this.sortFlag = sortFlag;
    }
    public Integer getZoneId()
    {
        return zoneId;
    }
    public void setZoneId(Integer zoneId)
    {
        this.zoneId = zoneId;
    }
}
