/*
 * <p>Title: :simpleMode.java </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: digitalchina.Ltd</p>
 * @author 
 * Created :2018-12-19 16:16:52
 * @version 1.0
 * ModifyList:
 * <Author> <Time(yyyy/mm/dd)>  <Description>  <Version>
 */

package com.dc.esb.base.impls.service;

import com.dc.esb.container.core.data.IServiceDataObject;
import com.dc.esb.container.core.sclite.IBaseContext;
import com.dc.esb.container.core.sclite.IBaseService;
import com.dc.esb.container.core.sclite.ITransferableContext;
import com.dc.esb.container.core.sclite.InvokeException;
import com.dc.esb.container.core.sclite.ServiceMaintainException;
import com.dc.esb.container.core.sclite.trans.PropertyType;
import com.dc.esb.container.log.MyLogHelper;
import com.dc.esb.container.log.MyLog;

/**
 * <p>
 * 
 * <li>
 * </li>
 * </p>
 * 是否使用简单开发模式：无须配置服务，只需要注册接出协议和系统即可
 * @author zhaiyda
 * @E-Mail 
 */
public class simpleMode implements IBaseService {
private final MyLog log = MyLogHelper.getFactory().getLog(
			simpleMode.class);
	public IServiceDataObject invoke(IServiceDataObject arg0, IBaseContext context)
			throws InvokeException {
		// TODO Auto-generated method stub
	if(log.isDebugEnabled()){
		log.debug("开始进入ESB简单模式");
	}
		((ITransferableContext) context).setDelocalizedValue(
					"Simplemode", "true",
					PropertyType.RGLOBALVAR);
		return arg0;
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
