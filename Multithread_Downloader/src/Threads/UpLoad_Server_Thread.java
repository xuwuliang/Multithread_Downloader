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

public class UpLoad_Server_Thread extends Thread {// �ϴ��ļ�������߳�
	private Socket s = null;
	private String path = "ServerFile/";// ����������ļ��ĵ�ַ

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
			// ����ļ�

			// ��������
			int len = 0;
			FileOutputStream fout = new FileOutputStream(path + filename, true);
			while ((len = in.read(bytes)) != -1) {
				fout.write(bytes, 0, len);
			}
			fout.close();
			s.close();
			System.out.println("�ļ��ϴ����");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
