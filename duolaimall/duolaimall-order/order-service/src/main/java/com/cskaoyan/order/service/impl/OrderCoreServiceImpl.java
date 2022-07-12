package com.cskaoyan.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cskaoyan.mall.commons.exception.ExceptionProcessorUtils;
import com.cskaoyan.mall.commons.exception.ProcessException;
import com.cskaoyan.mall.order.constant.OrderRetCode;
import com.cskaoyan.order.biz.TransOutboundInvoker;
import com.cskaoyan.order.biz.context.AbsTransHandlerContext;
import com.cskaoyan.order.biz.factory.OrderProcessPipelineFactory;
import com.cskaoyan.order.constant.OrderConstants;
import com.cskaoyan.order.converter.OrderConverter;
import com.cskaoyan.order.dal.entitys.Order;
import com.cskaoyan.order.dal.entitys.OrderItem;
import com.cskaoyan.order.dal.entitys.OrderShipping;
import com.cskaoyan.order.dal.entitys.Stock;
import com.cskaoyan.order.dal.persistence.OrderItemMapper;
import com.cskaoyan.order.dal.persistence.OrderMapper;
import com.cskaoyan.order.dal.persistence.OrderShippingMapper;
import com.cskaoyan.order.dal.persistence.StockMapper;
import com.cskaoyan.order.dto.*;
import com.cskaoyan.order.dto.liulu.OrderResponse;
import com.cskaoyan.order.dto.zyl.CancelOrDeleteOrderDto;
import com.cskaoyan.order.dto.zyl.QueryOrderDto;
import com.cskaoyan.order.dto.zyl.QueryOrderRequest;
import com.cskaoyan.order.dto.zyl.QueryOrderResponse;
import com.cskaoyan.order.form.OrderPageInfoRequest;
import com.cskaoyan.order.form.PageResponse;
import com.cskaoyan.order.service.OrderCoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OrderCoreServiceImpl implements OrderCoreService {


	@Autowired
    OrderMapper orderMapper;

	@Autowired
    OrderItemMapper orderItemMapper;

	@Autowired
    OrderShippingMapper orderShippingMapper;

	@Autowired
    OrderProcessPipelineFactory orderProcessPipelineFactory;

	@Autowired
    StockMapper stockMapper;
	@Autowired
	OrderConverter orderConverter;


	/**
	 * 创建订单的处理流程
	 *
	 * @param request
	 * @return
	 */
	@Override
	public CreateOrderResponse createOrder(CreateOrderRequest request) {
		CreateOrderResponse response = new CreateOrderResponse();
		try {
			//创建pipeline对象
			TransOutboundInvoker invoker = orderProcessPipelineFactory.build(request);

			//启动pipeline
			invoker.start(); //启动流程（pipeline来处理）

			//获取处理结果
			AbsTransHandlerContext context = invoker.getContext();

			//把处理结果转换为response
			response = (CreateOrderResponse) context.getConvert().convertCtx2Respond(context);



		} catch (Exception e) {
			log.error("OrderCoreServiceImpl.createOrder Occur Exception :" + e);
			ExceptionProcessorUtils.wrapperHandlerException(response, e);
		}
		return response;
	}

	/**
	 * 取消订单
 	 * @param request
	 * @return com.cskaoyan.order.dto.CancelOrderResponse
	 * @author zyl
	 * @since 2022/07/11 14:49
	 */
	@Override
	public CancelOrderResponse cancelOrder(CancelOrderRequest request) {
		CancelOrderResponse response = new CancelOrderResponse();
		try {
			// 参数校验
			request.requestCheck();
			String orderId = request.getOrderId();

			// 业务实现
			// 1. 根据订单id查询商品id和购买数量
			Example example = new Example(Order.class);
			example.createCriteria().andEqualTo("orderId",orderId);
			List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
			if (orderItems == null || orderItems.size() == 0){
				throw new ProcessException(OrderRetCode.DB_EXCEPTION.getCode(),OrderRetCode.DB_EXCEPTION.getMessage());
			}
			// 2. 根据商品id和购买数量修改库存锁定
			updateStock(orderItems);
			// 3. 根据订单id修改库存锁定状态和订单更新时间
			OrderItem orderItem = new OrderItem();
			orderItem.setStatus(OrderConstants.ORDER_STATUS_TRANSACTION_RELEASE);
			orderItem.setUpdateTime(new Date());
			System.out.println(new Date());
			orderItemMapper.updateByExampleSelective(orderItem,example);
			// 4. 订单状态修改为交易关闭，交易关闭时间修改为当前时间，订单更新时间修改为当前时间
			Order order = new Order();
			order.setStatus(OrderConstants.ORDER_STATUS_TRANSACTION_CLOSE);
			order.setUpdateTime(new Date());
			order.setCloseTime(new Date());
			orderMapper.updateByExampleSelective(order,example);
			// 封装响应数据
			CancelOrDeleteOrderDto cancelOrderDto = new CancelOrDeleteOrderDto();
			cancelOrderDto.setCode(OrderRetCode.SUCCESS.getCode());
			cancelOrderDto.setMsg(OrderRetCode.SUCCESS.getMessage());
			response.setCode(OrderRetCode.SUCCESS.getCode());
			response.setMsg(OrderRetCode.SUCCESS.getMessage());
			response.setCancelOrderDto(cancelOrderDto);
		}catch (Exception e){
			e.printStackTrace();
			ExceptionProcessorUtils.wrapperHandlerException(response, e);
		}

		return response;
	}

	/**
	 * 删除订单
 	 * @param request
	 * @return com.cskaoyan.order.dto.DeleteOrderResponse
	 * @author zyl
	 * @since 2022/07/11 17:51
	 */
	@Override
	public DeleteOrderResponse deleteOrder(DeleteOrderRequest request) {
		DeleteOrderResponse response = new DeleteOrderResponse();
		try {
			// 参数校验
			request.requestCheck();
			String orderId = request.getOrderId();

			Example example = new Example(OrderItem.class);
			example.createCriteria().andEqualTo("orderId",orderId);

			// 如果是未付款订单，则删除订单前先释放库存
			Order order = orderMapper.selectByPrimaryKey(orderId);
			if (order.getStatus() == 0){
				List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
				updateStock(orderItems);
			}
			// 删除订单
			orderMapper.deleteByPrimaryKey(orderId);
			orderItemMapper.deleteByExample(example);

			// 封装响应数据
			CancelOrDeleteOrderDto deleteOrderDto = new CancelOrDeleteOrderDto();
			deleteOrderDto.setCode(OrderRetCode.SUCCESS.getCode());
			deleteOrderDto.setMsg(OrderRetCode.SUCCESS.getMessage());
			response.setCode(OrderRetCode.SUCCESS.getCode());
			response.setMsg(OrderRetCode.SUCCESS.getMessage());
			response.setDeleteOrderDto(deleteOrderDto);

		} catch (Exception e) {
			e.printStackTrace();
			ExceptionProcessorUtils.wrapperHandlerException(response, e);
		}
		return response;
	}

	/**
	 * 释放库存
 	 * @param orderItems
	 * @return void
	 * @author zyl
	 * @since 2022/07/11 18:12
	 */
	private void updateStock(List<OrderItem> orderItems) {
		for (OrderItem orderItem : orderItems) {
			Long itemId = orderItem.getItemId();
			Stock originalStockInfo = stockMapper.selectByPrimaryKey(itemId);
			if (originalStockInfo == null) {
				throw new ProcessException(OrderRetCode.DB_EXCEPTION.getCode(), OrderRetCode.DB_EXCEPTION.getMessage());
			}
			Stock stock = new Stock();
			stock.setStockCount(originalStockInfo.getStockCount() + orderItem.getNum());
			stock.setLockCount(originalStockInfo.getLockCount() - orderItem.getNum());
			Example stockExample = new Example(Stock.class);
			stockExample.createCriteria().andEqualTo("itemId", itemId);
			stockMapper.updateByExampleSelective(stock, stockExample);
		}
	}

	/**
	* @Author: liulu
	* @Description: 展示全部订单
	* @DateTime: 2022/7/9 9:44
	* @Params:
	* @Return
	*/
	@Override
	public PageResponse displayAllOrder(OrderPageInfoRequest request, HttpServletRequest servletrequest) {
		//获取用户id
		String user_info = servletrequest.getHeader("user_info");
		JSONObject jsonObject = JSON.parseObject(user_info);
		long uid = Long.parseLong(jsonObject.get("uid").toString());
		//业务代码
		Example example = new Example(Order.class);
		example.createCriteria().andEqualTo("userId",uid);
		List<Order> orderList = orderMapper.selectByExample(example);
		OrderDetailResponse response = new OrderDetailResponse();
		List<OrderDetailResponse> responseList = new ArrayList<>();
		for (Order order : orderList) {
			String orderId = order.getOrderId();
			List<OrderItemDto> orderItemDto = orderItemMapper.selectOrderItemDto(orderId);
			OrderShippingDto orderShippingDto = orderShippingMapper.selectOrderShippingDto(orderId);
					response = orderConverter.order2res(order);
					response.setOrderItemDto(orderItemDto);
					response.setOrderShippingDto(orderShippingDto);
					responseList.add(response);
		}
		PageResponse response1 = new PageResponse();
		response1.setData(responseList);
		PageInfo<Order> pageInfo = new PageInfo(orderList);
		response1.setTotal(pageInfo.getTotal());
		//分页
		PageHelper.startPage(request.getPage(),request.getSize());
		return response1;
	}
	/**
	* @Author: liulu
	* @Description: 获取用户地址
	* @DateTime: 2022/7/9 11:39
	* @Params:
	* @Return
	*/
	@Override
	public List<OrderAddressReponse> selectAllAddress(HttpServletRequest servletRequest) {
		//获取用户id
		String user_info = servletRequest.getHeader("user_info");
		JSONObject jsonObject = JSON.parseObject(user_info);
		long uid = Long.parseLong(jsonObject.get("uid").toString());
		List<OrderAddressReponse> reponseList = orderMapper.selectAllAddress(uid);
		return reponseList;
	}
	/**
	 * @Author: 刘露
	 * @Description: 添加用户地址
	 * @DateTime: 2022/7/9 13:53
	 * @Params:
	 * @Return
	 */
	@Override
	public void insertUserAddress(OrderAddressRequest request,HttpServletRequest servletRequest) {
		String user_info = servletRequest.getHeader("user_info");
		JSONObject jsonObject = JSON.parseObject(user_info);
		long uid = Long.parseLong(jsonObject.get("uid").toString());
		orderMapper.insertOrderAddress(request,uid);
	}

	/**
	 * 查询订单
 	 * @param request
	 * @return com.cskaoyan.order.dto.zyl.QueryOrderResponse
	 * @author zyl
	 * @since 2022/07/11 12:05
	 */
	@Override
	public QueryOrderResponse queryOrder(QueryOrderRequest request) {
		QueryOrderResponse queryOrderResponse = new QueryOrderResponse();
		try {
			// 参数校验
			request.requestCheck();
			String orderId = request.getId();
			OrderShipping orderShipping = orderShippingMapper.selectByPrimaryKey(orderId);
			Order order = orderMapper.selectByPrimaryKey(orderId);
			Example example = new Example(OrderItem.class);
			example.createCriteria().andEqualTo("orderId",orderId);
			List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
			QueryOrderDto queryOrderDto = orderConverter.order2QueryOrderDto(order, orderItems, orderShipping);
			queryOrderResponse.setCode(OrderRetCode.SUCCESS.getCode());
			queryOrderResponse.setMsg(OrderRetCode.SUCCESS.getMessage());
			queryOrderResponse.setQueryOrderDto(queryOrderDto);
		}catch (Exception e){
			e.printStackTrace();
			ExceptionProcessorUtils.wrapperHandlerException(queryOrderResponse, e);
		}
		return queryOrderResponse;
	}
}
