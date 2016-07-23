package carcar.alex.biblerandomverses;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends ListActivity {

    private List<Long> favoriteIds = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BibleFavorites bibleFavorites = new BibleFavorites(this);
        ArrayList<String> favorites = new ArrayList<>();
        favoriteIds = bibleFavorites.getFavorites();
        for (Long id : favoriteIds) {
            favorites.add(BibleFavorites.title(id));
        }
/*        Activity view = new Activity();
        view.setContentView(R.layout.list_close_row);
        TextView footer = (TextView) view.findViewById(R.id.closeFavorites);
        getListView().addFooterView(footer);*/
        setListAdapter(new ArrayAdapter<>(this, R.layout.activity_favorites, R.id.favorites, favorites));
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        long phraseSelected = favoriteIds.get(position);
        intent.putExtra("phraseSelected", phraseSelected);
        startActivity(intent);
        this.finish();
    }
}
