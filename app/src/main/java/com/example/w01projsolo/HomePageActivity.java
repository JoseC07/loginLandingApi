package com.example.w01projsolo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePageActivity extends AppCompatActivity {
    public static final String ACTIVITY_LABEL = "SECOND_ACTIVITY";

//    String sessionId = getIntent().getStringExtra("USER_ID");
    private TextView textViewResult;

    private TextView eUserInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        eUserInfo = findViewById(R.id.text_view_result);


        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("STRING_I_NEED");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }
        int idUserr = Integer.parseInt(newString);

        textViewResult = findViewById(R.id.text_view_result);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                List<Post> posts = response.body();


                for (Post post: posts) {
                    String content = "";

                    if(post.getUserId() == idUserr) {

                        content += "ID: " + post.getId() + "\n";
                        content += "User ID: " + post.getUserId() + "\n";
                        content += "Title: " + post.getTitle() + "\n";
                        content += "Text: " + post.getText() + "\n\n";
                    }


                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());

            }
        });

        String toastValue = getIntent().getStringExtra(ACTIVITY_LABEL);
        Toast.makeText(this, toastValue,Toast.LENGTH_SHORT).show();

    }

    public static Intent getIntent(Context context, String toastValue){
        Intent intent = new Intent(context, HomePageActivity.class);

        intent.putExtra(HomePageActivity.ACTIVITY_LABEL,toastValue);

        return intent;
    }


}