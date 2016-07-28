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
    private static final long MIN_SIZE = 0;
    private static final long MAX_SIZE = FILE_SIZE - SIZE;
    private static final long LINES = 5;

    private static TextView txtPassage;
    private boolean favorite = false;
    private Long favoriteIndex = 0L;
    private BibleFavorites bibleFavorites = null;
    private Menu menu = null;
    private long phraseSelected = -1;
    private long pickStart = -1;
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
        wireupSwipe();
    }

    private String getPassage(long goToPassage) {
        if (goToPassage == -1) {
            pickStart = (long) Math.floor(Math.random() * MAX_SIZE);
        } else {
            pickStart = goToPassage;
        }

        if (favoriteClicked) {
            pickStart = phraseSelected;
            favoriteClicked = false;
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

    public void clearFavorites(MenuItem item) {
        bibleFavorites.clear();
        favorite = false;
        setFavoritesIcon();
    }

    public void pickForward(MenuItem item) {
        goForward();
    }

    public void pickBackward(MenuItem item) {
        goBackward();
    }

    // ========================== Utilities ===============================

    public void pickFavorites(MenuItem item) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void goForward() {
        long goToPassage = pickStart + SIZE;
        if (goToPassage > MAX_SIZE)
            getPassage(MAX_SIZE);
        else
            getPassage(goToPassage);
    }

    public void goBackward() {
        long goToPassage = pickStart - SIZE;
        if (goToPassage < MIN_SIZE)
            getPassage(MIN_SIZE);
        else
            getPassage(goToPassage);
    }

    private void wireupSwipe() {
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
                        if (Math.abs(x2 - x1) < 10) return true;
                        if (x2 > x1) {
                            goBackward();
                        } else {
                            goForward();
                        }
                        return true;
                }
                return false;
            }
        });
    }

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
}
