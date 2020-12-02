
package com.dc.esb.base.impls.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.dc.esb.container.adaptor.helper.AdaptorHelper;
import com.dc.esb.container.core.data.IServiceDataObject;
import com.dc.esb.container.core.protocol.IConnector;
import com.dc.esb.container.core.sclite.IBaseContext;
import com.dc.esb.container.core.sclite.IBaseService;
import com.dc.esb.container.core.sclite.ITransferableContext;
import com.dc.esb.container.core.sclite.InvokeException;
import com.dc.esb.container.core.sclite.ServiceMaintainException;
import com.dc.esb.container.core.sclite.trans.PropertyType;
import com.dc.esb.container.log.MyLog;
import com.dc.esb.container.log.MyLogHelper;
import com.dc.esb.container.protocol.http.HTTPTransfer;
import com.dc.esb.container.protocol.http.server.BufferServletRequest;
import com.dc.esb.container.protocol.http.server.HTTPServerConnector;
import com.dc.esb.container.protocol.ws.server.WSServerConnector;
import com.dc.esb.container.sclite.ContextConstants;

/**
 * <p>
 * 
 * <li></li>
 * </p>
 * 功能：服务识别，系统识别，传递http请求头，传递httpMethod（根据请求方的请求方式）
 * http://127.0.0.1:16604/ESB/API/reqSysid/sysId/serviceName
 * 
 * @author zhaiyda
 */
public class UrlIdentifyService implements IBaseService {
	private static final MyLog log = MyLogHelper.getFactory().getLog(UrlIdentifyService.class);

	public IServiceDataObject invoke(IServiceDataObject data, IBaseContext context) throws InvokeException {
		String servletPath = null;
		String pathInfo = null;
		String headers = null;
		String channelid = null;
		String sysid = null;
		String serviceid = null;
		String matchurl = null;
		Object conn = (IConnector) context.getLocalizedValue("connector");
		if ((conn instanceof WSServerConnector) || (conn instanceof HTTPServerConnector)) {
			Object obj = context.getLocalizedValue("httpRequest");
			if (obj instanceof BufferServletRequest) {
				BufferServletRequest httpRequest = (BufferServletRequest) obj;
				HttpServletRequest request = ((BufferServletRequest) context
						.getLocalizedValue(ContextConstants.HTTPREQ)).getRequest();
				Map header = HTTPTransfer.copyRequestHeader(request);
				// 此处移除不必要的请求头:host,Accept-Encoding
				String host = (String) header.get("host");
				String encoding = (String) header.get("Accept-Encoding");
				if (null != host || !"".equals(host)) {
					header.remove("host");
				}
				if (null != encoding || !"".equals(encoding)) {
					header.remove("Accept-Encoding");
				}

				headers = HTTPTransfer.mapToString(header);
				String uri = httpRequest.getRequest().getRequestURI();
				pathInfo = httpRequest.getRequest().getPathInfo();
				if (StringUtils.length(uri) > 0) {
					servletPath = uri;
				} else {
					servletPath = pathInfo;
				}
				if (log.isDebugEnabled()) {
					log.debug("the request servletPath is:" + servletPath);
				}
				// 接出协议通配
				matchurl = servletPath.substring(1, servletPath.length());
				String clientAddress = request.getRemoteAddr();
				String method = request.getMethod();
				if (log.isDebugEnabled()) {
					log.debug("来源IP为:" + clientAddress + " httpMethod: " + method + "=======请求头为=======" + headers);
				}
				// 默认请求系统以什么方式（get,post）访问ESB,ESB继续使用该方式访问后端服务
				((ITransferableContext) context).setDelocalizedValue("HTTP_METHOD", method, PropertyType.CONSTANT);
			}
			if (null != servletPath) {
				StringTokenizer st = new StringTokenizer(servletPath, "/", false);
				List<String> list = new ArrayList<String>();
				int count = st.countTokens();
				String message = null;
				if (count < 5) {
					message = "请求方系统调用ESB地址[" + servletPath
							+ "]有误，参考格式为:http://ESBIP:PORT/ESB/API/reqSysid/sysId/serviceName";
					log.error(message);
					AdaptorHelper.setError(context, "ESB-E-000060", message);
					return data;
				}
				while (st.hasMoreElements()) {
					String s = st.nextToken();
					list.add(s);
				}
				try {
					for (int i = 0; i < list.size(); i++) {
						channelid = (String) list.get(2);
						sysid = (String) list.get(3);
						serviceid = (String) list.get(4);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					/*
					 * log.error(message);
					 * AdaptorHelper.setError(context,"ESB-E-000060" ,message);
					 */
					e.printStackTrace();
					return data;
				}

			}
			if (log.isDebugEnabled()) {
				log.debug("协议识别成功 " + "服务名为:[" + serviceid + "]调用方系统为:[" + channelid + "]提供方系统为[" + sysid + "]");
			}
			// 接出协议通配,可视化系统需要单独处理，此处通配符根据实际情况可以修改
			if (sysid.equals("IVS") && !serviceid.equals("weatherDetail")) {
				matchurl = serviceid;
			} else if (serviceid.equals("weatherDetail") && sysid.equals("IVS")) {
				int serviceIndex = servletPath.indexOf(serviceid);
				matchurl = servletPath.substring(serviceIndex, servletPath.length());
			} else {
				matchurl = servletPath.substring(1, servletPath.length());
			}
			((ITransferableContext) context).setDelocalizedValue("HTTP_HEADERS", headers, PropertyType.RGLOBALVAR);
			context.removeDelocalizedValue(ContextConstants.REAL_CHANNEL);
			context.removeDelocalizedValue(ContextConstants.LOGIC_CHANNEL);

			((ITransferableContext) context).setDelocalizedValue(ContextConstants.REAL_CHANNEL, channelid,
					PropertyType.RGLOBALVAR);
			((ITransferableContext) context).setDelocalizedValue(ContextConstants.LOGIC_CHANNEL, channelid,
					PropertyType.RGLOBALVAR);
			// 渠道识别
			if (!isSimpleMode(context)) {
				context.removeDelocalizedValue(ContextConstants.IMMUNE_CHANNELID);
				((ITransferableContext) context).setDelocalizedValue(ContextConstants.IMMUNE_CHANNELID, channelid,
						PropertyType.RGLOBALVAR);
			}

			// 接出协议通配
			((ITransferableContext) context).setDelocalizedValue("httpMatchUrl", matchurl, PropertyType.RGLOBALVAR);
			if (isSimpleMode(context)) {
				((ITransferableContext) context).setDelocalizedValue(ContextConstants.IMMUNE_SERVICEID, "BusiESB",
						PropertyType.CONSTANT);
				// 简单开发模式下，系统名和服务名内置
				((ITransferableContext) context).setDelocalizedValue(ContextConstants.LOGIC_SYSTEM, "ESBSYS",
						PropertyType.RGLOBALVAR);
				((ITransferableContext) context).setDelocalizedValue("matchServiceId", serviceid,
						PropertyType.RGLOBALVAR);
				((ITransferableContext) context).setDelocalizedValue(ContextConstants.REAL_SYSTEM, sysid,
						PropertyType.RGLOBALVAR);
			} else {
				((ITransferableContext) context).setDelocalizedValue(ContextConstants.IMMUNE_SERVICEID, serviceid,
						PropertyType.CONSTANT);
				((ITransferableContext) context).setDelocalizedValue(ContextConstants.LOGIC_SYSTEM, sysid,
						PropertyType.RGLOBALVAR);
				((ITransferableContext) context).setDelocalizedValue(ContextConstants.REAL_SYSTEM, sysid,
						PropertyType.RGLOBALVAR);

			}
			AdaptorHelper.setServiceid(context, serviceid);
			if (log.isDebugEnabled()) {
				log.debug("=======当前上下文==========" + context.toString());
			}

		}
		return data;

	}

	/**
	 * 判断是否是简单开发模式
	 * 
	 * @param context
	 * @return simplemode
	 */
	public boolean isSimpleMode(IBaseContext context) {
		boolean simplemode = false;
		String mode = context.getDelocalizedValue("Simplemode");
		if (null != mode && mode.equals("true")) {
			simplemode = true;
		} else {
			simplemode = false;
		}
		return simplemode;

	}

	private boolean started = false;

	public boolean isStarted() {
		return started;
	}

	public void start() throws ServiceMaintainException {
		started = true;
	}

	public void stop() throws ServiceMaintainException {
		started = false;
	}

}
