/*
 * <p>Title: :TranshferResHeader.java </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: digitalchina.Ltd</p>
 * @author 
 * Created :2018-12-3 14:14:55
 * @version 1.0
 * ModifyList:
 * <Author> <Time(yyyy/mm/dd)>  <Description>  <Version>
 */

package com.dc.esb.base.impls.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.dc.esb.container.core.data.IServiceDataObject;
import com.dc.esb.container.core.sclite.IBaseContext;
import com.dc.esb.container.core.sclite.IBaseService;
import com.dc.esb.container.core.sclite.InvokeException;
import com.dc.esb.container.core.sclite.ServiceMaintainException;
import com.dc.esb.container.protocol.http.HTTPTransfer;
import com.dc.esb.container.sclite.ContextConstants;

/**
 * <p>
 * 
 * <li>
 * </li>
 * </p>
 * 
 * @author zhaiyd 将后端系统的响应头传递给 请求方
 * @E-Mail
 */
public class TranshferResHeader implements IBaseService {

	public IServiceDataObject invoke(IServiceDataObject sdo,
			IBaseContext context) throws InvokeException {
		HttpServletResponse response = (HttpServletResponse) context
				.getLocalizedValue(ContextConstants.HTTPRESP);
		String headers = context
				.getDelocalizedValue(ContextConstants.HTTP_HEADERS);
		if (null != headers) {
			Map header = HTTPTransfer.stringToMap(headers);
			HTTPTransfer.setResponseHeader(response, header);

		}
		return sdo;
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
