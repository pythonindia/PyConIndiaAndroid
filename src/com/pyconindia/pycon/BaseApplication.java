package com.pyconindia.pycon;

import android.app.Application;

public class BaseApplication extends Application {

    private int pageNo = 0;

    public int getPageNumber() {
        return pageNo;
    }

    public void setPageNumber(int pageNo) {
        this.pageNo = pageNo;
    }
}
