package com.lz.center.control;
import com.lz.center.AttributeManager;
import com.lz.center.JsonResponse;
import com.lz.center.acl.ACL;
import com.lz.center.acl.Limit;
import com.lz.center.acl.RequestType;
import com.lz.center.entity.ZoneEntity;
import com.lz.center.rpc.RpcClientManager;
import com.lz.center.utils.JsonHelper;
import com.lz.game.rpc.GameServerRpc;
import com.lz.game.rpc.GameServerRpcClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
/**
 * User: Teaey
 * Date: 13-9-13
 */
@Controller
@RequestMapping(value = "/player")
public class PlayerController
{
    @ACL(limit = Limit.PLAYER_SEARCH)
    @RequestMapping("/")
    public String playerSearch()
    {
        return "playerSearch";
    }
    @ACL(limit = Limit.PLAYER_SEARCH, type = RequestType.JSON)
    @RequestMapping("/doSearch")
    @ResponseBody
    public String doSearch(String condition, HttpSession session)
    {
        JsonResponse response = new JsonResponse();
        ZoneEntity zoneEntity = AttributeManager.getContext(session).getTargetZone();
        try
        {
            if (null != zoneEntity)
            {
                GameServerRpcClient client = RpcClientManager.getInstance().getGameServerRpcClientByZoneId(zoneEntity.getZoneId());
                GameServerRpc.SearchPlayerReq.Builder req = GameServerRpc.SearchPlayerReq.newBuilder();
                req.setZoneId(zoneEntity.getZoneId());
                req.setCondition(condition);
                GameServerRpc.SearchPlayerResp resp = client.searchPlayer(req.build());
                response.setMsg(JsonHelper.toJson(resp.getPlayerJsonDetailList()));
            }
            else
            {
                response.setResult(JsonResponse.Result.FALSE);
                response.setMsg("请选择操作大区");
            }
        } catch (Exception e)
        {
            response.setResult(JsonResponse.Result.FALSE);
            response.setMsg("操作失败");
        }
        return response.toJson();
    }
}
