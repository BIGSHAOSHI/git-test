package com.cskaoyan.user.converter;

import com.cskaoyan.user.dal.entitys.Member;
import com.cskaoyan.user.dto.QueryMemberResponse;
import com.cskaoyan.user.dto.UpdateMemberRequest;
import java.math.BigDecimal;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-11T15:11:10+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_333 (Oracle Corporation)"
)
@Component
public class MemberConverterImpl implements MemberConverter {

    @Override
    public QueryMemberResponse member2Res(Member member) {
        if ( member == null ) {
            return null;
        }

        QueryMemberResponse queryMemberResponse = new QueryMemberResponse();

        queryMemberResponse.setId( member.getId() );
        queryMemberResponse.setUsername( member.getUsername() );
        queryMemberResponse.setPassword( member.getPassword() );
        queryMemberResponse.setPhone( member.getPhone() );
        queryMemberResponse.setEmail( member.getEmail() );
        queryMemberResponse.setCreated( member.getCreated() );
        queryMemberResponse.setUpdated( member.getUpdated() );
        queryMemberResponse.setSex( member.getSex() );
        queryMemberResponse.setAddress( member.getAddress() );
        queryMemberResponse.setState( member.getState() );
        queryMemberResponse.setFile( member.getFile() );
        queryMemberResponse.setDescription( member.getDescription() );
        queryMemberResponse.setPoints( member.getPoints() );
        if ( member.getBalance() != null ) {
            queryMemberResponse.setBalance( BigDecimal.valueOf( member.getBalance() ) );
        }

        return queryMemberResponse;
    }

    @Override
    public Member updateReq2Member(UpdateMemberRequest request) {
        if ( request == null ) {
            return null;
        }

        Member member = new Member();

        member.setId( request.getId() );
        member.setPhone( request.getPhone() );
        member.setEmail( request.getEmail() );
        member.setSex( request.getSex() );
        member.setAddress( request.getAddress() );
        member.setFile( request.getFile() );
        member.setDescription( request.getDescription() );
        member.setPoints( request.getPoints() );
        if ( request.getBalance() != null ) {
            member.setBalance( request.getBalance().doubleValue() );
        }

        return member;
    }
}
