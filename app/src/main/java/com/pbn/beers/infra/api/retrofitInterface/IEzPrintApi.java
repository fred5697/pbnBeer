package com.pbn.beers.infra.api.retrofitInterface;

import com.pbn.beers.infra.api.model.responese.ResponseGetDeviceUserID;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 訂單管理平台的 API 接口
 */
public interface IEzPrintApi
{
	/**
	 * 使用者綁定光譜儀後，使用 SN 取回 userID (hashID)
	 */
	@POST("checkDeviceSN")
	Call<ResponseGetDeviceUserID> checkDeviceSN(@Body RequestBody params);
}
