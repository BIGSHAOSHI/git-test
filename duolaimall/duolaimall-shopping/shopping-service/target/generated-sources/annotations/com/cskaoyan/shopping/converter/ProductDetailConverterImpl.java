package com.cskaoyan.shopping.converter;

import com.cskaoyan.mall.dto.ProductDetailDto;
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
public class ProductDetailConverterImpl implements ProductDetailConverter {

    @Override
    public ProductDetailDto item2Dto(Item item) {
        if ( item == null ) {
            return null;
        }

        ProductDetailDto productDetailDto = new ProductDetailDto();

        productDetailDto.setProductImageSmall( stringArrayToStringList( item.getImages() ) );
        productDetailDto.setProductId( item.getId() );
        productDetailDto.setSubTitle( item.getSellPoint() );
        productDetailDto.setSalePrice( item.getPrice() );
        productDetailDto.setProductImageBig( item.getImageBig() );
        productDetailDto.setProductName( item.getTitle() );
        if ( item.getLimitNum() != null ) {
            productDetailDto.setLimitNum( item.getLimitNum().longValue() );
        }

        return productDetailDto;
    }

    protected List<String> stringArrayToStringList(String[] stringArray) {
        if ( stringArray == null ) {
            return null;
        }

        List<String> list = new ArrayList<String>( stringArray.length );
        for ( String string : stringArray ) {
            list.add( string );
        }

        return list;
    }
}
