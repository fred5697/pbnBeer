package com.pbn.beers.infra.api.model.base;

import java.io.Serializable;

/**
 * 所有 Request Model 都會繼承，保持格式的統一
 * @param <ApiReqModel>
 */
public class BasicReqModel<ApiReqModel extends Serializable> implements Serializable
{
   public String action;
   public ApiReqModel data;
}