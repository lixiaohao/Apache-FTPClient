package test.tfp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.logging.Logger;

/**
 * Created by lixiaohao on 2016/10/27
 *
 * @Description
 * @Create 2016-10-27 13:24
 * @Company
 */
public class FtpTest {
Logger logger = Logger.getLogger("FtpTestLogger");
    FTPClient ftp;

    @Before
    public void initFTP(){
        ftp = new FTPClient();
        Boolean flag;
        try {
            //开始连接
            ftp.connect("192.168.0.136",21);
            //输出连接返回码
            logger.info("replyCode{} "+ftp.getReplyCode());
            //登录
            flag = ftp.login("lixiaohao","1234567");
            //输入登录日志
            logger.info("result{} login status :"+flag);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @After
    public void destory(){
        try {
            ftp.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ftpBaseTest(){

        try {
            //列出文件夹下所有文件和文件夹名
            String[] fileNames = ftp.listNames();
            for(String name:fileNames){
                logger.info("directoryName:"+name);
            }
            //新建文件夹
            Boolean dirName = ftp.makeDirectory("aabbcc");
            logger.info("make directory status:"+dirName);
            //进入download文件夹下
            Boolean sflag = ftp.changeWorkingDirectory("\\download");
            logger.info("reslult{}:"+sflag);
            //上传文件
            boolean isStore = ftp.storeFile("BasicSqlFormatter.java", new FileInputStream("C:\\Users\\lixiaohao.ZZGRP\\Desktop\\temp\\BasicSqlFormatter.java"));
            ftp.storeFile("test.png", new FileInputStream("C:\\Users\\lixiaohao.ZZGRP\\Desktop\\temp\\test.jpg"));
            logger.info("result:{}"+isStore);
            //断开连接
            ftp.disconnect();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

//创建文件夹
    @Test
    public void createDir(){
        try {
            Boolean cFlag = ftp.makeDirectory("aabbcc");
            logger.info("result{}"+cFlag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//创建文件
    @Test
    public void makdFile(){
        String fileName = "20161027.txt";
        File file = new File(fileName);
        try {
            InputStream inputStream = new FileInputStream(file);
        Boolean flag = ftp.storeFile(fileName,inputStream);
        logger.info("result{}"+flag);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //创建ftp
        FtpClient ftp = null;
        try {
            //创建地址
            SocketAddress addr = new InetSocketAddress("192.168.0.136", 21);
            //连接
            ftp = FtpClient.create();
            ftp.connect(addr);
            //登陆
            ftp.login("lixiaohao", "1234567".toCharArray());

//            ftp.setBinaryType();
        } catch (FtpProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
