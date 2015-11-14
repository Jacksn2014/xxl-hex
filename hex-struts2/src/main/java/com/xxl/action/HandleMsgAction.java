package com.xxl.action;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxl.action.base.DataBaseAction;
import com.xxl.action.hex.HexEnum;
import com.xxl.action.hex.logic.ILogic;
import com.xxl.action.hex.msg.IResponse;
import com.xxl.action.hex.msg.response.RedirectResponse;
import com.xxl.action.hex.util.MsgReflectUtil;
import com.xxl.core.exception.ExceptionKey;
import com.xxl.core.exception.WebException;

@SuppressWarnings("serial")
public class HandleMsgAction extends DataBaseAction {
	private static transient Logger logger = LoggerFactory.getLogger(HandleMsgAction.class);
	
	/**
	 * 16进制通讯入口
	 * @return
	 * @throws WebException
	 */
	public String handleMsg() throws WebException {
		// 获取消息类型
		int msgType = getMsg().getMsgType();		
		try {
			ILogic logic = (ILogic) HexEnum.get(msgType).getLogicClazz().newInstance();
			IResponse responseInfo = logic.handle(getMsg(), getClientIp());
			
			if (responseInfo instanceof RedirectResponse) {
				String redirectUrl = ((RedirectResponse) responseInfo).getRedirectInfo();
				redirectUrl = initRedirectUrl(redirectUrl);
				super.setResp(redirectUrl);
				return SUCCESS;
			} else {
				super.setResp(MsgReflectUtil.responseToHex(responseInfo));
				return "success_text";
			}		
		} catch (Exception e) {
			logger.error("[logic handler exception...]", e);
			throw new WebException(ExceptionKey.defaultKey, "服务器系统异常");
		}
	}
	
	/**
	 * 拼接url
	 * @param redirectUrl
	 * @return
	 */
	public static String initRedirectUrl (String redirectUrl) {
		HttpServletRequest request = ServletActionContext.getRequest();	
		@SuppressWarnings("unchecked")
		Map<String, String[]> requestParams = request.getParameterMap();
		Iterator<String> keyIter = requestParams.keySet().iterator();
		while (keyIter.hasNext()) {
			String name = keyIter.next();
			if (!"aa".equalsIgnoreCase(name.trim()) && !"hex".equalsIgnoreCase(name.trim())) {
				String[] values = (String[]) requestParams.get(name);
				String valueStr = (values != null && values.length > 0) ? values[0] : "";
				redirectUrl = redirectUrl + "&" + name + "=" + valueStr;
			}
		}	
		return redirectUrl;
	}
	
}
