package carcar.alex.biblerandomverses;

import android.content.Intent;
import android.net.Uri;
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
    private static final long LINES = 5;
    private static final long SIZE = LINES * 100;
    private static final long MIN_SIZE = 0;
    private static final long MAX_SIZE = FILE_SIZE - SIZE;

    private long passageSize = 0;
    private String passageTitle = "";
    private static TextView txtPassage;
    private boolean favorite = false;
    private Long favoriteIndex = 0L;
    private BibleFavorites bibleFavorites = null;
    private Menu menu = null;
    private long pickStart = -1;

    public static long favoriteBookmark = -1;
    public static long contentsBookmark = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.bibleFavorites = new BibleFavorites(this);
        txtPassage = (TextView) findViewById(R.id.text_box);
        displayPassage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (favoriteBookmark != -1) {
            displayPassage(favoriteBookmark);
            favoriteBookmark = -1;
        }

        if (contentsBookmark != -1) {
            gotoPassage(contentsBookmark, true);
            contentsBookmark = -1;
        }
    }

    private String getPassage(long index, boolean exact) {
        if (index == -1) {
            pickStart = (long) Math.floor(Math.random() * MAX_SIZE);
        } else {
            pickStart = index;
        }
        favoriteIndex = pickStart;
        displayTitle();
        return readPassage(pickStart, exact);
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

    public void onContentsClick(MenuItem item) {
        Intent intent = new Intent(this, TableOfContents.class);
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

//    public void pickPassage(MenuItem item) {
//        displayPassage();
//    }

    public void gotoPassage(long index, boolean exact) {
        txtPassage.setText(getPassage(index, exact));
    }
    public void displayPassage() {
        gotoPassage(-1, false);
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

    public void pickOldTestament(MenuItem item) {
        gotoPassage(0, true);
    }

    public void pickNewTestament(MenuItem item) {
        gotoPassage(3310386, true);
    }

//    public void pickPsalmProverb(MenuItem item) {
//        long startIndex = 2071141;
//        long endIndex = 2386860;
//        long searchSize = endIndex - startIndex;
//        long choosePsalmProverb = startIndex + (long) Math.floor(Math.random() * searchSize);
//        gotoPassage(choosePsalmProverb, false);
//    }

    public void pickWebSearch(MenuItem item) {
        String url = "https://www.google.com/#q=" + passageTitle;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    // ========================== Navigation ===============================

    public void nextPassage() {
        long index = pickStart + passageSize;
        if (index > MAX_SIZE)
            displayPassage(MAX_SIZE);
        else
            displayPassage(index);
    }

    public void previousPassage() {
        long index = pickStart - passageSize;
        if (index < MIN_SIZE)
            displayPassage(MIN_SIZE, true);
        else
            displayPassage(index);
    }

    public void previousPassageClick(MenuItem item) {
        previousPassage();
    }

    public void nextPassageClick(MenuItem item) {
        nextPassage();
    }

    public void displayPassage(long index) {
        txtPassage.setText(getPassage(index, false));
    }

    public void displayPassage(long index, boolean exact) {
        txtPassage.setText(getPassage(index, exact));
    }

    // ========================== Utilities ===============================

    private void displayTitle() {
        TextView scriptureTitle = (TextView) findViewById(R.id.pick);
        if (scriptureTitle != null) {
            passageTitle = BibleFavorites.title(pickStart);
            // scriptureTitle.setText(passageTitle);
        }
        favorite = bibleFavorites.isFavorite(pickStart);
        setFavoritesIcon();
    }

    private String readPassage(long pickStart, boolean exact) {
        String passage = "";
        String line;
        passageSize = 0;
        long currentIndex = pickStart;
        boolean firstTime = true;
        try {
            InputStream source = this.getResources().openRawResource(R.raw.all);
            if (source.skip(pickStart) < 0) return "";
            if (!exact) {
                currentIndex += readLine(source).length();
            }
            for (int i = 0; i < LINES; i++) {
                line = readLine(source);
                if (firstTime) {
                    passage += "(" + BibleFavorites.title(currentIndex).toUpperCase() + ")\n\n";
                    firstTime = false;
                } else if (line.startsWith("1 ")) {
                    passage += "(" + BibleFavorites.title(currentIndex + line.length()).toUpperCase() + ", KJV)\n\n";
                }
                passage += line;
                passage += "\n\n";
                passageSize += line.length();
                currentIndex += line.length();
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
