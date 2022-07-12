package com.cskaoyan.user.converter;

import com.cskaoyan.user.dal.entitys.Member;
import com.cskaoyan.user.dto.UserInfoDto;
import com.cskaoyan.user.dto.UserLoginResponse;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-11T15:11:10+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_333 (Oracle Corporation)"
)
@Component
public class UserConverterMapperImpl implements UserConverterMapper {

    @Override
    public UserLoginResponse converter(Member member) {
        if ( member == null ) {
            return null;
        }

        UserLoginResponse userLoginResponse = new UserLoginResponse();

        userLoginResponse.setAddress( member.getAddress() );
        userLoginResponse.setFile( member.getFile() );
        if ( member.getBalance() != null ) {
            userLoginResponse.setBalance( member.getBalance().longValue() );
        }
        userLoginResponse.setPhone( member.getPhone() );
        userLoginResponse.setSex( member.getSex() );
        userLoginResponse.setDescription( member.getDescription() );
        userLoginResponse.setId( member.getId() );
        if ( member.getState() != null ) {
            userLoginResponse.setState( member.getState() );
        }
        userLoginResponse.setEmail( member.getEmail() );
        userLoginResponse.setUsername( member.getUsername() );
        userLoginResponse.setPoints( member.getPoints() );

        return userLoginResponse;
    }

    @Override
    public UserInfoDto converterToUserInfo(Member user) {
        if ( user == null ) {
            return null;
        }

        UserInfoDto userInfoDto = new UserInfoDto();

        if ( user.getId() != null ) {
            userInfoDto.setUid( user.getId().intValue() );
        }
        userInfoDto.setUsername( user.getUsername() );

        return userInfoDto;
    }
}
