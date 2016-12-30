package rtc.paritta.phiangthong.gameproverbs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowScoreActivity extends AppCompatActivity {
    private TextView scoreTotalView;
    //private int totalscore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_score);

        scoreTotalView = (TextView) findViewById(R.id.textView4);
        ImageView imageView = (ImageView) findViewById(R.id.imageView4);

        Intent mIntent = getIntent();
        int totalscore = mIntent.getIntExtra("Score",0);

        scoreTotalView.setText(Integer.toString(totalscore));


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowScoreActivity.this, MainActivity.class));
                finish();
            }
        });

        ImageView imageView1 = (ImageView) findViewById(R.id.imageView5);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
