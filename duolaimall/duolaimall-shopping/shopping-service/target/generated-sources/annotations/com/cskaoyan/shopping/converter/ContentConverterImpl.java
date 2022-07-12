package com.cskaoyan.shopping.converter;

import com.cskaoyan.shopping.dal.entitys.Panel;
import com.cskaoyan.shopping.dal.entitys.PanelContent;
import com.cskaoyan.shopping.dal.entitys.PanelContentItem;
import com.cskaoyan.shopping.dto.PanelContentDto;
import com.cskaoyan.shopping.dto.PanelContentItemDto;
import com.cskaoyan.shopping.dto.PanelDto;
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
public class ContentConverterImpl implements ContentConverter {

    @Override
    public PanelContentDto panelContent2Dto(PanelContent panelContent) {
        if ( panelContent == null ) {
            return null;
        }

        PanelContentDto panelContentDto = new PanelContentDto();

        panelContentDto.setId( panelContent.getId() );
        panelContentDto.setPanelId( panelContent.getPanelId() );
        panelContentDto.setType( panelContent.getType() );
        panelContentDto.setProductId( panelContent.getProductId() );
        panelContentDto.setSortOrder( panelContent.getSortOrder() );
        panelContentDto.setFullUrl( panelContent.getFullUrl() );
        panelContentDto.setPicUrl( panelContent.getPicUrl() );
        panelContentDto.setPicUrl2( panelContent.getPicUrl2() );
        panelContentDto.setPicUrl3( panelContent.getPicUrl3() );

        return panelContentDto;
    }

    @Override
    public PanelContentDto panelContentItem2Dto(PanelContentItem panelContentItem) {
        if ( panelContentItem == null ) {
            return null;
        }

        PanelContentDto panelContentDto = new PanelContentDto();

        panelContentDto.setId( panelContentItem.getId() );
        panelContentDto.setPanelId( panelContentItem.getPanelId() );
        panelContentDto.setType( panelContentItem.getType() );
        panelContentDto.setProductId( panelContentItem.getProductId() );
        panelContentDto.setSortOrder( panelContentItem.getSortOrder() );
        panelContentDto.setFullUrl( panelContentItem.getFullUrl() );
        panelContentDto.setPicUrl( panelContentItem.getPicUrl() );
        panelContentDto.setPicUrl2( panelContentItem.getPicUrl2() );
        panelContentDto.setPicUrl3( panelContentItem.getPicUrl3() );

        return panelContentDto;
    }

    @Override
    public PanelDto panel2Dto(Panel panel) {
        if ( panel == null ) {
            return null;
        }

        PanelDto panelDto = new PanelDto();

        panelDto.setId( panel.getId() );
        panelDto.setName( panel.getName() );
        panelDto.setType( panel.getType() );
        panelDto.setSortOrder( panel.getSortOrder() );
        panelDto.setPosition( panel.getPosition() );
        panelDto.setLimitNum( panel.getLimitNum() );
        panelDto.setStatus( panel.getStatus() );
        panelDto.setRemark( panel.getRemark() );
        panelDto.setPanelContentItems( panelContentItem2Dto( panel.getPanelContentItems() ) );

        return panelDto;
    }

    @Override
    public List<PanelContentDto> panelContents2Dto(List<PanelContent> panelContents) {
        if ( panelContents == null ) {
            return null;
        }

        List<PanelContentDto> list = new ArrayList<PanelContentDto>( panelContents.size() );
        for ( PanelContent panelContent : panelContents ) {
            list.add( panelContent2Dto( panelContent ) );
        }

        return list;
    }

    @Override
    public List<PanelContentItemDto> panelContentItem2Dto(List<PanelContentItem> panelContentItems) {
        if ( panelContentItems == null ) {
            return null;
        }

        List<PanelContentItemDto> list = new ArrayList<PanelContentItemDto>( panelContentItems.size() );
        for ( PanelContentItem panelContentItem : panelContentItems ) {
            list.add( panelContentItemToPanelContentItemDto( panelContentItem ) );
        }

        return list;
    }

    protected PanelContentItemDto panelContentItemToPanelContentItemDto(PanelContentItem panelContentItem) {
        if ( panelContentItem == null ) {
            return null;
        }

        PanelContentItemDto panelContentItemDto = new PanelContentItemDto();

        panelContentItemDto.setId( panelContentItem.getId() );
        panelContentItemDto.setPanelId( panelContentItem.getPanelId() );
        panelContentItemDto.setType( panelContentItem.getType() );
        panelContentItemDto.setProductId( panelContentItem.getProductId() );
        panelContentItemDto.setSortOrder( panelContentItem.getSortOrder() );
        panelContentItemDto.setFullUrl( panelContentItem.getFullUrl() );
        panelContentItemDto.setPicUrl( panelContentItem.getPicUrl() );
        panelContentItemDto.setPicUrl2( panelContentItem.getPicUrl2() );
        panelContentItemDto.setPicUrl3( panelContentItem.getPicUrl3() );
        panelContentItemDto.setCreated( panelContentItem.getCreated() );
        panelContentItemDto.setUpdated( panelContentItem.getUpdated() );
        panelContentItemDto.setProductName( panelContentItem.getProductName() );
        panelContentItemDto.setSalePrice( panelContentItem.getSalePrice() );
        panelContentItemDto.setSubTitle( panelContentItem.getSubTitle() );

        return panelContentItemDto;
    }
}
