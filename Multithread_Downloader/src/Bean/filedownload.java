package Bean;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Font;

public class filedownload {
	 private  String name;
    private  String size;
    private String bytes;
    private Button download =new Button("обть");
    private ProgressBar progress = new ProgressBar(0);
    private Label speed = new Label("0KB/S");
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Button getDownload() {
		return download;
	}
	public void setDownload(Button download) {
		this.download = download;
	}
	public String getBytes() {
		return bytes;
	}
	public ProgressBar getProgress() {
		return progress;
	}
	public void setProgress(ProgressBar progress) {
		this.progress = progress;
	}
	public void setBytes(String bytes) {
		this.bytes = bytes;
	}
	
	public Label getSpeed() {
		return speed;
	}
	public void setSpeed(Label speed) {
		this.speed = speed;
	}
	public filedownload(String name, String size,  String bytes) {
		super();
		this.name = name;
		this.size = size;
		this.bytes = bytes;
		progress.setPrefWidth(90);
		download.setFont(new Font(9));
		download.setPrefWidth(100);
	}
    
  
}
