package cs2901.utec.chat_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Map;
import java.util.HashMap;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import android.content.Intent;
import org.json.JSONException;
import android.view.View;



public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
	
    public void onBtnLoginClicked(View view) {
        // 1. Getting username and password inputs from view
        EditText txtUsername = (EditText) findViewById(R.id.txtUsername); //obtengo elemento a traves del id. Sin el EditText me da algo generico
        EditText txtPassword = (EditText) findViewById(R.id.txtPassword); //obtengo password a través del id.
        String username = txtUsername.getText().toString(); //obtengo el username  
        String password = txtPassword.getText().toString(); //obtengo el password

        // 2. Creating a message from user input data
        Map<String, String> message = new HashMap<>();   //Map en esteem caso es una interfaz que para la implementación del hashmap
        message.put("username", username);
        message.put("password", password);
	
	//Hasta ahora la información esta en HASHMAP
		
        // 3. Converting the message object to JSON string (jsonify)
        JSONObject jsonMessage = new JSONObject(message);

        // 4. Sending json message to Server
        JsonObjectRequest request = new JsonObjectRequest(
            Request.Method.POST,
            "http://10.0.2.2:8080/authenticate", //url-uri
            jsonMessage,			//el mensaje en formato json
            new Response.Listener<JSONObject>() {  //Para procesar la respuesta 
                @Override
                public void onResponse(JSONObject response) {
                    //TODO
                    try {
                        String message = response.getString("message");
                        if(message.equals("Authorized")) {
                            showMessage("Authenticated");
                        }
                        else {
                            showMessage("Wrong username or password");
                        }
                        showMessage(response.toString());
                    }catch (Exception e) {
                        e.printStackTrace();
                        showMessage(e.getMessage());
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace(); //imprimir esto en la consola del dispositivo movil
                    if( error instanceof  AuthFailureError ){ //esto corre si la respuesta del servidor es 401
                        showMessage("Unauthorized");
                    }
                    else {
                        showMessage(error.getMessage());
                    }
                }
            }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

}

//request(
//POST
//URL-URI
//MESSAGE
//LISTENER 
//ERRORLIST
//ENVIAR UN REQUEST
//)


