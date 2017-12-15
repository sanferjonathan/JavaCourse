package lab2;
import java.awt.Button;
import java.util.Stack;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.*;

public class RandomBall extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
//		Circle c = new Circle();
//		Group root = new Group(c);
//		Scene scene = new Scene(root,100,100);
//
//		c.centerXProperty().bind(
//		c.centerYProperty().bind(...
//		c.radiusProperty().bind(...
//
//		stage.setTitle("Binding in JavaFX");
//		stage.setScene(scene);
//		stage.sizeToScene();
//		stage.show();
		
		stage.setTitle("Hello!");
		Button button = new Button("click me!");
		
		StackPane layout = new StackPane();
		layout.getChildren().add(button);
	}
}
