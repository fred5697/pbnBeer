package com.pbn.beers.infra.api.apiManager;

import android.content.Context;

import com.pbn.beers.infra.api.model.request.RequestCheckDeviceSN;
import com.pbn.beers.infra.api.model.responese.ResponseGetDeviceUserID;
import com.pbn.beers.infra.api.retrofitManager.RetrofitManager;

import okhttp3.RequestBody;
import retrofit2.Call;

public class EzPrintServiceApi extends BaseApiManager
{
	private final Context mContext;
	
	public EzPrintServiceApi(Context mContext) {
		this.mContext = mContext;
	}
	
	/**
	 * 使用者綁定光譜儀後，使用 SN 取回 userID (hashID)
	 */
	public void checkDeviceSn(RequestCheckDeviceSN reqModel,
			ApiDataCallback.onDataCallback<ResponseGetDeviceUserID> callback) {
		if(ifCanConnected(mContext)) {
			RequestBody requestBody = makeRequestBody(reqModel);
			Call<ResponseGetDeviceUserID> call = RetrofitManager.getEzPrintGsonApi().
					checkDeviceSN(requestBody);
			
			call.enqueue(getGenericCallback(callback));
		}
		else
			callback.noInternet();
	}
}
