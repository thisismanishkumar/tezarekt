import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ViewUserProfileController implements Initializable {

    @FXML
    private Label uname_id, fname_id, lname_id, subscost_id;
    @FXML
    private JFXTextArea bio_id;
    @FXML
    private ImageView image_view_id;
    @FXML
    private JFXButton subscribe_btn;
    @FXML
    private JFXSpinner subs_spinner_id;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    public void isSubscribe(){
        subs_spinner_id.setVisible(true);
        new Thread(){
            Future<HttpResponse> future = null;
            @Override
            public void run() {
                super.run();
                CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
                client.start();
                HttpGet request = new HttpGet(Main.Connectingurl+"/isSubscribed/"+uname_id.getText()+"/"+LoginController.curr_username);
                future= client.execute(request, null);
                while(!future.isDone());

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(future.get().getStatusLine().getStatusCode() == 200){
                                String jsonString = EntityUtils.toString(future.get().getEntity());
                                if(jsonString.equals("1")){
                                    subscribe_btn.setText("Unsubscribe");
                                    subscribe_btn.setStyle("-fx-background-color:#a09ea9;");
                                }
                                else{
                                    subscribe_btn.setText("Subscribe");
                                    subscribe_btn.setStyle("-fx-background-color:#dc0808;");
                                }
                            }
                            else{
                                subscribe_btn.setText("error");
                            }

                        } catch (InterruptedException | ExecutionException | IOException e) {
                            e.printStackTrace();
                        } finally {
                            subs_spinner_id.setVisible(false);
                        }
                    }
                });
            }
        }.start();
    }
    public JFXTextArea getBio_id(){
        return bio_id;
    }
    public Label getUname_id(){
        return uname_id;
    }
    public Label getFname_id(){
        return fname_id;
    }
    public Label getLname_id(){
        return lname_id;
    }
    public Label getSubscost_id(){
        return subscost_id;
    }
    public ImageView getImage_view_id(){
        return image_view_id;
    }
    public void Subscribe(ActionEvent actionEvent){

    }
}
