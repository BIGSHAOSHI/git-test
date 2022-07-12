package com.cskaoyan.shopping.service;


import com.cskaoyan.shopping.dto.HomePageResponse;
import com.cskaoyan.shopping.dto.PanelContentDtoResponse;
import com.cskaoyan.shopping.dto.ProductCateDtoResponse;

public interface IHomeService {

    /*
         显示首页商品信息，显示的是Panel信息，但是这里注意下，如何获取Panel信息呢？
         1. 先调用 PanelMapper selectPanelContentById，自己看下sql语句，获取 List<Panel>
         2. Panel对象中，少了 List<PanelContentItem> panelContentItems
         3. 因此，需要遍历List中的Panel，在调用PanelContentMapper 的 selectPanelContentAndProductWithPanelId方法
            获取List<PanelContentItem>，利用MapStruct将其转化为 List<PanelContentItemDto>
         4. 然后set 到Panel

     */
    HomePageResponse homepage();

    PanelContentDtoResponse selectNavigation();

    ProductCateDtoResponse selectCategories();
}
