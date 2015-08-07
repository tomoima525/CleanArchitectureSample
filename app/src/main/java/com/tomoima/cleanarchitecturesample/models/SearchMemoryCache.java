package com.tomoima.cleanarchitecturesample.models;

import android.support.v4.util.LruCache;

/**
 * Created by tomoaki on 8/7/15.
 */
public class SearchMemoryCache {

    private LruCache<String, User> mMap;

    private SearchMemoryCache(){
        //100kbを確保
        final int cacheSize = 1024 * 100;

        mMap = new LruCache<String, User>(cacheSize);
    }

    public User getUser(String key){
        synchronized(mMap){
            User user = mMap.get(key);
            if (user == null){
                return null;
            } else {
                return user;
            }
        }
    }

    public void put(String key, User user){
        if(user == null) return;
        synchronized (user) {
            User oldUser = mMap.put(key, user);
            if(oldUser != null){
                oldUser = null;
            }
        }
    }

    public void clear(){
        synchronized (mMap) {
            mMap.evictAll();
        }
    }

    private static SearchMemoryCache searchMemoryCache;
    public static SearchMemoryCache getInstance(){
        if(searchMemoryCache == null){
            searchMemoryCache = new SearchMemoryCache();
        }
        return searchMemoryCache;
    }

}
