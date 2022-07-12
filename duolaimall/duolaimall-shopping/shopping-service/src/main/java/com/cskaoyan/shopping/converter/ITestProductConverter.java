package com.cskaoyan.shopping.converter;

import com.cskaoyan.shopping.dal.entitys.Item;
import com.cskaoyan.shopping.dto.TestProductDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @auther cskaoyan
 * @date 2022/7/8:11:27
 */
@Mapper(componentModel = "spring")
public interface ITestProductConverter {

    @Mappings(
            {
                    @Mapping(source = "title", target = "productName"),
                    @Mapping(source = "image", target = "imgUrl")
            }
    )
    TestProductDetailDto productDoToDto(Item item);
}
