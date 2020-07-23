package Threads;

import java.io.File;
import java.util.Date;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.Duration;
import ui.uploadStage;

public class Update_upload extends Thread {// �����ϴ�����
	// private Label progress ;
	private Upload_Thread u;
	private long bytesize = 0;// ���ֽ���
	private Task task;// ���ڸ��½�����
	private ProgressBar progress;
	private Label speed;
	private long lastbyte = 0;//��¼�ֽ���
	private long lasttime=0;//��¼ʱ��
	private long nowbyte = 0;//��¼�ֽ���
	private long nowtime=0;//��¼ʱ��

	public Update_upload(Upload_Thread u, ProgressBar progress, long bytesize,Label speed) {
		super();
		this.u = u;
		this.bytesize = bytesize;
		this.progress = progress;
		this.speed = speed;
	}

	public void run() {
		task = new Task<String>() {
			@Override
			public void run() {
				updateProgress(u.getPro(), bytesize);
				if(u.getPro() >= bytesize){
					updateValue("�ϴ��ɹ�");
				}else{
				updateValue(((nowbyte-lastbyte)*1000)/((nowtime-lasttime)*1024)+"KB/S");
				}
				
			}

			@Override
			protected String call() throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		};

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				progress.progressProperty().bind(task.progressProperty());
				speed.textProperty().bind(task.valueProperty());
			}
		});
		lastbyte=u.getPro();
		lasttime = System.currentTimeMillis();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
		}
		while (true) {
			nowbyte=u.getPro();
			nowtime = System.currentTimeMillis();
			task.run();
			//System.out.println("ʱ���:"+(nowtime.getTime()-lasttime.getTime())+" nowtime:"+nowtime.getTime()+" lasttime:"+lasttime.getTime());
			if (u.getPro() >= bytesize) {
				break;
			}
			lastbyte=u.getPro();
			lasttime = System.currentTimeMillis();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		task.run();
	}

}
