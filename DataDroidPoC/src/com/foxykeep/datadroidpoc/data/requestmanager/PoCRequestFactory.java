/**
 * 2012 Foxykeep (http://datadroid.foxykeep.com)
 * <p>
 * Licensed under the Beerware License : <br />
 * As long as you retain this notice you can do whatever you want with this stuff. If we meet some
 * day, and you think this stuff is worth it, you can buy me a beer in return
 */

package com.foxykeep.datadroidpoc.data.requestmanager;

import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroidpoc.data.operation.CityListOperation;
import com.foxykeep.datadroidpoc.data.operation.CrudSyncPhoneAddEditOperation;
import com.foxykeep.datadroidpoc.data.operation.CrudSyncPhoneDeleteOperation;
import com.foxykeep.datadroidpoc.data.operation.CrudSyncPhoneListOperation;
import com.foxykeep.datadroidpoc.data.operation.PersonListOperation;
import com.foxykeep.datadroidpoc.data.operation.RssFeedOperation;

/**
 * Class used to create the {@link Request}s.
 *
 * @author Foxykeep
 */
public final class PoCRequestFactory {

    // Request types
    public static final int REQUEST_TYPE_PERSON_LIST = 0;
    public static final int REQUEST_TYPE_CITY_LIST = 1;

    public static final int REQUEST_TYPE_CRUD_SYNC_PHONE_LIST = 10;
    public static final int REQUEST_TYPE_CRUD_SYNC_PHONE_DELETE = 11;
    public static final int REQUEST_TYPE_CRUD_SYNC_PHONE_ADD = 12;
    public static final int REQUEST_TYPE_CRUD_SYNC_PHONE_EDIT = 13;

    public static final int REQUEST_TYPE_RSS_FEED = 20;

    // Response data
    public static final String BUNDLE_EXTRA_CITY_LIST =
            "com.foxykeep.datadroidpoc.extras.cityList";
    public static final String BUNDLE_EXTRA_PHONE_LIST =
            "com.foxykeep.datadroidpoc.extras.phoneList";
    public static final String BUNDLE_EXTRA_PHONE_DELETE_DATA =
            "com.foxykeep.datadroidpoc.extras.phoneDeleteData";
    public static final String BUNDLE_EXTRA_PHONE_ADD_EDIT_DATA =
            "com.foxykeep.datadroidpoc.extras.phoneAddEditData";
    public static final String BUNDLE_EXTRA_RSS_FEED_DATA =
            "com.foxykeep.datadroidpoc.extras.rssFeed";

    private PoCRequestFactory() {
        // no public constructor
    }

    /**
     * Create the request to get the list of persons and save it in the database.
     *
     * @param returnFormat 0 for XML, 1 for JSON.
     * @return The request.
     */
    public static Request createGetPersonListRequest(int returnFormat) {
        Request request = new Request(REQUEST_TYPE_PERSON_LIST);
        request.put(PersonListOperation.PARAM_RETURN_FORMAT, returnFormat);
        return request;
    }

    /**
     * Create the request to get the list of cities and save it in the memory provider.
     * 
     * @return The request.
     */
    public static Request createGetCityListRequest() {
        return createGetCityListRequest(1);
    }

    /**
     * Create the request to get the list of cities and save it in the memory provider.
     *
     * @return The request.
     */
    public static Request createGetCityListRequest(int dataset) {
        Request request = new Request(REQUEST_TYPE_CITY_LIST);
        request.put(CityListOperation.PARAM_DATASET, dataset);
        request.setMemoryCacheEnabled(true);
        return request;
    }

    /**
     * Create the request to get the list of phones synchronously and save it in the memory.
     *
     * @param userId the id of the user (generated by the application).
     * @return The request.
     */
    public static Request createGetSyncPhoneListRequest(String userId) {
        Request request = new Request(REQUEST_TYPE_CRUD_SYNC_PHONE_LIST);
        request.setMemoryCacheEnabled(true);
        request.put(CrudSyncPhoneListOperation.PARAM_USER_ID, userId);
        return request;
    }

    /**
     * Create the request to delete a phone synchronously.
     *
     * @param userId the id of the user (generated by the application).
     * @param phoneIdList the list of phone ids to delete (comma separated).
     * @return The request.
     */
    public static Request createDeleteSyncPhonesRequest(String userId, String phoneIdList) {
        Request request = new Request(REQUEST_TYPE_CRUD_SYNC_PHONE_DELETE);
        request.setMemoryCacheEnabled(true);
        request.put(CrudSyncPhoneDeleteOperation.PARAM_USER_ID, userId);
        request.put(CrudSyncPhoneDeleteOperation.PARAM_PHONE_ID_LIST, phoneIdList);
        return request;
    }

    /**
     * Create the request to add a phone synchronously.
     *
     * @param userId the id of the user (generated by the application).
     * @param name the phone name.
     * @param manufacturer the phone manufacturer.
     * @param androidVersion the phone android version.
     * @param screenSize the phone screen size.
     * @param price the phone price.
     * @return The request.
     */
    public static Request createAddSyncPhoneRequest(String userId, String name,
            String manufacturer, String androidVersion, double screenSize, int price) {
        Request request = new Request(REQUEST_TYPE_CRUD_SYNC_PHONE_ADD);
        return createAddEditSyncPhoneRequest(request, userId, -1, name, manufacturer,
                androidVersion, screenSize, price);
    }

    /**
     * Create the request to edit a phone synchronously.
     *
     * @param userId the id of the user (generated by the application).
     * @param phoneId the phone id.
     * @param name the phone new name.
     * @param manufacturer the phone new manufacturer.
     * @param androidVersion the phone new android version.
     * @param screenSize the phone new screen size.
     * @param price the phone new price.
     * @return The request.
     */
    public static Request createEditSyncPhoneRequest(String userId, long phoneId, String name,
            String manufacturer, String androidVersion, double screenSize, int price) {
        Request request = new Request(REQUEST_TYPE_CRUD_SYNC_PHONE_EDIT);
        return createAddEditSyncPhoneRequest(request, userId, phoneId, name, manufacturer,
                androidVersion, screenSize, price);
    }

    private static Request createAddEditSyncPhoneRequest(Request request, String userId,
            long phoneId, String name, String manufacturer, String androidVersion,
            double screenSize, int price) {
        request.setMemoryCacheEnabled(true);
        request.put(CrudSyncPhoneAddEditOperation.PARAM_USER_ID, userId);
        request.put(CrudSyncPhoneAddEditOperation.PARAM_PHONE_ID, phoneId);
        request.put(CrudSyncPhoneAddEditOperation.PARAM_NAME, name);
        request.put(CrudSyncPhoneAddEditOperation.PARAM_MANUFACTURER, manufacturer);
        request.put(CrudSyncPhoneAddEditOperation.PARAM_ANDROID_VERSION, androidVersion);
        request.put(CrudSyncPhoneAddEditOperation.PARAM_SCREEN_SIZE, screenSize);
        request.put(CrudSyncPhoneAddEditOperation.PARAM_PRICE, price);
        return request;
    }

    /**
     * Create the request to get the RSS feed of the given URL and save it in the memory.
     *
     * @param feedUrl the URL of the RSS feed.
     * @return The request.
     */
    public static Request createGetRssFeedRequest(String feedUrl) {
        Request request = new Request(REQUEST_TYPE_RSS_FEED);
        request.setMemoryCacheEnabled(true);
        request.put(RssFeedOperation.PARAM_FEED_URL, feedUrl);
        return request;
    }

}
