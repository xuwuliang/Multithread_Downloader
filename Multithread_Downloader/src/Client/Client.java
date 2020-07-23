package Client;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.MenuStage;
import ui.downloadStage;
import ui.uploadStage;

public class Client extends Application{
	private MenuStage m = new MenuStage();
	private downloadStage download ;
	private  uploadStage upload ;
	@Override
	public void start(Stage arg0) throws Exception {
		
		m.show();
		m.getDownload().setOnAction(e->{
			download = new downloadStage();
			download.show();
			m.close();
			download.getExit().setOnAction(e1->{
				m.show();
				download.close();
				
			});
		});
		m.getUpload().setOnAction(e->{
			upload=new uploadStage();
			upload.show();
			m.close();
			upload.getExit().setOnAction(e1->{
				m.show();
				upload.close();

			});
		});
		
	}
	public static void main(String[] args) {
		launch(args);
	}

}
