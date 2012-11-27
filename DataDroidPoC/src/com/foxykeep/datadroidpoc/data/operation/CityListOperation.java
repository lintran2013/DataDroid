/**
 * 2011 Foxykeep (http://datadroid.foxykeep.com)
 * <p>
 * Licensed under the Beerware License : <br />
 * As long as you retain this notice you can do whatever you want with this stuff. If we meet some
 * day, and you think this stuff is worth it, you can buy me a beer in return
 */

package com.foxykeep.datadroidpoc.data.operation;

import android.content.Context;
import android.os.Bundle;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.network.NetworkConnection.Builder;
import com.foxykeep.datadroid.network.NetworkConnection.ConnectionResult;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;
import com.foxykeep.datadroidpoc.config.WSConfig;
import com.foxykeep.datadroidpoc.data.factory.CityListJsonFactory;

import java.util.HashMap;

public final class CityListOperation implements Operation {

    public static final String PARAM_DATASET =
            "com.foxykeep.datadroidpoc.extras.dataset";

    public CityListOperation() {}

    @Override
    public Bundle execute(Context context, Request request) throws ConnectionException,
            DataException {
        String dataset = String.valueOf(request.getInt(PARAM_DATASET));

        HashMap<String, String> parameterMap = new HashMap<String, String>();
        parameterMap.put(WSConfig.WS_CITY_LIST_PROPERTY_DATASET, dataset);

        Builder builder = new Builder(context, WSConfig.WS_CITY_LIST_URL);
        builder.setParameters(parameterMap);
        ConnectionResult result = builder.execute();

        return CityListJsonFactory.parseResult(result.body);
    }
}
