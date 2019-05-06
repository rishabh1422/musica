package commm.example.touch.navigation;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.FrameLayout;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    ImageView iv;
        Button btn,btn1;
EditText et1,et;
    DrawerLayout dl;
    NavigationView nv;
    Toolbar tb;
    FrameLayout f1;
    android.support.v4.app.FragmentManager fm;
    android.support.v4.app.FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=findViewById(R.id.et);
        et1=findViewById(R.id.et1);
        btn=findViewById(R.id.btn);
        btn1=findViewById(R.id.btn1);
        dl=findViewById(R.id.dl);
        nv=findViewById(R.id.nv);
        tb=findViewById(R.id.tb);
        setSupportActionBar(tb);

        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        View v1=nv.getHeaderView(0);
        iv=v1.findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent,1);

            }
        });
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                item.setCheckable(false);
                dl.closeDrawers();
                switch (item.getItemId())
                {
                    case R.id.homeid:
                        break;
                    case R.id.mobid:

                        break;
                    case R.id.shopid:
                        Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.orderid:
                        break;
                    case R.id.pid:
                        break;
                }
                return true;

                }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                dl.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1)
        {
            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
            Bundle extras=data.getExtras();
            Bitmap imageBitmap=(Bitmap)extras.get("data");
            iv.setImageBitmap(imageBitmap);

        }
    }
    public void Register(View view)
    {
        Intent intent=new Intent(MainActivity.this,Main3Activity.class);
        startActivity(intent);
    }
   /* public void LOGIN(View view)


    {
        Intent intent=new Intent(MainActivity.this,Main2Activity.class);
        startActivity(intent);
    }*/
    public void LOGIN(View view)
    {
        if(et.getText().toString().equals("Admin")&&et1.getText().toString().equals("Admin"))
        {
            Intent intent=new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent);
        }
    }

}
