package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	public static int precedence(char c) {
		switch (c) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		}
		return -1;
	}

	public static String infixToPostFix(String expression) {
		String result = "";
		CursorStack stack = new CursorStack();
		for (int i = 0; i < expression.length(); i++) {
			char c = expression.charAt(i);

			if (precedence(c) > 0) {
				while (stack.isEmpty() == false && precedence((char) stack.peek()) >= precedence(c)) {
					result += stack.pop();
				}
				stack.Push(c);
			} else if (c == ')') {
				char x = (char) stack.pop();
				while (x != '(') {
					result += x;
					x = (char) stack.pop();
				}
				//stack.pop();
			} else if (c == '(') {
				stack.Push(c);
			} else {

				result += c;
			}
		}
		for (int i = 0; i <= 0; i++) {
			result += stack.pop();
		}
		return result;
	}

	public static double evaluatePostfix(String exp) throws NullPointerException {

		CursorStack stack = new CursorStack();
		for (int i = 0; i < exp.length(); i++) {
			char c = exp.charAt(i);
			if (c == ' ')
				continue;
			else if (Character.isDigit(c)) {
				double n = 0;
				while (Character.isDigit(c)) {
					n = n * 10 + (double) (c - '0');
					i++;
					c = exp.charAt(i);
				}
				i--;
				stack.Push(n);
			} else {
				double val1 = 0;
				double val2 = 0;
				double res = 0;

				switch (c) {
				case '+':
					val1 = (double) stack.pop();
					val2 = (double) stack.pop();
					res = val1 + val2;
					stack.Push(res);
					break;

				case '-':
					val1 = (double) stack.pop();
					val2 = (double) stack.pop();
					res = val2 - val1;
					stack.Push(res);
					break;

				case '/':
					val1 = (double) stack.pop();
					val2 = (double) stack.pop();
					res = val2 / val1;
					stack.Push(res);
					break;

				case '*':
					val1 = (double) stack.pop();
					val2 = (double) stack.pop();
					res = val2 * val1;
					stack.Push(res);
					break;

				default:

					stack.Push((double) c);
					break;
				}
			}
		}
		return (double) stack.pop();
	}

	private static boolean check(String exp) {
		boolean res = false;
		CursorStack s = new CursorStack();
		char temp;
		char[] c = exp.toCharArray();
		for (int i = 0; i < c.length; i++) {
			switch (c[i]) {
			case '(':
				s.Push(c[i]);
				break;
			// if character equal )
			case ')':

				if (s.isEmpty() == false) {
					// pop the peek if !=( return false
					temp = (char) s.pop();
					if (temp != '(') {
						// //System.out.println("Erorr1");
						return false;
					}
				} else {
					return false;
				}

				break;
			default:
				res = true;
			}

		}
		return (s.isEmpty()) ? true : false;
	}

	public static boolean isRight(String exp) throws NullPointerException {
		for (int i = 0; i < exp.length() - 1; i++) {
			if ((exp.charAt(i) == '+' || exp.charAt(i) == '-' || exp.charAt(i) == '/' || exp.charAt(i) == '*')
					&& ((exp.charAt(i + 2) == '+' || exp.charAt(i + 2) == '-' || exp.charAt(i + 2) == '/'
							|| exp.charAt(i + 2) == '*')))
				return false;
		}
		return true;
	}

	public static boolean isValid(String line) {
		if ((line.contains("<242>")) && !(line.contains("</242>"))) {
			return false;
		} else if ((line.contains("<equations>")) && !(line.contains("</equations>"))) {
			return false;
		} else if ((line.contains("<files>")) && !(line.contains("</files>"))) {
			return false;
		} else if ((line.contains("<equation>")) && !(line.contains("</equation>"))) {
			return false;
		} else if ((line.contains("<file>")) && !(line.contains("</file>"))) {
			return false;
		} else if ((line.contains("</242>")) && !(line.contains("<242>"))) {
			return false;
		} else if ((line.contains("</equations>")) && !(line.contains("<equations>"))) {
			return false;
		} else if ((line.contains("</files>")) && !(line.contains("<files>"))) {
			return false;
		} else if ((line.contains("</equation>")) && !(line.contains("<equation>"))) {
			return false;
		} else if ((line.contains("<f/ile>")) && !(line.contains("<file>"))) {
			return false;
		} else {
			return true;
		}
	}

	CursorStack FileStack = new CursorStack();
	//CursorStack FilesclickStack = new CursorStack();

	@Override
	public void start(Stage stage) {
		try {
			// first hbox
			Button backbtn = new Button("Back");
			backbtn.setMaxSize(100, 50);
			backbtn.setTextFill(Color.BLUE);
			Button loadbtn = new Button("Load");
			loadbtn.setMaxSize(100, 50);
			loadbtn.setTextFill(Color.GREEN);
			TextArea filenamefield = new TextArea();
			filenamefield.setMaxSize(260, 1);
			HBox firstrow = new HBox(5);
			firstrow.setAlignment(Pos.TOP_LEFT);
			firstrow.getChildren().addAll(backbtn, filenamefield, loadbtn);
			// second hbox
			Label equationlabel = new Label("equations");
			TextArea equationfield = new TextArea();
			equationfield.setMaxSize(350, 200);
			VBox secondrow = new VBox(5);
			secondrow.getChildren().addAll(equationlabel, equationfield);
			// third hbox
			Label fileslabel = new Label("Files");
			ListView<String> fillesfield = new ListView<String>();
			fillesfield.setMaxSize(350, 50);
			VBox thirdrow = new VBox(5);
			thirdrow.getChildren().addAll(fileslabel, fillesfield);
			// vbox
			VBox vbox = new VBox(10);
			vbox.getChildren().addAll(firstrow, secondrow, thirdrow);
			// file text
			FileChooser filechooser = new FileChooser();
			loadbtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					File selectedFile = filechooser.showOpenDialog(stage);
					equationfield.clear();
					if (selectedFile != null) {
						FileStack.Push(selectedFile.getPath());
//						System.out.println(FileStack.peek());
//						System.out.println(FileStack.pop());
//						System.out.println(FileStack.peek());
//						System.out.println(FileStack.pop());
//						System.out.println(FileStack.peek());
//						System.out.println(FileStack.pop());

						try {
							String filePath = selectedFile.getPath();
							filenamefield.setText(filePath);
							FileStack.Push(filePath);

							BufferedReader br1 = new BufferedReader(new FileReader(filePath));
							StringBuilder sb1 = new StringBuilder();
							String filetext = null;
							String ls = System.getProperty("line.separator");
							while ((filetext = br1.readLine()) != null) {
								sb1.append(filetext);
								sb1.append(ls);
							}
							sb1.deleteCharAt(sb1.length() - 1);
							br1.close();
							try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
								String line = "";
								while ((line = br.readLine()) != null) {
									if (line.contains("<equation>") && line.endsWith("</equation>")) {
										String[] splitOne1 = line.split("<equation>");
										String[] splitTwo1 = splitOne1[1].split("</equation>");
										String fileequ = new String(splitTwo1[0]);
										equationfield.insertText(0, fileequ + "\n");
									}
//									
									if (line.contains("<file>") && line.endsWith("</file>")) {
										String[] splitOne = line.split("<file>");
										String[] splitTwo = splitOne[1].split("</file>");
										String filess = new String(splitTwo[0]);
										File f = new File(filess);
										String parent = f.getName();
										fillesfield.getItems().add(parent);
									}
								}
							}
							String content = sb1.toString();
							//System.out.println(content);
							String[] lines = equationfield.getText().split("\n");
							equationfield.clear();

							// equationfield.setText(null);
							for (int i = 0; i < lines.length; i++) {
								//System.out.println(lines[i]);
								String b = infixToPostFix(lines[i]);
								if ((isValid(content) && (!(content.contains("<equation>"))))) {
									equationfield.setText("No Equations");
								} 
								else if ((!(isValid(content)) )) {
									equationfield.setText("Invalid file: Missing end tags");
								}else if (check(lines[i]) == true && isRight(lines[i]) == true
										&& isValid(content) == true) {
									equationfield.insertText(0, lines[i] + " --> " + infixToPostFix(b) + " = "
											+ String.valueOf(evaluatePostfix(b)) + "\n");
								} else if (isValid(content) == false) {
									equationfield.insertText(0, "The File Is Not Valid" + "\n");
								} else if (check(lines[i]) == false) {
									equationfield.insertText(0,
											lines[i] + " --> " + "The equation is not balanced" + "\n");
								} else if (isRight(lines[i]) == false) {
									equationfield.insertText(0,
											lines[i] + " --> " + "The equation is not right" + "\n");
								} else if (check(lines[i]) == false) {
									equationfield.insertText(0, lines[i] + " --> "
											+ "The equation is not balanced, Missing parentheses" + "\n");
								} else if (isRight(lines[i]) == false) {
									equationfield.insertText(0,
											lines[i] + " --> " + "The equation is not right" + "\n");
								}
								else {
									equationfield.insertText(0, lines[i] + " --> " + "Missing parentheses" + "\n");
								}

							}
						} catch (IOException e1) {
						}
					}
				}

			});

			File directory = new File("C:\\Users\\tabak\\Downloads\\");
			fillesfield.setOnMouseClicked(e -> {
				String filedir = directory + "\\" + fillesfield.getSelectionModel().getSelectedItem();
				FileStack.Push(filedir);
				File selectedFile = new File((String) FileStack.peek());
				filenamefield.setText("");
				fillesfield.getItems().clear();
				filenamefield.setText(selectedFile.getPath());

				if (selectedFile != null) {
					equationfield.clear();
					try {
						String filePath = selectedFile.getPath();
						filenamefield.setText(filePath);
						filenamefield.setText(filePath);
						FileStack.Push(filePath);
					//	//System.out.println(FileStack.peek());
						////System.out.println(FileStack.pop());

						BufferedReader br1 = new BufferedReader(new FileReader(filePath));
						StringBuilder sb1 = new StringBuilder();
						String filetext = null;
						String ls = System.getProperty("line.separator");
						while ((filetext = br1.readLine()) != null) {
							sb1.append(filetext);
							sb1.append(ls);
						}
						sb1.deleteCharAt(sb1.length() - 1);
						br1.close();
						try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
							String line = "";
							while ((line = br.readLine()) != null) {
								if (line.contains("<equation>") && line.endsWith("</equation>")) {
									String[] splitOne1 = line.split("<equation>");
									String[] splitTwo1 = splitOne1[1].split("</equation>");
									String fileequ = new String(splitTwo1[0]);
									equationfield.insertText(0, fileequ + "\n");
								}

								if (line.contains("<file>") && line.endsWith("</file>")) {
									String[] splitOne = line.split("<file>");
									String[] splitTwo = splitOne[1].split("</file>");
									String filess = new String(splitTwo[0]);
									File f = new File(filess);
									String parent = f.getName();
									fillesfield.getItems().add(parent);
								}
							}
						}
						String content = sb1.toString();
						////System.out.println(content);
						String[] lines = equationfield.getText().split("\n");
						equationfield.clear();
						// equationfield.setText(null);

						for (int i = 0; i < lines.length; i++) {
							////System.out.println(lines[i]);
							String b = infixToPostFix(lines[i]);
							if ((isValid(content) && (!(content.contains("<equation>"))))){
								equationfield.setText("No Equations");
							}
							else if (!(isValid(content) )) {
								equationfield.setText("invalid file: missing end tag");
							} else if (check(lines[i]) == true && isRight(lines[i]) == true
									&& isValid(content) == true) {
								equationfield.insertText(0, lines[i] + " --> " + infixToPostFix(b) + " = "
										+ String.valueOf(evaluatePostfix(b)) + "\n");
							} else if (isValid(content) == false) {
								equationfield.insertText(0, "The File Is Not Valid" + "\n");
							} else if (check(lines[i]) == false) {
								equationfield.insertText(0, lines[i] + " --> " + "The equation is not balanced" + "\n");
							} else if (isRight(lines[i]) == false) {
								equationfield.insertText(0, lines[i] + " --> " + "The equation is not right" + "\n");
							} else if (check(lines[i]) == false) {
								equationfield.insertText(0, lines[i] + " --> "
										+ "The equation is not balanced, Missing parentheses" + "\n");
							} else if (isRight(lines[i]) == false) {
								equationfield.insertText(0, lines[i] + " --> " + "The equation is not right" + "\n");
							}
							else {
								equationfield.insertText(0, lines[i] + " --> " + "Missing parentheses" + "\n");
							}

						}
					} catch (IOException e1) {
					}

				}

			});

			backbtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println(FileStack.peek());
					System.out.println(FileStack.pop());
					System.out.println(FileStack.peek());
					System.out.println(FileStack.pop());
					System.out.println(FileStack.peek());
					System.out.println(FileStack.pop());

					//FileStack.pop();
					String selectedFile = (String) (FileStack.peek());
					String filepath = selectedFile;
					if (selectedFile != null) {
						equationfield.clear();
						try {
							String filePath = filepath;
							filenamefield.setText(filePath);
							FileStack.Push(filePath);
							BufferedReader br1 = new BufferedReader(new FileReader(filePath));
							StringBuilder sb1 = new StringBuilder();
							String filetext = null;
							String ls = System.getProperty("line.separator");
							while ((filetext = br1.readLine()) != null) {
								sb1.append(filetext);
								sb1.append(ls);
							}
							sb1.deleteCharAt(sb1.length() - 1);
							br1.close();
							try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
								String line = "";
								while ((line = br.readLine()) != null) {
									if (line.contains("<equation>") && line.endsWith("</equation>")) {
										String[] splitOne1 = line.split("<equation>");
										String[] splitTwo1 = splitOne1[1].split("</equation>");
										String fileequ = new String(splitTwo1[0]);
										equationfield.insertText(0, fileequ + "\n");
									}
//									else if  ((isValid(content)&&(!(content.contains("<equation>"))))){
//										equationfield.setText("INVALID");
//
//									}
									if (line.contains("<file>") && line.endsWith("</file>")) {
										String[] splitOne = line.split("<file>");
										String[] splitTwo = splitOne[1].split("</file>");
										String filess = new String(splitTwo[0]);
										File f = new File(filess);
										String parent = f.getName();
										fillesfield.getItems().add(parent);
									}
								}
							}
							String content = sb1.toString();
						//	//System.out.println(content);
							String[] lines = equationfield.getText().split("\n");
							equationfield.clear();
							// equationfield.setText(null);
							for (int i = 0; i < lines.length; i++) {
								//System.out.println(lines[i]);
								String b = infixToPostFix(lines[i]);
								if ((isValid(content) && (!(content.contains("<equation>"))))){
									equationfield.setText("No Equations");
								}
								else if ((isValid(content) && (!(content.contains("<equation>"))))) {
									equationfield.setText("invalid file: missing end tag");
								} else if (check(lines[i]) == true && isRight(lines[i]) == true
										&& isValid(content) == true) {
									equationfield.insertText(0, lines[i] + " --> " + infixToPostFix(b) + " = "
											+ String.valueOf(evaluatePostfix(b)) + "\n");

								} else if (isValid(content) == false) {
									equationfield.insertText(0, "The File Is Not Valid" + "\n");
								} else if (check(lines[i]) == false) {
									equationfield.insertText(0,
											lines[i] + " --> " + "The equation is not balanced" + "\n");
								} else if (isRight(lines[i]) == false) {
									equationfield.insertText(0,
											lines[i] + " --> " + "The equation is not right" + "\n");
								} else if (check(lines[i]) == false) {
									equationfield.insertText(0, lines[i] + " --> "
											+ "The equation is not balanced, Missing parentheses" + "\n");
								} else if (isRight(lines[i]) == false) {
									equationfield.insertText(0,
											lines[i] + " --> " + "The equation is not right" + "\n");
								}

								else {
									equationfield.insertText(0, lines[i] + " --> " + "Missing parentheses" + "\n");
								}

							}
						} catch (IOException e1) {
						}
					}
				}

			});

			Group gp = new Group(vbox);
			BorderPane root = new BorderPane(gp);
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}