package Threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class Download_Thread extends Thread {
	private long startByte;// 起始位置
	private long downloadedBytes; // 下载字节数
	private String path;// 文件保存的路径
	private String name;// 文件名
	private int Thread_num;// 下载线程数
	private long downloaded;// 已经下载字节数

	public Download_Thread(long startByte, long downloadedBytes, String path, String name, int thread_num) {
		super();
		this.downloaded = 0;
		this.startByte = startByte;
		this.downloadedBytes = downloadedBytes;
		this.path = path;
		this.name = name;
		this.Thread_num = thread_num;
		System.out
				.println("客户端:下载线程：文件名：" + name + " 起始位置:" + startByte + " 下载字节数：" + downloadedBytes + " 下载路径：" + path);
	}

	public void run() {
		Socket s;

		PrintWriter pw;
		try {

			s = new Socket(InetAddress.getByName("localhost"), 3458);
			InputStream in = s.getInputStream();
			pw = new PrintWriter(s.getOutputStream());
			pw.println(name);
			pw.println(startByte);
			pw.println(downloadedBytes);
			pw.flush();
			RandomAccessFile write = new RandomAccessFile(path + "/" + name, "rwd");
			write.seek(startByte);
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				write.write(b, 0, len);
				downloaded += len;
			}
			write.close();
			s.close();
			System.out.println(" " + name + "：起始为" + startByte + " 部分下载成功");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// DownloadTask.setProgress(progress, Thread_num);;
	}

	public long getDownloaded() {
		return downloaded;
	}

}
