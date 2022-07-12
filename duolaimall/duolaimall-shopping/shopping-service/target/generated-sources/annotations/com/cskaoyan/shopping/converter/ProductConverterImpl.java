package com.cskaoyan.shopping.converter;

import com.cskaoyan.mall.dto.ItemDto;
import com.cskaoyan.mall.dto.ProductDto;
import com.cskaoyan.shopping.dal.entitys.Item;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-11T15:11:03+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_333 (Oracle Corporation)"
)
@Component
public class ProductConverterImpl implements ProductConverter {

    @Override
    public ProductDto item2Dto(Item item) {
        if ( item == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setPicUrl( item.getImageBig() );
        productDto.setProductId( item.getId() );
        productDto.setSubTitle( item.getSellPoint() );
        productDto.setSalePrice( item.getPrice() );
        productDto.setProductName( item.getTitle() );

        return productDto;
    }

    @Override
    public List<ProductDto> items2Dto(List<Item> items) {
        if ( items == null ) {
            return null;
        }

        List<ProductDto> list = new ArrayList<ProductDto>( items.size() );
        for ( Item item : items ) {
            list.add( item2Dto( item ) );
        }

        return list;
    }

    @Override
    public ItemDto item2ItemDto(Item item) {
        if ( item == null ) {
            return null;
        }

        ItemDto itemDto = new ItemDto();

        itemDto.setId( item.getId() );
        itemDto.setTitle( item.getTitle() );
        itemDto.setSellPoint( item.getSellPoint() );
        itemDto.setPrice( item.getPrice() );
        itemDto.setNum( item.getNum() );
        itemDto.setLimitNum( item.getLimitNum() );
        itemDto.setImage( item.getImage() );
        itemDto.setCid( item.getCid() );
        itemDto.setStatus( item.getStatus() );
        itemDto.setCreated( item.getCreated() );
        itemDto.setUpdated( item.getUpdated() );
        itemDto.setImageBig( item.getImageBig() );

        return itemDto;
    }

    @Override
    public List<ItemDto> items2ItemDtos(List<Item> items) {
        if ( items == null ) {
            return null;
        }

        List<ItemDto> list = new ArrayList<ItemDto>( items.size() );
        for ( Item item : items ) {
            list.add( item2ItemDto( item ) );
        }

        return list;
    }
}
