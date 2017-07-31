package com.xiaolin.bean;

import java.io.Serializable;

/**
 * Created by sjy on 2017/7/31.
 */

public class UpgradeBean implements Serializable {
    private static final long serialVersionUID = 1L;


    private String Version;

    private String Message;

    private String PackageUrl;


    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getPackageUrl() {
        return PackageUrl;
    }

    public void setPackageUrl(String packageUrl) {
        PackageUrl = packageUrl;
    }

    @Override
    public String toString() {
        return "UpgradeBean{" +
                "Version='" + Version + '\'' +
                ", Message='" + Message + '\'' +
                ", PackageUrl='" + PackageUrl + '\'' +
                '}';
    }
}
