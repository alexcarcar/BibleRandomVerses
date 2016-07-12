package carcar.alex.biblerandomverses;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BibleFavorites {
    private static final String BIBLE_FAVORITES = "bible-favorites.txt";
    private List<Long> favorites;
    private Context context = null;

    public BibleFavorites(Context context) {
        super();
        this.context = context;
        this.favorites = new ArrayList<>();

        try {
            InputStream in = context.openFileInput(BIBLE_FAVORITES);
            if (in != null) {
                InputStreamReader tmp = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(tmp);
                String line;
                while ((line = reader.readLine()) != null) {
                    this.addFavorite(Long.parseLong(line));
                }
                in.close();
            }
        } catch (FileNotFoundException e) {
            // okay: on the first load
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            OutputStreamWriter out = new OutputStreamWriter(context.openFileOutput(BIBLE_FAVORITES, 0));
            for (Long favorite : this.favorites) {
                out.write(favorite + "\n");
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public List<Long> getFavorites() {
//        return favorites;
//    }

//    public void setFavorites(List<Long> favorites) {
//        this.favorites = favorites;
//    }

    @Override
    public String toString() {
        return "BibleFavorites [favorites=" + favorites + "]";
    }

//    public void print() {
//        System.out.println(this.toString());
//    }

    public void addFavorite(Long favorite) {
        for (Long fav : this.favorites) {
            if (fav.longValue() == favorite.longValue()) {
                return; // no need to add it since it already exists
            }
        }
        this.favorites.add(favorite);
        this.save();
    }

    public void removeFavorite(Long favorite) {
        boolean modified = false;
        Iterator<Long> i = this.favorites.iterator();
        while (i.hasNext()) {

            if (i.next().longValue() == favorite.longValue()) {
                i.remove();
                modified = true;
            }
        }
        if (modified) this.save();
    }
}

