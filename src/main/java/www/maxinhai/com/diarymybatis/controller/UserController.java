package www.maxinhai.com.diarymybatis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import www.maxinhai.com.diarymybatis.entity.User;
import java.util.List;
import java.util.Map;

@Api(tags = "用户管理相关接口")
@RequestMapping(value = "user")
@RestController
public class UserController extends AbstractController {

    @ApiOperation(value = "获得全部用户信息接口", notes = "", httpMethod = "GET")
    @ApiImplicitParam(dataType = "null",name = "null",value = "null",required = false)
    @GetMapping(value = "getAllUser")
    public Map<String, Object> getAllUser() throws Exception {
        List<User> allUser = userMapper.getAllUser();
        return getSuccess(allUser);
    }

}
