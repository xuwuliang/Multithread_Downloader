package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.filechooser.FileSystemView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import Bean.filedownload;
import Client.DownloadTask;

public class downloadStage extends Stage {

	private Pane p = new Pane();
	private Label pathhint = new Label("��ѡ���ļ������·��:");
	private Label l1 = new Label("�����߳���(���Ե��������ٶȣ��߳���Խ�������ٶ�Խ��):");
	private TextField path = new TextField("");
	private Button pathchoose = new Button("ѡ��");
	private ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("1", "2", "3", "4", "5"));
	private Button exit = new Button(" �� �� ");
	private final TableView<filedownload> table = new TableView<>();

	public downloadStage() {
		pathhint.setTranslateX(70);
		pathhint.setTranslateY(25);
		path.setTranslateX(200);
		path.setTranslateY(20);
		path.setPrefWidth(270);
		pathchoose.setTranslateX(470);
		pathchoose.setTranslateY(20);
		l1.setTranslateX(70);
		l1.setTranslateY(65);
		cb.setTranslateX(400);
		cb.setTranslateY(60);
		exit.setTranslateX(460);
		exit.setTranslateY(60);
		// table.setEditable(true);
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File home = fsv.getHomeDirectory();
		String homePath = home.getPath();
		path.setText(homePath);
		TableColumn NameCol = new TableColumn("�ļ���");
		NameCol.setMinWidth(145);
		NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn SizeCol = new TableColumn("�ļ���С");
		SizeCol.setMinWidth(80);
		SizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
		TableColumn byCol = new TableColumn("�ֽ���");
		byCol.setMinWidth(80);
		byCol.setCellValueFactory(new PropertyValueFactory<>("bytes"));

		TableColumn downloadCol = new TableColumn("����");
		downloadCol.setMinWidth(100);
		downloadCol.setCellValueFactory(new PropertyValueFactory<>("download"));
		TableColumn progressCol = new TableColumn("���ؽ���");
		progressCol.setMinWidth(100);
		progressCol.setCellValueFactory(new PropertyValueFactory<>("progress"));
		TableColumn speedCol = new TableColumn("�����ٶ�");
		speedCol.setMinWidth(80);
		speedCol.setCellValueFactory(new PropertyValueFactory<>("speed"));
		table.getColumns().addAll(NameCol, SizeCol, byCol, downloadCol, progressCol,speedCol);
		table.setTranslateX(5);
		table.setTranslateY(90);
		table.setPrefHeight(300);
		get_All_Files();
		set_download_event();
		p.getChildren().addAll(pathhint, path, pathchoose, l1, cb, exit, table);
		Scene s = new Scene(p, 600, 400);
		this.setScene(s);
		this.setTitle("���ؿͻ���");
		cb.setValue("1");
		pathchoose.setOnAction(e -> {

			try {
				DirectoryChooser directoryChooser = new DirectoryChooser();
				File file = directoryChooser.showDialog(new Stage());
				String pat = file.getPath();// ѡ����ļ���·��
				path.setText(pat.replaceAll("\\\\", "/"));
			} catch (NullPointerException e1) {
				// System.out.println("û��ѡ��·��");
			}

		});
	}

	public Button getExit() {
		return exit;
	}

	public void get_All_Files() {
		Socket s;
		BufferedReader bin;
		PrintWriter pw;

		try {

			s = new Socket(InetAddress.getByName("localhost"), 3457);

			bin = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String name = null;
			ObservableList<filedownload> data = FXCollections.observableArrayList();
			System.out.println("���п������ص��ļ��б�");
			while ((name = bin.readLine()) != null) {
				String size = bin.readLine();
				String bytes = bin.readLine();
				data.add(new filedownload(name, size, bytes));
				System.out.println(name + "           " + size + "           " + bytes);
			}
			table.setItems(data);
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void set_download_event() {
		for (int i = 0; i < table.getItems().size(); i++) {
			int j = i;
			table.getItems().get(j).getDownload().setOnAction(e -> {
				// table.getItems().get(j).getProgress().setProgress(0);
				System.out.println("���ص��ļ�Ϊ��" + table.getItems().get(j).getName() + "  "
						+ table.getItems().get(j).getSize() + "  " + table.getItems().get(j).getBytes());
				System.out.println("�����߳���:" + (cb.getSelectionModel().getSelectedIndex() + 1));
				DownloadTask d = new DownloadTask(table.getItems().get(j).getName(),
						(cb.getSelectionModel().getSelectedIndex() + 1),
						Long.parseLong(table.getItems().get(j).getBytes()), table.getItems().get(j).getProgress(),
						path.getText(),table.getItems().get(j).getSpeed());
				d.Download();
			});
		}

	}
}
