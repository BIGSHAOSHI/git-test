package com.cskaoyan.shopping;

import com.cskaoyan.shopping.dal.entitys.Stock;
import com.cskaoyan.shopping.dal.entitys.Item;
import com.cskaoyan.shopping.dal.persistence.ItemMapper;
import com.cskaoyan.shopping.dal.persistence.StockMapper;
import com.cskaoyan.shopping.dto.CartProductDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShoppingTest {

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    RedissonClient redissonClient;

    // 测试数据库
    @Test
    public void testItem() {
        Item item = itemMapper.selectByPrimaryKey(100046401);
    }

//    // 测试服务业务
//    @Autowired
//    IProductService productService;
    public void testService() {

    }

//    @Autowired
//    JavaMailSender javaMailSender;
    // 测试服务的Controller
    @Test
    public void testController() {
        RestTemplate restTemplate = new RestTemplate();
        // 发送http请求测试
        String url = "http://localhost:8888";
        ResponseEntity<Object> forEntity = restTemplate.getForEntity(url, Object.class);
    }

    /**
     * 测试向redis中存入购物车商品对象
     */
    @Test
    public void testPutRedis(){
        RMap<Object, Object> map = redissonClient.getMap("12");
        CartProductDto cartProductDto = new CartProductDto();
        cartProductDto.setProductId(1L);
        cartProductDto.setProductName("pen");
        map.put(1L,cartProductDto);
    }

    /**
     *  测试从redis中取购物车商品对象
     */
    @Test
    public void testGetRedis(){
        RMap<Object, Object> map = redissonClient.getMap("12");
        CartProductDto cartProductDto = (CartProductDto) map.get(1L);
        System.out.println(cartProductDto.toString());
    }

    @Test
    public void addCheckedProducts(){
        RMap<Object, Object> map = redissonClient.getMap("12");
        for (int i = 10; i < 20; i++) {
            CartProductDto cartProductDto = new CartProductDto();
            cartProductDto.setProductId(i+0L);
            cartProductDto.setProductName(i+"");
            cartProductDto.setSalePrice(new BigDecimal(i));
            cartProductDto.setChecked("true");
            map.put(cartProductDto.getProductId(),cartProductDto);
        }

    }

    @Test
    public void testCheckedProducts(){
        RMap<Object, Object> cart = redissonClient.getMap("80");
        cart.get("10051701");

    }

    @Autowired
    StockMapper stockMapper;
    @Test
    public void insertStock() {
        List<Item> itemList = itemMapper.selectAll();
        ArrayList<Stock> stockList = new ArrayList<>();
        itemList.forEach(item -> {
            Stock s = new Stock();
            s.setItem_id(item.getId());
            s.setStock_count(Long.valueOf(item.getNum()));
            stockList.add(s);
        });
        for (Stock stock : stockList) {
            stockMapper.insertSelective(stock);
        }
    }


}
