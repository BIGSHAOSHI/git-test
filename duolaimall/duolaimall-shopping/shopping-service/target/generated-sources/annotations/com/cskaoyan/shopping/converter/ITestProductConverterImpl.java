package com.cskaoyan.shopping.converter;

import com.cskaoyan.shopping.dal.entitys.Item;
import com.cskaoyan.shopping.dto.TestProductDetailDto;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-11T15:11:03+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_333 (Oracle Corporation)"
)
@Component
public class ITestProductConverterImpl implements ITestProductConverter {

    @Override
    public TestProductDetailDto productDoToDto(Item item) {
        if ( item == null ) {
            return null;
        }

        TestProductDetailDto testProductDetailDto = new TestProductDetailDto();

        testProductDetailDto.setImgUrl( item.getImage() );
        testProductDetailDto.setProductName( item.getTitle() );
        testProductDetailDto.setPrice( item.getPrice() );

        return testProductDetailDto;
    }
}
