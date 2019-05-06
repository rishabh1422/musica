package commm.example.touch.navigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.*;
import android.widget.LinearLayout;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    public static final String TAG = "Main2Activity";
    RecyclerView songListRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<SongModal> arrayListSongs;
    SongListAdapter adapter;
    TextView textView;
    SeekBar songSeekBar;
    Button nextBtn, prevBtn, pauseBtn;
    int prevPosition, currentPosition;
    int count = 0;
    BottomSheetBehavior bottomSheetBehavior;
    LinearLayout linearLayout;

    static MediaPlayer mediaPlayer;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
      //  linearLayout=findViewById(R.id.delete_buttomsheet_sheet_Layout);
       // bottomSheetBehavior=BottomSheetBehavior.from(linearLayout);

        nextBtn = findViewById(R.id.skip_next);
        prevBtn = findViewById(R.id.skip_back);
        pauseBtn = findViewById(R.id.play_selected_song);
        textView = findViewById(R.id.playing_song_name);
        songListRecyclerView = findViewById(R.id.songs_list);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        songListRecyclerView.setLayoutManager(layoutManager);
        arrayListSongs = new ArrayList<>();
        mediaPlayer = new MediaPlayer();
        runTimePermission();


    }

    public void runTimePermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        displaySongs();
                    }

                    @Override

                    public void onPermissionDenied(PermissionDeniedResponse response) {


                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {

                    }


                }).check();
    }

    private void displaySongs() {

        Log.d(TAG, "Inside DisplaySongFunction");

        Uri uri;
        Cursor cursor;
        int Column_id, Column_index_data;
        int Column_name,Column_size;
        String absolutePathSong = null;
        String SongSize=MediaStore.Audio.Media.SIZE;

        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.AudioColumns.DATA,
                SongSize,
                MediaStore.Audio.Media._ID};

        String orderBy = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
        cursor = getApplicationContext().getContentResolver()
                .query(uri, projection, null, null, orderBy);
        Column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA);
        Column_id = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
        Column_name = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
        Column_size=cursor.getColumnIndexOrThrow(SongSize);


        while (cursor.moveToNext()) {
            Log.d(TAG, "Inside WHile loop");
            absolutePathSong = cursor.getString(Column_index_data);
                SongModal songModel = new SongModal();
            songModel.setmSongSelected(false);
            songModel.setmSongSize(cursor.getString(Column_size));
            songModel.setmSongPath(absolutePathSong);
            songModel.setmSongText(cursor.getString(Column_name));
            arrayListSongs.add(songModel);
        }

        adapter = new SongListAdapter(getApplicationContext(), arrayListSongs);
        songListRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyClickLister() {
            @Override
            public void itemClicked(View view, int position) {
                playSongs(position);
            }
        });

    }

    private void playSongs(int position) {
        currentPosition = position;
        if (count == 0) {
            Uri uri = Uri.parse(arrayListSongs.get(currentPosition).getmSongPath());
            textView.setText(arrayListSongs.get(currentPosition).getmSongText());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
            pauseBtn.setBackgroundResource(R.drawable.ic_pause_black_24dp);
            count += 1;
            prevPosition = position;

        }
        if (count > 0) {
            if (mediaPlayer.isPlaying() && (prevPosition != currentPosition)) {
                mediaPlayer.reset();
                Uri uri = Uri.parse(arrayListSongs.get(currentPosition).getmSongPath());
                textView.setText(arrayListSongs.get(currentPosition).getmSongText());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mediaPlayer.start();
                pauseBtn.setBackgroundResource(R.drawable.ic_pause_black_24dp);
            } else {
                mediaPlayer.reset();
                Uri uri = Uri.parse(arrayListSongs.get(currentPosition).getmSongPath());
                textView.setText(arrayListSongs.get(currentPosition).getmSongText());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mediaPlayer.start();
                pauseBtn.setBackgroundResource(R.drawable.ic_pause_black_24dp);
            }
        }
    }

    public void OnPrevClicked(View view) {
        mediaPlayer.reset();
        pauseBtn.setBackgroundResource(R.drawable.ic_play_circle_filled_black_24dp);
        if(currentPosition>0) {
            currentPosition = currentPosition - 1;
            Uri uri = Uri.parse(arrayListSongs.get(currentPosition).getmSongPath());
            textView.setText(arrayListSongs.get(currentPosition).getmSongText());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
            pauseBtn.setBackgroundResource(R.drawable.ic_pause_black_24dp);
        }else {
            currentPosition=arrayListSongs.size()-1;
            Uri uri = Uri.parse(arrayListSongs.get(currentPosition).getmSongPath());
            textView.setText(arrayListSongs.get(currentPosition).getmSongText());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
            pauseBtn.setBackgroundResource(R.drawable.ic_pause_black_24dp);

        }
    }

    public void onPauseClicked(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            pauseBtn.setBackgroundResource(R.drawable.ic_play_circle_filled_black_24dp);
        } else {
            mediaPlayer.start();
            pauseBtn.setBackgroundResource(R.drawable.ic_pause_black_24dp);
        }
    }

    public void onNextClicked(View view) {
        mediaPlayer.reset();
        pauseBtn.setBackgroundResource(R.drawable.ic_play_circle_filled_black_24dp);
        if(currentPosition<arrayListSongs.size()-1) {
            currentPosition = currentPosition + 1;
            Uri uri = Uri.parse(arrayListSongs.get(currentPosition).getmSongPath());
            textView.setText(arrayListSongs.get(currentPosition).getmSongText());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
            pauseBtn.setBackgroundResource(R.drawable.ic_pause_black_24dp);
        }
        else {
            currentPosition=0;
            Uri uri = Uri.parse(arrayListSongs.get(currentPosition).getmSongPath());
            textView.setText(arrayListSongs.get(currentPosition).getmSongText());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
            pauseBtn.setBackgroundResource(R.drawable.ic_pause_black_24dp);
        }

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Music Player");
        builder.setMessage("You Want to exsit ->");
        mediaPlayer.pause();
        builder.setPositiveButton("If You Want to Exist Press here", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mediaPlayer.release();
                finish();
            }
        });
        builder.setNegativeButton("Start Song to Press Here", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mediaPlayer.start();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }
}
