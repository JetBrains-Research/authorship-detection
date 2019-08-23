package com.lz.center.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * User: Teaey
 * Date: 13-9-13
 */
@Entity
@Table(name = "gm_zone")
public class ZoneEntity extends Persistance
{
    @Column(name = "zone_id")
    private Integer zoneId = Integer.valueOf(0);
    @Column(name = "zone_name")
    private String zoneName;
    @Column(name = "server_group_id")
    private Integer serverGroupId = Integer.valueOf(0);
    @Column(name = "game_server_host")
    private String gameServerHost;
    @Column(name = "chat_server_host")
    private String chatServerHost;
    @Column(name = "auth_server_host")
    private String authServerHost;
    @Column(name = "gateway_server_host_list")
    private String gatewayServerHostList;
    @Column(name = "db_url")
    private String dbUrl;
    @Column(name = "db_username")
    private String dbUsername;
    @Column(name = "db_passwd")
    private String dbPasswd;
    public void setZoneId(Integer zoneId)
    {
        this.zoneId = zoneId;
    }
    public void setServerGroupId(Integer serverGroupId)
    {
        this.serverGroupId = serverGroupId;
    }
    public String getDbUrl()
    {
        return dbUrl;
    }
    public void setDbUrl(String dbUrl)
    {
        this.dbUrl = dbUrl;
    }
    public String getDbUsername()
    {
        return dbUsername;
    }
    public void setDbUsername(String dbUsername)
    {
        this.dbUsername = dbUsername;
    }
    public String getDbPasswd()
    {
        return dbPasswd;
    }
    public void setDbPasswd(String dbPasswd)
    {
        this.dbPasswd = dbPasswd;
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
    public int getServerGroupId()
    {
        return serverGroupId;
    }
    public void setServerGroupId(int serverGroupId)
    {
        this.serverGroupId = serverGroupId;
    }
    public String getGameServerHost()
    {
        return gameServerHost;
    }
    public void setGameServerHost(String gameServerHost)
    {
        this.gameServerHost = gameServerHost;
    }
    public String getChatServerHost()
    {
        return chatServerHost;
    }
    public void setChatServerHost(String chatServerHost)
    {
        this.chatServerHost = chatServerHost;
    }
    public String getAuthServerHost()
    {
        return authServerHost;
    }
    public void setAuthServerHost(String authServerHost)
    {
        this.authServerHost = authServerHost;
    }
    public String getGatewayServerHostList()
    {
        return gatewayServerHostList;
    }
    public void setGatewayServerHostList(String gatewayServerHostList)
    {
        this.gatewayServerHostList = gatewayServerHostList;
    }
    @Override
    public String toString()
    {
        return "ZoneEntity{" +
                "zoneId=" + zoneId +
                ", zoneName='" + zoneName + '\'' +
                ", serverGroupId=" + serverGroupId +
                ", gameServerHost='" + gameServerHost + '\'' +
                ", chatServerHost='" + chatServerHost + '\'' +
                ", authServerHost='" + authServerHost + '\'' +
                ", gatewayServerHostList='" + gatewayServerHostList + '\'' +
                '}';
    }
}
