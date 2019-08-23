package com.lz.center.control;
import com.lz.center.AttributeManager;
import com.lz.center.entity.ZoneEntity;
import com.lz.center.rpc.RpcClientManager;
import com.lz.center.utils.JsonHelper;
import com.lz.center.acl.ACL;
import com.lz.center.acl.Limit;
import com.lz.center.acl.RequestType;
import com.lz.center.entity.CDKeyEntity;
import com.lz.game.rpc.GameServerRpc;
import com.lz.game.rpc.RpcDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * User: Teaey
 * Date: 13-8-27
 */
@Controller
@RequestMapping("cdkey")
public class CdKeyController
{
    private static final int    CDKLENTH    = 16;
    private static final int    SPLITKEYLEN = 4;
    private static final Logger log         = LoggerFactory.getLogger(CdKeyController.class);
    @ACL(limit = Limit.CDKEY)
    @RequestMapping("/")
    public void cdkey()
    {
    }
    private boolean contain(String key, Integer zoneId)
    {
        //try{
        GameServerRpc.ContainCdKeyResp rpcResp = RpcClientManager.getInstance().getGameServerRpcClientByZoneId(zoneId).containCdKey(GameServerRpc.ContainCdKeyReq.newBuilder().setCdKey(key).build());
        return rpcResp.getIsContain();
        //}catch (Exception e){
        //    log.error("",e);
        //}
        //return false;
    }
    @ACL(limit = Limit.CDKEY, type = RequestType.JSON)
    @RequestMapping("getall")
    @ResponseBody
    public String getall(HttpSession session) throws IOException
    {
        ZoneEntity zoneEntity = AttributeManager.getAttribute(AttributeManager.SESSION_ATTR_CTX, session).getTargetZone();
        if (null == zoneEntity)
        {
            return JsonHelper.toJson(new ArrayList<>());
        }
        else
        {
            return JsonHelper.toJson(listCdKey(zoneEntity.getZoneId()));
        }
    }
    final List<CDKeyEntity> listCdKey(Integer zoneId)
    {
        List<CDKeyEntity> list = new ArrayList<>();
        List<RpcDto.CdKeyDTO> cdKeyDTOs = RpcClientManager.getInstance().getGameServerRpcClientByZoneId(zoneId).getAllCdKey(GameServerRpc.GetAllCdKeyReq.newBuilder().build()).getCdKeyDTOList();
        for (RpcDto.CdKeyDTO each : cdKeyDTOs)
        {
            list.add(new CDKeyEntity(each));
        }
        return list;
    }
    @ACL(limit = Limit.CDKEY, type = RequestType.JSON)
    @RequestMapping("generate")
    @ResponseBody
    public String generate(String cdName, @RequestParam(required = true) Integer cdCount, @RequestParam(required = true) Integer legealCount, @RequestParam(required = true) Integer giftId, @RequestParam(required = true) String dateLimit, HttpSession session) throws IOException, ParseException
    {
        UUID uuid = UUID.randomUUID();
        Integer zoneId = AttributeManager.getAttribute(AttributeManager.SESSION_ATTR_CTX, session).getTargetZone().getZoneId();
        //        int cdCount = Integer.valueOf(cdCountStr);
        //        int legealCount = Integer.valueOf(legealCountStr);
        //        int giftId = Integer.valueOf(giftIdStr);
        //        Date dateLimit = StringToDate(dateLimitStr);
        //        int zoneId = Integer.valueOf(zoneIdStr);
        GameServerRpc.AddCdKeyReq.Builder rpcReq = GameServerRpc.AddCdKeyReq.newBuilder();
        List<CDKeyEntity> cdkeys = new ArrayList<>();
        while (cdCount > 0)
        {
            String key = getRandomString();
            while (contain(key, zoneId))
            {
                key = getRandomString();
            }
            //Criterion crt = Restrictions.eq("cdKey", key);
            //List<CDKeyEntity> cdkeyTemp = Dao.getInstance().selectList(CDKeyEntity.class, crt);
            //if (null == cdkeyTemp || cdkeyTemp.size() == 0)
            //{
            CDKeyEntity cdKeyEntity = new CDKeyEntity();
            cdKeyEntity.setCdName(cdName);
            cdKeyEntity.setCdKey(key);
            cdKeyEntity.setGiftId(giftId);
            cdKeyEntity.setDateLimit(StringToDate(dateLimit));
            cdKeyEntity.setLegealCount(legealCount);
            cdKeyEntity.setLeftCount(legealCount);
            cdKeyEntity.setZoneId(zoneId);
            cdKeyEntity.setUuid(uuid.toString());
            cdkeys.add(cdKeyEntity);
            rpcReq.addCdKeyDTO(cdKeyEntity.toDTO());
            cdCount--;
            //}
            //else
            //{
            //    continue;
            //}
        }
        if (cdkeys.size() > 0)
        {
            RpcClientManager.getInstance().getGameServerRpcClientByZoneId(zoneId).addCdKey(rpcReq.build());
            //Dao.getInstance().saveOrUpdateAll(cdkeys);
            //List<RpcDto.CdKeyDTO> cdKeyDTOs = RpcClientManager.getGameServerRpcClient().getAllCdKey(GameServerRpc.GetAllCdKeyReq.newBuilder().build()).getCdKeyDTOList();
        }
        return JsonHelper.toJson(listCdKey(zoneId));
    }
    private static final Random ran = new Random();
    /**
     * 生成CDK算法
     * @return
     */
    final String getRandomString()
    { //length表示生成字符串的长度               abcdefghjklmnpqrstuvwxyz
        String base = "ABCDEFGHJKLMNPQRSTUVWXYZ123456789";
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= CDKLENTH; i++)
        {
            int number = ran.nextInt(base.length());
            sb.append(base.charAt(number));
            if ((i) % SPLITKEYLEN == 0 && i != CDKLENTH)
            {
                sb.append("-");
            }
        }
        return sb.toString();
    }
    final Date StringToDate(String source) throws ParseException
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(source);
    }
}
