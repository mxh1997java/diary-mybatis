package www.maxinhai.com.diarymybatis.controller;

import com.alibaba.fastjson.JSON;
import io.lettuce.core.RedisConnectionException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import www.maxinhai.com.diarymybatis.config.annotation.LoginRequired;
import www.maxinhai.com.diarymybatis.entity.LoginInfo;
import www.maxinhai.com.diarymybatis.entity.User;
import www.maxinhai.com.diarymybatis.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Api(tags = "用户管理相关接口", value = "用户管理相关接口")
@RequestMapping(value = "api/user/")
@RestController
public class UserController extends AbstractController {

    @Value("${redis_user_key}")
    private String redis_user_key;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "根据条件获取用户信息", notes = "findAllByCondition", httpMethod = "POST")
    @ApiImplicitParam(dataType = "java.util.Map",name = "params",value = "params",required = true)
    @PostMapping(value = "findAllByCondition")
    public Map<String, Object> findAllByCondition(@RequestBody Map<String, Object> params) throws Exception {
        Map<String, Object> result = userService.findAllByCondition(params);
        if(EmptyUtils.isEmpty(result)) {
            return getFailure();
        }
        return result;
    }

    @ApiOperation(value = "用户注册接口", notes = "registered", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",name = "username", value = "username",required = true),
            @ApiImplicitParam(dataType = "String",name = "password", value = "password",required = true)
    })
    @LoginRequired
    @PostMapping(value = "registered")
    public BaseResponse registered(@RequestBody User user) throws Exception {
        int result = userService.addUser(user);
        if(result == 1) {
            return BaseResponse.out(CodeEnum.SUCCESS);
        }
        return BaseResponse.out(CodeEnum.FAIL);
    }


    @ApiOperation(value = "用户登陆接口", notes = "login", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",name = "username", value = "username",required = true),
            @ApiImplicitParam(dataType = "String",name = "password", value = "password",required = true)
    })
    @Transactional
    @LoginRequired
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseResponse login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(user.getUsername()), "必要参数缺失!");
        AssertUtils.assertTrue(EmptyUtils.isEmpty(user.getPassword()), "必要参数缺失!");
        Map<String, Object> params = new HashMap<>();
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());
        User findResult = userService.findOneByCondition(params);
        AssertUtils.assertTrue(EmptyUtils.isEmpty(findResult), "账户不存在!");
        //添加session会话
        String token = null;
        try {
            token = UUID.randomUUID().toString();
            System.out.println("token:" + token);
            redisUtils.setRedisTemplate(redisTemplate);
            redisUtils.set(redis_user_key + ":" + token, findResult);
            redisUtils.expire("USER_SESSION_KEY:" + token, 60 * 60);
        } catch (RedisConnectionException e) {
            AssertUtils.assertTrue(true, "redis连接异常!请联系管理员!");
        }
        //添加登录信息
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUser_id(findResult.getUser_id());
        Date nowTime = new Date();
        loginInfo.setCreateTime(nowTime);
        loginInfo.setDescription(user.getUsername() + "于" + DateUtils.getFormatDateTime(nowTime) + "登陆账户!");
        loginInfoService.addLoginInfo(loginInfo);
        //添加客户端cookie
        CookieUtils.setCookie(request, response, "token", token);
        String encodeCookie = URLEncoder.encode(JSON.toJSONString(findResult), "utf-8");
        CookieUtils.setCookie(request, response, "userinfo", encodeCookie);
        ResponseData<Object> responseData = ResponseData.out(CodeEnum.SUCCESS, null);
        return responseData;
    }



    @ApiOperation(value = "用户退出登录", notes = "loginOut", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "HttpServletRequest",name = "request", value = "request",required = true),
            @ApiImplicitParam(dataType = "HttpServletResponse",name = "response", value = "response",required = true)
    })
    @PostMapping(value = "loginOut")
    public BaseResponse loginOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //删除redis会话
        String token = request.getHeader("token");
        redisUtils.setRedisTemplate(redisTemplate);
        redisUtils.del(redis_user_key + ":" + token);
        //清除客户端cookie
        CookieUtils.deleteCookie(request, response, "token");
        CookieUtils.deleteCookie(request, response, "userinfo");
        return ResponseData.out(CodeEnum.SUCCESS, null);
    }




    @ApiOperation(value = "删除用户信息", notes = "removeUser", httpMethod = "POST")
    @ApiImplicitParam(dataType = "Long",name = "id", value = "id",required = true)
    @PostMapping(value = "removeUser/{id}")
    public BaseResponse removeUser(@PathVariable("id") Long id) throws Exception {
        int result = userService.delUser(id);
        if(result == 1) {
            return ResponseData.out(CodeEnum.SUCCESS, null);
        }
        return ResponseData.out(CodeEnum.FAIL, null);
    }



    @ApiOperation(value = "修改用户信息", notes = "modifyUser", httpMethod = "POST")
    @ApiImplicitParam(dataType = "User",name = "user", value = "user",required = true)
    @PostMapping(value = "modifyUser")
    public BaseResponse modifyUser(@RequestBody Map<String, Object> params) throws Exception {
        int result = userService.modifyUser(params);
        if(result == 1) {
            return ResponseData.out(CodeEnum.SUCCESS, null);
        }
        return ResponseData.out(CodeEnum.FAIL, null);
    }

}
