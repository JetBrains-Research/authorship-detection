package com.lz.center.control;
import com.lz.center.dao.DaoManager;
import com.lz.center.rpc.ZoneEntityManager;
import com.lz.center.utils.JsonHelper;
import com.lz.center.JsonResponse;
import com.lz.center.entity.ZoneEntity;
import com.lz.center.rpc.RpcClientManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * User: Teaey
 * Date: 13-9-13
 */
@Controller
@RequestMapping("/server")
public class ServerController
{
    @RequestMapping("/zoneManage/")
    public String zoneManage(Map<String, Object> map)
    {
        List list = new ArrayList<>();
        map.put("data", list);
        return "zoneManage";
    }
    @ResponseBody
    @RequestMapping("/zoneManage/get")
    public String getZone(int pageNum, int pageSize)
    {
        //List<ZoneEntity> list = Dao.getInstance().selectList(ZoneEntity.class, pageNum, pageSize);
        List<ZoneEntity> list = DaoManager.getLocalDao().selectList(ZoneEntity.class);
        return JsonHelper.toJson(list);
    }
    @ResponseBody
    @RequestMapping("/zoneManage/add")
    public String addZone(@RequestParam(required = true) int zoneId, @RequestParam(required = true) String zoneName, @RequestParam(required = true) int serverGroupId, @RequestParam(required = true) String gameServerHost, @RequestParam(required = true) String chatServerHost, @RequestParam(required = true) String authServerHost, @RequestParam(required = true) String gatewayServerHostList)
    {
        JsonResponse resultCode = new JsonResponse();
        try
        {
            ZoneEntity entity = new ZoneEntity();
            entity.setZoneId(zoneId);
            entity.setZoneName(zoneName);
            entity.setServerGroupId(serverGroupId);
            entity.setGameServerHost(gameServerHost);
            entity.setAuthServerHost(authServerHost);
            entity.setChatServerHost(chatServerHost);
            entity.setGatewayServerHostList(gatewayServerHostList);
            DaoManager.getLocalDao().saveOrUpdate(entity);
            ZoneEntityManager.getInstance().addOrUpdateZoneEntity(entity);
        } catch (Exception e)
        {
            resultCode.setResult(JsonResponse.Result.FALSE);
            resultCode.setMsg(e.getMessage());
        }
        return resultCode.toJson();
    }
    @ResponseBody
    @RequestMapping("/zoneManage/delete")
    public String deleteZone(@RequestParam(required = true) int id)
    {
        JsonResponse resultCode = new JsonResponse();
        try
        {
            ZoneEntity entity = DaoManager.getLocalDao().select(ZoneEntity.class, id);
            if (null == entity)
            {
                resultCode.setResult(JsonResponse.Result.FALSE);
                resultCode.setMsg("不存在");
            }
            else
            {
                DaoManager.getLocalDao().delete(entity);
                ZoneEntityManager.getInstance().deleteZoneEntity(entity);
            }
        } catch (Exception e)
        {
            resultCode.setResult(JsonResponse.Result.FALSE);
            resultCode.setMsg(e.getMessage());
        }
        return resultCode.toJson();
    }
    @ResponseBody
    @RequestMapping("/zoneManage/update")
    public String updateZone(@RequestParam(required = true) int id, @RequestParam(required = true) int zoneId, @RequestParam(required = true) String zoneName, @RequestParam(required = true) int serverGroupId, @RequestParam(required = true) String gameServerHost, @RequestParam(required = true) String chatServerHost, @RequestParam(required = true) String authServerHost, @RequestParam(required = true) String gatewayServerHostList,@RequestParam(required = true) String dbUrl,@RequestParam(required = true) String dbUsername,@RequestParam(required = true) String dbPasswd )
    {
        JsonResponse resultCode = new JsonResponse();
        try
        {
            ZoneEntity entity = new ZoneEntity();
            entity.setId(id);
            entity.setZoneId(zoneId);
            entity.setZoneName(zoneName);
            entity.setServerGroupId(serverGroupId);
            entity.setGameServerHost(gameServerHost);
            entity.setAuthServerHost(authServerHost);
            entity.setChatServerHost(chatServerHost);
            entity.setGatewayServerHostList(gatewayServerHostList);
            entity.setDbUrl(dbUrl);
            entity.setDbUsername(dbUsername);
            entity.setDbPasswd(dbPasswd);
            DaoManager.getLocalDao().saveOrUpdate(entity);
            ZoneEntityManager.getInstance().addOrUpdateZoneEntity(entity);
        } catch (Exception e)
        {
            resultCode.setResult(JsonResponse.Result.FALSE);
            resultCode.setMsg(e.getMessage());
        }
        return resultCode.toJson();
    }
}
