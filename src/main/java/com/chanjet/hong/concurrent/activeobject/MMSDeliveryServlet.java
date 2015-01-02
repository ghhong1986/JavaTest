/* 
 * Copyright 畅捷通股份有限公司  @ 2014 版权所有    
 *    
 */
package com.chanjet.hong.concurrent.activeobject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *    
 * <p>
 * TODO (用一句话描述该文件做什么)   
 *</p>
 * @author 洪光华 </br>
 * @Email honggh@chanjet.com 
 * @date 2014年11月29日 上午11:04:59
 *
 * @version V1.0     
 *     
 */
@SuppressWarnings("all")
public class MMSDeliveryServlet extends HttpServlet {

	private static final long serialVersionUID = 5886933373599895099L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//将请求中的数据解析为内部对象
		MMSDeliverRequest mmsDeliverReq = this.parseRequest(req.getInputStream());
		Recipient shortNumberRecipient = mmsDeliverReq.getRecipient();
		Recipient originalNumberRecipient = null;

		try {
			// 将接收方短号转换为长号
			originalNumberRecipient = convertShortNumber(shortNumberRecipient);
		} catch (SQLException e) {

			// 接收方短号转换为长号时发生数据库异常，触发请求消息的缓存
			AsyncRequestPersistence.getInstance().store(mmsDeliverReq);

			// 继续对当前请求的其它处理，如给客户端响应
			resp.setStatus(202);
		}

	}

	private MMSDeliverRequest parseRequest(InputStream reqInputStream) {
		MMSDeliverRequest mmsDeliverReq = new MMSDeliverRequest();
		//省略其它代码
		return mmsDeliverReq;
	}

	private Recipient convertShortNumber(Recipient shortNumberRecipient)
			throws SQLException {
		Recipient recipent = null;
		//省略其它代码
		return recipent;
	}

}
