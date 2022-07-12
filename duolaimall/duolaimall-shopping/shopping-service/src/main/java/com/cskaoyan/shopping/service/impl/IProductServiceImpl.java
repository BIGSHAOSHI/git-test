package com.cskaoyan.shopping.service.impl;

import com.cskaoyan.mall.commons.exception.ExceptionProcessorUtils;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.mall.dto.*;
import com.cskaoyan.shopping.converter.ContentConverter;
import com.cskaoyan.shopping.converter.ProductConverter;
import com.cskaoyan.shopping.converter.ProductDetailConverter;
import com.cskaoyan.shopping.dal.entitys.*;
import com.cskaoyan.shopping.dal.persistence.ItemDescMapper;
import com.cskaoyan.shopping.dal.persistence.ItemMapper;
import com.cskaoyan.shopping.dal.persistence.PanelContentMapper;
import com.cskaoyan.shopping.dal.persistence.PanelMapper;
import com.cskaoyan.shopping.dto.*;
import com.cskaoyan.shopping.service.IProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.redisson.client.RedisClient;
import org.redisson.client.RedisConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName IProductServiceImpl
 * @Description TODO 商品模块
 * @Author 屈正
 * @Date 2022/7/8 23:03
 * Version 1.0
 **/
@Service
public class IProductServiceImpl implements IProductService {

    @Autowired
    ItemMapper itemMapper;
    @Autowired
    ItemDescMapper itemDescMapper;
    @Autowired
    ProductConverter productConverter;
    @Autowired
    ProductDetailConverter productDetailConverter;
    @Autowired
    PanelMapper panelMapper;
    @Autowired
    ContentConverter contentConverter;
    @Autowired
    PanelContentMapper panelContentMapper;

    /**
     * @return com.cskaoyan.mall.dto.ProductDetailResponse
     * @Author 屈正
     * @Description TODO 查看商品详情
     * @Date 12:09 2022/7/9
     * @Param [request]
     **/
    @Override
    public ProductDetailResponse getProductDetail(ProductDetailRequest request) {

        ProductDetailResponse response = new ProductDetailResponse();

        try {
            request.requestCheck();
            Item item = itemMapper.selectByPrimaryKey(request.getId());
            ItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(request.getId());
            ProductDetailDto productDetailDto = productDetailConverter.item2Dto(item);
            productDetailDto.setDetail(itemDesc.getItemDesc());
            //执行成功
            response.setCode(ShoppingRetCode.SUCCESS.getCode());
            response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
            response.setProductDetailDto(productDetailDto);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * @return com.cskaoyan.shopping.dto.AllProductResponse
     * @Author 屈正
     * @Description TODO 分页查询商品列表
     * @Date 12:10 2022/7/9
     * @Param [request]
     **/
    @Override
    public AllProductResponse getAllProduct(AllProductRequest request) {
        AllProductResponse response = new AllProductResponse();

        try {
            request.requestCheck();
            PageHelper.startPage(request.getPage(), request.getSize());
            String orderCol = "price";
            String sort = request.getSort();
            String orderDir = null;
            if ("-1".equals(sort)) {
                orderDir = "desc";
            } else if ("1".equals(sort)) {
                orderDir = "asc";
            } else {
                orderCol = "created";
                orderDir = "desc";
            }
            List<Item> items = itemMapper.selectItemFront(request.getCid(), orderCol, orderDir, request.getPriceGt(), request.getPriceLte());
            PageInfo<Item> itemPageInfo = new PageInfo<>(items);
            List<ProductDto> productDtos = productConverter.items2Dto(items);
            response.setCode(ShoppingRetCode.SUCCESS.getCode());
            response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
            response.setTotal(itemPageInfo.getTotal());
            response.setProductDtoList(productDtos);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * @return com.cskaoyan.shopping.dto.RecommendResponse
     * @Author 屈正
     * @Description TODO 查询推荐商品
     * @Date 14:53 2022/7/9
     * @Param []
     **/
    @Override
    public RecommendResponse getRecommendGoods() {
        RecommendResponse response = new RecommendResponse();

        try {
            Example example = new Example(Panel.class);
            example.createCriteria().andEqualTo("name", "热门推荐").andEqualTo("remark", "热门推荐");
            List<Panel> panels = panelMapper.selectByExample(example);
            Panel panel = panels.get(0);
            PanelDto panelDto = contentConverter.panel2Dto(panel);
            Integer panelId = panel.getId();
            List<PanelContentItem> panelContentItems = panelContentMapper.selectPanelContentAndProductWithPanelId(panelId);
            List<PanelContentItemDto> panelContentItemDtos = contentConverter.panelContentItem2Dto(panelContentItems);
            panelDto.setPanelContentItems(panelContentItemDtos);

            Set<PanelDto> set = new HashSet<>();
            set.add(panelDto);
            response.setCode(ShoppingRetCode.SUCCESS.getCode());
            response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
            response.setPanelContentItemDtos(set);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }


        return response;
    }

    @Override
    public AllItemResponse getAllItems() {
        return null;
    }
}
