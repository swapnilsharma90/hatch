package dd.android.hatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by swapsharma on 10/13/16..
 */
public class MainActivity extends AppCompatActivity {

TextView myid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myid=(TextView) findViewById(R.id.myid);

    }
}
