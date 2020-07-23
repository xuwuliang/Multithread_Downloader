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

public class Update_upload extends Thread {// 更新上传进度
	// private Label progress ;
	private Upload_Thread u;
	private long bytesize = 0;// 总字节数
	private Task task;// 用于更新进度条
	private ProgressBar progress;
	private Label speed;
	private long lastbyte = 0;//记录字节数
	private long lasttime=0;//记录时间
	private long nowbyte = 0;//记录字节数
	private long nowtime=0;//记录时间

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
					updateValue("上传成功");
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
			//System.out.println("时间差:"+(nowtime.getTime()-lasttime.getTime())+" nowtime:"+nowtime.getTime()+" lasttime:"+lasttime.getTime());
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
