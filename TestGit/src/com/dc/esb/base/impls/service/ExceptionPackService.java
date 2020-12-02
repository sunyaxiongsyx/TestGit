/*
 * <p>Title: :ExceptionPackService.java </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: digitalchina.Ltd</p>
 * @author 
 * Created :2018-12-3 17:23:44
 * @version 1.0
 * ModifyList:
 * <Author> <Time(yyyy/mm/dd)>  <Description>  <Version>
 */

package com.dc.esb.base.impls.service;


import com.dc.esb.container.adaptor.helper.AdaptorHelper;
import com.dc.esb.container.core.data.IServiceDataObject;
import com.dc.esb.container.core.protocol.IConnector;
import com.dc.esb.container.core.sclite.IBaseContext;
import com.dc.esb.container.core.sclite.IBaseService;
import com.dc.esb.container.core.sclite.InvokeException;
import com.dc.esb.container.core.sclite.ServiceMaintainException;
import com.dc.esb.container.exception.ESBCodes;
import com.dc.esb.container.log.MyLog;
import com.dc.esb.container.log.MyLogHelper;
import com.dc.esb.container.protocol.http.server.HTTPServerConnector;
import com.dc.esb.container.protocol.ws.server.WSServerConnector;
import com.dc.esb.container.sclite.ContextConstants;

/**
 * <p>
 * 
 * <li>
 * </li>
 * </p>
 * 封装异常信息 http默认响应json   ws默认响应soap
 * @author zhaiyd
 * @E-Mail
 */
public class ExceptionPackService implements IBaseService {

	private final MyLog log = MyLogHelper.getFactory().getLog(
			ExceptionPackService.class);

	public IServiceDataObject invoke(IServiceDataObject data,
			IBaseContext context) throws InvokeException {
		try {
			String serviceid = context
					.getDelocalizedValue(ContextConstants.IMMUNE_SERVICEID);
			String sysid = context
					.getDelocalizedValue(ContextConstants.REAL_SYSTEM);
			String statusCode = context
					.getDelocalizedValue(ContextConstants.HTTP_STATUS_CODE);

			String url = context.getDelocalizedValue("HTTP_SERVICE_URL");
			String channelid = context
					.getDelocalizedValue(ContextConstants.IMMUNE_CHANNELID);
			String esbflowno = context
					.getDelocalizedValue(ContextConstants.UNIQUEFLOWNO);
			String esbmessage = null;
			String headers = context.getDelocalizedValue("HTTP_HEADERS");
			String esbcode = context.getDelocalizedValue(ContextConstants.CODE);
			String matchServiceId=context.getDelocalizedValue("matchServiceId");
			if (log.isDebugEnabled()) {
				log.debug("===响应头为=== " + headers);
			}
			if ("200".equals(statusCode) && esbcode == null) {
				return data;
			}

			if (null != statusCode) {
				int httpcode = Integer.parseInt(statusCode);
				switch (httpcode) {
				case 404:
					if(matchServiceId!=null){
						serviceid=matchServiceId;
					}
					esbmessage = "ESB调用" + sysid + "的服务" + serviceid
							+ "异常[HTTP_STATUS_CODE:" + statusCode
							+ "],对应服务地址为:" + url;
					AdaptorHelper.setError(context, ESBCodes.CODE_E000404,
							esbmessage + "，对应ESB流水号为：" + esbflowno);
					break;
				case 500:
					if(matchServiceId!=null){
						serviceid=matchServiceId;
					}
					esbmessage = "ESB调用" + sysid + "的服务" + serviceid
							+ "异常[HTTP_STATUS_CODE:" + statusCode
							+ "],对应服务地址为:" + url;
					AdaptorHelper.setError(context, ESBCodes.CODE_E000500,
							esbmessage + "，对应ESB流水号为：" + esbflowno);
					break;
				case 499:
					if(matchServiceId!=null){
						serviceid=matchServiceId;
					}
					esbmessage = "ESB调用" + sysid + "的服务" + serviceid
							+ "异常[HTTP_STATUS_CODE:" + statusCode
							+ "]读取超时,对应服务地址为:" + url;
					AdaptorHelper.setError(context, ESBCodes.CODE_E000499,
							esbmessage + "，对应ESB流水号为：" + esbflowno);
					break;
				case 498:
					if(matchServiceId!=null){
						serviceid=matchServiceId;
					}
					esbmessage = "ESB调用" + sysid + "的服务" + serviceid
							+ "异常[HTTP_STATUS_CODE:" + statusCode
							+ "]拒绝链接,对应服务地址为:" + url;
					AdaptorHelper.setError(context, ESBCodes.CODE_E000498,
							esbmessage + "，对应ESB流水号为：" + esbflowno);
					break;
					default:
				if(log.isWarnEnabled()){
					log.warn("ESB调用服务"+"未知的错误类型,statusCode  = "+statusCode);
				}
				break;
				}

			}
    else if(null!=esbcode){
	if (esbcode.equals("ESB-E-000002")) {
		context.removeDelocalizedValue(ContextConstants.MESSAGE);
		if(matchServiceId!=null){
			serviceid=matchServiceId;
		}
		esbmessage = "ESB调用" + sysid + "的服务" + serviceid
				+ "异常,接出地址拒绝连接或超时,接出[HTTP_STATUS_CODE:" + statusCode + "],对应服务地址为:"+ url;
					
	} else if (esbcode.equals("ESB-E-000024")
			|| esbcode.equals("ESB-E-000001")) {
		context.removeDelocalizedValue(ContextConstants.MESSAGE);
		if(matchServiceId!=null){
			serviceid=matchServiceId;
		}
		esbmessage = "ESB调用" + sysid + "的服务" + serviceid + "处理超时";
		
	}

	else if (esbcode.equals("ESB-E-000008")) {
		context.removeDelocalizedValue(ContextConstants.MESSAGE);
		esbmessage = "请求方调用地址中请求方系统名:" + channelid + "未在ESB注册成功，请核实";
		
	} else if (esbcode.equals("ESB-E-000009")) {
		if(matchServiceId!=null){
			serviceid=matchServiceId;
		}
		context.removeDelocalizedValue(ContextConstants.MESSAGE);
		esbmessage = "请求方调用地址中服务名:" + serviceid + "校验失败，请核实";
			
	} else if (esbcode.equals("ESB-E-000053")) {
		context.removeDelocalizedValue(ContextConstants.MESSAGE);
		if(matchServiceId!=null){
			serviceid=matchServiceId;
		}
		esbmessage = "请求方调用地址中服务名:" + serviceid + "不存在，请核实";
			
	}
	else if (esbcode.equals("ESB-E-000048")
			|| esbcode.equals("ESB-E-000047")) {
		context.removeDelocalizedValue(ContextConstants.MESSAGE);
		esbmessage = "ESB解析报文失败,请核实报文内容、请求系统ID、服务名是否正确";
		
	}
	else if(esbcode.startsWith("ESB-E")){
	context.setDelocalizedValue(ContextConstants.MESSAGE, context.getDelocalizedValue(ContextConstants.MESSAGE)
			+ "，对应ESB流水号为：" + esbflowno);
	}
	context.setDelocalizedValue(ContextConstants.MESSAGE, esbmessage
			+ "，对应ESB流水号为：" + esbflowno);
}
		
			if (!ESBCodes.isSuccessful(context)) {

				Object conn = (IConnector) context
						.getLocalizedValue("connector");
				if (conn instanceof WSServerConnector) {
					packmsg(context, "soap");
				} else if (conn instanceof HTTPServerConnector) {
					packmsg(context, "json");

				}
			}

		} catch (Exception e) {
			log.error("ESB封装异常报文失败");
			e.printStackTrace();
		}
		return data;
	}

	public void packmsg(IBaseContext context, String type) {
		String esbCode = context.getDelocalizedValue(ContextConstants.CODE);
		String emessage = context.getDelocalizedValue(ContextConstants.MESSAGE).trim();
		if (type.equals("soap")) {
			String messgae = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
					+ "<soap:Body>"
					+ "<soap:Fault>"
					+ "<faultcode>"
					+ esbCode
					+ "</faultcode>"
					+ "<faultstring>"
					+ transfer(emessage)
					+ "</faultstring>"
					+ "</soap:Fault>"
					+ "</soap:Body>"
					+ "</soap:Envelope>";
			context.removeDelocalizedValue(ContextConstants.ORIGINALDATA);
			context.removeDelocalizedValue(ContextConstants.CODE);
			context.removeDelocalizedValue(ContextConstants.MESSAGE);
			context.setLocalizedValue(ContextConstants.ORIGINALDATA, messgae);
		} else if (type.equals("json")) {
			String messgae = "{\"ESBCODE\":\"" + esbCode + "\","+"\"ESBMESSAGE\":\"" + emessage + "\"}";
			context.removeDelocalizedValue(ContextConstants.ORIGINALDATA);
			context.removeDelocalizedValue(ContextConstants.CODE);
			context.removeDelocalizedValue(ContextConstants.MESSAGE);
			context.setLocalizedValue(ContextConstants.ORIGINALDATA, messgae);
		}
	}
public void setErroMessage(){
					
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

	private String transfer(String emessage) {
		return emessage.replace("<", "[").replace(">", "]");
	}

	/**
	 * 判断是否是成功的
	 * 
	 * @param code
	 * @return
	 */
	/*
	 * public static boolean isSuccessful(IBaseContext context) { return
	 * context.getDelocalizedValue(ContextConstants.CODE) == null; }
	 */

}
