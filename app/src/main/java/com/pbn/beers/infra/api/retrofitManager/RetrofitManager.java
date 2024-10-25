package com.pbn.beers.infra.api.retrofitManager;

import com.pbn.beers.BuildConfig;
import com.pbn.beers.infra.api.retrofitInterface.IEzPrintApi;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 管理透過 Retrofit 建立的 Api instance
 */
public class RetrofitManager
{
	/**
	 * 訂單管理平台的 Api Instance
	 */
	private static IEzPrintApi ezPrintGsonApi = null;
	private final static String mEzPrintURL = BuildConfig.EzPrint;
	
	private final static GsonConverterFactory mGsonConverter
			= GsonConverterFactory.create(new GsonBuilder().setLenient().create());
	//			= GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").setLenient().create());
	
	public static IEzPrintApi getEzPrintGsonApi() {
		if(ezPrintGsonApi == null) {
			Retrofit retrofit = new Retrofit.Builder().baseUrl(mEzPrintURL).addConverterFactory(mGsonConverter).build();
			ezPrintGsonApi = retrofit.create(IEzPrintApi.class);
		}
		return ezPrintGsonApi;
	}
}
