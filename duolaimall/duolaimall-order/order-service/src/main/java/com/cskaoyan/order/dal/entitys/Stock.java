package com.cskaoyan.order.dal.entitys;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_stock")
public class Stock {
	//商品id
	@Id
	@Column(name = "item_id")
	private Long itemId;

	//库存可用数量
	@Column(name = "stock_count")
	private Long stockCount;

	//锁定数量
	@Column(name = "lock_count")
	private Integer lockCount;

	//限购数量
	@Column(name = "restrict_count")
	private Integer restrictCount;

	//售卖ID(放在不同区域售卖的id)
	@Column(name = "sell_id")
	private Integer sellId;
}