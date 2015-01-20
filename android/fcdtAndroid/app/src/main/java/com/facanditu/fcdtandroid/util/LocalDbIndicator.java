package com.facanditu.fcdtandroid.util;

/**
 * Created by fengqin on 15/1/12.
 */
public class LocalDbIndicator {

    private static LocalDbIndicator ins = new LocalDbIndicator();

    private LocalDbIndicator(){

    }

    private boolean syncSuccess;

    public boolean isSyncSuccess() {
        return syncSuccess;
    }

    public void setSyncSuccess(boolean syncSuccess) {
        this.syncSuccess = syncSuccess;
    }

    public static LocalDbIndicator getIns(){
        return ins;
    }
}
