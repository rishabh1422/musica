package commm.example.touch.navigation;

public class SongModal  {
    public String getmSongPath() {
        return mSongPath;
    }

    public void setmSongPath(String mSongPath) {
        this.mSongPath = mSongPath;
    }

    public String getmSongText() {
        return mSongText;
    }

    public void setmSongText(String mSongText) {
        this.mSongText = mSongText;
    }

    public String getmSongSize() {
        return mSongSize;
    }

    public void setmSongSize(String mSongSize) {
        this.mSongSize = mSongSize;
    }

    public boolean ismSongSelected() {
        return mSongSelected;
    }

    public void setmSongSelected(boolean mSongSelected) {
        this.mSongSelected = mSongSelected;
    }

    String mSongPath;

    public SongModal() {
        this.mSongPath = mSongPath;
        this.mSongText = mSongText;
        this.mSongSize = mSongSize;
        this.mSongSelected = mSongSelected;
    }

    String mSongText;
    String mSongSize;
    boolean mSongSelected;
}
