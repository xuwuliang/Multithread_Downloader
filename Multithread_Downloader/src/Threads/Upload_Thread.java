package Threads;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Upload_Thread extends Thread{//�ϴ��ļ��߳�
	private String filename;//�ļ���
	private long bytesize ;//���ֽ���
	private long pro ;//�Ѿ��ϴ��ֽ���
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
			System.out.println("�ϴ��ļ���С:" + bytesize);
			OutputStream out = s.getOutputStream();
			byte[] name = filename.getBytes();
			out.write(name, 0, name.length);
			InputStream in = s.getInputStream();// ����˽��յ��ļ������ظ�ֵ
			in.read();//ȷ������˽��յ��ļ���

			FileInputStream fin = new FileInputStream(new File(path));
			// �������ݰ�
			byte[] bytes = new byte[1024];
			// ��������
			int len = 0;

			while ((len = fin.read(bytes)) != -1) {
				//System.out.println("�ϴ�����:" + (pro * 100 / bytesize) + "%");
				out.write(bytes, 0, len);
				pro += len;
			}

			out.flush();
			fin.close();
			s.close();
			System.out.println("�ļ��ϴ����");
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
