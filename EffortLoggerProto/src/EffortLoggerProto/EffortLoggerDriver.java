package EffortLoggerProto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class EffortLoggerDriver extends Application {
	String cdir;
	public static void main(String[] args) {
		launch(args);
	}

	// login screen stage
	public void start(Stage primaryStage) {
		System.out.println("Effort Logger Console Started!");
		primaryStage.setTitle("Effort Logger Prototype");
		File file = new File("credentials.txt");

		if (!file.exists()) {
			try {

				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Create text fields for ID and password with black text and white background
		TextField idTextField = new TextField();
		idTextField.setPromptText("ID");
		idTextField.setStyle("-fx-text-fill: white; -fx-control-inner-background: black; -fx-border-color: white;");
		TextField passwordTextField = new TextField();
		passwordTextField.setPromptText("Password");
		passwordTextField
		.setStyle("-fx-text-fill: white; -fx-control-inner-background: black; -fx-border-color: white;");

		// Create labels for text fields with white text
		Label idLabel = new Label("ID:");
		Label passwordLabel = new Label("Password:");
		idLabel.setTextFill(Color.WHITE);
		passwordLabel.setTextFill(Color.WHITE);

		// Creates label for red invalid id input
		Label warningLabel = new Label("Invalid ID");
		warningLabel.setTextFill(Color.RED);
		warningLabel.setVisible(false); // Initially, hide the warning label

		// Create a grid pane to organize the elements with padding
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));
		grid.add(idLabel, 0, 0);
		grid.add(idTextField, 1, 0);
		grid.add(passwordLabel, 0, 1);
		grid.add(passwordTextField, 1, 1);
		grid.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY))); // Set the
		// background
		// to
		// black

		// Create a layout for centering
		BorderPane root = new BorderPane();
		root.setCenter(grid);
		root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		// Add the warning label to the GridPane
		grid.add(warningLabel, 1, 2);

		// sets up login button and includes conditions
		Button loginButton = new Button("Login");
		loginButton.setMinWidth(Button.USE_PREF_SIZE);
		loginButton.setStyle("-fx-text-fill: white; -fx-background-color: black; -fx-border-color: white;");
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				String id = idTextField.getText();
				String password = passwordTextField.getText();

				// hashes login user-input to later be compared to hashed stored data
				PasswordValidation.hashPassword(id, password);

				// variable for input validation
				InputValidator iv = new InputValidator("");

				// checks if login user-input is valid
				if (/*
				 * EffortLoggerPermissions.isIdValid(id) == true &&
				 * EffortLoggerPermissions.isPasswordValid(password) == true && iv.checkType(id)
				 * == 5
				 */ true) {
					// checks if login user-input matches
					if (/*
					 * EncryptionModule.fileReadAndValidation(id,
					 * PasswordValidation.hashPassword(id, password), "credentials.txt") ==
					 */true) {

						// Close the previous window
						Stage oldStage = (Stage) loginButton.getScene().getWindow();
						oldStage.close();

						// checks if isSupervisor is true
						if (EffortLoggerPermissions.isSupervisor(id)) {
							// Create a new stage for the new window
							Stage newStage = new Stage();
							newStage.setTitle("Supervisor Landing"); // Set the title of the new window

							// Create the content for the new window (e.g., a label)
							Label newLabel = new Label("Welcome to the new window!");
							newLabel.setTextFill(Color.WHITE);

							// Create a layout for the content
							BorderPane newRoot = new BorderPane();
							newRoot.setCenter(newLabel);

							// Create a scene for the new window
							Scene newScene = new Scene(newRoot, 400, 300);
							newScene.setFill(Color.BLACK);

							// Set the scene for the new stage
							newStage.setScene(newScene);

							// Show the new window
							newStage.show();
						} else { // if user is not supervisor, they are employee
							// Create a new stage for the new window
							Stage newStage = new Stage();
							newStage.setTitle("Employee Landing"); // Set the title of the new window

							// Create the content for the new window based on the image
							GridPane gridPane = new GridPane();

							// Welcome Label with placeholder for Employee Name
							Label welcomeLabel = new Label("Welcome [Employee Name]!");
							welcomeLabel.setFont(Font.font("Verdana", 20));
							welcomeLabel.setTextFill(Color.WHITE);

							// Dropdowns for "Choose Available Project" and "Choose Action"
							ComboBox<String> projectDropdown = new ComboBox<>();
							projectDropdown.setPromptText("Choose Available Project:");
							projectDropdown.setPrefWidth(200);
						
							Label actionDropdown = new Label("Choose Action:");
							actionDropdown.setTextFill(Color.WHITE);
							

							// Buttons for "Planning Poker", "Edit/View Tasks", and "Logout"
							Button planningPokerBtn = new Button("Planning Poker");
							planningPokerBtn.setOnAction(e -> showPlanningPoker() );
							Button editViewTasksBtn = new Button("Edit/View Tasks");
							editViewTasksBtn.setOnAction(e -> showTaskListView());
							Button logoutBtn = new Button("Logout");

							// Add DropDown to the first column, first row
							gridPane.add(projectDropdown, 0, 0);

							// Organizing components using VBox and HBox layouts
							VBox vbox = new VBox(10); // reduced spacing between elements
							vbox.setAlignment(Pos.BASELINE_LEFT);
							vbox.setPadding(new Insets(50, 50, 50, 50)); // increased padding to center elements more
							vbox.getChildren().addAll(welcomeLabel, gridPane, actionDropdown, planningPokerBtn,
									editViewTasksBtn, logoutBtn);

							// Creating a new BorderPane for this interface
							BorderPane employeeRoot = new BorderPane();
							employeeRoot.setCenter(vbox);
							employeeRoot.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
							Scene employeeScene = new Scene(employeeRoot, 400, 300);
							employeeScene.setFill(Color.BLACK);

							// Set the scene for the new stage
							newStage.setScene(employeeScene);

							// Show the new window
							newStage.show();
						}
					} else {
						warningLabel.setVisible(true);
					}
				} else {
					warningLabel.setVisible(true);
				}
			}

			// scene setup for planning poker data
			public void showPlanningPoker() {
				Label headerLabel = new Label("Planning Poker");
				headerLabel.setFont(Font.font("Verdana", 20));
				headerLabel.setTextFill(Color.WHITE);

				// Fields for Task Name and Project
				
			
				ComboBox<String> taskNameField = new ComboBox<>();
				taskNameField.setPromptText("[Task Name]");
				
				TextField projectField = new TextField();
				projectField.setPromptText("[Project]");
				
				loadTasksintoComboBox(taskNameField);
				// Left side controls
				Button generateCardBtn = new Button("Generate Card");
				TextField enterWeightField = new TextField();
				enterWeightField.setPromptText("[Enter Weight]");
				Button sendEstimateBtn = new Button("Send Estimate");
				Button backButton = new Button("BACK");
				VBox leftVBox = new VBox(10);
				leftVBox.getChildren().addAll(generateCardBtn, enterWeightField, sendEstimateBtn, backButton);

				// Right side controls
				Label planningCardLabel = new Label("Planning Poker Card");
				planningCardLabel.setFont(Font.font("Verdana", 16));
				planningCardLabel.setTextFill(Color.WHITE);
				TextField rangeField = new TextField();
				rangeField.setPromptText("Range");
				TextField averageField = new TextField();
				averageField.setPromptText("Average");
				TextField discardAvgField = new TextField();
				discardAvgField.setPromptText("Discard Average");
				TextField lowField = new TextField();
				lowField.setPromptText("Low");
				TextField highField = new TextField();
				highField.setPromptText("High");
				VBox rightVBox = new VBox(10);
				rightVBox.getChildren().addAll(planningCardLabel, rangeField, averageField, discardAvgField, lowField,
						highField);

				// Bottom User Story Key Points
				TextField keyPointLabel1 = new TextField();
				TextField keyPointLabel2 = new TextField();
				TextField keyPointLabel3 = new TextField();
				keyPointLabel1.setPromptText("User Story Key Point");
				keyPointLabel2.setPromptText("User Story Key Point");
				keyPointLabel3.setPromptText("User Story Key Point");
				VBox bottomVBox = new VBox(10);
				bottomVBox.getChildren().addAll(keyPointLabel1, keyPointLabel2, keyPointLabel3);

				// Assembling the whole layout
				GridPane gridPane = new GridPane();
				gridPane.add(headerLabel, 0, 0, 2, 1);
				gridPane.add(taskNameField, 0, 1);
				gridPane.add(projectField, 1, 1);
				gridPane.add(leftVBox, 0, 2);
				gridPane.add(rightVBox, 1, 2);
				gridPane.add(bottomVBox, 0, 3, 2, 1);
				gridPane.setHgap(20);
				gridPane.setVgap(10);
				gridPane.setAlignment(Pos.CENTER);
				gridPane.setPadding(new Insets(20));
				gridPane.setStyle("-fx-background-color: black;");

				Scene planningScene = new Scene(gridPane, 600, 500);
				planningScene.setFill(Color.WHITE);

				Stage planningStage = new Stage();
				planningStage.setTitle("Planning Poker");
				planningStage.setScene(planningScene);
				planningStage.show();
				generateCardBtn.setOnAction(new EventHandler<ActionEvent>() {
					// outputs all inputted data to show inputted data is being stored
					public void handle(ActionEvent event) {
						String taskN = taskNameField.getValue();
						System.out.println("Task-to-do: " + taskN + "\n");
						String proj = projectField.getText();
						System.out.println("Project Name: " + proj + "\n");
						String range = rangeField.getText();
						System.out.println("Range: " + range + "\n");
						String avg = averageField.getText();
						System.out.println("Average: " + avg + "\n");
						String discAvg = discardAvgField.getText();
						System.out.println("Discard Average: " + discAvg + "\n");
						String lowF = lowField.getText();
						System.out.println("Low Card: " + lowF + "\n");
						String highF = highField.getText();
						System.out.println("High Card: " + highF + "\n");
						String kPL1 = keyPointLabel1.getText();
						System.out.println("User Story Key Point 1: " + kPL1 + "\n");
						String kPL2 = keyPointLabel2.getText();
						System.out.println("User Story Key Point 2: " + kPL2 + "\n");
						String kPL3 = keyPointLabel3.getText();
						System.out.println("User Story Key Point 3: " + kPL3 + "\n");
					}
				});
			}

		});

		// Create a label for the title with custom font and white text
		Label titleLabel = new Label("Effort Logger");
		titleLabel.setFont(new Font("Lucida Console", 20)); // Set the font to Lucida Console with size 20
		titleLabel.setTextFill(Color.WHITE);
		titleLabel.setPadding(new Insets(20));

		// Create a layout for centering
		root.setTop(titleLabel); // Add the title label at the top
		BorderPane.setAlignment(titleLabel, Pos.CENTER);

		// Place the "Login" button in the bottom right corner
		HBox buttonBox = new HBox(10);
		buttonBox.setPadding(new Insets(10));
		buttonBox.getChildren().add(loginButton);
		buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
		root.setBottom(buttonBox);

		// Set constraints to make text fields take up 50% of the window
		idTextField.setMaxWidth(Double.MAX_VALUE);
		passwordTextField.setMaxWidth(Double.MAX_VALUE);

		// Set the stage to not be resizable
		primaryStage.setResizable(false);

		// Set the background color for the entire scene to black
		Scene scene = new Scene(root, 300, 250);
		scene.setFill(Color.BLACK);

		// Sets the scene
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	/*
	 * ALL CODE BELOW IS PROPERTY OF ABHI
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
	private void showTaskListView() {
		Stage tasksStage = new Stage();
		tasksStage.setTitle("Task List");

		ListView<String> taskListView = new ListView<>();



		loadTasksFromFile(taskListView);

		// Create a layout for the content
		VBox tasksLayout = new VBox(10);
		tasksLayout.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		tasksLayout.setPadding(new Insets(20));

		// Create a button to view historical data for the selected task
		Button viewHistoricalDataBtn = new Button("View Historical Data");
		viewHistoricalDataBtn.setOnAction(e -> viewHistoricalData(taskListView.getSelectionModel().getSelectedItem()));
		Label taskList = new Label("Task List");
		taskList.setTextFill(Color.WHITE);
		tasksLayout.getChildren().add(taskList);
		
	
		// Add the button and task list to the layout
		if (!tasksLayout.getChildren().contains(viewHistoricalDataBtn)) {
			tasksLayout.getChildren().addAll( taskListView, viewHistoricalDataBtn);
		}
		
		Button addTaskButton = new Button("Add Task");
		addTaskButton.setOnAction(e -> showAddTaskWindow(taskListView));
		if (!tasksLayout.getChildren().contains(addTaskButton)) {
			tasksLayout.getChildren().addAll(addTaskButton);
		}

		// Create a scene for the task list window
		Scene tasksScene = new Scene(tasksLayout, 300, 400);
		tasksScene.setFill(Color.BLACK);

		// Set the scene for the tasks stage
		tasksStage.setScene(tasksScene);

		// Show the task list window
		tasksStage.show();
	}

	private void showAddTaskWindow(ListView<String> taskListView) {
		Stage addTaskStage = new Stage();
		addTaskStage.setTitle("Add Task");

		// Create input fields for task details
		TextField taskNameField = new TextField();
		taskNameField.setPromptText("Enter Task Name");
		
		// Create a button to add the new task
		Button addButton = new Button("Add Task");
		addButton.setOnAction(e -> {
			String newTaskName = taskNameField.getText();
			if (!newTaskName.isEmpty()) {
				String newTaskDisplayName = newTaskName;
				taskListView.getItems().add(newTaskDisplayName);
				saveNewTaskToFile(newTaskName, newTaskDisplayName);
				addTaskStage.close();
			}
		});

		// Create a layout for the content
		VBox addTaskLayout = new VBox(10);
		addTaskLayout.setPadding(new Insets(20));
		addTaskLayout.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		Label AddTask = new Label("Add Task");
		AddTask.setTextFill(Color.WHITE);
		addTaskLayout.getChildren().addAll(AddTask, taskNameField, addButton);

		// Create a scene for the add task window
		Scene addTaskScene = new Scene(addTaskLayout, 300, 150);
		addTaskScene.setFill(Color.BLACK);

		// Set the scene for the add task stage
		addTaskStage.setScene(addTaskScene);

		// Show the add task window
		addTaskStage.show();
	}
	// Add a method to handle viewing historical data for the selected task
	private void viewHistoricalData(String selectedTask) {
		// Implement logic to retrieve historical data for the selected task
		// For demonstration purposes, we'll use some dummy historical data
		String historicalData = getHistoricalDataForTask(selectedTask);

		// Create a new stage for displaying historical data
		Stage historicalDataStage = new Stage();
		historicalDataStage.setTitle("Historical Data for " + selectedTask);

		// Create a TextArea for displaying historical data
		TextArea textArea = new TextArea();
		textArea.setEditable(false); // Make the text area read-only
		textArea.setWrapText(true); // Enable text wrapping
		textArea.setText(historicalData);

		// Create a layout for the content
		VBox historicalDataLayout = new VBox(10);
		historicalDataLayout.setPadding(new Insets(20));
		Label Historical_Data = new Label("Historical Data");
		Historical_Data.setTextFill(Color.WHITE);
		historicalDataLayout.getChildren().addAll(Historical_Data, textArea);
		Button editHistoricalDataBtn = new Button("Edit Historical Data");

		String fileName = selectedTask+ "_historical_data.txt";
		editHistoricalDataBtn.setOnAction(e -> editHistoricalData(selectedTask, textArea, fileName));

		if (!historicalDataLayout.getChildren().contains(editHistoricalDataBtn)) {
			historicalDataLayout.getChildren().addAll(editHistoricalDataBtn);
		}

		// Create a scene for the historical data window
		Scene historicalDataScene = new Scene(historicalDataLayout, 400, 300);
		historicalDataLayout.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		historicalDataScene.setFill(Color.BLACK);

		// Set the scene for the historical data stage
		historicalDataStage.setScene(historicalDataScene);

		// Show the historical data window
		historicalDataStage.show();
	}

	// Method to get historical data for a task 

	private String getHistoricalDataForTask(String task) {
		System.out.println("Current working directory: " + System.getProperty("user.dir"));
		// Replace spaces and special characters in the task name to create a valid
		// filename
		String fileName = task + "_historical_data.txt";

		// Build the full path to the historical data file
		String filePath = "historical_data" + File.separator + fileName;

		// Check if the file exists, and create it if it doesn't
		try {
			System.out.println("File Path: " + filePath);
			if (!fileExists(fileName)) {
				createEmptyFile(fileName);
			}

			// Read historical data from the file
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			StringBuilder historicalData = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				historicalData.append(line).append("\n");
			}

			reader.close();
			return historicalData.toString();
		} catch (IOException e) {
			// Handle file-related exceptions
			e.printStackTrace();
			return "Error: Unable to retrieve or create historical data for " + task;
		}
	}

	private void saveNewTaskToFile(String newTaskName, String newTaskDisplayName) {
		try {
			String fileName = "tasks.txt";

			// Open the file in append mode
			FileWriter writer = new FileWriter(fileName, true);

			// Write the new task information to the file

			writer.write(newTaskName +"_historical_data.txt"+ "\n");

			// Close the file
			writer.close();

			System.out.println("Saved new task to file: " + fileName);
		} catch (IOException e) {
			// Handle file-related exceptions
			e.printStackTrace();
			System.err.println("Error saving new task to file: " + e.getMessage());
		}
	}


	// Check if a file exists
	private boolean fileExists(String filePath) {
		return new File(filePath).exists();
	}

	// Create an empty file
	private void createEmptyFile(String filePath) throws IOException {
		FileWriter writer = new FileWriter(filePath);
		// You can add some initial content to the file if needed
		writer.write("Initial content for " + filePath);
		writer.close();
	}

	private void editHistoricalData(String selectedTask, TextArea textArea, String fileName) {
		// Create a new stage for editing historical data

		Stage editHistoricalDataStage = new Stage();
		editHistoricalDataStage.setTitle("Edit Historical Data for " + selectedTask);

		// Create a TextArea for editing historical data
		TextArea editTextArea = new TextArea();
		editTextArea.setEditable(true); // Allow the user to edit the text
		editTextArea.setWrapText(true); // Enable text wrapping
		editTextArea.setText(textArea.getText()); // Set the initial content to the current historical data

		// Create a layout for the content
		VBox editHistoricalDataLayout = new VBox(10);
		editHistoricalDataLayout.setPadding(new Insets(20));
		Label Edit_Historical_Data = new Label("Edit Historical Data");
		Edit_Historical_Data.setTextFill(Color.WHITE);
		editHistoricalDataLayout.getChildren().addAll(Edit_Historical_Data, editTextArea);

		// Create a button to save the edited data
		Button saveButton = new Button("Save");
		saveButton.setOnAction(event -> {
			saveEditedHistoricalData(selectedTask, editTextArea, fileName);
			editHistoricalDataStage.close();
		});

		// Add the save button to the layout
		editHistoricalDataLayout.getChildren().add(saveButton);

		// Create a scene for the edit historical data window
		Scene editHistoricalDataScene = new Scene(editHistoricalDataLayout, 1000, 800);
		editHistoricalDataLayout.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		editHistoricalDataScene.setFill(Color.BLACK);

		// Set the scene for the edit historical data stage
		editHistoricalDataStage.setScene(editHistoricalDataScene);

		// Show the edit historical data window
		editHistoricalDataStage.show();
	}

	private void saveEditedHistoricalData(String selectedTask, TextArea editTextArea, String fileName) {
		try {
			// Write the edited historical data to the file

			String filePath = System.getProperty("user.dir") + File.separator + fileName;

			FileWriter writer = new FileWriter(fileName);
			System.out.println(fileExists(filePath));
			writer.write(editTextArea.getText());
			writer.close();
			System.out.println("Saved edited historical data to file: " + fileName);
		} catch (IOException e) {
			// Handle file-related exceptions
			e.printStackTrace();
			System.err.println("Error saving edited historical data to file: " + fileName);
		}
	}

	private void loadTasksFromFile(ListView<String> taskListView) {
		File currentDirectory = new File(System.getProperty("user.dir"));
		File[] files = currentDirectory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile() && file.getName().endsWith("_historical_data.txt")) {
					String fileName = file.getName();
					String taskName = getTaskNameFromFileName(fileName); 
					if(taskName.equals("false")) {
						System.err.println("Error listing files in the current directory.");
					}else {
						taskListView.getItems().add(taskName);
					}
				}       
			}
			System.out.println("Loaded tasks from the current directory.");
		} else {
			System.err.println("Error listing files in the current directory.");
		}
	}
	private void loadTasksintoComboBox(ComboBox<String> box) {
		File currentDirectory = new File(System.getProperty("user.dir"));
		File[] files = currentDirectory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile() && file.getName().endsWith("_historical_data.txt")) {
					String fileName = file.getName();
					String taskName = getTaskNameFromFileName(fileName); 
					if(taskName.equals("false")) {
						System.err.println("Error listing files in the current directory.");
					}else {
						box.getItems().add(taskName);
					}
				}       
			}
			System.out.println("Loaded tasks from the current directory.");
		} else {
			System.err.println("Error listing files in the current directory.");
		}
	}

	private String getTaskNameFromFileName(String fileName) {
		// Extract the task number from the file name (assuming the format "Task 1_historical_data.txt")
		String[] parts = fileName.split("_");
		if (parts.length >= 2) {
			String taskName= parts[0];
			return taskName;
		} else {
			return "false";
		}
	}


}