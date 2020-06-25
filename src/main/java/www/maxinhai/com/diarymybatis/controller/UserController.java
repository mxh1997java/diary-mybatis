package www.maxinhai.com.diarymybatis.controller;

import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import www.maxinhai.com.diarymybatis.entity.User;
import www.maxinhai.com.diarymybatis.util.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Api(tags = "用户管理相关接口")
@RequestMapping(value = "user")
@RestController
public class UserController extends AbstractController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "获得全部用户信息接口", notes = "", httpMethod = "GET")
    @ApiImplicitParam(dataType = "null",name = "null",value = "null",required = false)
    @GetMapping(value = "getAllUser")
    public Map<String, Object> getAllUser() throws Exception {
        List<User> allUser = userMapper.getAllUser();
        return getSuccess(allUser);
    }

    @ApiOperation(value = "用户注册接口", notes = "", httpMethod = "POST")
    @ApiImplicitParam(dataType = "User",name = "user", value = "user",required = true)
    @PostMapping(value = "registered")
    public BaseResponse registered(@RequestBody User user) throws Exception {
        int result = userService.addUser(user);
        if(result == 1) {
            return BaseResponse.out(CodeEnum.SUCCESS);
        }
        return BaseResponse.out(CodeEnum.FAIL);
    }


    @GetMapping(value = "login")
    public BaseResponse login(@Param(value = "username") String username, @Param(value = "password") String password,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(username), "必要参数缺失!");
        AssertUtils.assertTrue(EmptyUtils.isEmpty(password), "必要参数缺失!");
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        User user = userService.findOneByCondition(params);
        String token = UUID.randomUUID().toString();
        redisUtils.setRedisTemplate(redisTemplate);
        redisUtils.set("USER_SESSION_KEY:" + token, user);
        redisUtils.expire("USER_SESSION_KEY:" + token, 60 * 60);
        Cookie cookie = new Cookie("token", token);
        response.addCookie(cookie);
        ResponseData<Object> responseData = ResponseData.out(CodeEnum.SUCCESS, null);
        return responseData;
    }

}
