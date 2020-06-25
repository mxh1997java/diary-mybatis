package www.maxinhai.com.diarymybatis.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import www.maxinhai.com.diarymybatis.entity.LoginInfo;
import www.maxinhai.com.diarymybatis.service.AbstractService;
import www.maxinhai.com.diarymybatis.service.LoginInfoServce;
import www.maxinhai.com.diarymybatis.util.AssertUtils;
import www.maxinhai.com.diarymybatis.util.EmptyUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginInfoServiceImpl extends AbstractService implements LoginInfoServce {

    @Override
    public int addLoginInfo(LoginInfo info) {
        return loginInfoMapper.addLoginInfo(info);
    }

    @Override
    public Map<String, Object> findAllByCondition(Map<String, Object> params) throws Exception {
        AssertUtils.assertTrue(EmptyUtils.isEmpty(params.get("pageNum")), "pageNum不为空!");
        AssertUtils.assertTrue(EmptyUtils.isEmpty(params.get("pageSize")), "pageSize不为空!");
        Integer pageNum = Integer.valueOf(String.valueOf(params.get("pageNum")));
        Integer pageSize = Integer.valueOf(String.valueOf(params.get("pageSize")));
        if(pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if(pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<LoginInfo> loginInfoList = loginInfoMapper.findAllByCondition(params);
        PageInfo<LoginInfo> pageInfo = new PageInfo<>(loginInfoList);
        Map<String, Object> result = new HashMap<>();
        result.put("message", "success");
        result.put("code", 200);
        result.put("data", pageInfo.getList());
        result.put("total", pageInfo.getTotal());
        return result;
    }
}
