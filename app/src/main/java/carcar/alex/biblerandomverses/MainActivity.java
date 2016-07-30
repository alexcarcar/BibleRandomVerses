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
    private static final long LINES = 5;
    private static final long SIZE = LINES * 100;
    private static final long MIN_SIZE = 0;
    private static final long MAX_SIZE = FILE_SIZE - SIZE;


    private static TextView txtPassage;
    private boolean favorite = false;
    private Long favoriteIndex = 0L;
    private BibleFavorites bibleFavorites = null;
    private Menu menu = null;
    private long pickStart = -1;

    public static long favoriteBookmark = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.bibleFavorites = new BibleFavorites(this);
        txtPassage = (TextView) findViewById(R.id.text_box);
        displayPassage();
        swipeLeftRightListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (favoriteBookmark != -1) {
            displayPassage(favoriteBookmark);
            favoriteBookmark = -1;
        }
    }

    private String getPassage(long index) {
        if (index == -1) {
            pickStart = (long) Math.floor(Math.random() * MAX_SIZE);
        } else {
            pickStart = index;
        }
        favoriteIndex = pickStart;
        displayTitle();
        return readPassage(pickStart);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public void pickPassage(View view) {
        displayPassage();
    }

    public void pickPassage(MenuItem item) {
        displayPassage();
    }

    public void displayPassage() {
        txtPassage.setText(getPassage(-1));
    }

//    public void clearFavorites(MenuItem item) {
//        bibleFavorites.clear();
//        favorite = false;
//        setFavoritesIcon();
//    }

    public void pickFavorites(MenuItem item) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

    // ========================== Navigation ===============================

    public void nextPassage() {
        long index = pickStart + SIZE;
        if (index > MAX_SIZE)
            displayPassage(MAX_SIZE);
        else
            displayPassage(index);
    }

    public void previousPassage() {
        long index = pickStart - SIZE;
        if (index < MIN_SIZE)
            displayPassage(MIN_SIZE);
        else
            displayPassage(index);
    }

//    public void previousPassageClick(View view) {
//        previousPassage();
//    }
//
//    public void nextPassageClick(View view) {
//        nextPassage();
//    }

    public void displayPassage(long index) {
        txtPassage.setText(getPassage(index));
    }

    private void swipeLeftRightListener() {
        txtPassage.setOnTouchListener(new OnTouchListener() {
            float x1 = -1, y1 = -1;
            float x2 = -1, y2 = -1;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        y2 = event.getY();
                        if (x2 > x1) {
                            previousPassage();
                        } else {
                            nextPassage();
                        }
                        return true;
                }
                return false;
            }
        });
    }
    // ========================== Utilities ===============================


    private void displayTitle() {
        TextView scriptureTitle = (TextView) findViewById(R.id.pick);
        if (scriptureTitle != null) {
            scriptureTitle.setText(BibleFavorites.title(pickStart));
        }
        favorite = bibleFavorites.isFavorite(pickStart);
        setFavoritesIcon();
    }

    private String readPassage(long pickStart) {
        String passage = "";
        try {
            InputStream source = this.getResources().openRawResource(R.raw.all);
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


}
