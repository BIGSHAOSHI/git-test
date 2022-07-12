package com.cskaoyan.shopping.service;

import com.cskaoyan.shopping.dto.CheckAllItemRequest;
import com.cskaoyan.shopping.dto.CheckAllItemResponse;

public interface IProductCheckedService {
    CheckAllItemResponse checkAllItems(CheckAllItemRequest request);
}
