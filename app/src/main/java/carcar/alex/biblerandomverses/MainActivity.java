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

        this.bibleFavorites = new BibleFavorites(this);
        setContentView(R.layout.activity_main);
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

            favorite = false;
            if (menu != null) {
                MenuItem item = menu.findItem(R.id.favClick);
                if (item != null) {
                    item.setIcon(R.drawable.ic_fav_off);
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
        return super.onPrepareOptionsMenu(menu);
    }

    public void pickPassage(MenuItem item) {
        displayPassage();
    }

    public void pickFavorites(MenuItem item) {
        Intent intent = new Intent(this, Favorites.class);
        startActivity(intent);
        this.finish();
    }
}
