package carcar.alex.biblerandomverses;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class Favorites extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        String[] favorites = {"Fav 01", "Fav 02"};
        setListAdapter(new ArrayAdapter<>(this, R.layout.activity_favorites, R.id.favorites, favorites));
    }
}
