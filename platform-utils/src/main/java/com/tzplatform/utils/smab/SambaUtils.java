package com.tzplatform.utils.smab;


import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class SambaUtils {

    /**
     * 方法说明:上传文件到samba路径
     *
     * @param name     参数:用户名[匿名null]
     * @param password 参数:密码[匿名null]
     * @param host     参数:主机名或IP
     * @param source   参数:源文件,需要上传的文件
     * @param target   参数:目标文件全路径/samba文件路径
     * @throws IOException 异常:抛出异常
     */
    public static void uploadFile(String name, String password, String host, String source, String target)
            throws IOException {
        // 本地路径转换
        target = target.replaceAll("\\\\", "/");
        // samba URL组合
        StringBuffer urlBuffer = new StringBuffer();
        // 转换源路径中的反斜杠
        source = source.replaceAll("\\\\", "/");
        // 判断是否为匿名访问
        if (null != name && password != null && name.length() > 0) {
            urlBuffer.append("smb://");
            urlBuffer.append(name);
            urlBuffer.append(":");
            urlBuffer.append(password);
            urlBuffer.append("@");
            urlBuffer.append(host);
            urlBuffer.append("/");
            urlBuffer.append(target);
        } else {
            urlBuffer.append("samba://");
            urlBuffer.append(host);
            urlBuffer.append("/");
            urlBuffer.append(target);
        }

        InputStream in = null;
        OutputStream out = null;
        // 判断远程路径是否存在,如果不存在创建目录
        /** 远程存放文件路径 **/
        String targetPath = urlBuffer.toString().substring(0, urlBuffer.toString().lastIndexOf("/"));
        SmbFile targetSmbFile = new SmbFile(targetPath);
        // 判断路径是否存在
        if (!targetSmbFile.exists() || !targetSmbFile.isDirectory()) {
            // 创建目录
            targetSmbFile.mkdirs();
        }

        // 处理上传->读取本地文件->上传到samba文件
        // 读取本地文件
        File localFile = new File(source);
        // 创建远程服务器上Samba文件对象
        SmbFile remoteSmbFile = new SmbFile(urlBuffer.toString());
        // 打开一个文件输入流执行本地文件，要从它读取内容
        in = new BufferedInputStream(new FileInputStream(localFile));
        // 打开一个远程Samba文件输出流，作为复制到的目的地
        out = new BufferedOutputStream(new SmbFileOutputStream(remoteSmbFile));

        // 缓冲内存
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        // 关闭相应流
        out.close();
        in.close();
    }
    /**
     * 显示图片
     * @param name
     * @param password
     * @param host
     * @param remoteUrl  图片位置
     * @throws Exception
     */
    public static void showImage(String name, String password, String host,String remoteUrl) throws  Exception{
        HttpServletResponse response  = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        SmbFileInputStream in = null;
        OutputStream out = null;
        // samba URL组合
        StringBuffer urlBuffer = new StringBuffer();
        // 转换源路径中的反斜杠
        remoteUrl = remoteUrl.replaceAll("\\\\", "/");
        // 判断是否为匿名访问
        if (null != name && password != null && name.length() > 0) {
            urlBuffer.append("smb://");
            urlBuffer.append(name);
            urlBuffer.append(":");
            urlBuffer.append(password);
            urlBuffer.append("@");
            urlBuffer.append(host);
            urlBuffer.append("/");
            urlBuffer.append(remoteUrl);
        } else {
            urlBuffer.append("samba://");
            urlBuffer.append(host);
            urlBuffer.append("/");
            urlBuffer.append(remoteUrl);
        }
        String targetPath = urlBuffer.toString();
        SmbFile targetSmbFile = new SmbFile(targetPath);
        // 页面显示图片时必须加
        response.setContentType("image/jpeg");
        // 读取要下载的文件，保存到文件输入流
        in = new SmbFileInputStream(targetSmbFile);
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
     * 查看应用小图标
     * @param bytes
     * @param response
     * @throws Exception
     */
    public static void showAppImage(byte[] bytes,HttpServletResponse response) throws  Exception{
        InputStream in = new ByteArrayInputStream(bytes);
        OutputStream out = null;
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
    public static void download(String name, String password, String host, String source, String target)
            throws Exception {
        SambaHelper.download(name, password, host, source, target);
    }
}
