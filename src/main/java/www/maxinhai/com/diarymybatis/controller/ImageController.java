package www.maxinhai.com.diarymybatis.controller;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import www.maxinhai.com.diarymybatis.util.ImageUtils;
import www.maxinhai.com.diarymybatis.util.RedisUtils;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "验证码图片相关接口", value = "验证码图片相关接口")
@RequestMapping(value = "api/image/")
@RestController
public class ImageController {

    @Resource
    private RedisUtils redisUtils;


    /**
     * 功能描述: 通过流获取到验证码图片
     * 〈〉
     * @Param: [request, response]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: 15735400536
     * @Date: 2020/7/9 21:22
     */
    @ApiOperation(value = "获取验证码图片通过IO流", notes = "Get Validate Iamge By Stream", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "HttpServletRequest",name = "request", value = "request",required = true),
            @ApiImplicitParam(dataType = "HttpServletResponse",name = "response", value = "response",required = true)
    })
    @RequestMapping(value = "getCheckImageByStream", method = RequestMethod.POST)
    public Map<String, Object> getCheckImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> result = new HashMap<>();
        //第一个数据验证码，第二个参数生成的验证码图片
        Object[] objs = ImageUtils.createImage();
        //将验证码存入缓存，方便待会比对
        String token = request.getHeader("token");
        //保存验证码到缓存
        redisUtils.set(token + ":" + "validate", objs[0]);
        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
        result.put("code", 200);
        result.put("message", "success");
        result.put("code", objs[0]);
        return result;
    }


    /**
     * 功能描述: 通过Base64编码拿到验证码图片
     * 〈〉
     * @Param: [request, response]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: 15735400536
     * @Date: 2020/7/9 21:23
     */
    @ApiOperation(value = "获取验证码图片通过Base64", notes = "Get Validate Iamge By Base64", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "HttpServletRequest",name = "request", value = "request",required = true),
            @ApiImplicitParam(dataType = "HttpServletResponse",name = "response", value = "response",required = true)
    })
    @RequestMapping(value = "getCheckImageByBase64", method = RequestMethod.POST)
    public Map<String, Object> getCheckImageByBase64(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> result = new HashMap<>();

        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = ImageUtils.createImage();
        //将验证码存入缓存，方便待会比对
        String token = request.getHeader("token");
        //保存验证码到缓存
        redisUtils.set(token + ":" + "validate", objs[0]);
        ByteArrayOutputStream os = new ByteArrayOutputStream();//新建流
        BufferedImage image = (BufferedImage) objs[1];
        ImageIO.write(image, "png", os);
        byte b[] = os.toByteArray();//从流中获取数据数组。
        String baseCode = Base64.encode(b);

        result.put("code", 200);
        result.put("message", "success");
        result.put("code", objs[0]);
        result.put("image", baseCode);
        return result;
    }


}
