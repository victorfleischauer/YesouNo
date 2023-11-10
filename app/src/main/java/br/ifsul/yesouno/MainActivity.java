package br.ifsul.yesouno;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private final String URL = "https://yesno.wtf/";
    private Retrofit retrofitYesNo;

    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.editText = findViewById(R.id.editText);
        this.button = findViewById(R.id.button);

        button.setOnClickListener(
                v -> {
                    String pergunta = editText.getText().toString();

                    if(pergunta.matches("")){
                        Toast toast = new Toast(getApplicationContext());
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setText("Escreva uma pergunta");
                        toast.show();
                    }
                    else {
                        retrofitYesNo = new Retrofit.Builder()
                                .baseUrl(URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        RestService restService = retrofitYesNo.create(RestService.class);
                        Call<YesNo> call = restService.consultarYesNo();

                        // Colocando a requisição na fila para execução
                        call.enqueue(new Callback<YesNo>() {
                            @Override
                            public void onResponse(Call<YesNo> call, Response<YesNo> response) {
                                if (response.isSuccessful()) {
                                    YesNo yesNo = response.body();

                                    exibirPopUp(yesNo.getAnswer());
                                }
                            }

                            @Override
                            public void onFailure(Call<YesNo> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Ocorreu um erro ao tentar consultar a resposta. Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }
        );
    }

    private void exibirPopUp(String texto) {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        TextView popupText = popupView.findViewById(R.id.popupText);
        popupText.setText(texto.toUpperCase());

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);


        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}