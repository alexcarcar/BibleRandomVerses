package carcar.alex.biblerandomverses

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import carcar.alex.biblerandomverses.databinding.ActivityMainBinding
import java.io.IOException
import java.io.InputStream
import kotlin.math.floor

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var passageSize = 0L
    private var passageTitle = ""
    private var favorite = false
    private var favoriteIndex = 0L
    private lateinit var bibleFavorites: BibleFavorites
    private var menu: Menu? = null
    private var pickStart = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bibleFavorites = BibleFavorites(this)
        displayPassage()
    }

    override fun onResume() {
        super.onResume()
        if (favoriteBookmark != -1L) {
            displayPassage(favoriteBookmark)
            favoriteBookmark = -1L
        }

        if (contentsBookmark != -1L) {
            gotoPassage(contentsBookmark, true)
            contentsBookmark = -1L
        }
    }

    private fun getPassage(index: Long, exact: Boolean): String {
        pickStart = if (index == -1L) {
            floor(Math.random() * MAX_SIZE).toLong()
        } else {
            index
        }
        favoriteIndex = pickStart
        displayTitle()
        return readPassage(pickStart, exact)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        setFavoritesIcon()
        return super.onPrepareOptionsMenu(menu)
    }

    private fun setFavoritesIcon() {
        menu?.findItem(R.id.favClick)?.let { item ->
            item.setIcon(if (favorite) R.drawable.ic_fav_on else R.drawable.ic_fav_off)
        }
    }

    // ================================ Menu Actions ===========================
    fun onAboutClick(item: MenuItem) {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    fun onContentsClick(item: MenuItem) {
        val intent = Intent(this, TableOfContents::class.java)
        startActivity(intent)
    }

    fun onFavClick(item: MenuItem) {
        favorite = !favorite

        if (favorite) {
            item.setIcon(R.drawable.ic_fav_on)
            bibleFavorites.addFavorite(favoriteIndex)
        } else {
            item.setIcon(R.drawable.ic_fav_off)
            bibleFavorites.removeFavorite(favoriteIndex)
        }
    }

    fun pickPassage(view: View) {
        displayPassage()
    }

    fun gotoPassage(index: Long, exact: Boolean) {
        binding.textBox.text = getPassage(index, exact)
    }

    fun displayPassage() {
        gotoPassage(-1, false)
    }

    fun pickFavorites(item: MenuItem) {
        val intent = Intent(this, FavoritesActivity::class.java)
        startActivity(intent)
    }

    fun pickOldTestament(item: MenuItem) {
        gotoPassage(0, true)
    }

    fun pickNewTestament(item: MenuItem) {
        gotoPassage(3310386, true)
    }

    fun pickWebSearch(item: MenuItem) {
        val url = "https://www.google.com/#q=$passageTitle"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    // ========================== Navigation ===============================

    fun nextPassage() {
        val index = pickStart + passageSize
        if (index > MAX_SIZE)
            displayPassage(MAX_SIZE)
        else
            displayPassage(index)
    }

    fun previousPassage() {
        val index = pickStart - passageSize
        if (index < MIN_SIZE)
            displayPassage(MIN_SIZE, true)
        else
            displayPassage(index)
    }

    fun previousPassageClick(item: MenuItem) {
        previousPassage()
    }

    fun nextPassageClick(item: MenuItem) {
        nextPassage()
    }

    fun displayPassage(index: Long) {
        binding.textBox.text = getPassage(index, false)
    }

    fun displayPassage(index: Long, exact: Boolean) {
        binding.textBox.text = getPassage(index, exact)
    }

    // ========================== Utilities ===============================

    private fun displayTitle() {
        // In activity_main.xml, 'pick' is a Button, which inherits from TextView
        passageTitle = BibleFavorites.title(pickStart)
        favorite = bibleFavorites.isFavorite(pickStart)
        setFavoritesIcon()
    }

    private fun readPassage(pickStart: Long, exact: Boolean): String {
        var passage = ""
        var line: String
        passageSize = 0
        var currentIndex = pickStart
        var firstTime = true
        try {
            resources.openRawResource(R.raw.all).use { source ->
                source.skip(pickStart)
                if (!exact) {
                    currentIndex += readLine(source).length.toLong()
                }
                for (i in 0 until LINES) {
                    line = readLine(source)
                    if (firstTime) {
                        passage += "(${BibleFavorites.title(currentIndex).uppercase()}, KJV)\n\n"
                        firstTime = false
                    } else if (line.startsWith("1 ")) {
                        passage += "(${BibleFavorites.title(currentIndex + line.length).uppercase()}, KJV)\n\n"
                    }
                    passage += line
                    passage += "\n\n"
                    passageSize += line.length.toLong()
                    currentIndex += line.length.toLong()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return passage
    }

    companion object {
        private const val FILE_SIZE = 4289338L
        private const val LINES = 5
        private const val MIN_SIZE = 0L
        private const val MAX_SIZE = FILE_SIZE - (LINES * 100)

        @JvmField
        var favoriteBookmark = -1L
        @JvmField
        var contentsBookmark = -1L

        @JvmStatic
        fun readLine(source: InputStream): String {
            val sb = StringBuilder()
            try {
                while (true) {
                    val i = source.read()
                    if (i == -1) break
                    val c = i.toChar()
                    if (c == '\n') break
                    sb.append(c)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return sb.toString()
        }
    }
}
