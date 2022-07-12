package com.cskaoyan.shopping.controller;

import com.cskaoyan.mall.commons.result.ResponseData;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.shopping.dto.HomePageResponse;
import com.cskaoyan.shopping.dto.PanelContentDtoResponse;
import com.cskaoyan.shopping.dto.ProductCateDtoResponse;
import com.cskaoyan.shopping.service.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date2022/7/9 9:19
 * @Decription TODO
 * @Author dai_yuan
 */
@RestController
public class ShoppingController {
    @Autowired
    IHomeService homeService;

    /**
     * @Author daimaoyuan
     * @Date 19:24 2022/7/8
     * @Description 导航栏显示
     * @Param
     * @Return
     */
    @GetMapping("/shopping/navigation")
    public ResponseData shoppingNavigation() {
        //不需要入参
        PanelContentDtoResponse panelContentDtoList = homeService.selectNavigation();
        if (ShoppingRetCode.SUCCESS.getCode().equals(panelContentDtoList.getCode())) {
            //执行成功
            return new ResponseUtil().setData(panelContentDtoList.getPanelContentDto());
        }
        //执行失败
        return new ResponseUtil().setErrorMsg(panelContentDtoList.getMsg());


    }


    /**
     * @Author daimaoyuan
     * @Date 19:24 2022/7/8
     * @Description 列举所有商品种类
     * @Param
     * @Return
     */
    @GetMapping("/shopping/categories")
    public ResponseData shoppingCategories() {
        //不需要入参
        ProductCateDtoResponse response = homeService.selectCategories();
        if (ShoppingRetCode.SUCCESS.getCode().equals(response.getCode())) {
            //执行成功
            return new ResponseUtil().setData(response.getProductCateDtos());
        }
        //执行失败
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    /**
     * 商场主页显示
     * @return com.cskaoyan.mall.commons.result.ResponseData
     * @author DaneHuang
     * @since 2022/07/09 10:30
     */
    @GetMapping("/shopping/homepage")
    public ResponseData homepage() {
        HomePageResponse response = homeService.homepage();
        if (ShoppingRetCode.SUCCESS.getCode().equals(response.getCode())) {
            // 成功
            return new ResponseUtil().setData(response.getPanelContentItemDtos());
        }
        // 失败
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }
}


