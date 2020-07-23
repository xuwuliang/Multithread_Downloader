package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import Threads.Download_Server_Thread;
import Threads.Get_All_Thread;
import Threads.UpLoad_Server_Thread;
import ui.uploadStage;

public class Server {
	/*
	 * 端口号3456：上传文件
	 * 端口号3457：得到所有文件信息
	 * 端口号3458：下载文件
	 */
	private static int port1 = 3456;
	private static int port2 = 3457;
	private static int port3 = 3458;
	public static void main(String[] args) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				ServerSocket ss;
				Socket s;
				try {
					ss = new ServerSocket(port1);
					while (true) {
						s = ss.accept();
						new UpLoad_Server_Thread(s).start();
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				ServerSocket ss;
				Socket s;
				try {
					ss = new ServerSocket(port2);
					while (true) {
						s = ss.accept();
						new Get_All_Thread(s).start();
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				ServerSocket ss;
				Socket s;
				try {
					ss = new ServerSocket(port3);
					while (true) {
						s = ss.accept();
						new Download_Server_Thread(s).start();
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}).start();
	}

}
