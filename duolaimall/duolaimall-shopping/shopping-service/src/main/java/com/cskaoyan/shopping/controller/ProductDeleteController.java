package com.cskaoyan.shopping.controller;

import com.cskaoyan.mall.commons.result.ResponseData;
import com.cskaoyan.mall.commons.result.ResponseUtil;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.mall.dto.ClearCartItemRequest;
import com.cskaoyan.mall.dto.ClearCartItemResponse;
import com.cskaoyan.shopping.dto.*;
import com.cskaoyan.shopping.service.IProductCheckedService;
import com.cskaoyan.shopping.service.IProductDeleteInCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductDeleteController {

    @Autowired
    IProductDeleteInCartService productDeleteInCartService;

    @Autowired
    IProductCheckedService productCheckedService;

    @DeleteMapping("/shopping/carts/{uid}/{pid}")
    public ResponseData deleteProductInCart(@PathVariable Long uid ,@PathVariable Long pid){
        DeleteCartItemRequest request = new DeleteCartItemRequest();
        request.setUserId(uid);
        request.setItemId(pid);
        DeleteCartItemResponse response = productDeleteInCartService.deleteProductInCartByProductId(request);
        if (ShoppingRetCode.SUCCESS.getCode().equals(response.getCode())){
            return new ResponseUtil<>().setData(response.getMsg());
        }
        return new ResponseUtil<>().setErrorMsg("删除失败");
    }

    @DeleteMapping("shopping/items/{uid}")
    public ResponseData deleteChosenProduct2(@PathVariable Long uid){
        DeleteCheckedItemRequest request = new DeleteCheckedItemRequest();
        request.setUserId(uid);
        DeleteCheckedItemResposne resposne = productDeleteInCartService.deleteCheckedItemsByUid(request);

        if (ShoppingRetCode.SUCCESS.getCode().equals(resposne.getCode())){
            return new ResponseUtil<>().setData(resposne.getMsg());
        }

        return new ResponseUtil<>().setErrorMsg("删除失败");
    }

//    @DeleteMapping(value = "/rpc/items")
//    ClearCartItemResponse clearCartItemByUserID(@RequestBody ClearCartItemRequest request){
//        ClearCartItemResponse response = productDeleteInCartService.clearCartItemByUserID(request);
//        return response;
//    }

    @PutMapping("shopping/items")
    public ResponseData checkAllItems(@RequestBody CheckAllItemRequest request){
        CheckAllItemResponse response = productCheckedService.checkAllItems(request);
        if (ShoppingRetCode.SUCCESS.getCode().equals(response.getCode())){
            return new ResponseUtil<>().setData(response.getMsg());
        }
        return new ResponseUtil<>().setErrorMsg("选中失败");
    }


}
