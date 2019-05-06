package commm.example.touch.navigation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import java.util.ArrayList;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.MySongViewHolder> {


    Context context;
    ArrayList<SongModal> arrayListSongs;
    private MyClickLister onCardClickListener=null;

    public SongListAdapter(MyClickLister onCardClickListener) {
        this.onCardClickListener = onCardClickListener;
    }

    public void setOnItemClickListener(MyClickLister mListener)
    {
        onCardClickListener=mListener;
    }
    public SongListAdapter(Context context, ArrayList<SongModal> arrayListSongs) {
        this.context = context;
        this.arrayListSongs = arrayListSongs;
    }

    @NonNull
    @Override
    public MySongViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.songs_list_layout,viewGroup,false);
        MySongViewHolder holder=new MySongViewHolder(view,onCardClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MySongViewHolder mySongViewHolder,int i) {
        String s=arrayListSongs.get(i).getmSongSize();
        float size=Integer.parseInt(s);
        size=(size/(1024*1024));
        String mSongSize=String.format("%.2f",size);

        mySongViewHolder.mSizetextView.setText(mSongSize+" MB");
        mySongViewHolder.textView.setText(arrayListSongs.get(i).getmSongText());
    }

    @Override
    public int getItemCount() {
        return arrayListSongs.size();
    }

    public static class MySongViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        TextView mSizetextView;
        LinearLayout songLinearlayout;
        public MySongViewHolder(@NonNull View itemView, final MyClickLister listener) {
            super(itemView);
            textView=itemView.findViewById(R.id.song_name);
            mSizetextView=itemView.findViewById(R.id.song_size_text);
            songLinearlayout=itemView.findViewById(R.id.linear_songlist_layout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.itemClicked(v,position);

                        }
                    }
                }
            });

        }
    }
}
