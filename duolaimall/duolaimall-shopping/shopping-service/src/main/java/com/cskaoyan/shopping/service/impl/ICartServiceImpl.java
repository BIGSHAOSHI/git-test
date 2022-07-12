package com.cskaoyan.shopping.service.impl;

import com.cskaoyan.mall.commons.exception.ExceptionProcessorUtils;
import com.cskaoyan.mall.constant.ShoppingRetCode;
import com.cskaoyan.mall.dto.*;
import com.cskaoyan.shopping.converter.CartItemConverter;
import com.cskaoyan.shopping.dal.entitys.Item;
import com.cskaoyan.shopping.dal.persistence.ItemMapper;
import com.cskaoyan.shopping.dto.*;
import com.cskaoyan.shopping.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

/**
 * @Description: 购物车实现类
 * @author: 秦炯森
 * @date: 2022/7/11 9:22
 * @version: V1.0
 */
@Slf4j
@Service
public class ICartServiceImpl implements ICartService {

    @Autowired
    RedissonClient redissonClient;

    @Override
    public CartListByIdResponse getCartListById(CartListByIdRequest request) {
        //获取目标响应
        CartListByIdResponse response = new CartListByIdResponse();

        try {
            //不做校验
            request.requestCheck();

            //获取redisson中的资源
            RMap<Long, CartProductDto> cart = redissonClient.getMap(request.getUserId() + "");
            //不需要做其他处理了
            ArrayList<CartProductDto> list = new ArrayList<>();
            cart.keySet().forEach(key ->
                    list.add(cart.get(key)));
            response.setCode(ShoppingRetCode.SUCCESS.getCode());
            response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
            response.setCartProductDtos(list);

        } catch (Exception e) {
            e.printStackTrace();
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }

        return response;
    }

    @Override
    public AddCartResponse addToCart(AddCartRequest request) {
        AddCartResponse response = new AddCartResponse();
        try {
//            请求体进行校验
            request.requestCheck();

            //获取redisson中,该用户的购物车商品列表
            RMap<Long, CartProductDto> cart = redissonClient.getMap(request.getUserId() + "");
            //从该列表中找出指定商品id的商品
            CartProductDto cartProductDto = (CartProductDto) cart.get(request.getProductId());

            /*//如果该商品不为空
            if (cartProductDto != null) {
                //刷新数据
                cartProductDto.setProductNum(Long.valueOf(request.getProductNum()));
                //将修改传回redis
                cart.replace(request.getUserId(), cartProductDto);
            } else {*/
            //需要去mysql获取该商品的详情,再写入到redis
            CartProductDto cartProductDtoFromDB = getCartProductDto(request.getProductId());
            //将新增传回redis
            cartProductDtoFromDB.setProductNum(Long.valueOf(request.getProductNum()));
            cartProductDtoFromDB.setChecked("false");
            cart.put(request.getProductId(), cartProductDtoFromDB);
            //}
            response.setCode(ShoppingRetCode.SUCCESS.getCode());
            response.setMsg(ShoppingRetCode.SUCCESS.getMessage());

        } catch (Exception e) {
            log.error("ICartServiceImpl.addToCart Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    @Autowired
    ItemMapper itemMapper;

    //获取单件商品的部分信息, 因为是从mysql获取,转交给redis,也就不再封装为请求.
    // 符合字段需求CartProductDto
    // @Override
    public CartProductDto getCartProductDto(Long ItemId) {
        CartProductDto cartProductDto = new CartProductDto();
        try {
            Item item = itemMapper.selectByPrimaryKey(ItemId);
            cartProductDto = CartItemConverter.item2Dto(item);
        } catch (Exception e) {
            log.error("ICartServiceImpl.getCartProductDto Occur Exception :" + e);
            e.printStackTrace();
        }
        return cartProductDto;
    }


    @Override
    public UpdateCartNumResponse updateCartNum(UpdateCartNumRequest request) {
        UpdateCartNumResponse response = new UpdateCartNumResponse();
        try {
            //请求体进行校验
//            request.requestCheck();

            //获取redisson中,该用户的购物车商品列表
            RMap<Long, CartProductDto> cart = redissonClient.getMap(request.getUserId() + "");
            //从该列表中找出指定商品id的商品
            CartProductDto cartProductDto = (CartProductDto) cart.get(request.getProductId());

            //如果该商品为空,理论上不可能
            if (cartProductDto == null) {
                log.error("ICartServiceImpl.updateCartNum Occur Exception :" + "修改的商品不存在");
                response.setCode(ShoppingRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode());
                response.setMsg("修改的商品不存在");
            }
            //新数据替换旧数据,需要做Integer转Long
            if (request.getProductNum() != null) {
                cartProductDto.setProductNum(Long.valueOf(request.getProductNum()));
            }
            if (request.getChecked() != null) {
                cartProductDto.setChecked(request.getChecked());
            }
            //将修改传回redis
            cart.put(request.getProductId(), cartProductDto);
            response.setCode(ShoppingRetCode.SUCCESS.getCode());
            response.setMsg(ShoppingRetCode.SUCCESS.getMessage());

        } catch (Exception e) {
            log.error("ICartServiceImpl.updateCartNum Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    @Override
    public CheckAllItemResponse checkAllCartItem(CheckAllItemRequest request) {
        return null;
    }

    @Override
    public DeleteCartItemResponse deleteCartItem(DeleteCartItemRequest request) {
        return null;
    }

    @Override
    public DeleteCheckedItemResposne deleteCheckedItem(DeleteCheckedItemRequest request) {
        return null;
    }

    @Override
    public ClearCartItemResponse clearCartItemByUserID(ClearCartItemRequest request) {
        ClearCartItemResponse resposne = new ClearCartItemResponse();

        try {
            request.requestCheck();

            RMap<Long, CartProductDto> cart = redissonClient.getMap(request.getUserId() + "");

            Set<Long> keySet = cart.keySet();

            keySet.forEach(pid -> {
                if ( "true".equals(cart.get(pid).getChecked())){
                    cart.remove(pid);
                }
            });

            resposne.setCode(ShoppingRetCode.SUCCESS.getCode());
            resposne.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionProcessorUtils.wrapperHandlerException(resposne,e);
        }

        return resposne;
    }
}
