package application;

import java.util.Stack;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class Calculator extends Application implements EventHandler<ActionEvent>
{
    
	private boolean start=true;
    private TextField textField;
    private Button button;


    @Override
    public void start(Stage primaryStage) 
    {
        
        //Buttons for Numbers and Operations 

        FlowPane flowPane=new FlowPane();
        flowPane.setVgap(10);
        flowPane.setHgap(5);
        flowPane.setAlignment(Pos.TOP_CENTER);

        flowPane.getChildren().add(CreatCancleButtons());
        flowPane.getChildren().add(CreatBackButtonn());

        flowPane.getChildren().add(CreatbuttonforNumbers("7"));
        flowPane.getChildren().add(CreatbuttonforNumbers("8"));
        flowPane.getChildren().add(CreatbuttonforNumbers("9"));
        flowPane.getChildren().add(CreatOPerators("/"));

        
        flowPane.getChildren().add(CreatbuttonforNumbers("4"));
        flowPane.getChildren().add(CreatbuttonforNumbers("5"));
        flowPane.getChildren().add(CreatbuttonforNumbers("6"));
        flowPane.getChildren().add(CreatOPerators("*"));

        
        flowPane.getChildren().add(CreatbuttonforNumbers("1"));
        flowPane.getChildren().add(CreatbuttonforNumbers("2"));
        flowPane.getChildren().add(CreatbuttonforNumbers("3"));
        flowPane.getChildren().add(CreatOPerators("-"));

        
        flowPane.getChildren().add(CreatbuttonforNumbers("0"));
        flowPane.getChildren().add(CreatbuttonforNumbers("."));
        flowPane.getChildren().add(CreatOPerators("="));
        flowPane.getChildren().add(CreatOPerators("+"));



        //TextField for Screen 
        textField=new TextField();
        textField.setPrefSize(20, 35);
        textField.setFont(Font.font(20));
        textField.setEditable(false);
    
    
        StackPane stackPane=new StackPane();
        stackPane.setPadding(new Insets(10));
        stackPane.getChildren().add(textField);

        // Creating a root pane 
        BorderPane root=new BorderPane();
        root.setCenter(flowPane);
        root.setTop(stackPane);
        
        //Creating scene for stage 
        
        Scene scene=new Scene(root,300,375);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("My Calculator ");
        primaryStage.show();


   
    }

    public static void main(String[] args) throws Exception 
    {
        launch(args);
    }
    //METHOD FOR CREATING BUTTONS NUMBERS 
    private Button CreatbuttonforNumbers(String str)
    {
    	
       Button button=new Button(str);
       button.setPrefSize(65, 50);
       button.setFont(Font.font(18));
       button.setOnAction(this::processNumbers);
      
       return button;
    }
    
    // METHOD FOR PROCESS NUMBER 
    
    private void processNumbers (ActionEvent e)
    
    {
        if(start)
        {
            textField.setText("");
            start=false;
        }
        
        Button  button=(Button) e.getSource();
        String value=button.getText();
        textField.setText(textField.getText()+value);
 
     }
    
    // METHOD FOR CREATE BUTTONS FOR OPERATOR 
    
    private Button CreatOPerators(String str)
    
    {
   
        Button button=new Button(str);
        button.setPrefSize(65, 50);
        button.setFont(Font.font(18));
        button.setOnAction(this::processOperators);
    
        
        return button;
    }
    
    //METHOD FOR PROCESS OPERATOR 
    
    private void processOperators(ActionEvent e)
    {
        if(start)
        {
            textField.setText("");
            start=false;
        }
        Button  button=(Button) e.getSource();
        String value=button.getText();
      
        if(value!="=")
        {
            textField.setText(textField.getText()+value);
        }else{
            String expression=textField.getText();
            String  result=MathExpression.evaluate(expression);
            textField.setText(result);
            start=true;
  
        }
    

    }
    
    //METHOD FOR CREATING CANCLE BUTTONS 
    
    private Button CreatCancleButtons()
    {
       Button button=new Button("C");
       button.setPrefSize(140, 60);
       button.setFont(Font.font(18));
       button.setOnAction(e->{
          textField.setText(" ");
          start=true;
       });
    
        
    return button;
    }
    
    //METHOD FOR CREATING BACK BUTTON 
    
    private Button CreatBackButtonn()
    {
       Button button=new Button("B");
       button.setPrefSize(140, 60);
       button.setFont(Font.font(18));
       button.setOnAction(e->{
       String text=textField.getText();
       
       if(!text.isEmpty())
       {
       text=text.substring(0,text.length()-1);
       textField.setText(text);
       }
       
      });
    
        
    return button;
    }

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
// CLASS FOR CALULATING MATH EXPRESSION 

class MathExpression{
    private static int getPrecedence(char ch)
    {
       
       if(ch=='+' || ch=='-'){
        return 1;
       }
       else if(ch=='*' || ch=='/'){
        return 2;
       }
       else if(ch=='^'){
        return 3;

       }
       else if(ch=='(' || ch==')'){
        return 4;
       }
       else
        return 0;


    }
    
    public static double Calculate(double num1,double num2,char operation )
    {
        switch (operation) 
        {
            case '+':return num1+num2;
            case '-':return num1-num2;
            case '*':return num1*num2;
            case '/': if(num2==0)
                        return 0;
                      else
                        return  num1/num2;
            default:
      return 0;
        }
    }
    
    public static String evaluate(String exp)
    {

        Stack<Double>operands=new Stack<>();
        Stack<Character>operators=new Stack<>();

        for (int i=0;i<exp.length();i++){
            char ch=exp.charAt(i);

            if(Character.isDigit(ch))
            {
                operands.push(Double.parseDouble(ch+""));
            }
            
            else if(ch=='+'|| ch=='-'|| ch=='*'|| ch=='/'|| ch=='^'){
                
            	if(operators.isEmpty())
                {
                    operators.push(ch);
                }else if(operators.peek()=='('){
                    operators.push(ch);
                }else if(getPrecedence(operators.peek())>=getPrecedence(ch)){
                    char op=operators.pop();
                    double num2=operands.pop();
                    double num1=operands.pop();
                    double value=Calculate(num1, num2, op);
                    operands.push(value);
                    operators.push(ch);
                }else{
                    operators.push(ch);
                }
            }
            else if(ch==')')
            {
                while(operators.peek()!='(')
                {
                    char op=operators.pop();
                    double num2=operands.pop();
                    double num1=operands.pop();
                    double value=Calculate(num1, num2, op);
                    operands.push(value);
                }
                operators.pop();
            }
        }
        while(operators.size()!=0)
        {
            char op=operators.pop();
            double num2=operands.pop();
            double num1=operands.pop();
            double value=Calculate(num1, num2, op);
            operands.push(value);
        }
        return operands.pop()+"";
    }
}
class evaluation
{
    public static void main(String[] args) 
    {
        String exp="2^3+1+(5-3*6/2)";
        System.out.println(MathExpression.evaluate(exp));
    }
}
                                                                                                    