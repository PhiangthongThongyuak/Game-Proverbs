package rtc.paritta.phiangthong.gameproverbs;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

//import com.squareup.picasso.Picasso;
public class PlayActivity extends AppCompatActivity implements View.OnClickListener {
    //Explicit
    private TextView scoreTextView, timeTextView, articleTextView, questiontextView;
    private Button choice1Button, choice2Button;
    private int timeAnInt = 0, timesAnInt = 1, scoreAnInt = 0, randomAnInt, lengthAnInt, myAnswer;
    private boolean timeABoolean = false;   // false ==> ยังไม่หมดเวลา 120 วินาที
    private String jsonString;
    private String[] questionStrings, choice1Strings, choice2Strings, answerStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //Sound Effect
        MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.bgm);
        mediaPlayer.start();

        // Auto Run
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(PlayActivity.this, ShowScoreActivity.class));
                finish();
            }
        }, 120000);


        //Bind Widget
        scoreTextView = (TextView) findViewById(R.id.textView);
        timeTextView = (TextView) findViewById(R.id.textView2);
        //articleTextView = (TextView) findViewById(R.id.textView3);
        questiontextView = (TextView) findViewById(R.id.textView4);
        choice1Button = (Button) findViewById(R.id.button1);
        choice2Button = (Button) findViewById(R.id.button2);
        //Syn Question From Server
        synQuestion();
        //Show First View
        Random random = new Random();
        randomAnInt = random.nextInt(lengthAnInt);
        Log.d("gameV2", "randomAnInt ==> " + randomAnInt);
        ShowView();
        //My Loop
        myLoop();
        //Button Controller
        choice1Button.setOnClickListener(this);
        choice2Button.setOnClickListener(this);
    }   // Main Method

    private void ShowView() {
        questiontextView.setText(questionStrings[randomAnInt]);
        choice1Button.setText(choice1Strings[randomAnInt]);
        choice2Button.setText(choice2Strings[randomAnInt]);
        scoreTextView.setText("คะแนน : " + Integer.toString(scoreAnInt));

    }   // showView

    private void synQuestion() {
        try {
            SynQuestion synQuestion = new SynQuestion(PlayActivity.this);
            synQuestion.execute();
            jsonString = synQuestion.get();
            Log.d("gameV1", "JSON == > " + jsonString);
            JSONArray jsonArray = new JSONArray(jsonString);
            lengthAnInt = jsonArray.length();
            //จองหน่วยความจำ
            questionStrings = new String[jsonArray.length()];
            choice1Strings = new String[jsonArray.length()];
            choice2Strings = new String[jsonArray.length()];
            answerStrings = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                questionStrings[i] = jsonObject.getString("Question");
                choice1Strings[i] = jsonObject.getString("Choice1");
                choice2Strings[i] = jsonObject.getString("Choice2");
                answerStrings[i] = jsonObject.getString("Answer");
            }   // for
        } catch (Exception e) {
            Log.d("gameV1", "e synQuestion ==> " + e.toString());
        }
    }   // synQuestion

    private void myLoop() {
        //***************
        // To Do
        //***************
        //Show Time
        timeTextView.setText("เวลา " + Integer.toString(120 - timeAnInt) + " วินาที");
        timeAnInt += 1;
        if (timeAnInt == 120) {
            timeABoolean = true;
        }   // if
        //Post Delay
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!timeABoolean) {
                    myLoop();
                } else {
                    //หมดเวลา 120 sec
                    Intent intent = new Intent(PlayActivity.this, ShowScoreActivity.class);
                    intent.putExtra("Score", scoreAnInt);
                    startActivity(intent);
                    finish();
                }   // if
            }   //run
        }, 1000);
    }   // myLoop

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button1:
                myAnswer = 1;
                break;
            case R.id.button2:
                myAnswer = 2;
                break;
        }

        Random random = new Random();
        randomAnInt = random.nextInt(lengthAnInt);
        Log.d("gameV2", "randomAnInt ==> " + randomAnInt);

        if (myAnswer == Integer.parseInt(answerStrings[randomAnInt])) {
            scoreAnInt += 1;
        }

        ShowView();
    }   //onClick
}   // Main Class