package ipower.wechat.message.req;

import java.util.Map;
/**
 * 微信请求消息帮助类。
 * @author yangyong.
 * @since 2014-02-26.
 * */
public final class ReqMessageHelper {	
	/**
	 * 将请求消息解析成对象。
	 * @param req
	 *  消息基类。
	 *  @param data
	 *  请求消息。
	 * */
	private static void parseReqMessage(BaseReqMessage req, Map<String, String> data){
		if(req != null && data != null && data.size() > 0){
			req.setMsgId(Long.parseLong(data.get("MsgId")));
			req.setMsgType(data.get("MsgType"));
			req.setCreateTime(Integer.parseInt(data.get("CreateTime")));
			req.setFromUserName(data.get("FromUserName"));
			req.setToUserName(data.get("ToUserName"));
		}
	}
	/**
	 * 解析文本消息。
	 * @param data
	 * 	消息数据。
	 * @return 
	 * 	文本消息对象。
	 * */
	public static TextReqMessage parseTextReqMessage(Map<String, String> data){
		//创建文本消息对象。
		TextReqMessage textReqMessage = new TextReqMessage();
		//
		parseReqMessage(textReqMessage,data);
		//
		textReqMessage.setContent(data.get("Content"));
		//返回消息对象。
		return textReqMessage;
	}
	/**
	 * 解析图片消息。
	 * @param data
	 * 	消息数据。
	 * @return
	 * 	图片消息对象。
	 * */
	public static ImageReqMessage parseImageReqMessage(Map<String, String> data){
		//创建图片消息对象。
		ImageReqMessage imageReqMessage = new ImageReqMessage();
		//
		parseReqMessage(imageReqMessage,data);
		//
		imageReqMessage.setPicUrl(data.get("PicUrl"));
		imageReqMessage.setMediaId(data.get("MediaId"));
		//
		return imageReqMessage;
	}
	/**
	 * 解析语音消息。
	 * @param data
	 * 	消息数据。
	 * @return 语音消息对象。
	 * */
	public static VoiceReqMessage parseVoiceReqMessage(Map<String, String> data){
		VoiceReqMessage voiceReqMessage = new VoiceReqMessage();
		//
		parseReqMessage(voiceReqMessage,data);
		//
		voiceReqMessage.setMediaId(data.get("MediaId"));
		voiceReqMessage.setFormat(data.get("Format"));
		
		return voiceReqMessage;
	}
	/**
	 * 解析视频消息。
	 * @param 
	 * 	消息数据。
	 * @return
	 * 	视频消息对象。
	 * */
	public static VideoReqMessage parseVideoReqMessage(Map<String, String> data){
		VideoReqMessage videoReqMessage = new VideoReqMessage();
		//
		parseReqMessage(videoReqMessage,data);
		//
		videoReqMessage.setMediaId(data.get("MediaId"));
		videoReqMessage.setThumbMediaId(data.get("ThumbMediaId"));
		//
		return videoReqMessage;
	}
	/**
	 * 解析地理位置消息。
	 * @param data
	 * 	消息数据。
	 * @return
	 * 	地理位置消息对象。
	 * */
	public static LocationReqMessage parseLocationReqMessage(Map<String, String> data){
		LocationReqMessage locationReqMessage = new LocationReqMessage();
		//
		parseReqMessage(locationReqMessage, data);
		//
		locationReqMessage.setLocation_X(data.get("Location_X"));
		locationReqMessage.setLocation_Y(data.get("Location_Y"));
		locationReqMessage.setScale(Integer.parseInt(data.get("Scale")));
		locationReqMessage.setLabel(data.get("Label"));
		//
		return locationReqMessage;
	}
	/**
	 * 解析链接消息。
	 * @param data
	 * 	消息数据。
	 * @return
	 * 	链接消息对象。
	 * */
	public static LinkReqMessage parseLinkReqMessage(Map<String, String> data){
		LinkReqMessage linkReqMessage = new LinkReqMessage();
		//
		parseReqMessage(linkReqMessage, data);
		//
		linkReqMessage.setTitle(data.get("Title"));
		linkReqMessage.setDescription(data.get("Description"));
		linkReqMessage.setUrl(data.get("Url"));
		
		return linkReqMessage;
	}
}