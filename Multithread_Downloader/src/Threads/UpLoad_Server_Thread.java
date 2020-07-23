package Threads;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class UpLoad_Server_Thread extends Thread {// 上传文件服务端线程
	private Socket s = null;
	private String path = "ServerFile/";// 服务器存放文件的地址

	public UpLoad_Server_Thread(Socket s) {
		super();
		this.s = s;
	}

	public void run() {
		InputStream in = null;
		byte[] b = new byte[1024];
		try {
			in = s.getInputStream();
			OutputStream out = s.getOutputStream();
			DataInputStream dis = new DataInputStream(in);
			int l = in.read(b);
			String filename = new String(b, 0, l);
			String ok = "ok";
			out.write(ok.getBytes());
			byte[] bytes = new byte[1024];
			// 输出文件

			// 输入数据
			int len = 0;
			FileOutputStream fout = new FileOutputStream(path + filename, true);
			while ((len = in.read(bytes)) != -1) {
				fout.write(bytes, 0, len);
			}
			fout.close();
			s.close();
			System.out.println("文件上传完成");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
