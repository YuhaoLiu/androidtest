package liu.yuhao.androidtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

import liu.yuhao.androidtest.R;
import liu.yuhao.androidtest.readFile;


public class result extends AppCompatActivity {
    private static JSONObject results;
    private final int FP = ViewGroup.LayoutParams.FILL_PARENT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final CountDownLatch latch = new CountDownLatch(1);
        Thread a = new Thread() {

            @Override
            public void run() {
                super.run();
                String info = readFile.read("http://api.rottentomatoes.com/api/public/v1.0/" +
                        "movies.json?apikey=6qvrmbehyspcu57hma2q222z&q=android&page_limit=30");
                try {
                    results = new JSONObject(info);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                latch.countDown();

            }
        };
        a.start();
        try {
            latch.await();
            setContentView(R.layout.result);

            a.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TableLayout tableLayout = (TableLayout) findViewById(R.id.resultTable);
        try {




            JSONArray movie = readFile.getMovie(results);
            int count = 0;
            JSONObject temp = null;

            while (count != movie.length()) {
                TableRow newRow = new TableRow(this);
                temp = movie.getJSONObject(count);
                for (int i = 0; i < 3; i++) {
                    TextView v = new TextView(this);
                    if (i == 0) {
                        v.setText(temp.getString("title"));
                        newRow.addView(v);
                    }
                    if (i == 1) {
                        v.setText(temp.getString("year"));
                        newRow.addView(v);
                    }
                    if (i == 2) {
                        if(temp.getString("runtime").isEmpty())v.setText("Not data");
                        else v.setText(temp.getString("runtime"));
                        newRow.addView(v);
                    }

                }
                tableLayout.addView(newRow, new TableLayout.LayoutParams(FP,FP));
                count++;

            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        }



    }


}

