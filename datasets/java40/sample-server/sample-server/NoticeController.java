package com.lz.center.control;
import com.lz.center.dao.DaoManager;
import com.lz.center.rpc.RpcClientManager;
import com.lz.center.rpc.ZoneEntityManager;
import com.lz.center.utils.JsonHelper;
import com.lz.center.JsonResponse;
import com.lz.center.acl.ACL;
import com.lz.center.acl.Limit;
import com.lz.center.acl.RequestType;
import com.lz.center.entity.NoticeEntity;
import com.lz.center.entity.ZoneEntity;
import com.lz.center.service.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * User: Teaey
 * Date: 13-8-27
 */
@Controller
@RequestMapping(value = "/notice")
public class NoticeController
{
    private static final Logger log = LoggerFactory.getLogger(NoticeController.class);
    @ACL(limit = Limit.NOTICE)
    @RequestMapping(value = "/")
    public void notice(Map<String, Object> map)
    {
        List<NoticeEntity> list = DaoManager.getLocalDao().selectList(NoticeEntity.class);
        map.put("data", list);
        Collection<ZoneEntity> zoneEntityList = ZoneEntityManager.getInstance().getAllZoneEntiry();
        map.put("zones", zoneEntityList);
    }
    @ACL(limit = Limit.NOTICE, type = RequestType.JSON)
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(String noticeId) throws IOException
    {
        JsonResponse result = new JsonResponse();
        if (null == noticeId)
        {
            result.setMsg("failed");
        }
        else
        {
            int id = Integer.valueOf(noticeId);
            NoticeEntity notice = DaoManager.getLocalDao().select(NoticeEntity.class, id);
            if (null == notice)
            {
                result.setMsg("failed");
            }
            if (NoticeService.delete(notice))
            {
                result.setMsg("success");
            }
            else
            {
                result.setMsg("failed");
            }
        }
        return result.toJson();
    }
    @ACL(limit = Limit.NOTICE, type = RequestType.JSON)
    @RequestMapping(value = "/get")
    @ResponseBody
    public String get(String pageNo, String pageCount) throws IOException
    {
        List<NoticeEntity> list = DaoManager.getLocalDao().selectList(NoticeEntity.class);
        return JsonHelper.toJson(list);
    }
    /**
     * 修改Notice
     */
    @ACL(limit = Limit.NOTICE, type = RequestType.JSON)
    @RequestMapping(value = "/modify")
    @ResponseBody
    public String modify(String noticeId, String content, String start, String stop, int zoneId, String interval, String noticeLevel, String roundTime, String noticeType) throws ServletException, IOException
    {
        start = start.replace('T', ' ');
        stop = stop.replace('T', ' ');
        NoticeEntity newNotice = DaoManager.getLocalDao().select(NoticeEntity.class, Integer.parseInt(noticeId));
        JsonResponse result = new JsonResponse();
        newNotice.setContent(content);
        try
        {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            newNotice.setStartTime(df.parse(start));
            newNotice.setStopTime(df.parse(stop));
            newNotice.setZoneId(zoneId);
            newNotice.setEveryInterval(Integer.valueOf(interval));
            newNotice.setRound(Integer.valueOf(roundTime));
            newNotice.setLevel(Integer.valueOf(noticeLevel));
            newNotice.setType(Integer.valueOf(noticeType));
            newNotice.setModifyTime(new Date());
            if (NoticeService.update(newNotice))
            {
                result.setMsg("success");
            }
            else
            {
                result.setMsg("failed");
            }
            // syn 2 chat server
        } catch (ParseException e)
        {
            result.setMsg("failed");
            log.error("", e);
            // resultCode.setResult(JsonResponse.Result.FAILED);
        }
        return result.toJson();
    }
    @ACL(limit = Limit.NOTICE, type = RequestType.JSON)
    @RequestMapping(value = "/add")
    @ResponseBody
    public String add(String content, String start, String stop, int zoneId, String interval, String noticeLevel, String roundTime, String noticeType) throws IOException
    {
        start = start.replace('T', ' ');
        stop = stop.replace('T', ' ');
        NoticeEntity newNotice = new NoticeEntity();
        JsonResponse result = new JsonResponse();
        newNotice.setContent(content);
        try
        {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            newNotice.setStartTime(df.parse(start));
            newNotice.setStopTime(df.parse(stop));
            newNotice.setZoneId(zoneId);
            newNotice.setEveryInterval(Integer.valueOf(interval));
            newNotice.setRound(Integer.valueOf(roundTime));
            newNotice.setLevel(Integer.valueOf(noticeLevel));
            newNotice.setType(Integer.valueOf(noticeType));
            newNotice.setAddTime(new Date());
            newNotice.setRemark("");
            if (NoticeService.add(newNotice))
            {
                result.setMsg("success");
            }
            else
            {
                result.setMsg("failed");
            }
            // syn 2 chat server
        } catch (ParseException e)
        {
            result.setMsg("failed");
            log.error("", e);
        }
        return result.toJson();
    }
}
