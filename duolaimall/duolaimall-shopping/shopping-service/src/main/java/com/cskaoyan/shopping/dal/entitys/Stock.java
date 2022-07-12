package com.cskaoyan.shopping.dal.entitys;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: BIGSHAOSHI
 * @Date: 2022/7/12 09:27
 * @Description:
 */

@Table(name = "tb_stock")
@Data
public class Stock {

    @Id
    Long item_id;

    Long stock_count;

    Integer lock_count = 0;

    Integer restrict_count = 5;

    Integer sell_id;
}
