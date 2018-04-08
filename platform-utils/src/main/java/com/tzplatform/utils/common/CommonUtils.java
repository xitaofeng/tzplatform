package com.tzplatform.utils.common;

import com.tzplatform.utils.jwtauth.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * 通用工具类 待完善中
 *
 * @author leijie
 */
public class CommonUtils {

    /**
     * 获取远程主机IP
     *
     * @param request
     * @return
     */
    public static String getRemoteAddress(HttpServletRequest request) {
        String forwards = request.getHeader("x-forwarded-for");
        if (forwards == null || forwards.trim().length() == 0 || "unknown".equalsIgnoreCase(forwards)) {
            forwards = request.getHeader("Proxy-Client-IP");
        }
        if (forwards == null || forwards.trim().length() == 0 || "unknown".equalsIgnoreCase(forwards)) {
            forwards = request.getHeader("WL-Proxy-Client-IP");
        }
        if (forwards == null || forwards.trim().length() == 0 || "unknown".equalsIgnoreCase(forwards)) {
            forwards = request.getRemoteAddr();
        }
        if (forwards == null || forwards.trim().length() == 0 || "unknown".equalsIgnoreCase(forwards)) {
            forwards = request.getHeader("X-Real-IP");
        }
        if (forwards != null && forwards.trim().length() > 0) {
            String[] ips = forwards.split(",");
            return ips[0];
        }
        return forwards;
    }

    /**
     * 是否包含中英文特殊字符，除英文"-_"字符外
     *
     * @param text
     * @return
     */
    public static boolean isContainsSpecialChar(String text) {
        if (StringUtils.isBlank(text))
            return false;
        String[] chars = {"[", "`", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "+", "=", "|", "{", "}", "'", ":", ";", "'", ",", "[", "]", ".", "<", ">", "/", "?", "~", "！", "@", "#", "￥", "%", "…", "&", "*", "（", "）", "—", "+", "|", "{", "}",
                "【", "】", "‘", "；", "：", "”", "“", "’", "。", "，", "、", "？", "]"};
        for (String ch : chars) {
            if (text.contains(ch))
                return true;
        }
        return false;
    }

    /**
     * 判断是否是ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {

        String requestType = request.getHeader("X-Requested-With");

        return requestType != null ? "XMLHttpRequest".equals(requestType) : false;

    }

    /**
     * 获取主键
     *
     * @return
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    public static Map<String, Object> upLoadFile(CommonsMultipartFile file, String path) {
        String[] split = file.getOriginalFilename().split("\\.");
        String type = split[split.length - 1];
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + type;
        String serveraddr = "";
        String realpath = "";
        File tmpfile = null;
        try {
            serveraddr = "/uploadtmp/" + DateUtil.format(new Date(), "yyyy-MM-dd") + "/";
            realpath = path + serveraddr;
            tmpfile = new File(realpath, newFileName);
            if (!tmpfile.exists()) {
                tmpfile.mkdirs();
            }
            file.transferTo(tmpfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("realpath", realpath + newFileName);
        resultMap.put("filepath", serveraddr + newFileName);
        resultMap.put("file", tmpfile);
        return resultMap;
    }

    /**
     * 获取随机数字加字母
     *
     * @param length
     * @return
     */
    public static String getRandomCharAndNumr(Integer length) {
        String str = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            boolean b = random.nextBoolean();
            if (b) { // 字符串
                // int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母
                str += (char) (65 + random.nextInt(26));// 取得大写字母
            } else { // 数字
                str += String.valueOf(random.nextInt(10));
            }
        }
        return str;
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    public static Map<String, Object> upLoadFiles(CommonsMultipartFile file, String path,String fileType) {
        String[] split = file.getOriginalFilename().split("\\.");
        String type = split[split.length - 1];
        String newFileName = UUID.randomUUID().toString() + "." + type;
        //文件名称
        String fileName = file.getOriginalFilename();
        String serveraddr = "";
        String realpath = "";
        File tmpfile = null;
        try {
            serveraddr = "/"+fileType+"/"+DateUtil.format(new Date(), "yyyy-MM-dd") + "/";
            realpath = path + serveraddr;
            tmpfile = new File(realpath, newFileName);
            if (!tmpfile.exists()) {
                tmpfile.mkdirs();
            }
            file.transferTo(tmpfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("filepath", serveraddr + newFileName);
        resultMap.put("filename", fileName);
        resultMap.put("realname", newFileName);
        resultMap.put("realpath", realpath + newFileName);
        return resultMap;
    }

    /**
     * 文件下载
     *
     * @param request
     * @param response
     * @param realName
     * @throws Exception
     */
    public static void download(HttpServletRequest request, HttpServletResponse response, String downPath,
                                String realName) throws Exception {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        long fileLength = new File(downPath).length();
        //设置下载头信息
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");//设置强制下载不打开
        response.setHeader("Content-disposition", "attachment; filename="
                + new String(realName.getBytes("utf-8"), "ISO8859-1"));//为了解决中文名称乱码问题
        response.setHeader("Content-Length", String.valueOf(fileLength));
        //文件流输出
        bis = new BufferedInputStream(new FileInputStream(downPath));
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();
    }

    /**
     * 页面显示图片
     *
     * @param request
     * @param response
     * @throws Exception
     */
    public static void showImage(HttpServletRequest request, HttpServletResponse response, String basePath,
                                 String imagePath) throws Exception {
        FileInputStream in = null;
        OutputStream out = null;
        // 页面显示图片时必须加
        response.setContentType("image/jpeg");
        // 读取要下载的文件，保存到文件输入流
        in = new FileInputStream(basePath + imagePath);
        // 创建输出流
        out = response.getOutputStream();
        // 创建缓冲区
        byte buffer[] = new byte[1024];
        int len = 0;
        // 循环将输入流中的内容读取到缓冲区当中
        while ((len = in.read(buffer)) > 0) {
            // 输出缓冲区的内容到浏览器，实现文件下载
            out.write(buffer, 0, len);
        }
        // 关闭文件输入流
        in.close();
        out.flush();
        // 关闭输出流
        out.close();
    }

    /**
     * 移动端获取用户账号
     *
     * @throws Exception
     */
    public static String getAccount() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String accountId= JwtUtils.veriftToken(request.getHeader("TZToken"));
        return accountId;
    }

}
