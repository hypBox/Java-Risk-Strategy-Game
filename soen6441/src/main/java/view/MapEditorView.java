/**
 * 
 */
package view;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import controller.ReadController;
import controller.WriteController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * @author Team15
 *
 */
public class MapEditorView {
	
	private Button closeButton;	
	private static Scene editorScene = null;
	private ReadController readController = null;
	private WriteController writeController = null;
	private static String country,continent = null; 
	
	private Button startGame = new Button();
	TextInputDialog dialog = new TextInputDialog();

	
	public MapEditorView(ReadController new_readController,WriteController new_writeController) {
		readController = new_readController;
		writeController = new_writeController;
	}



	public Scene getMapEditorView(){
		 ObservableList<String> continents = FXCollections.observableArrayList();		 
		 ObservableList<String> contries = FXCollections.observableArrayList();
		 
		 continents.addAll(model.SingletonData.continentValues.keySet());
		 GridPane gridPane = new GridPane();
		 gridPane.setHgap(10);
	     gridPane.setVgap(10);
	     
	     ChoiceBox<String> continentChoiceBox = new ChoiceBox<String>();
	     ChoiceBox<String> contriesChoiceBox = new ChoiceBox<String>();
	     
	     Button  addContent = new Button("Add Continent");	     
	     Button addCountry = new Button("Add Country");	     
	     Button  deleteContent = new Button("Delete Continent");	     
	     Button deleteCountry = new Button("Delete Country");
	     Button saveChanges = new Button("Save Changes");
	     
	     TextField editadjacentContries = new TextField ();
	     editadjacentContries.setPrefWidth(800);
	     TextField editContinentValue = new TextField ();
	     
	     
	     addContent.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				String tmp = getUserInput("Enter continent Value");
				continentChoiceBox.getItems().add(tmp);
			}
		});
	     
	     
	     addCountry.setOnAction(new EventHandler<ActionEvent>() {				
				@Override
				public void handle(ActionEvent event) {
					String tmp = getUserInput("Enter contry Value");
					contriesChoiceBox.getItems().add(tmp);
				}
		 });
	     
	     

	     saveChanges.setOnAction(new EventHandler<ActionEvent>() {
			@Override
	 		public void handle(ActionEvent event) {
	               List<String> continents = new ArrayList<String>();
	               for(String continent: continentChoiceBox.getItems()){
	            	   if(!continent.equals("Continents"))
	            		    continents.add(continent);
	               }
	               List<String> contries = new ArrayList<String>();
	               for(String contry: contriesChoiceBox.getItems()){
	            	   if(!contry.equals("Countries"))
	            		   contries.add(contry);
	               }       
	               writeController.saveData(continents,contries,editadjacentContries.getText(),continent,country,editContinentValue.getText());
			 }
		  });
	     
	     continentChoiceBox.setTooltip(new Tooltip("Select the Continent"));
	     continentChoiceBox.getItems().add("Continents");
	     continentChoiceBox.setValue("Continents");
	     continentChoiceBox.getItems().addAll(continents);
	     
	     
	     
	     continentChoiceBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue observable, String oldValue, String newValue){
				continent = newValue;
				ArrayList<String> territories = readController.getTerritoriesNames(continent);
				contries.clear();
				contriesChoiceBox.getItems().clear();
				if(territories != null){
					contries.addAll(territories);
				}
				editContinentValue.setText(""+readController.getContinentValue(continent));
				editadjacentContries.setText("");
				contriesChoiceBox.getItems().add("Countries");			    
				contriesChoiceBox.getItems().addAll(contries);
				contriesChoiceBox.setValue("Countries");
			}
		});

	     
	    contriesChoiceBox.valueProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue observable, String oldValue, String newValue) {
					if(newValue != null && !newValue.equals("Countries")){
						country = newValue;
						editadjacentContries.setText(readController.getAdjacentTerritories(continent, country).toString().replaceAll(" ", ""));
					}
				}
	     });
	     
	    
	     BorderPane borderPane = new BorderPane();

	     Image imageOk = new Image(getClass().getResourceAsStream("/icons8-Back Arrow-35.png")); 
	     closeButton = new Button();
	     getCloseButton().setGraphic(new ImageView(imageOk));
	     
	     gridPane.add(continentChoiceBox,0,0);
	     gridPane.add(contriesChoiceBox,1,0);
	     gridPane.add(editContinentValue, 2, 0);
	     
	     gridPane.add(editadjacentContries, 0, 1,3,1);
	     
	     gridPane.add(addContent, 0,2);
	     gridPane.add(addCountry, 1,2);
	     gridPane.add(deleteContent, 2,2);
	     gridPane.add(deleteCountry, 3,2);
	     
	     
         ToolBar header = new ToolBar(closeButton);
         header.setStyle( 
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 0 0 1 0;" +  
                "-fx-border-color: black;");
         gridPane.setStyle("-fx-padding:10");
         borderPane.setTop(header);
         HBox footer = new HBox();
         
         footer.setStyle( 
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 1 0 0 0;" +
                "-fx-padding:10;"+
                "-fx-border-color: black;");
         gridPane.setStyle("-fx-padding:10");
	     startGame = new Button();
	     startGame.setText("Start Game");
	     footer.setAlignment(Pos.CENTER_RIGHT);
	     footer.setPadding(new Insets(15, 12, 15, 12));
	     footer.getChildren().addAll(startGame,saveChanges);
         
	     borderPane.setBottom(footer);
	     borderPane.setCenter(gridPane);
	     editorScene = new Scene(borderPane, 500, 500);
	     return editorScene;		
	}



	/**
	 * @return the closeButton
	 */
	public Button getCloseButton() {
		return closeButton;
	}


	/**
	 * @return the startGame
	 */
	public Button getStartGame() {
		return startGame;
	}


	/**
	 * @param question displays the question
	 * @return the input text given in dialog
	 */
	
	public String getUserInput(String question){
		dialog.setHeaderText(question);
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
				return result.get();
		}
		return null;
	}
	
}
