package com.cskaoyan.shopping.controller;

import com.cskaoyan.mall.commons.result.ResponseData;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.mall.dto.ProductDetailRequest;
import com.cskaoyan.mall.dto.ProductDetailResponse;
import com.cskaoyan.shopping.converter.ProductConverter;
import com.cskaoyan.shopping.dto.AllProductRequest;
import com.cskaoyan.shopping.dto.AllProductResponse;
import com.cskaoyan.shopping.dto.RecommendResponse;
import com.cskaoyan.shopping.form.PageResponse;
import com.cskaoyan.shopping.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ProductDetailController
 * @Description TODO 商品模块
 * @Author 屈正
 * @Date 2022/7/8 22:23
 * Version 1.0
 **/
@RestController
public class ProductController_quzheng {
    @Autowired
    IProductService iProductService;

    @Autowired
    ProductConverter productConverter;

    /**
     * @return com.cskaoyan.mall.commons.result.ResponseData
     * @Author 屈正
     * @Description TODO 查看商品详情
     * @Date 11:36 2022/7/9
     * @Param [id]
     **/
    @GetMapping("/shopping/product/{id}")
    public ResponseData getProductDetail(@PathVariable long id) {
        ProductDetailRequest request = new ProductDetailRequest();
        request.setId(id);
        ProductDetailResponse response = iProductService.getProductDetail(request);
        if (ShoppingRetCode.SUCCESS.getCode().equals(response.getCode())) {
            return new ResponseUtil().setData(response.getProductDetailDto());
        }
        //检验失败
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    /**
     * @Author 屈正
     * @Description TODO 查询所有商品（分页）
     * @Date 14:32 2022/7/9
     * @Param [request]
     * @return com.cskaoyan.mall.commons.result.ResponseData
     **/
    @GetMapping("shopping/goods")
    public ResponseData getGoodsList( AllProductRequest request) {
        AllProductResponse response = iProductService.getAllProduct(request);
        if (ShoppingRetCode.SUCCESS.getCode().equals(response.getCode())) {
            PageResponse pageResponse = new PageResponse();
            pageResponse.setData(response.getProductDtoList());
            pageResponse.setTotal(response.getTotal());
            return new ResponseUtil().setData(pageResponse);
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    /**
     * @Author 屈正
     * @Description TODO 查询推荐商品
     * @Date 16:42 2022/7/9
     * @Param []
     * @return com.cskaoyan.mall.commons.result.ResponseData
     **/
    @GetMapping("/shopping/recommend")
    public ResponseData getRecommendGoodsList(){
        RecommendResponse response = iProductService.getRecommendGoods();
        if (ShoppingRetCode.SUCCESS.getCode().equals(response.getCode())) {
            return new ResponseUtil().setData(response.getPanelContentItemDtos());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }
}
