package ui;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.sun.glass.ui.CommonDialogs.ExtensionFilter;

import Threads.Update_upload;
import Threads.Upload_Thread;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class uploadStage extends Stage {

	private Pane p = new Pane();
	private Label pathhint = new Label("��ѡ���ϴ��ļ���·��:");
	private TextField path = new TextField("");
	private Button pathchoose = new Button("ѡ��");
	private Button up = new Button(" �� �� ");
	private Label progress = new Label("�ϴ�����");
	private ProgressBar pb = new ProgressBar(0);
	private Button exit = new Button(" �� �� ");
	private long pro = 0;// �Ѿ��ϴ��ֽ���
	private long bytesize = 0;// ���ֽ���
	private Label speed = new Label("0KB/S");

	public uploadStage() {
		pathhint.setTranslateX(70);
		pathhint.setTranslateY(65);
		path.setTranslateX(200);
		path.setTranslateY(60);
		path.setPrefWidth(270);
		pathchoose.setTranslateX(470);
		pathchoose.setTranslateY(60);
		progress.setTranslateX(200);
		progress.setTranslateY(120);
		progress.setFont(new Font(18));
		up.setTranslateX(200);
		up.setTranslateY(170);
		up.setFont(new Font(15));
		exit.setTranslateX(330);
		exit.setTranslateY(170);
		exit.setFont(new Font(15));
		pb.setTranslateX(280);
		pb.setTranslateY(125);
		speed.setTranslateY(123);
		speed.setTranslateX(390);
		speed.setFont(new Font(15));
		p.getChildren().addAll(pathhint, path, pathchoose, progress, up, exit, pb,speed);
		Scene s = new Scene(p, 600, 400);
		this.setScene(s);
		this.setTitle("�ϴ���");
		pathchoose.setOnAction(e -> {

			try {
				pro = 0;
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("ѡ���ļ�");
				File file = fileChooser.showOpenDialog(new Stage());
				String pat = file.getPath();// ѡ����ļ�·��
				path.setText(pat.replaceAll("\\\\", "/"));
			} catch (NullPointerException e1) {
				// System.out.println("û��ѡ��·��");
			}
		});
		up.setOnAction(e -> {
			File f = new File(path.getText());
			String filename = f.getName();
			bytesize = f.length();
			Upload_Thread u = new Upload_Thread(filename, bytesize, path.getText());
			u.start();
			new Update_upload(u, pb, bytesize,speed).start();
		});
	}

	public Button getExit() {
		return exit;
	}

	public void setExit(Button exit) {
		this.exit = exit;
	}

	public long getPro() {
		return pro;
	}

	public void setPro(long pro) {
		this.pro = pro;
	}

	public ProgressBar getPb() {
		return pb;
	}

}
