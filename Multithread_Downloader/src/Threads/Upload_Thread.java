package Threads;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Upload_Thread extends Thread{//上传文件线程
	private String filename;//文件名
	private long bytesize ;//总字节数
	private long pro ;//已经上传字节数
	private String path;

	public Upload_Thread(String filename, long bytesize, String path) {
		super();
		this.filename = filename;
		this.bytesize = bytesize;
		this.path = path;
	}
	public void run(){
		try {
			Socket s = new Socket(InetAddress.getByName("localhost"), 3456);
			System.out.println("上传文件大小:" + bytesize);
			OutputStream out = s.getOutputStream();
			byte[] name = filename.getBytes();
			out.write(name, 0, name.length);
			InputStream in = s.getInputStream();// 服务端接收到文件名返回该值
			in.read();//确定服务端接收到文件名

			FileInputStream fin = new FileInputStream(new File(path));
			// 设置数据包
			byte[] bytes = new byte[1024];
			// 输入数据
			int len = 0;

			while ((len = fin.read(bytes)) != -1) {
				//System.out.println("上传进度:" + (pro * 100 / bytesize) + "%");
				out.write(bytes, 0, len);
				pro += len;
			}

			out.flush();
			fin.close();
			s.close();
			System.out.println("文件上传完成");
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	public long getPro() {
		return pro;
	}
	

}
