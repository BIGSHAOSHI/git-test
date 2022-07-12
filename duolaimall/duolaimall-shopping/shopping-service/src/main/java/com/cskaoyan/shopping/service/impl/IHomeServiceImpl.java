package com.cskaoyan.shopping.service.impl;

import com.cskaoyan.mall.commons.exception.ExceptionProcessorUtils;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.shopping.converter.ContentConverter;
import com.cskaoyan.shopping.converter.ProductCateConverter;
import com.cskaoyan.shopping.dal.entitys.ItemCat;
import com.cskaoyan.shopping.dal.entitys.Panel;
import com.cskaoyan.shopping.dal.entitys.PanelContent;
import com.cskaoyan.shopping.dal.entitys.PanelContentItem;
import com.cskaoyan.shopping.dal.persistence.ItemCatMapper;
import com.cskaoyan.shopping.dal.persistence.PanelContentMapper;
import com.cskaoyan.shopping.dal.persistence.PanelMapper;
import com.cskaoyan.shopping.dto.*;
import com.cskaoyan.shopping.service.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Date2022/7/8 19:31
 * @Decription TODO
 * @Author dai_yuan
 */
@Service
public class IHomeServiceImpl implements IHomeService {


    @Autowired
    PanelContentMapper panelContentMapper;

    @Autowired
    ContentConverter contentConverter;

    @Autowired
    PanelMapper panelMapper;


    /**
     * 商城主页显示
     * @return com.cskaoyan.shopping.dto.HomePageResponse
     * @author DaneHuang
     * @since 2022/07/09 10:26
     */
    @Override
    public HomePageResponse homepage() {

        // 获取所有的panel
        List<Panel> panels = panelMapper.selectAll();

        Set<PanelDto> panelDtos = new HashSet<>();
        // 遍历
        for (Panel panel : panels) {
            // 转换成panelDto
            PanelDto panelDto = contentConverter.panel2Dto(panel);

            // 获取panel里面缺少的panelContent
            List<PanelContentItem> panelContentItems = panelContentMapper.selectPanelContentAndProductWithPanelId(panelDto.getId());

            // 转换成panContentDto
            List<PanelContentItemDto> panelContentItemDtos = contentConverter.panelContentItem2Dto(panelContentItems);

            panelDto.setPanelContentItems(panelContentItemDtos);

            // 用Set接收转换成功的panelDto
            panelDtos.add(panelDto);
        }
        HomePageResponse response = new HomePageResponse();

        // 执行成功
        response.setPanelContentItemDtos(panelDtos);
        response.setCode(ShoppingRetCode.SUCCESS.getCode());
        response.setMsg(ShoppingRetCode.SUCCESS.getMessage());

        return response;
    }

    //导航栏显示
    @Override
    public PanelContentDtoResponse selectNavigation() {
        PanelContentDtoResponse response = new PanelContentDtoResponse();

        try {
            Example example = new Example(PanelContent.class);
            example.createCriteria().andEqualTo("panelId", "0");
            //查询数据库
            List<PanelContent> panelContentList = panelContentMapper.selectByExample(example);
            //转换器转换
            List<PanelContentDto> panelContentDtos = contentConverter.panelContents2Dto(panelContentList);

            response.setCode(ShoppingRetCode.SUCCESS.getCode());
            response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
            response.setPanelContentDto(panelContentDtos);


        } catch (Exception e) {
            e.printStackTrace();
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;

    }

    @Autowired
    ProductCateConverter productCateConverter;
    @Autowired
    ItemCatMapper itemCatMapper;

    //列举所有商品种类
    @Override
    public ProductCateDtoResponse selectCategories() {
        ProductCateDtoResponse response = new ProductCateDtoResponse();
        try {
            ItemCat itemCat = new ItemCat();
            List<ItemCat> allCategories = itemCatMapper.select(itemCat);
            List<ProductCateDto> productCateDtos = productCateConverter.items2Dto(allCategories);
            response.setCode(ShoppingRetCode.SUCCESS.getCode());
            response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
            response.setProductCateDtos(productCateDtos);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }

        return response;
    }
}
