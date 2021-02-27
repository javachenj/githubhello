//package com.nancal.minio.web.security.service;
//
//import com.nancal.common.enums.CodeEnum;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//import java.util.Map;
//
///**
// * @类功能说明: 认证工具类
// * @类路径: com.tecsun.common.utils.security
// * @公司名称: 广东德生科技有限公司
// * @作者: nongfu Email:nongfu.n.yao@qq.com
// * @创建时间: 2020/6/12 12:27
// * @版本: V1.0
// */
//@Component
//public class AuthorityService {
//
//    @Autowired
//    private TokenService tokenService;
//
//    /**
//     * 权限认证
//     */
//    public ResponseData isPass(String authorization, HttpServletRequest request) {
//        if(tokenService == null){
//            tokenService = new TokenService();
//        }
//        Map<String,Object> map = tokenService.getLoginUserPermissions(request);
//
//        Integer code = (Integer) map.get("code");
//        if(CodeEnum.LOGIN_FAILURE.getCode().equals(code)){
//            //用户登录失效
//           return ResponseData.out(CodeEnum.LOGIN_FAILURE);
//        }
//
//        if(CodeEnum.LOGIN_ERROR.getCode().equals(code)){
//            //用户未登录，请先登录
//            return ResponseData.out(CodeEnum.LOGIN_ERROR);
//        }
//
//        List<String> permissions = (List<String>) map.get("data");
//
//        for (String auth : permissions) {
//            if(authorization.equals(auth) || "*:*:*".equals(auth)){
//                //通过
//                return ResponseData.out(CodeEnum.PASS);
//            }
//        }
//        //未授权
//        return ResponseData.out(CodeEnum.NOT_AUTHORIZATION);
//    }
//
//}
