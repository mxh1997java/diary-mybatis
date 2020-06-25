package www.maxinhai.com.diarymybatis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import www.maxinhai.com.diarymybatis.entity.LoginInfo;
import www.maxinhai.com.diarymybatis.util.BaseResponse;
import www.maxinhai.com.diarymybatis.util.CodeEnum;
import www.maxinhai.com.diarymybatis.util.EmptyUtils;
import www.maxinhai.com.diarymybatis.util.ResponseData;
import java.util.List;
import java.util.Map;

@Api(tags = "用户登录日志管理接口")
@RequestMapping("loginInfo")
@RestController
public class LoginInfoController extends AbstractController {


    @ApiOperation(value = "根据条件获取日记信息集合", notes = "findAllByCondition", httpMethod = "POST")
    @ApiImplicitParam(dataType = "java.util.Map",name = "params",value = "params",required = true)
    @PostMapping(value = "findAllByCondition")
    public Map<String, Object> findAllByCondition(@RequestBody Map<String, Object> params) throws Exception {
        Map<String, Object> result = loginInfoService.findAllByCondition(params);
        if(EmptyUtils.isEmpty(result)) {
            return getFailure();
        }
        return result;
    }

}
