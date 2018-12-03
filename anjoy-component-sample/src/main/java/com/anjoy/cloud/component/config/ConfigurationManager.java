package com.anjoy.cloud.component.config;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/***
 * 配置管理
 * @author wangfucai
 */
public class ConfigurationManager {

	//本地
	private static final String CONFIG_Local = "config-local.properties";
	
	//远程
	private static final String CONFIG_REMOTE = "/webser/java/config/config.properties";

	private static ConfigurationManager self = new ConfigurationManager();

	private Properties props = new Properties();

	private ConfigurationManager() {
		InputStream in=null;
		try 
		{
			in = this.getClass().getClassLoader().getResourceAsStream(CONFIG_Local);
			props.load(in);
		} catch (Exception e) 
		{
			try
			{
				in = new BufferedInputStream(new FileInputStream(CONFIG_REMOTE));
				props.load(in);
			} catch (Exception e2)
			{
				e2.printStackTrace();
				throw new RuntimeException(e);
			}
		}finally
		{
			try
			{
				if(in!=null)
				{
				  in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getValue(String key) {
		return props.getProperty(key);
	}

	public static ConfigurationManager instance() {
		return self;
	}
}
