package com.common.utils.cache;

import android.content.Context;
import java.io.Serializable;

/**
 * Created by zengjing on 17/11/4.
 * 封装本地缓存
 * 自定义的Application类中需要初始化，LocalCacheManager.getInstance().init(this);
 * 便利于替换缓存库，而无需修改业务代码
 */

public class LocalCacheManager {
    private String user = "user";
    private ACache mCache;
    /**
     * 类加载时创建
     */
    private static class SingletonHolder{
        private static final LocalCacheManager INSTANCE = new LocalCacheManager();
    }

    public static LocalCacheManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * need application context
     * @param context
     */
    public void init(Context context){
        if(mCache==null){
            mCache = ACache.get(context);
        }
    }

    public void setUser(String user){
        this.user = user;
    }

    public void put(String key, String value){
        mCache.put(user+key, value);
    }
    public String getAsString(String key){
        return mCache.getAsString(user+key);
    }

    public void put(String key, Serializable value){
        mCache.put(user+key, value);
    }
    public Object getAsObject(String key){
        return mCache.getAsObject(user+key);
    }

    public void put(String key, byte[] data){
        mCache.put(user+key, data);
    }
    public byte[] getAsByte(String key){
        return mCache.getAsBinary(user+key);
    }

    public void remove(String key){
        mCache.remove(user+key);
    }


    public void putCommon(String key, String value){
        mCache.put(key, value);
    }
    public String getCommon(String key){
        return mCache.getAsString(key);
    }

    public void putCommonObject(String key, Serializable value){
        mCache.put(key, value);
    }
    public Object getCommonObject(String key){
        return mCache.getAsObject(key);
    }
    public void removeCommon(String key){
        mCache.remove(key);
    }
}
