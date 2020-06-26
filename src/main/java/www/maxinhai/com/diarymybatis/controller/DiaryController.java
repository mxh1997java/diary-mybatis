package www.maxinhai.com.diarymybatis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import www.maxinhai.com.diarymybatis.entity.Diary;
import www.maxinhai.com.diarymybatis.util.BaseResponse;
import www.maxinhai.com.diarymybatis.util.CodeEnum;
import www.maxinhai.com.diarymybatis.util.EmptyUtils;
import www.maxinhai.com.diarymybatis.util.ResponseData;
import java.util.Map;

@Api(tags = "日记管理接口文档")
@RequestMapping(value = "api/diary/")
@RestController
public class DiaryController extends AbstractController {


    @ApiOperation(value = "根据条件获取日记信息集合", notes = "findAllDiary", httpMethod = "POST")
    @ApiImplicitParam(dataType = "java.util.Map",name = "params",value = "params",required = true)
    @PostMapping(value = "findAllDiary")
    public Map<String, Object> findAllDiary(@RequestBody Map<String, Object> params) throws Exception {
        Map<String, Object> result = diaryService.findAllDiary(params);
        if(EmptyUtils.isEmpty(result)) {
            return getFailure();
        }
        return result;
    }



    @ApiOperation(value = "根据条件获取日记信息集合", notes = "findOneById", httpMethod = "POST")
    @ApiImplicitParam(dataType = "Long",name = "id",value = "id",required = true)
    @PostMapping(value = "findOneById")
    public BaseResponse findOneById(@RequestParam("id") Long id) throws Exception {
        Diary diary = diaryService.findOneById(id);
        if(EmptyUtils.isEmpty(diary)) {
            return ResponseData.out(CodeEnum.FAIL, null);
        }
        return ResponseData.out(CodeEnum.SUCCESS, diary);
    }



    @ApiOperation(value = "添加日记信息", notes = "addDiary", httpMethod = "POST")
    @ApiImplicitParam(dataType = "Diary",name = "diary",value = "diary",required = true)
    @PostMapping(value = "addDiary")
    public BaseResponse addDiary(@RequestBody Diary diary) throws Exception {
        int result = diaryService.addDiary(diary);
        if(result == 1) {
            return ResponseData.out(CodeEnum.SUCCESS, null);
        }
        return ResponseData.out(CodeEnum.FAIL, null);
    }



    @ApiOperation(value = "删除日记信息", notes = "removeDiary", httpMethod = "POST")
    @ApiImplicitParam(dataType = "Long",name = "id",value = "id",required = true)
    @PostMapping(value = "removeDiary/{id}")
    public BaseResponse removeDiary(@PathVariable("id") Long id) throws Exception {
        int result = diaryService.deleteDiary(id);
        if(result == 1) {
            return ResponseData.out(CodeEnum.SUCCESS, null);
        }
        return ResponseData.out(CodeEnum.FAIL, null);
    }



    @ApiOperation(value = "根据id修改日记信息", notes = "modifyDiary", httpMethod = "POST")
    @ApiImplicitParam(dataType = "Diary",name = "diary",value = "diary",required = true)
    @PostMapping(value = "modifyDiary")
    public BaseResponse modifyDiary(@RequestBody Diary diary) throws Exception {
        int result = diaryService.modifyDiary(diary);
        if(result == 1) {
            return ResponseData.out(CodeEnum.SUCCESS, null);
        }
        return ResponseData.out(CodeEnum.FAIL, null);
    }

}
