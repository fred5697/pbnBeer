package com.pbn.beers.infra.api.apiManager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

import com.pbn.beers.infra.api.model.base.BasicReqModel;
import com.pbn.beers.infra.api.model.base.BasicResModel;
import com.google.gson.Gson;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class BaseApiManager
{
	/**
	 * okHttp3 請求的 requestModel 轉 json 後, 製作出 requestBody
	 *
	 * @param modelData request Model
	 * @return RequestBody
	 */
	protected static <model extends BasicReqModel<?>> RequestBody makeRequestBody(model modelData) {
		//		GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss"); // 設定時間格式的轉型
		Gson gson = new Gson();
		String json = gson.toJson(modelData);
		return RequestBody.create(json, okhttp3.MediaType.parse("application/json; charset=utf-8"));
	}
	
	/**
	 * 抽出 retrofit2 的 http 請求 resultModel callback. (*Json 轉換使用)
	 *
	 * @param onDataCallback callback
	 * @param <model>        指定類別需繼承 Serializable
	 * @return 對應型別的 callback
	 */
	protected static <model extends BasicResModel<?>> Callback<model> getGenericCallback(final ApiDataCallback.onDataCallback<model> onDataCallback) {
		return new Callback<model>()
		{
			@Override public void onResponse(@NonNull Call<model> call, @NonNull Response<model> response) {
				if(response.isSuccessful())
					onDataCallback.onGetDataSuccess(response.body());
				else {
					String errMsg = !response.message().equals("") ? response.message() : "站台響應失敗";
					onDataCallback.onResponseFailure(response.code() + errMsg);
				}
			}
			
			@Override public void onFailure(@NonNull Call<model> call, @NonNull Throwable t) {
				onDataCallback.onResponseFailure(t.getMessage());
			}
		};
	}
	
	/**
	 * * 網路是否能連線?
	 *
	 * @return 網路是否能連線?
	 */
	protected static boolean ifCanConnected(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager.getActiveNetworkInfo();
		boolean internetIsNotConnected = info == null || !info.isConnected();
		return !internetIsNotConnected;
	}
	
}
