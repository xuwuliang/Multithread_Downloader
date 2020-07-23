package Threads;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class Update_progress extends Thread {// 更新下载进度
	private List<Download_Thread> list = new ArrayList<Download_Thread>();
	private ProgressBar progress;
	private long bytes;
	long sum = 0;
	private Label speed;
	private long lastbyte = 0;//记录字节数
	private long lasttime=0;//记录时间
	private long nowbyte = 0;//记录字节数
	private long nowtime=0;//记录时间

	public Update_progress(List<Download_Thread> list, ProgressBar progress, long bytes,Label speed) {
		super();
		this.list = list;
		this.progress = progress;
		this.bytes = bytes;
		this.speed = speed;
	}

	public void run() {
		Task task = new Task<String>() {

			@Override
			public void run() {

				updateProgress(sum, bytes);
				if(sum >= bytes){
					updateValue("下载成功");
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
		sum = 0;
		for (Download_Thread t : list) {
			sum += t.getDownloaded();
		}
		lastbyte=sum;
		lasttime = System.currentTimeMillis();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
		}

		while (true) {
			sum = 0;
			for (Download_Thread t : list) {
				sum += t.getDownloaded();
			}
			nowbyte=sum;
			nowtime = System.currentTimeMillis();
			task.run();
			if (sum >= bytes) {
				break;
			}
			sum = 0;
			for (Download_Thread t : list) {
				sum += t.getDownloaded();
			}
			lastbyte=sum;
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
