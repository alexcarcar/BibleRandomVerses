package carcar.alex.biblerandomverses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    }

    public String getPassage() {
        String passage = "";
        try {
            InputStream source = this.getResources().openRawResource(R.raw.all);
            long n = FILE_SIZE - SIZE;
            long pickStart = (long) Math.floor(Math.random() * n);

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
            if (this.menu != null) {
                MenuItem item = this.menu.findItem(R.id.favClick);
                if (item != null) {
                    item.setIcon(favorite ? R.drawable.ic_fav_on : R.drawable.ic_fav_off);
                }
            }

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
        txtPassage.setText(getPassage());
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
        if (this.menu != null) {
            MenuItem item = this.menu.findItem(R.id.favClick);
            if (item != null) {
                item.setIcon(favorite ? R.drawable.ic_fav_on : R.drawable.ic_fav_off);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    // ================================ Menu Actions ===========================
    public void pickPassage(MenuItem item) {
        displayPassage();
    }

    public void pickFavorites(MenuItem item) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

    public void clearFavorites(MenuItem item) {
        bibleFavorites.clear();
    }

    // ==========================================================================

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
