package carcar.alex.biblerandomverses;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends ListActivity {

    private List<Long> favoriteIds = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View headerView = ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.activity_favorites_header, this.getListView(), false);
        this.getListView().addHeaderView(headerView);

        View footerView = ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.activity_favorites_footer, this.getListView(), false);
        this.getListView().addFooterView(footerView);

        BibleFavorites bibleFavorites = new BibleFavorites(this);
        ArrayList<String> favorites = new ArrayList<>();
        favoriteIds = bibleFavorites.getFavorites();
        for (Long id : favoriteIds) {
            favorites.add(BibleFavorites.title(id));
        }
        setListAdapter(new ArrayAdapter<>(this, R.layout.activity_favorites, R.id.favorites, favorites));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        long phraseSelected = favoriteIds.get(position-1);
        intent.putExtra("phraseSelected", phraseSelected);
        startActivity(intent);
    }

    public void closeWindow(View view) {
        this.finish();
    }
}
