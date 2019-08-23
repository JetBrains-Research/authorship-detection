package com.lz.center.control;
import com.lz.center.AttributeManager;
import com.lz.center.JsonResponse;
import com.lz.center.acl.ACL;
import com.lz.center.acl.Limit;
import com.lz.center.acl.RequestType;
import com.lz.center.dao.DaoManager;
import com.lz.center.entity.BulletinEntity;
import com.lz.center.entity.ZoneEntity;
import com.lz.center.rpc.RpcClientManager;
import com.lz.center.rpc.ZoneEntityManager;
import com.lz.center.service.BulletinService;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
/**
 * User: Teaey
 * Date: 13-8-27
 */
@Controller
@RequestMapping("/bulletin")
public class BulletinController
{
    @ACL(limit = Limit.BULLETIN)
    @RequestMapping(value = "/")
    public void bulletin(Map<String, Object> map, HttpSession session)
    {
        ZoneEntity zoneEntity = AttributeManager.getAttribute(AttributeManager.SESSION_ATTR_CTX, session).getTargetZone();
        if (null == zoneEntity)
        {
            map.put("data", new ArrayList<>());
            map.put("zones", new ArrayList<>());
        }
        else
        {
            Criterion criterion = Restrictions.eq("zoneId", zoneEntity.getZoneId());
            List<BulletinEntity> list = DaoManager.getLocalDao().selectList(BulletinEntity.class, criterion);
            map.put("data", list);
            Collection<ZoneEntity> zoneEntityList = ZoneEntityManager.getInstance().getAllZoneEntiry();
            map.put("zones", zoneEntityList);
        }
    }
    @ACL(limit = Limit.BULLETIN, type = RequestType.JSON)
    @RequestMapping(value = "/delete")
    @ResponseBody
    protected String delete(@RequestParam(required = true) String id) throws ServletException, IOException
    {
        int bulletinId = Integer.valueOf(id);
        BulletinEntity bulletin = DaoManager.getLocalDao().select(BulletinEntity.class, bulletinId);
        JsonResponse resultCode = new JsonResponse();
        if (null != bulletin)
        {
            if (!BulletinService.delete(bulletin))
            {
                resultCode.setMsg("删除失败");
                resultCode.setResult(JsonResponse.Result.FALSE);
            }
        }
        return resultCode.toJson();
    }
    @ACL(limit = Limit.BULLETIN, type = RequestType.JSON)
    @RequestMapping(value = "/modify")
    @ResponseBody
    public String modify(String id, int zoneId, String linkId, String title, String content, String sortFlag) throws IOException
    {
        JsonResponse resultCode = new JsonResponse();
        BulletinEntity bulletin = DaoManager.getLocalDao().select(BulletinEntity.class, Integer.parseInt(id));
        if (null == bulletin)
        {
            resultCode.setMsg("failed");
        }
        else
        {
            bulletin.setSortFlag(Integer.valueOf(sortFlag));
            bulletin.setLinkId(Integer.valueOf(linkId));
            bulletin.setTitle(title);
            bulletin.setContent(content);
            bulletin.setModifyTime(new Date());
            if (BulletinService.modify(bulletin))
                resultCode.setMsg("success");
            else
                resultCode.setMsg("failed");
        }
        return resultCode.toJson();
    }
    @ACL(limit = Limit.BULLETIN, type = RequestType.JSON)
    @RequestMapping(value = "/add")
    @ResponseBody
    public String add(String linkId, int zoneId, String title, String content, String sortFlag) throws IOException
    {
        JsonResponse resultCode = new JsonResponse();
        BulletinEntity bulletin = new BulletinEntity();
        bulletin.setLinkId(Integer.valueOf(linkId));
        bulletin.setSortFlag(Integer.valueOf(sortFlag));
        bulletin.setTitle(title);
        bulletin.setContent(content);
        bulletin.setModifyTime(new Date());
        if (BulletinService.add(bulletin))
        {
            resultCode.setMsg("success");
        }
        else
        {
            resultCode.setMsg("failed");
        }
        return resultCode.toJson();
    }
}
