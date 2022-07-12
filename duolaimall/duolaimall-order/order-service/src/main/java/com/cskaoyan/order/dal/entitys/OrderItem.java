package com.cskaoyan.order.dal.entitys;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;


@Table(name = "tb_order_item")

@Data

public class OrderItem {

    @Id
    private String id;



    /**

     * 商品id

     */

    @Column(name = "item_id")

    private Long itemId;



    /**

     * 订单id

     */

    @Column(name = "order_id")

    private String orderId;



    /**

     * 商品购买数量

     */

    private Integer num;



    /**

     * 商品标题

     */

    private String title;



    /**

     * 商品单价

     */

    private Double price;



    /**

     * 商品总金额

     */

    @Column(name = "total_fee")

    private Double totalFee;



    /**

     * 商品图片地址

     */

    @Column(name = "pic_path")

    private String picPath;

    /**
     * 库存锁定状态 1库存已锁定 2库存已释放 3-库存减扣成功
     */
    private Integer status;

    @Column(name = "create_time")
    @ColumnType(jdbcType = JdbcType.TIMESTAMP)
//    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Timestamp create;

    @Column(name = "update_time")
    private Date updateTime;

}
