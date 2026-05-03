package carcar.alex.biblerandomverses

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import carcar.alex.biblerandomverses.databinding.ActivityFavoritesListBinding

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesListBinding
    private var favoriteIds: List<Long>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bibleFavorites = BibleFavorites(this)
        val favoritesStrings = mutableListOf<String>()
        favoriteIds = bibleFavorites.getFavorites()
        
        favoriteIds?.let { ids ->
            for (id in ids) {
                favoritesStrings.add(BibleFavorites.title(id))
            }
        }
        
        val adapter = ArrayAdapter(this, R.layout.activity_favorites, R.id.favorites, favoritesStrings)
        binding.favoritesList.adapter = adapter

        binding.favoritesList.setOnItemClickListener { _, _, position, _ ->
            favoriteIds?.let { ids ->
                if (position >= 0 && position < ids.size) {
                    MainActivity.favoriteBookmark = ids[position]
                    finish()
                }
            }
        }
    }

    fun closeWindow(view: View) {
        finish()
    }
}
