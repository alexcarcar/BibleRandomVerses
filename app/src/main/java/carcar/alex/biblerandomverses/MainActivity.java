package carcar.alex.biblerandomverses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final long FILE_SIZE = 4289338;
    private static final long SIZE = 2000;
    private static final long LINES = 5;
    private static TextView txtPassage;
    private boolean favorite = false;
    private Long favoriteIndex = 0L;
    private BibleFavorites bibleFavorites = null;
    private Menu menu = null;
    private long phraseSelected = -1;
    private boolean favoriteClicked = false;

    public static String readLine(InputStream source) {
        String line = "";
        char c;
        try {
            do {
                int i = source.read();
                if (i == -1) break;
                c = (char) i;
                if (c != '\n') line += c;
            } while (c != '\n');
        } catch (Exception e) {
            e.printStackTrace();
        }
        return line;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.bibleFavorites = new BibleFavorites(this);
        txtPassage = (TextView) findViewById(R.id.text_box);
        try {
            Intent intent = getIntent();
            phraseSelected = intent.getExtras().getLong("phraseSelected", -1);
            if (phraseSelected != -1) favoriteClicked = true;
        } catch (Exception ex) {
            favoriteClicked = false;
        }
        displayPassage();

        txtPassage.setOnTouchListener(new OnTouchListener() {
            float x1 = 0, y1 = 0, t1 = 0, x2 = 0, y2 = 0, t2 = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();
                        t1 = System.currentTimeMillis();
                        return true;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        y2 = event.getY();
                        t2 = System.currentTimeMillis();
                        if (x1 > x2) {
                            goBackward();
                        } else if (x2 > x1) {
                            goForward();
                        }
                        return true;
                }
                return false;
            }
        });
    }

    public String getPassage(long bookmark) {
        String passage = "";
        try {
            InputStream source = this.getResources().openRawResource(R.raw.all);
            long n = FILE_SIZE - SIZE;
            long pickStart = (long) Math.floor(Math.random() * n);
            if (bookmark != -1)
                pickStart = bookmark;

            if (favoriteClicked) {
                pickStart = phraseSelected;
                favoriteClicked = false;
            }

            favoriteIndex = pickStart;

            TextView scriptureTitle = (TextView) findViewById(R.id.pick);
            if (scriptureTitle != null) {
                scriptureTitle.setText(BibleFavorites.title(pickStart));
            }

            favorite = bibleFavorites.isFavorite(pickStart);
            setFavoritesIcon();

            if (source.skip(pickStart) < 0) return "";
            readLine(source);
            for (int i = 0; i < LINES; i++) {
                passage += readLine(source);
                passage += "\n\n";
            }
            source.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return passage;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void displayPassage() {
        txtPassage.setText(getPassage(-1));
    }

    public void pickPassage(View view) {
        displayPassage();
    }

    public void onAboutClick(MenuItem item) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void onFavClick(MenuItem item) {
        favorite = !favorite;

        if (favorite) {
            item.setIcon(R.drawable.ic_fav_on);
            bibleFavorites.addFavorite(favoriteIndex);
        } else {
            item.setIcon(R.drawable.ic_fav_off);
            bibleFavorites.removeFavorite(favoriteIndex);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        this.menu = menu;
        setFavoritesIcon();
        return super.onPrepareOptionsMenu(menu);
    }

    private void setFavoritesIcon() {
        if (this.menu != null) {
            MenuItem item = this.menu.findItem(R.id.favClick);
            if (item != null) {
                item.setIcon(favorite ? R.drawable.ic_fav_on : R.drawable.ic_fav_off);
            }
        }
    }

    // ================================ Menu Actions ===========================
    public void pickPassage(MenuItem item) {
        displayPassage();
    }

//    public void clearFavorites(MenuItem item) {
//        bibleFavorites.clear();
//        favorite = false;
//        setFavoritesIcon();
//    }

    // ==========================================================================

    public void pickFavorites(MenuItem item) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void goForward() {
        long bookmark = favoriteIndex + SIZE;
        long max = FILE_SIZE - SIZE;
        if (bookmark > max)
            getPassage(max);
        else
            getPassage(bookmark);
    }

    public void goBackward() {
        long bookmark = favoriteIndex - SIZE;
        if (bookmark < 0)
            getPassage(0);
        else
            getPassage(bookmark);
    }

    public void pickForward(MenuItem item) {
        goForward();
    }

    public void pickBackward(MenuItem item) {
        goBackward();
    }
}
