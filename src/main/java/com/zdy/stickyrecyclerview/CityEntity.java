package com.zdy.stickyrecyclerview;

import java.io.Serializable;

/**
 * Created with Android Studio.
 * Time: 9:55  2017/10/30
 * Author:ZhuangYuan
 */

public class CityEntity implements Serializable {
    private String provinceName;
    private String cityName;
    private String cityPlateNumber;

    public String getCityPlateNumber() {
        return cityPlateNumber;
    }

    public void setCityPlateNumber(String cityPlateNumber) {
        this.cityPlateNumber = cityPlateNumber;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
