package ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuStage extends Stage {
	private Pane p = new Pane();
	private Button upload = new Button(" ио ╢╚ ");
	private Button download = new Button(" об ть ");

	public MenuStage() {
		upload.setPrefSize(150, 50);
		upload.setFont(new Font(25));
		upload.setTranslateX(220);
		upload.setTranslateY(90);
		download.setFont(new Font(25));
		download.setPrefSize(150, 50);
		download.setTranslateX(220);
		download.setTranslateY(170);
		p.getChildren().addAll(upload, download);
		Scene s = new Scene(p, 600, 400);
		this.setScene(s);

	}

	public Button getUpload() {
		return upload;
	}

	public Button getDownload() {
		return download;
	}

}
