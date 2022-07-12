package com.cskaoyan.shopping.service;

import com.cskaoyan.mall.dto.ClearCartItemRequest;
import com.cskaoyan.mall.dto.ClearCartItemResponse;
import com.cskaoyan.shopping.dto.DeleteCartItemRequest;
import com.cskaoyan.shopping.dto.DeleteCartItemResponse;
import com.cskaoyan.shopping.dto.DeleteCheckedItemRequest;
import com.cskaoyan.shopping.dto.DeleteCheckedItemResposne;

public interface IProductDeleteInCartService {
    DeleteCartItemResponse deleteProductInCartByProductId(DeleteCartItemRequest request);

    DeleteCheckedItemResposne deleteCheckedItemsByUid(DeleteCheckedItemRequest request);

    ClearCartItemResponse clearCartItemByUserID(ClearCartItemRequest request);
}
