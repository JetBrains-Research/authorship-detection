package ipower.wechat.message.resp;

import java.io.ByteArrayInputStream;
import java.util.Map;

import ipower.wechat.message.Article;
import ipower.wechat.message.WeChatContext;
import ipower.wechat.utils.MsgUtil;

/**
 * 响应回复消息处理帮助类。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public final class RespMesssageHelper extends MsgUtil {
	/**
	 * 响应回复消息对象转换成xml。
	 * @param message
	 *  响应回复消息对象。
	 * @return xml。
	 * */
	public static <T extends BaseRespMessage> String respMessageToXml(T message){
		xstream.alias("xml", message.getClass());
		if(message.getClass() == ArticleRespMessage.class){
			xstream.alias("item", new Article().getClass());
		}
		return xstream.toXML(message);
	}
	/**
	 * 将XML转换成回复对象。
	 * @param xml
	 * @return
	 */
	public static BaseRespMessage respXmlToObject(String xml){
		if(xml == null || xml.trim().isEmpty()) return null;
		try {
			Map<String, String> map = parseXml(new ByteArrayInputStream(xml.getBytes("utf-8")));
			String type = map.get("MsgType");
			switch(type){
				case WeChatContext.REQ_MESSAGE_TYPE_TEXT://文本。
					return respXmlToObject(TextRespMessage.class, xml);
				case WeChatContext.REQ_MESSAGE_TYPE_IMAGE://图片。
					return respXmlToObject(ImageRespMessage.class, xml);
				case WeChatContext.REQ_MESSAGE_TYPE_VOICE://语音。
					return respXmlToObject(VoiceRespMessage.class, xml);
				case WeChatContext.REQ_MESSAGE_TYPE_VIDEO://视频
					return respXmlToObject(VoiceRespMessage.class, xml);
				case "music"://音乐。
					return respXmlToObject(MusicRespMessage.class, xml);
				case "news"://图文。
					return respXmlToObject(ArticleRespMessage.class, xml);
				default: 
					return null;
			}
		} catch (Exception e) {
			logger.error("将XML转换成对象时异常：", e);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将XML消息转换成响应对象。
	 * @param clazz
	 * 目标对象类型。
	 * @param xml
	 * XML。
	 * @return 目标对象。
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	protected static <T extends BaseRespMessage> T respXmlToObject(Class<T> clazz, String xml) throws InstantiationException, IllegalAccessException{
		if(clazz == null || xml == null || xml.trim().isEmpty()) return null;
		xstream.alias("xml", clazz);
		if(clazz == ArticleRespMessage.class){
			xstream.alias("item", new Article().getClass());
		}
		T t = clazz.newInstance();
		Object obj = xstream.fromXML(xml, t);
		if(obj == null) return null;
		return (T)obj;
	}
}