package com.cskaoyan.shopping.dal.persistence;


import com.cskaoyan.shopping.dal.entitys.Item;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ItemMapper extends Mapper<Item> {

    /*
          cid: 使用前端传递的cid值(通常为null)
          orderCol：表示排序字段的名称，如果指定按照价格搜索，即前端的sort字段有值，则排序字段为price，
                    否则，可以在代码里指定默认的排序字段，比如created
          orderDir: 表示对orderCol字段的排序方式，即升序或者降序
          priceGt：价格区间上限

     */
    List<Item> selectItemFront(@Param("cid") Long cid,
                               @Param("orderCol") String orderCol, @Param("orderDir") String orderDir,
                               @Param("priceGt") Integer priceGt, @Param("priceLte") Integer priceLte);
}