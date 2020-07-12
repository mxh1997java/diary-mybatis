package www.maxinhai.com.diarymybatis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import www.maxinhai.com.diarymybatis.config.annotation.LoginRequired;
import www.maxinhai.com.diarymybatis.util.AssertUtils;
import www.maxinhai.com.diarymybatis.util.CookieUtils;
import www.maxinhai.com.diarymybatis.util.EmptyUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Api(tags = "测试接口")
//@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:8529", "http://127.0.0.1:8529"})
@RequestMapping("api/test/")
@RestController
public class TestController extends AbstractController{

    @ApiOperation(value = "删除客户端cookie", notes = "delete cookie", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "HttpServletRequest",name = "request", value = "request",required = true),
            @ApiImplicitParam(dataType = "HttpServletResponse",name = "response", value = "response",required = true)
    })
    @LoginRequired
    @RequestMapping(value = "delCookie", method = RequestMethod.GET)
    public Map<String, Object> delCookie(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, "token");
        CookieUtils.deleteCookie(request, response, "userinfo");
        return getSuccess();
    }


    @ApiOperation(value = "添加客户端cookie", notes = "add cookie", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "HttpServletRequest",name = "request", value = "request",required = true),
            @ApiImplicitParam(dataType = "HttpServletResponse",name = "response", value = "response",required = true)
    })
    @LoginRequired
    @RequestMapping(value = "addCookie", method = RequestMethod.GET)
    public Map<String, Object> addCookie(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.setCookie(request, response, "token", "www.maxinhai.com");
        CookieUtils.setCookie(request, response, "userinfo", "www.maxinhai.com");
        return getSuccess();
    }


    @LoginRequired
    @RequestMapping(value = "concurrencyTest", method = RequestMethod.POST)
    public Map<String, Object> concurrencyTest(@RequestBody Map<String, Object> params) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(params), "params不为空!");
        String name = params.get("name").toString();
        String description = params.get("description").toString();
        return getSuccess(name+description, params);
    }


}
