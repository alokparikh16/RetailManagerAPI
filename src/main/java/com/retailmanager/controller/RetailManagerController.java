package com.retailmanager.controller;

/**
 * Created by Alok on 23-05-2017.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.retailmanager.bean.ShopAddress;
import com.retailmanager.bean.ShopBean;
import com.retailmanager.store.RetailStore;
import com.retailmanager.util.JSONUtil;
@Controller
public class RetailManagerController {
    @RequestMapping(value = "/getShopDetails")
    public ResponseEntity get() {
        ShopBean shopBean = new ShopBean();
        ShopAddress shopAddress = new ShopAddress();
        shopAddress.setShopNumber(12345);
        shopAddress.setShopPostCode(458990);
        shopBean.setShopAddress(shopAddress);
        shopBean.setShopName("Vishal Shop");
        shopBean.setShopLongitude(1.2);
        shopBean.setShopLatitude(1.2);
        return new ResponseEntity(shopBean, HttpStatus.OK);
    }

    @RequestMapping(value = "/postShopDetails", method = RequestMethod.POST)
    public ResponseEntity postShopDetails(
            @RequestBody ShopBean shopBean) {

        if (shopBean != null) {
            try {
                String latLong = JSONUtil.readLatLongFromAddress(shopBean
                        .getShopAddress().getShopPlace());
                String[] latLongAry = latLong.split(":");
                shopBean.setShopLongitude(Double.parseDouble(latLongAry[0]));
                shopBean.setShopLatitude(Double.parseDouble(latLongAry[1]));
                List shops = RetailStore.datamap.get(shopBean
                        .getShopAddress().getShopPlace());
                if (shops == null) {
                    shops = new ArrayList();
                }

                shops.add(shopBean);
                RetailStore.datamap.put(shopBean.getShopAddress()
                        .getShopPlace(), shops);

            } catch (Exception e) {

            }
        }

        return new ResponseEntity(shopBean, HttpStatus.OK);
    }

    @RequestMapping(value = "/getShopList", method = RequestMethod.GET)
    public ResponseEntity<List> getShopList(
            @RequestParam("Lng") double lng, @RequestParam("Lat") double lat) {
        List shopLst = new ArrayList();
        for (Map.Entry<Stringist> shopMap : RetailStore.datamap
                .entrySet()) {

            for (ShopBean shopBean : shopMap.getValue()) {
                if (lng == shopBean.getShopLongitude()
                        && lat == shopBean.getShopLatitude()) {
                    shopLst.add(shopBean);
                }

            }

        }

        return new ResponseEntity<List>(shopLst, HttpStatus.OK);
    }
}
