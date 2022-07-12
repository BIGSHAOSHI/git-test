package com.cskaoyan.user.converter;

import com.cskaoyan.user.dal.entitys.Address;
import com.cskaoyan.user.dto.AddAddressRequest;
import com.cskaoyan.user.dto.AddressDto;
import com.cskaoyan.user.dto.UpdateAddressRequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-11T15:11:10+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_333 (Oracle Corporation)"
)
@Component
public class AddressConverterImpl implements AddressConverter {

    @Override
    public AddressDto address2List(Address addresses) {
        if ( addresses == null ) {
            return null;
        }

        AddressDto addressDto = new AddressDto();

        addressDto.setAddressId( addresses.getAddressId() );
        addressDto.setUserId( addresses.getUserId() );
        addressDto.setUserName( addresses.getUserName() );
        addressDto.setTel( addresses.getTel() );
        addressDto.setStreetName( addresses.getStreetName() );
        addressDto.setIsDefault( addresses.getIsDefault() );

        return addressDto;
    }

    @Override
    public List<AddressDto> address2List(List<Address> addresses) {
        if ( addresses == null ) {
            return null;
        }

        List<AddressDto> list = new ArrayList<AddressDto>( addresses.size() );
        for ( Address address : addresses ) {
            list.add( address2List( address ) );
        }

        return list;
    }

    @Override
    public Address req2Address(AddAddressRequest request) {
        if ( request == null ) {
            return null;
        }

        Address address = new Address();

        address.setUserId( request.getUserId() );
        address.setUserName( request.getUserName() );
        address.setTel( request.getTel() );
        address.setStreetName( request.getStreetName() );
        address.setIsDefault( request.getIsDefault() );

        return address;
    }

    @Override
    public Address req2Address(UpdateAddressRequest request) {
        if ( request == null ) {
            return null;
        }

        Address address = new Address();

        address.setAddressId( request.getAddressId() );
        address.setUserId( request.getUserId() );
        address.setUserName( request.getUserName() );
        address.setTel( request.getTel() );
        address.setStreetName( request.getStreetName() );
        address.setIsDefault( request.getIsDefault() );

        return address;
    }
}
