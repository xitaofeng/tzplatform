package com.tzplatform.utils.smab;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SambaHelper {

    /***
     * 方法说明:文件下载
     *
     * @param name
     *            <br>
     *            参数:用户名称[若为匿名,此处null]
     * @param password
     *            <br>
     *            参数:用户密码[若为匿名,此处null]
     * @param host
     *            <br>
     *            参数:samba主机或IP地址
     * @param source
     *            <br>
     *            参数:源文件或目录路径
     * @param target
     *            <br>
     *            参数:下载存放的本地的文件全路径或者目录
     * @throws Exception
     *             <br>
     *             异常:文件传输中可能遇到的异常
     */
    public static void download(String name, String password, String host, String source, String target)
            throws Exception {
        // samba URL组合
        StringBuffer sambaURLBuff = new StringBuffer();
        sambaURLBuff.append("samba://");
        sambaURLBuff.append(host);
        sambaURLBuff.append("/");
        source = source.replaceAll("\\\\", "/");
        sambaURLBuff.append(source);
        // 创建一个smbFile对象对应远程服务器上的SmbFile
        SmbFile remoteSmbFile = new SmbFile(sambaURLBuff.toString());
        // 判断输入的源是需要传输路径还是文件夹
        if (remoteSmbFile.isDirectory()) {
            // 文件目录传输的情况
            downloadDirectory(name, password, host, source, target);
        } else {
            // 文件的传输情况
            downloadFile(name, password, host, source, target);
        }
    }

    /**
     * 方法说明:<br>
     * 通过sambaURL方式下载文件 <br>
     * 其他说明:<br>
     * 匿名URL:samba://host//url<br>
     * 用户URL:samba://name:password@host/url
     *
     * @param sambaURL <br>
     *                 参数:sambaURL路径
     * @param target   <br>
     *                 参数:本地下载路径
     * @throws Exception <br>
     *                   异常:文件传输中存在的异常
     */
    public static void download(String sambaURL, String target) throws Exception {
        // 参数判定
        if (null == sambaURL || "".equals(sambaURL)) {
            throw new Exception("sambaURL 不能为空!");
        }
        if (null == target || "".equals(target)) {
            throw new Exception("target 不能为空!");
        }
        SmbFile remoteSmbFile = new SmbFile(sambaURL);
        if (remoteSmbFile.isDirectory()) {
            downloadDirectory(sambaURL, target);
        } else {
            downloadFile(sambaURL, target);
        }
    }

    /***
     * 方法说明:通过sambaURL方式单个文件下载
     *
     * @param sambaURL
     *            <br>
     *            参数:sambaURL
     * @param target
     *            <br>
     *            参数:本地下载文件全路径
     * @throws Exception
     *             <br>
     *             异常:文件传输过程出现的异常
     */
    private static void downloadFile(String sambaURL, String target) throws Exception {
        // 参数判定
        if (null == sambaURL || "".equals(sambaURL)) {
            throw new Exception("sambaURL 不能为空!");
        }
        if (null == target || "".equals(target)) {
            throw new Exception("target 不能为空!");
        }

        // destFile
        target = target.replaceAll("\\\\", "/");
        // 本地存放路径
        String destFilePath = target.substring(0, target.lastIndexOf("/"));
        // 判断路径是否存在
        File localPath = new File(destFilePath);
        if (!localPath.exists() || !localPath.isDirectory()) {
            // 创建路径
            localPath.mkdirs();
        }
        InputStream in = null;
        OutputStream out = null;
        // 创建一个smbFile对象对应远程服务器上的SmbFile
        SmbFile remoteSmbFile = new SmbFile(sambaURL);
        // 本地文件由本地目录，路径分隔符，文件名拼接而成
        File localFile = new File(target);
        // 打开文件输入流，指向远程的smb服务器上的文件，特别注意，这里流包装器包装了SmbFileInputStream
        in = new BufferedInputStream(new SmbFileInputStream(remoteSmbFile));
        // 打开文件输出流，指向新创建的本地文件，作为最终复制到的目的地
        out = new BufferedOutputStream(new FileOutputStream(localFile));
        // 缓冲内存
        byte[] buffer = new byte[1024];
        while (in.read(buffer) != -1) {
            out.write(buffer);
            buffer = new byte[1024];
        }
        // 最后关闭流
        out.close();
        in.close();

    }

    /**
     * 方法说明:samba文件下载
     *
     * @param name     参数:用户名称[若为匿名,此处null]
     * @param password 参数:用户密码[若为匿名,此处null]
     * @param host     参数:samba主机名或IP地址
     * @param source   参数:源文件全路径[samba上文件全路径]
     * @param target   参数:目标文件全路径[本地下载的地方文件全路径]
     * @throws Exception 异常:传输操作可能存在的异常
     */
    private static void downloadFile(String name, String password, String host, String source, String target)
            throws Exception {
        // 1入参检查
        if ((host == null) || ("".equals(host.trim()))) {
            throw new Exception("Samba服务器HOST不能为空!");
        }
        // 2入参检查
        if ((source == null) || ("".equals(source.trim()))) {
            throw new Exception("Samba服务器远程文件路径不可以为空");

        }
        // 3入参检查
        if ((target == null) || ("".equals(target.trim()))) {
            throw new Exception("本地目录路径不可以为空");
        }
        // samba URL组合
        StringBuffer urlBuffer = new StringBuffer();
        // 转换源路径中的反斜杠
        source = source.replaceAll("\\\\", "/");
        // 判断是否为匿名访问
        if (null != name && password != null && name.length() > 0) {
            urlBuffer.append(" smb://");
            urlBuffer.append(name);
            urlBuffer.append(":");
            urlBuffer.append(password);
            urlBuffer.append("@");
            urlBuffer.append(host);
            urlBuffer.append("/");
            urlBuffer.append(source);
        } else {
            urlBuffer.append("samba://");
            urlBuffer.append(host);
            urlBuffer.append("/");
            urlBuffer.append(source);
        }
        System.out.println("SAMBA:" + urlBuffer.toString());

        // destFile
        target = target.replaceAll("\\\\", "/");
        // 本地存放路径
        String destFilePath = target.substring(0, target.lastIndexOf("/"));
        // 判断路径是否存在
        File localPath = new File(destFilePath);
        if (!localPath.exists() || !localPath.isDirectory()) {
            // 创建路径
            localPath.mkdirs();
        }
        InputStream in = null;
        OutputStream out = null;
        // 创建一个smbFile对象对应远程服务器上的SmbFile
        SmbFile remoteSmbFile = new SmbFile(urlBuffer.toString());
        // 本地文件由本地目录，路径分隔符，文件名拼接而成
        File localFile = new File(target);
        // 打开文件输入流，指向远程的smb服务器上的文件，特别注意，这里流包装器包装了SmbFileInputStream
        in = new BufferedInputStream(new SmbFileInputStream(remoteSmbFile));
        // 打开文件输出流，指向新创建的本地文件，作为最终复制到的目的地
        out = new BufferedOutputStream(new FileOutputStream(localFile));
        // 缓冲内存
        byte[] buffer = new byte[1024];
        while (in.read(buffer) != -1) {
            out.write(buffer);
            buffer = new byte[1024];
        }
        // 最后关闭流
        out.close();
        in.close();

    }

    /**
     * 直接从文件服务器上查看---qxk
     * @param name
     * @param password
     * @param host
     * @param source
     * @param response
     * @throws Exception
     */
    public static void downloadFileOutOpen(String name, String password, String host, String source, HttpServletResponse response)
            throws Exception {
        // 1入参检查
        if ((host == null) || ("".equals(host.trim()))) {
            throw new Exception("Samba服务器HOST不能为空!");
        }
        // 2入参检查
        if ((source == null) || ("".equals(source.trim()))) {
            throw new Exception("Samba服务器远程文件路径不可以为空");

        }
        // samba URL组合
        StringBuffer urlBuffer = new StringBuffer();
        // 转换源路径中的反斜杠
        source = source.replaceAll("\\\\", "/");
        // 判断是否为匿名访问
        if (null != name && password != null && name.length() > 0) {
            urlBuffer.append(" smb://");
            urlBuffer.append(name);
            urlBuffer.append(":");
            urlBuffer.append(password);
            urlBuffer.append("@");
            urlBuffer.append(host);
            urlBuffer.append("/");
            urlBuffer.append(source);
        } else {
            urlBuffer.append("samba://");
            urlBuffer.append(host);
            urlBuffer.append("/");
            urlBuffer.append(source);
        }
        System.out.println("SAMBA:" + urlBuffer.toString());

        InputStream in = null;
        // 创建一个smbFile对象对应远程服务器上的SmbFile
        SmbFile remoteSmbFile = new SmbFile(urlBuffer.toString());
        // 打开文件输入流，指向远程的smb服务器上的文件，特别注意，这里流包装器包装了SmbFileInputStream
        in = new BufferedInputStream(new SmbFileInputStream(remoteSmbFile));
        OutputStream out = new BufferedOutputStream(response.getOutputStream());
        // 缓冲内存
        byte[] buffer = new byte[1024];
        while (in.read(buffer) != -1) {
            out.write(buffer);
            buffer = new byte[1024];
        }
        // 最后关闭流
        out.close();
        in.close();

    }

    /**
     * 直接从文件服务器上下载附件---qxk
     * @param name
     * @param password
     * @param host
     * @param source
     * @param response
     * @throws Exception
     */
    public static void downloadFileOut(String name, String password, String host, String source,String filename, HttpServletResponse response)
            throws Exception {
        // 1入参检查
        if ((host == null) || ("".equals(host.trim()))) {
            throw new Exception("Samba服务器HOST不能为空!");
        }
        // 2入参检查
        if ((source == null) || ("".equals(source.trim()))) {
            throw new Exception("Samba服务器远程文件路径不可以为空");

        }
        // samba URL组合
        StringBuffer urlBuffer = new StringBuffer();
        // 转换源路径中的反斜杠
        source = source.replaceAll("\\\\", "/");
        // 判断是否为匿名访问
        if (null != name && password != null && name.length() > 0) {
            urlBuffer.append(" smb://");
            urlBuffer.append(name);
            urlBuffer.append(":");
            urlBuffer.append(password);
            urlBuffer.append("@");
            urlBuffer.append(host);
            urlBuffer.append("/");
            urlBuffer.append(source);
        } else {
            urlBuffer.append("samba://");
            urlBuffer.append(host);
            urlBuffer.append("/");
            urlBuffer.append(source);
        }
        System.out.println("SAMBA:" + urlBuffer.toString());

        InputStream in = null;
        // 创建一个smbFile对象对应远程服务器上的SmbFile
        SmbFile remoteSmbFile = new SmbFile(urlBuffer.toString());
        // 打开文件输入流，指向远程的smb服务器上的文件，特别注意，这里流包装器包装了SmbFileInputStream
        in = new BufferedInputStream(new SmbFileInputStream(remoteSmbFile));

        // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.replaceAll(" ", "").getBytes("utf-8"),"iso8859-1"));
        response.addHeader("Content-Length", "" + remoteSmbFile.length());
        OutputStream out = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        // 缓冲内存
        byte[] buffer = new byte[1024];
        while (in.read(buffer) != -1) {
            out.write(buffer);
            buffer = new byte[1024];
        }
        // 最后关闭流
        out.close();
        in.close();

    }

    /****
     * 方法说明:采用sambaURL方式下载文件夹
     *
     * @param sambaURL
     *            参数:sambaURL路径
     * @param target
     *            参数:下载到本地的文件路径
     * @throws Exception
     *             异常:文件传输过程中出现的异常
     */
    private static void downloadDirectory(String sambaURL, String target) throws Exception {
        // 参数判定
        if (null == sambaURL || "".equals(sambaURL)) {
            throw new Exception("sambaURL 不能为空!");
        }
        if (null == target || "".equals(target)) {
            throw new Exception("target 不能为空!");
        }
        // 本地路径转换
        target = target.replaceAll("\\\\", "/");
        // 创建一个smbFile对象对应远程服务器上的SmbFile
        SmbFile remoteSmbFile = new SmbFile(sambaURL);
        // 获取文件信息
        SmbFile[] sbfs = remoteSmbFile.listFiles();
        /** 源文件字符流 **/
        StringBuffer srcFileBuffer;
        /** 目标字符流 **/
        StringBuffer destFileBuffer;
        /** 源文件目录 **/
        StringBuffer srcPathBuffer;
        /** 目标文件目录 **/
        StringBuffer destPathBuffer;
        for (SmbFile f : sbfs) {
            if (f.isDirectory()) {// 如果是文件夹的情况
                //
                srcPathBuffer = new StringBuffer();
                sambaURL = sambaURL.replaceAll("\\\\", "/");
                srcPathBuffer.append(sambaURL);
                srcPathBuffer.append("/");
                srcPathBuffer.append(f.getName());

                destPathBuffer = new StringBuffer();
                target = target.replaceAll("\\\\", "/");
                destPathBuffer.append(target);
                destPathBuffer.append("/");
                destPathBuffer.append(f.getName());
                // 判断还有文件夹
                downloadDirectory(srcPathBuffer.toString(), destPathBuffer.toString());
            } else {
                // 源文件
                srcFileBuffer = new StringBuffer();
                sambaURL = sambaURL.replaceAll("\\\\", "/");
                srcFileBuffer.append(sambaURL);
                srcFileBuffer.append("/");
                srcFileBuffer.append(f.getName());
                // 目标
                destFileBuffer = new StringBuffer();
                target = target.replaceAll("\\\\", "/");
                destFileBuffer.append(target);
                destFileBuffer.append("/");
                destFileBuffer.append(f.getName());
                // 传输单文件
                downloadFile(srcFileBuffer.toString(), destFileBuffer.toString());
            }

        }

    }

    /**
     * 下载某个目录所有文件
     *
     * @param name     参数:用户名称[若为匿名,此处null]
     * @param password 参数:用户密码[若为匿名,此处null]
     * @param host     参数:SAMBA远程主机
     * @param source   参数:SAMBA远程路径
     * @param target   参数:本地下载路径
     * @throws Exception 异常:文件传输时异常
     */
    private static void downloadDirectory(String name, String password, String host, String source, String target)
            throws Exception {
        // 1入参检查
        if ((source == null) || ("".equals(source.trim()))) {
            throw new Exception("Samba服务器远程文件路径不可以为空");
        }
        // 2入参检查
        if ((target == null) || ("".equals(target.trim()))) {
            throw new Exception("本地目录路径不可以为空");
        }
        // 3入参检查
        if ((host == null) || ("".equals(host.trim()))) {
            throw new Exception("host不可以为空");
        }

        // 本地路径转换
        target = target.replaceAll("\\\\", "/");

        // samba URL组合
        StringBuffer urlBuffer = new StringBuffer();
        // 转换源路径中的反斜杠
        source = source.replaceAll("\\\\", "/");
        // 判断是否为匿名访问
        if (null != name && password != null && name.length() > 0) {
            urlBuffer.append(" smb://");
            urlBuffer.append(name);
            urlBuffer.append(":");
            urlBuffer.append(password);
            urlBuffer.append("@");
            urlBuffer.append(host);
            urlBuffer.append("/");
            urlBuffer.append(source);
        } else {
            urlBuffer.append("samba://");
            urlBuffer.append(host);
            urlBuffer.append("/");
            urlBuffer.append(source);
        }
        System.out.println("SAMBA:" + urlBuffer.toString());
        // 创建一个smbFile对象对应远程服务器上的SmbFile
        SmbFile remoteSmbFile = new SmbFile(urlBuffer.toString());
        // 获取文件信息
        SmbFile[] sbfs = remoteSmbFile.listFiles();
        /** 源文件字符流 **/
        StringBuffer srcFileBuffer;
        /** 目标字符流 **/
        StringBuffer destFileBuffer;
        /** 源文件目录 **/
        StringBuffer srcPathBuffer;
        /** 目标文件目录 **/
        StringBuffer destPathBuffer;
        for (SmbFile f : sbfs) {
            if (f.isDirectory()) {// 如果是文件夹的情况
                //
                srcPathBuffer = new StringBuffer();
                source = source.replaceAll("\\\\", "/");
                srcPathBuffer.append(source);
                srcPathBuffer.append("/");
                srcPathBuffer.append(f.getName());

                destPathBuffer = new StringBuffer();
                target = target.replaceAll("\\\\", "/");
                destPathBuffer.append(target);
                destPathBuffer.append("/");
                destPathBuffer.append(f.getName());
                // 判断还有文件夹
                downloadDirectory(name, password, host, srcPathBuffer.toString(), destPathBuffer.toString());
            } else {
                // 源文件
                srcFileBuffer = new StringBuffer();
                source = source.replaceAll("\\\\", "/");
                srcFileBuffer.append(source);
                srcFileBuffer.append("/");
                srcFileBuffer.append(f.getName());
                // 目标
                destFileBuffer = new StringBuffer();
                target = target.replaceAll("\\\\", "/");
                destFileBuffer.append(target);
                destFileBuffer.append("/");
                destFileBuffer.append(f.getName());
                // 传输单文件
                downloadFile(name, password, host, srcFileBuffer.toString(), destFileBuffer.toString());

            }

        }

    }

    /**
     * samba上传文件
     *
     * @param name     <br>
     *                 参数:用户名[匿名null]
     * @param password <br>
     *                 参数:密码[匿名null]
     * @param host     <br>
     *                 参数:samba主机名或IP地址
     * @param source   <br>
     *                 参数:源文件或目录
     * @param target   <br>
     *                 参数:目标文件或目录
     * @throws Exception
     */
    public static void upload(String name, String password, String host, String source, String target)
            throws Exception {
        // 1入参检查
        if ((target == null) || ("".equals(target.trim()))) {
            throw new Exception("Samba服务器远程目录路径不可以为空");
        }

        // 2入参检查
        if ((source == null) || ("".equals(source.trim()))) {
            throw new Exception("本地文件路径不可以为空");
        }
        // 3入参检查
        if ((host == null) || ("".equals(host.trim()))) {
            throw new Exception("本地文件路径不可以为空");
        }
        // 转换源路径中的反斜杠
        source = source.replaceAll("\\\\", "/");
        // 转换目标路径
        target = target.replaceAll("\\\\", "/");
        // 本地路径判断
        File localPath = new File(source);
        if (localPath.isDirectory()) {
            uploadDirectory(name, password, host, source, target);
        } else {
            uploadFile(name, password, host, source, target);
        }

    }

    /**
     * 方法说明:<br>
     * 通过sambaURL方式上传文件 <br>
     * 其他说明:<br>
     * 匿名URL:samba://host//url<br>
     * 用户URL:samba://name:password@host/url
     *
     * @param source   <br>
     *                 参数:源文件
     * @param sambaURL <br>
     *                 参数:sambaURL路径
     * @throws Exception <br>
     *                   异常:文件传输所产生的异常
     */
    public static void upload(String source, String sambaURL) throws Exception {
        if (null == source || "".equals(source)) {
            throw new Exception("源文件路径不能为空!");
        }
        if (null == sambaURL || "".equals(sambaURL)) {
            throw new Exception("sambaURL路径不能为空!");
        }
        // 本地路径判断
        File localPath = new File(source);
        if (localPath.isDirectory()) {
            uploadDirectory(source, sambaURL);
        } else {
            uploadFile(source, sambaURL);
        }

    }

    /****
     * 方法说明:通过sambaURL方式上传文件
     *
     * @param source
     *            <br>
     *            参数:需要上传的源文件
     * @param sambaURL
     *            <br>
     *            参数:sambaURL路径
     * @throws Exception
     *             <br>
     *             异常:文件传输过程中遇到的异常问题
     */
    private static void uploadFile(String source, String sambaURL) throws Exception {
        if (null == source || "".equals(source)) {
            throw new Exception("源文件路径不能为空!");
        }
        if (null == sambaURL || "".equals(sambaURL)) {
            throw new Exception("sambaURL路径不能为空!");
        }
        InputStream in = null;
        OutputStream out = null;
        // 判断远程路径是否存在,如果不存在创建目录
        /** 远程存放文件路径 **/
        String sambaPath = sambaURL.substring(0, sambaURL.lastIndexOf("/"));
        SmbFile targetSmbFile = new SmbFile(sambaPath);
        if (!targetSmbFile.exists() || !targetSmbFile.isDirectory()) {// 判断路径是否存在
            // 创建目录
            targetSmbFile.mkdirs();
        }

        // 处理上传->读取本地文件->上传到samba文件
        // 读取本地文件
        File localFile = new File(source);
        // 创建远程服务器上Samba文件对象
        SmbFile remoteSmbFile = new SmbFile(sambaURL);
        // 打开一个文件输入流执行本地文件，要从它读取内容
        in = new BufferedInputStream(new FileInputStream(localFile));
        // 打开一个远程Samba文件输出流，作为复制到的目的地
        out = new BufferedOutputStream(new SmbFileOutputStream(remoteSmbFile));

        // 缓冲内存
        byte[] buffer = new byte[1024];
        while (in.read(buffer) != -1) {
            out.write(buffer);
            buffer = new byte[1024];
        }
        // 关闭相应流
        out.close();
        in.close();
    }

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
    private static void uploadFile(String name, String password, String host, String source, String target)
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
        if (!targetSmbFile.exists() || !targetSmbFile.isDirectory()) {// 判断路径是否存在
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
        while (in.read(buffer) != -1) {
            out.write(buffer);
            buffer = new byte[1024];
        }
        // 关闭相应流
        out.close();
        in.close();
    }

    private static void uploadDirectory(String source, String sambaURL) throws Exception {
        if (null == source || "".equals(source)) {
            throw new Exception("源文件路径不能为空!");
        }
        if (null == sambaURL || "".equals(sambaURL)) {
            throw new Exception("sambaURL路径不能为空!");
        }

        // 转换源路径中的反斜杠
        source = source.replaceAll("\\\\", "/");

        File dir = new File(source);
        File[] files = dir.listFiles();
        /** 源文件字符流 **/
        StringBuffer srcFileBuffer;
        /** 目标字符流 **/
        StringBuffer destFileBuffer;
        /** 源文件目录 **/
        StringBuffer srcPathBuffer;
        /** 目标文件目录 **/
        StringBuffer destPathBuffer;
        for (File f : files) {
            if (f.isDirectory()) {
                srcPathBuffer = new StringBuffer();
                source = source.replaceAll("\\\\", "/");
                srcPathBuffer.append(source);
                srcPathBuffer.append("/");
                srcPathBuffer.append(f.getName());

                destPathBuffer = new StringBuffer();
                sambaURL = sambaURL.replaceAll("\\\\", "/");
                destPathBuffer.append(sambaURL);
                destPathBuffer.append("/");
                destPathBuffer.append(f.getName());

                uploadDirectory(srcPathBuffer.toString(), destPathBuffer.toString());
            } else {
                // 源文件
                srcFileBuffer = new StringBuffer();
                source = source.replaceAll("\\\\", "/");
                srcFileBuffer.append(source);
                srcFileBuffer.append("/");
                srcFileBuffer.append(f.getName());
                // 目标
                destFileBuffer = new StringBuffer();
                sambaURL = sambaURL.replaceAll("\\\\", "/");
                destFileBuffer.append(sambaURL);
                destFileBuffer.append("/");
                destFileBuffer.append(f.getName());

                uploadFile(srcFileBuffer.toString(), destFileBuffer.toString());
            }
        }

    }

    /**
     * 上传目录所有文件到samba
     *
     * @param name     参数:用户名[匿名null]
     * @param password 参数:密码[匿名null]
     * @param host     参数:samba主机或IP地址
     * @param source   参数:源文件,需要上传的文件或目录
     * @param target   参数:目标文件,需要上传到samba的路径
     * @throws IOException 异常:抛出异常
     */
    private static void uploadDirectory(String name, String password, String host, String source, String target)
            throws IOException {
        // 转换源路径中的反斜杠
        source = source.replaceAll("\\\\", "/");

        File dir = new File(source);
        File[] files = dir.listFiles();
        /** 源文件字符流 **/
        StringBuffer srcFileBuffer;
        /** 目标字符流 **/
        StringBuffer destFileBuffer;
        /** 源文件目录 **/
        StringBuffer srcPathBuffer;
        /** 目标文件目录 **/
        StringBuffer destPathBuffer;
        for (File f : files) {
            if (f.isDirectory()) {
                srcPathBuffer = new StringBuffer();
                source = source.replaceAll("\\\\", "/");
                srcPathBuffer.append(source);
                srcPathBuffer.append("/");
                srcPathBuffer.append(f.getName());

                destPathBuffer = new StringBuffer();
                target = target.replaceAll("\\\\", "/");
                destPathBuffer.append(target);
                destPathBuffer.append("/");
                destPathBuffer.append(f.getName());

                uploadDirectory(name, password, host, srcPathBuffer.toString(), destPathBuffer.toString());
            } else {
                // 源文件
                srcFileBuffer = new StringBuffer();
                source = source.replaceAll("\\\\", "/");
                srcFileBuffer.append(source);
                srcFileBuffer.append("/");
                srcFileBuffer.append(f.getName());
                // 目标
                destFileBuffer = new StringBuffer();
                target = target.replaceAll("\\\\", "/");
                destFileBuffer.append(target);
                destFileBuffer.append("/");
                destFileBuffer.append(f.getName());

                uploadFile(name, password, host, srcFileBuffer.toString(), destFileBuffer.toString());
            }
        }
    }

    /**
     * 方法说明:获取samba目录中的所有文件信息
     *
     * @param name     <br>
     *                 参数:用户名[匿名null]
     * @param password <br>
     *                 参数:密码[匿名null]
     * @param host     <br>
     *                 参数:samba主机或IP地址
     * @param path     <br>
     *                 参数:samba上需要获取信息的路径
     * @return 返回:List<br>
     * Map k-v:<br>
     * K-name :文件名<br>
     * K-lastModified:自1970.1.1到修改时刻的秒数<br>
     * K-path:文件所在samba路径<br>
     * @throws MalformedURLException 异常
     * @throws SmbException          异常
     */
    public static List<Map<String, Object>> sambaFileListInfo(String name, String password, String host, String path)
            throws MalformedURLException, SmbException {
        // samba URL组合
        StringBuffer urlBuffer = new StringBuffer();
        // 转换源路径中的反斜杠
        path = path.replaceAll("\\\\", "/");
        // 判断是否为匿名访问
        if (null != name && password != null && name.length() > 0) {
            urlBuffer.append(" smb://");
            urlBuffer.append(name);
            urlBuffer.append(":");
            urlBuffer.append(password);
            urlBuffer.append("@");
            urlBuffer.append(host);
            urlBuffer.append("/");
            urlBuffer.append(path);
        } else {
            urlBuffer.append("samba://");
            urlBuffer.append(host);
            urlBuffer.append("/");
            urlBuffer.append(path);
        }

        // 获取samba上的文件信息
        SmbFile sf = new SmbFile(urlBuffer.toString());
        // 获取文件列表
        SmbFile[] sfs = sf.listFiles();
        // 创建一个map的list集合,用于返回数据
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for (SmbFile f : sfs) {
            if (f.isDirectory()) {
                // path
                StringBuffer pathBuffer = new StringBuffer();
                pathBuffer.append(path);
                pathBuffer.append("/");
                pathBuffer.append(f.getName());
                pathBuffer.append("/");
                List<Map<String, Object>> ls = sambaFileListInfo(name, password, host, pathBuffer.toString());
                list.addAll(ls);
            } else {

                map = new HashMap<String, Object>();
                map.put("name", f.getName());
                map.put("lastModified", f.getLastModified());
                map.put("path", f.getPath());
                list.add(map);
            }

        }
        return list;
    }

    /**
     * 通过sambaURL方式获取目录下文件信息
     *
     * @param sambaURL 参数
     * @return 返回:
     * @throws MalformedURLException
     * @throws SmbException
     */
    public static List<Map<String, Object>> sambaFileListInfo(String sambaURL)
            throws MalformedURLException, SmbException {
        sambaURL = sambaURL.replaceAll("\\\\", "/");
        sambaURL = sambaURL + "/";
        // 获取samba上的文件信息
        SmbFile sf = new SmbFile(sambaURL);
        // 获取文件列表
        SmbFile[] sfs = sf.listFiles();
        // 创建一个map的list集合,用于返回数据
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for (SmbFile f : sfs) {
            if (f.isDirectory()) {
                // path
                StringBuffer pathBuffer = new StringBuffer();
                pathBuffer.append(sambaURL);
                pathBuffer.append("/");
                pathBuffer.append(f.getName());
                pathBuffer.append("/");
                List<Map<String, Object>> ls = sambaFileListInfo(pathBuffer.toString());
                list.addAll(ls);
            } else {

                map = new HashMap<String, Object>();
                map.put("name", f.getName());
                map.put("lastModified", f.getLastModified());
                map.put("path", f.getPath());
                list.add(map);
            }

        }
        return list;
    }
}