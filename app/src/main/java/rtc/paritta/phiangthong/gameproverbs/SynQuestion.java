package rtc.paritta.phiangthong.gameproverbs;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Administrator on 22/11/2559.
 */

public class SynQuestion extends AsyncTask<Void, Void, String>{

    //Explicit
    private static final String urlJSON = "http://swiftcodingthai.com/ary/get_question.php";
    private Context context;

    public SynQuestion(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {

        try {

        } catch (Exception e) {
            Log.d("gameV1", "e doIn ==> " + e.toString());
        }


        return null;
    }
}   // Main Class