package com.cskaoyan.user.converter;

import com.cskaoyan.user.dal.entitys.Member;
import com.cskaoyan.user.dto.UserInfoDto;
import com.cskaoyan.user.dto.UserLoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserConverterMapper {

    @Mappings({
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "username",target = "username"),
            @Mapping(source = "phone",target = "phone"),
            @Mapping(source = "email",target = "email"),
            @Mapping(source = "sex",target = "sex"),
            @Mapping(source = "address",target = "address"),
            @Mapping(source = "file",target = "file"),
            @Mapping(source = "description",target = "description"),
            @Mapping(source = "points",target = "points"),
            @Mapping(source = "balance",target = "balance"),
            @Mapping(source = "state",target = "state")
    })
    UserLoginResponse converter(Member member);

    @Mappings({
            @Mapping(source = "id",target = "uid"),
            @Mapping(source = "username",target = "username")
    })
    UserInfoDto converterToUserInfo(Member user);
}
