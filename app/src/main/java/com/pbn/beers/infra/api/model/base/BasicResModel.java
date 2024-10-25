package com.pbn.beers.infra.api.model.base;

import java.io.Serializable;

/**
 *  * 所有 Response Model 都會繼承，保持格式的統一
 * @param <ApiResModel>
 */
public class BasicResModel<ApiResModel extends Serializable> implements Serializable
{
   public String code;
   public String msg;
   public ApiResModel data;
}