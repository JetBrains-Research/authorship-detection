package ipower.wechat.service.impl;

import ipower.configuration.ModuleDefine;
import ipower.configuration.ModuleDefineCollection;
import ipower.configuration.ModuleSystem;
import ipower.configuration.ModuleSystemCollection;
import ipower.utils.MD5Util;
import ipower.wechat.service.IMenuService;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;
import org.xml.sax.SAXException;

/**
 * 菜单服务实现。
 * @author 杨勇。
 * @since 2014-01-18。
 * */
public class MenuServiceImpl implements IMenuService {
	private static Logger logger = Logger.getLogger(MenuServiceImpl.class);
	private static Map<String, ModuleSystemCollection> mapSystemCollectionCache = Collections.synchronizedMap(new HashMap<String, ModuleSystemCollection>());
	private static Map<String, ModuleSystem> mapSystemCache = Collections.synchronizedMap(new HashMap<String, ModuleSystem>());
	private String menuFilePath;
	
	public void setMenuFile(String menuFile) {
		if(menuFile == null || menuFile.trim().isEmpty()) return;
		this.menuFilePath = menuFile;
	}
	/**
	 * 将菜单文件解析为对象。
	 * @param menuFilePath
	 * 菜单文件。
	 * @return 系统集合。
	 * */
	private synchronized ModuleSystemCollection parse(String menuFilePath) throws SAXException, IOException, ParserConfigurationException{
		if(menuFilePath == null || menuFilePath.trim().isEmpty()) return null;
		String key = MD5Util.MD5(menuFilePath);
		ModuleSystemCollection collection = mapSystemCollectionCache.get(key);
		if(collection == null || collection.size() == 0){
			Resource rs = new ClassPathResource(menuFilePath,ClassUtils.getDefaultClassLoader());
			if(rs != null){
				collection = ModuleSystemCollection.parse(rs.getInputStream());
				if(collection != null) mapSystemCollectionCache.put(key, collection);
			}
		}
		return collection;
	}
	/**
	 * 加载系统对象。
	 * @param systemId
	 * 系统ID。
	 * @return 系统对象。
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * */
	@Override
	public synchronized ModuleSystem loadModuleSystem(String systemId){
		if(systemId == null || systemId.trim().isEmpty()) return null;
		ModuleSystem system = mapSystemCache.get(systemId);
		if(system == null && (this.menuFilePath != null && !this.menuFilePath.trim().isEmpty())){
			try {
				ModuleSystemCollection collection = this.parse(this.menuFilePath);
				if(collection != null){
					system = collection.item(systemId);
					if(system != null) mapSystemCache.put(systemId, system);
				}
			} catch (SAXException | IOException | ParserConfigurationException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
		return system;
	}
	/**
	 * 加载给定系统模块下的子集合。
	 * @param systemId
	 * 	系统ID。
	 * @param moduleId
	 *  模块ID。
	 * @return 模块子集合。
	 * */
	@Override
	public synchronized ModuleDefineCollection children(String systemId, String moduleId){
		if(systemId != null && !systemId.trim().isEmpty() && moduleId != null && !moduleId.trim().isEmpty()){
			ModuleSystem system = this.loadModuleSystem(systemId);
			if(system != null && system.getModules() != null && system.getModules().size() > 0){
				ModuleDefine define = this.findDefine(system.getModules(), moduleId);
				if(define != null) return define.getModules();
			}
		}
		return null;
	}
	
	private synchronized ModuleDefine findDefine(ModuleDefineCollection collection, String moduleId){
		if(collection != null && collection.size() > 0 && moduleId != null && !moduleId.trim().isEmpty()){
			for(int i = 0; i < collection.size(); i++){
				ModuleDefine m = collection.item(i);
				if(m != null && m.getModuleID().equalsIgnoreCase(moduleId)){
					return m;
				}else if(m.getModules() != null && m.getModules().size() > 0){
					ModuleDefine d = this.findDefine(m.getModules(), moduleId);
					if(d != null) return d;
				}
			}
		}
		return null;
	}
}