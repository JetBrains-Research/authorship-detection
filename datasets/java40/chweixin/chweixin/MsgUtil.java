package ipower.wechat.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 消息工具类。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class MsgUtil {
	/**
	 * 日志记录。
	 */
	protected static Logger logger = Logger.getLogger(MsgUtil.class);
	/**
	 * 将输入的XML流解析成Map集合。
	 * @param inXml
	 * 输入的XML流数据。
	 * @return Map集合对象。
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	protected final static Map<String, String> parseXml(InputStream inXml) throws DocumentException{
		if(inXml == null) return null;
		//读取输入流。
		SAXReader reader = new SAXReader();
		Document document = reader.read(inXml);
		//得到xml根元素。
		Element root = document.getRootElement();
		 
		logger.info("接收的xml：\r\n" + root.asXML());
		
		//得到根元素的所有子节点。
		List<Element> elements = root.elements();
		if(elements != null && elements.size() > 0){
			//将解析结果存储在HashMap中。
			Map<String, String> map = new HashMap<String, String>();
			logger.info("请求参数：");
			//遍历所有子节点。
			for(Element e : elements){
				map.put(e.getName(), e.getText());
				logger.info(e.getName() + " = " + e.getText());
			}
			return map;
		}
		return null;
	}
	/**
	 * 解析微信发来的请求(xml)
	 * @param request
	 * 	请求对象。
	 * @return 解析结果。
	 * @throws Exception
	 * */
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception{
		InputStream inputStream = null;
		try{
			//从request中取得输入流。
			inputStream = request.getInputStream();
			return parseXml(inputStream);
		}catch(IOException | DocumentException e){
			logger.error("解析微信服务器请求发生异常：", e);
			throw e;			
		}finally{
			//释放资源。
			if(inputStream != null){
				inputStream.close();
				inputStream = null;
			}
		}
	}
	/**
	 * 扩展xstream,使其支持CDATA块。
	 **/
	protected static XStream xstream = new XStream(new XppDriver(){
		public HierarchicalStreamWriter createWriter(Writer out){
			return new PrettyPrintWriter(out){
				//对所有的xml节点的转换都增加CDATA标签。
				boolean cdata = true;
				@SuppressWarnings("rawtypes")
				public void startNode(String name,Class clazz){
					super.startNode(name, clazz);
				}
				protected void writeText(QuickWriter writer, String text){
					Pattern pattern = Pattern.compile("[0-9]*");
					Matcher isNum = pattern.matcher(text);
					if(isNum.matches()){
						writer.write(text);
						return;
					}
					if(cdata){
						writer.write("<![CDATA["); 
                        writer.write(text); 
                        writer.write("]]>");
					}else {
						writer.write(text);
					}
				}
			};
		}
	});
}