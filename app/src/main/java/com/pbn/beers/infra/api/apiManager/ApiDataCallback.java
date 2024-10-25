package com.pbn.beers.infra.api.apiManager;

import java.io.Serializable;

public class ApiDataCallback
{
	/**
	 * api callback 介面
	 */
	public interface onDataCallback<T extends Serializable>
	{
		void onGetDataSuccess(T data);
		
		void onResponseFailure(String errMsg);
		
		void noInternet();
	}
}
