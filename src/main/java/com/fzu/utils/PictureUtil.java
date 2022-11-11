package com.fzu.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.OutputStream;

@Component
public class PictureUtil {
    @Value("${esc.username}")
    private String username;

    @Value("${esc.ip}")
    private String ip;

    @Value("${esc.password}")
    private String password;


    @Value("${esc.picture.path}")
    private String filepath;

    /**
     * 利用JSch包实现SFTP上传文件
     *
     * @param bytes    文件字节流
     * @param fileName 文件名
     * @throws Exception
     */
    public void sshSftp(byte[] bytes, String fileName) throws Exception {
        int port = 22;
        Session session = null;
        Channel channel = null;
        JSch jSch = new JSch();
        if (port <= 0) {
            //连接服务器，采用默认端口
            session = jSch.getSession(username, ip);
        } else {
            //采用指定的端口连接服务器
            session = jSch.getSession(username, ip, port);
        }
        //如果服务器连接不上，则抛出异常
        if (session == null) {
            throw new Exception("session is null");
        }
        //设置登陆主机的密码
        session.setPassword(password);//设置密码
        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("userauth.gssapi-with-mic", "no");
        session.setConfig("StrictHostKeyChecking", "no");
        //设置登陆超时时间
        session.connect(30000);
        OutputStream outstream = null;
        try {
            //创建sftp通信通道
            channel = (Channel) session.openChannel("sftp");
            channel.connect(1000);
            ChannelSftp sftp = (ChannelSftp) channel;
            //进入服务器指定的文件夹
            sftp.cd(filepath);
            //以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换一下流就可以了
            outstream = sftp.put(fileName);
            outstream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关流操作
            if (outstream != null) {
                outstream.flush();
                outstream.close();
            }
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
    }
}
