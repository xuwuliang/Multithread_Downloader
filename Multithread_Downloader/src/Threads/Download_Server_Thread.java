package Threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

public class Download_Server_Thread extends Thread {
	private Socket s = null;
	private String path = "ServerFile/";// 服务器存放文件的地址

	public Download_Server_Thread(Socket s) {
		super();
		this.s = s;
	}

	public void run() {
		BufferedReader bin;
		try {
			bin = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String name = bin.readLine();
			OutputStream out = s.getOutputStream();
			long startByte = Long.parseLong(bin.readLine());
			long downloadedBytes = Long.parseLong(bin.readLine());
			long endByte = startByte + downloadedBytes - 1;
			System.out.println("服务端:下载线程：文件名：" + name + " 起始位置:" + startByte + " 下载字节数：" + downloadedBytes);
			byte[] b = new byte[1024];
			RandomAccessFile read = new RandomAccessFile(path + name, "rwd");
			read.seek(startByte);
			int num = (int) (downloadedBytes / 1024) + 1;
			if (downloadedBytes <= 1024) {
				read.read(b, 0, (int) downloadedBytes);
				read.close();
				out.write(b, 0, (int) downloadedBytes);
			} else {
				for (int i = 0; i < num; i++) {
					if (startByte + 1023 <= endByte) {
						read.read(b, 0, 1024);
						out.write(b, 0, 1024);
					} else {
						long len = endByte - startByte + 1;
						read.read(b, 0, (int) len);
						out.write(b, 0, (int) len);
					}
					startByte += 1024;
				}
			}
			read.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
