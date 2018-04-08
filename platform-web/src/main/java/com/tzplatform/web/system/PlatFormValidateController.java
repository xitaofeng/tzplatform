package com.tzplatform.web.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tzplatform.utils.common.ValidateCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "platformvalidateCode")
public class PlatFormValidateController {

    Logger logger = Logger.getLogger(PlatFormValidateController.class);

    private final String VALIDATA_CODE = "validateCode";

    /**
     * 响应验证码页面
     *
     * @return
     */
    @RequestMapping(value = "/showCode")
    public String validateCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession();
        ValidateCode vCode = new ValidateCode(120, 40, 4, 100);
        session.setAttribute(VALIDATA_CODE, vCode.getCode());

        String getValidateCode = (String) session.getAttribute(VALIDATA_CODE);
        logger.debug("session code is  " + getValidateCode);

        vCode.write(response.getOutputStream());
        return null;
    }
}
