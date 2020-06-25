package www.maxinhai.com.diarymybatis.controller;

import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import www.maxinhai.com.diarymybatis.entity.LoginInfo;
import www.maxinhai.com.diarymybatis.entity.User;
import www.maxinhai.com.diarymybatis.util.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Api(tags = "用户管理相关接口", value = "用户管理相关接口")
@RequestMapping(value = "user")
@RestController
public class UserController extends AbstractController {

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
    @GetMapping(value = "login")
    public BaseResponse login(@Param(value = "username") String username, @Param(value = "password") String password,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(username), "必要参数缺失!");
        AssertUtils.assertTrue(EmptyUtils.isEmpty(password), "必要参数缺失!");
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        User user = userService.findOneByCondition(params);
        AssertUtils.assertTrue(EmptyUtils.isEmpty(user), "账户不存在!");
        String token = UUID.randomUUID().toString();
        redisUtils.setRedisTemplate(redisTemplate);
        redisUtils.set("USER_SESSION_KEY:" + token, user);
        redisUtils.expire("USER_SESSION_KEY:" + token, 60 * 60);
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUser_id(user.getUser_id());
        Date nowTime = new Date();
        loginInfo.setCreateTime(nowTime);
        loginInfo.setDescription(user.getUsername() + "于" + DateUtils.getFormatDateTime(nowTime) + "登陆账户!");
        loginInfoService.addLoginInfo(loginInfo);
        Cookie cookie = new Cookie("token", token);
        response.addCookie(cookie);
        ResponseData<Object> responseData = ResponseData.out(CodeEnum.SUCCESS, null);
        return responseData;
    }


    @ApiOperation(value = "删除用户信息", notes = "removeUser", httpMethod = "POST")
    @ApiImplicitParam(dataType = "Long",name = "id", value = "id",required = true)
    @PostMapping(value = "removeUser")
    public BaseResponse removeUser(@RequestParam("id") Long id) throws Exception {
        int result = userService.delUser(id);
        if(result == 1) {
            return ResponseData.out(CodeEnum.SUCCESS, null);
        }
        return ResponseData.out(CodeEnum.FAIL, null);
    }



    @ApiOperation(value = "修改用户信息", notes = "modifyUser", httpMethod = "POST")
    @ApiImplicitParam(dataType = "User",name = "user", value = "user",required = true)
    @PostMapping(value = "modifyUser")
    public BaseResponse modifyUser(@RequestBody User user) throws Exception {
        int result = userService.modifyUser(user);
        if(result == 1) {
            return ResponseData.out(CodeEnum.SUCCESS, null);
        }
        return ResponseData.out(CodeEnum.FAIL, null);
    }

}
