package com.pbn.beers.infra.api.model.request;

import com.pbn.beers.infra.api.model.base.BasicReqModel;

/**
 * 第一次綁定光譜儀後，向訂單管理後台詢問並註冊
 */
public class RequestCheckDeviceSN extends BasicReqModel<CheckDeviceSN>
{
	public RequestCheckDeviceSN() {
		new RequestCheckDeviceSN(null);
	}
	
	public RequestCheckDeviceSN(String sn) {
		super.action = "check";
		super.data = new CheckDeviceSN();
		super.data.sn = sn;
	}
}
