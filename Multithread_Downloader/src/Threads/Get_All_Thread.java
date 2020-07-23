package Threads;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import Bean.Server_File;

public class Get_All_Thread extends Thread {// 获得服务端所有可下载的文件
	private Socket s = null;
	private String path = "ServerFile";// 服务器存放文件的地址

	public Get_All_Thread(Socket s) {
		super();
		this.s = s;
	}

	public void run() {
		BufferedReader br;
		PrintWriter pw;
		try {
			while (true) {
				pw = new PrintWriter(s.getOutputStream());
				List<Server_File> files = getFiles(path);
				System.out.println("文件个数：" + files.size());
				for (Server_File file : files) {
					pw.println(file.getName());
					pw.println(file.getSize());
					pw.println(file.getBytes());
					System.out.println(file.getName() + "     " + file.getSize() + "      " + file.getBytes());
				}
				pw.flush();
				s.close();
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public static List<Server_File> getFiles(String path) {
		List<Server_File> files = new ArrayList<Server_File>();
		File file = new File(path);
		File[] tempList = file.listFiles();

		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				String fileName = tempList[i].getName();
				long length = tempList[i].length();
				String size = null;
				DecimalFormat df = new DecimalFormat("0.00");// 格式化小数
				System.out.println(length);
				if (length < (1024 * 1024)) {
					size = df.format((float) length / 1024) + "KB";
				} else {
					size = df.format((float) length / (1024 * 1024)) + "MB";
				}
				files.add(new Server_File(fileName, size, length + ""));
			}
		}
		return files;
	}

}
