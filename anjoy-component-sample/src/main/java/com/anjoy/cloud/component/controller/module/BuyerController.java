package com.anjoy.cloud.component.controller.module;


import com.anjoy.cloud.component.controller.base.BaseController;
import com.anjoy.cloud.component.entity.Seller;
import com.anjoy.cloud.component.result.JsonResult;
import com.anjoy.cloud.component.result.JsonResultCode;
import com.anjoy.cloud.component.service.BuyerService;
import com.anjoy.cloud.component.service.SellerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 买家信息表.(俗称小B) 前端控制器
 * </p>
 *
 * @author 
 * @since 2018-08-15
 */
@RestController
@RequestMapping("/buyer")
public class BuyerController extends BaseController {


    private static final Logger logger = LoggerFactory.getLogger(BuyerController.class);

    @Autowired
    BuyerService buyerService;

    @Autowired
    SellerService sellerService;


    /**
     * 测试接口
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "查询买家列表", tags = "查询买家列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "买家名称", paramType = "query", dataType = "String", required = true)
    })
    @RequestMapping(value = "/getBuyerList", method = { RequestMethod.GET })
    public JsonResult test(HttpServletRequest request, HttpServletResponse response) {
        String account = this.getNotNull("name", request);

        List<Seller> ls = sellerService.list(new QueryWrapper<Seller>()
                .eq("seller_name", "必火酒水饮料批发")
                .or()
                .eq("seller_name", "清真牛羊肉"));


        logger.info("SellerController.test.account:" + ls);
        return new JsonResult(JsonResultCode.SUCCESS, account, ls);
    }

    /**
     * 测试接口
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "查询买家列表分页", tags = "查询买家列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "买家名称", paramType = "query", dataType = "String", required = true)
    })
    @RequestMapping(value = "/getBuyerListAll", method = { RequestMethod.GET })
    public JsonResult testall(HttpServletRequest request, HttpServletResponse response) {
        String account = this.getNotNull("name", request);

        IPage page = sellerService.page(new Page<Seller>(),new QueryWrapper<Seller>()
                .like("seller_name","发"));

        return new JsonResult(JsonResultCode.SUCCESS, account, page);
    }

    /**
     * 测试接口
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "更新买家信息", tags = "更新买家信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "买家id", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "name", value = "买家名称", paramType = "query", dataType = "String", required = true)
    })
    @RequestMapping(value = "/updateBuyerInfo", method = { RequestMethod.POST })
    public JsonResult updateinfo(HttpServletRequest request, HttpServletResponse response) {
        String account = this.getNotNull("name", request);
        String id = this.getNotNull("id", request);

        try {
            Seller seller = sellerService.getOne(new QueryWrapper<Seller>().eq("seller_id",id));
            if (seller != null){
                seller.setSellerName(account);
                sellerService.update(seller,new UpdateWrapper<Seller>().eq("seller_id",id));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(JsonResultCode.FAILURE, account, "");

        }


        return new JsonResult(JsonResultCode.SUCCESS, account, "");
    }


}

