/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gcal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import static java.awt.event.KeyEvent.VK_ENTER;

import java.lang.Math;
import java.awt.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gnu.jel.CompiledExpression;
import gnu.jel.Evaluator;
import gnu.jel.Library;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import sun.reflect.generics.tree.Tree;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author pagal programmer
 */
public class GCalController implements Initializable {
        
    @FXML
    private JFXTextField user_input,user_output;
    
    @FXML
    private AnchorPane drawer,root;

    @FXML
    private void close(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void type(MouseEvent event) {
        user_input.requestFocus();
        String str=event.getSource().toString();
        Pattern p = Pattern.compile(".*\\' *(.*) *\\'");
        Matcher m = p.matcher(str);
        m.find();
        str=m.group(1);
        user_input.appendText(str);
    }

    @FXML
    private void back(MouseEvent event) {
        user_input.requestFocus();
        user_input.setText(user_input.getText().substring(0,user_input.getText().length()-1));
        user_input.appendText("");
    }

    double var1=0,var2=0;
    String op="NULL";

    /*@FXML void add(MouseEvent event){
        var1 = user_output.getText().equals("")?0:Double.parseDouble(user_output.getText());
        var2 = user_input.getText().equals("")?0:Double.parseDouble(user_input.getText());
        System.out.print(var1+"\n"+var2);
        var1 += var2;
        op = "add";
        user_input.setText("");
        user_output.setText(Double.toString(var1));
    }

    @FXML void subtract(MouseEvent event){
        var1 = user_output.getText().equals("")||op.equals("")?0:Double.parseDouble(user_output.getText());
        var2 = user_input.getText().equals("")?0:Double.parseDouble(user_input.getText());
        var1 -= var2;
        op = "minus";
        user_input.setText("");
        user_output.setText(Double.toString(var1));
    }

    @FXML void multiply(MouseEvent event){
        var1 = user_output.getText().equals("")||op.equals("")?1:Double.parseDouble(user_output.getText());
        var2 = user_input.getText().equals("")?1:Double.parseDouble(user_input.getText());
        var1 *= var2;
        op = "multiply";
        user_input.setText("");
        user_output.setText(Double.toString(var1));
    }

    @FXML void divide(MouseEvent event){
        var1 = user_output.getText().equals("")||op.equals("")?1:Double.parseDouble(user_output.getText());
        var2 = user_input.getText().equals("")?1:Double.parseDouble(user_input.getText());
        var1 /= var2;
        op = "divide";
        user_input.setText("");
        user_output.setText(Double.toString(var1));
    }*/

    @FXML void equal(MouseEvent event) throws ScriptException {
        user_input.requestFocus();
        String exp = user_input.getText();
        //var2 = user_input.getText().equals("")?1:Double.parseDouble(user_input.getText());
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("Javascript");

        user_output.setText((String) engine.eval(exp));
    }
    
    @FXML
    private void calc(KeyEvent event){
        //MouseEvent fakeMouse = null;
        /*KeyCode keys[] = {KeyCode.ADD,KeyCode.SUBTRACT,KeyCode.MULTIPLY,KeyCode.DIVIDE};
        KeyCode code = event.getCode();
        if(Arrays.asList(keys).contains(code)) {
            String in = user_input.getText();
            user_input.setText(in.substring(0, in.length() - 1));

            Method method = getClass().getDeclaredMethod(code.toString().toLowerCase(),MouseEvent.class);
            method.invoke(this,fakeMouse);
        }*/
        if( event.getCode().equals(KeyCode.ENTER)){
            user_input.requestFocus();
            String exp = user_input.getText();

            //var2 = user_input.getText().equals("")?1:Double.parseDouble(user_input.getText());
            //ScriptEngineManager mgr = new ScriptEngineManager();
            //ScriptEngine engine = mgr.getEngineByName("javascript");
            //user_output.setText(engine.eval(exp).toString());
            Expression e = new ExpressionBuilder(exp).build();/*
            Class[] staticLib = new Class[1];
            staticLib[0] = Class.forName("java.lang.math");
            Library lib = new Library(staticLib,null,null,null,null);
            lib.markStateDependent("random",null);
            CompiledExpression expr = Evaluator.compile(exp,lib);*/
            System.out.println(e.evaluate());
        }
    }
    
    @FXML
    private void pullDrawer(MouseEvent event) {
        TranslateTransition pull = new TranslateTransition();
        pull.setByX(-215);
        pull.setDuration(Duration.millis(500));
        pull.setNode(drawer);
        pull.play();
    }

    @FXML
    private void pushDrawer(MouseEvent event) {
        TranslateTransition push = new TranslateTransition();
        push.setByX(215);
        push.setDuration(Duration.millis(500));
        push.setNode(drawer);
        push.play();
    }
    
    double gapX=0,gapY=0;
    
    @FXML
    private void dragInit(MouseEvent event){
        Stage stage = (Stage)root.getScene().getWindow();
        gapX = stage.getX() - event.getScreenX();
        gapY = stage.getY() - event.getScreenY();
        stage.setOpacity(0.8);
    }
    
    @FXML
    private void drag(MouseEvent event){
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setX(event.getScreenX() + gapX);
        stage.setY(event.getScreenY() + gapY);
    }
    
    @FXML
    private void dragEnd(MouseEvent event){
        Stage stage = (Stage)root.getScene().getWindow();
        stage.setOpacity(1);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
