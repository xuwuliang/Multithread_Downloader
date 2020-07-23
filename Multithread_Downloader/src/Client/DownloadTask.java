package Client;

import java.util.ArrayList;
import java.util.List;

import Threads.Download_Thread;
import Threads.Update_progress;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
public class DownloadTask {//下载任务
	private String name;//文件名
	private int Thread_num;//下载线程数
	private long bytes;//文件总·字节数
	private ProgressBar progress;//进度
	private String path;//下载的文件路径
	private Label speed;


	public DownloadTask(String name, int thread_num, long bytes, ProgressBar progress, String path,Label speed) {
		this.name = name;
		Thread_num = thread_num;
		this.bytes = bytes;
		this.progress = progress;
		this.path = path;
		this.speed = speed;
	}
	public void Download(){
		List<Download_Thread>  list = new ArrayList<Download_Thread>();//存放下载线程
		if(Thread_num==1){
			Download_Thread d=new Download_Thread(0, bytes, path, name, Thread_num);
			d.start();
			list.add(d);
			 
		}else{
			long length = (bytes / Thread_num) + 1; 
			for(int i=0;i<Thread_num;i++){
				if(i!=Thread_num-1){
					Download_Thread d = new Download_Thread(i * length, length, path, name, Thread_num);
					d.start();
					list.add(d);
				}else{
					Download_Thread d = new Download_Thread(i * length, bytes - i * length, path, name, Thread_num);
					d.start();
					list.add(d);
				}
			
			}
		}
		Update_progress u = new Update_progress(list, progress, bytes,speed);
		u.start();
	}


}
