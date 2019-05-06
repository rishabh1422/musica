package commm.example.touch.navigation;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class Main3Activity extends AppCompatActivity {
    ImageView tv;
    EditText et1, et2;
    Button bt1, bt2;
    DataBaseHolder dataBaseHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        tv = findViewById(R.id.iv);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        bt1 = findViewById(R.id.bt1);

        dataBaseHolder = new DataBaseHolder(this);
        addData();

    }

    public void addData() {
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = dataBaseHolder.insertData(et1.getText().toString(), et2.getText().toString());
                if (isInserted == true) {
                    Toast.makeText(Main3Activity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Main3Activity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}

