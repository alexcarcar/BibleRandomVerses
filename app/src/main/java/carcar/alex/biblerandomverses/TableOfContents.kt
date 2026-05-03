package carcar.alex.biblerandomverses

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class TableOfContents : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents)
        
        setupClickListeners()
    }

    private val bookOffsets = mapOf(
        R.id.old_testament_btn to 0L,
        R.id.new_testament_btn to 3310386L,
        R.id.genesis to 0L,
        R.id.exodus to 202530L,
        R.id.leviticus to 376409L,
        R.id.numbers to 506463L,
        R.id.deuteronomy to 686935L,
        R.id.joshua to 836909L,
        R.id.judges to 939563L,
        R.id.ruth to 1040553L,
        R.id.samuel1 to 1053860L,
        R.id.samuel2 to 1186112L,
        R.id.kings1 to 1295108L,
        R.id.kings2 to 1425198L,
        R.id.chronicles1 to 1548613L,
        R.id.chronicles2 to 1663184L,
        R.id.ezra to 1805227L,
        R.id.nehemiah to 1846656L,
        R.id.estherGreek to 1905298L,
        R.id.esther to 1942195L,
        R.id.job to 1973005L,
        R.id.psalms to 2071141L,
        R.id.proverbs to 2303513L,
        R.id.ecclesiastes to 2386860L,
        R.id.songOfSolomon to 2416003L,
        R.id.isaiah to 2430088L,
        R.id.jeremiah to 2628657L,
        R.id.lamentations to 2857392L,
        R.id.ezekiel to 2876049L,
        R.id.daniel to 3086118L,
        R.id.hosea to 3149327L,
        R.id.joel to 3177117L,
        R.id.amos to 3188098L,
        R.id.obadiah to 3210463L,
        R.id.jonah to 3214116L,
        R.id.micah to 3220901L,
        R.id.nahum to 3237589L,
        R.id.habakkuk to 3244661L,
        R.id.zephaniah to 3252781L,
        R.id.haggai to 3261466L,
        R.id.zechariah to 3267335L,
        R.id.malachi to 3301006L,
        R.id.matthew to 3310386L,
        R.id.mark to 3438839L,
        R.id.luke to 3520526L,
        R.id.john to 3659585L,
        R.id.acts to 3760943L,
        R.id.romans to 3894548L,
        R.id.corinthians1 to 3946359L,
        R.id.corinthians2 to 3996888L,
        R.id.galatians to 4029802L,
        R.id.ephesians to 4046575L,
        R.id.philippians to 4063423L,
        R.id.colossians to 4075361L,
        R.id.thessalonians1 to 4086425L,
        R.id.thessalonians2 to 4096399L,
        R.id.timothy1 to 4102002L,
        R.id.timothy2 to 4115098L,
        R.id.titus to 4124583L,
        R.id.philemon to 4129870L,
        R.id.hebrews to 4132281L,
        R.id.james to 4170584L,
        R.id.peter1 to 4183108L,
        R.id.peter2 to 4196946L,
        R.id.john1 to 4205863L,
        R.id.john2 to 4218997L,
        R.id.john3 to 4220593L,
        R.id.jude to 4222233L,
        R.id.revelation to 4225847L
    )

    private fun setupClickListeners() {
        bookOffsets.forEach { (id, offset) ->
            findViewById<View>(id)?.setOnClickListener { gotoPassage(offset) }
        }
    }

    fun closeWindow(view: View) {
        finish()
    }

    private fun gotoPassage(index: Long) {
        MainActivity.contentsBookmark = index
        finish()
    }

    // Individual methods are kept for XML compatibility if needed, 
    // but the setupClickListeners overrides them for better control.
    fun oldTestment(view: View) = gotoPassage(0)
    fun newTestment(view: View) = gotoPassage(3310386)
    fun genesis(view: View) = gotoPassage(0)
    fun exodus(view: View) = gotoPassage(202530)
    fun leviticus(view: View) = gotoPassage(376409)
    fun numbers(view: View) = gotoPassage(506463)
    fun deuteronomy(view: View) = gotoPassage(686935)
    fun joshua(view: View) = gotoPassage(836909)
    fun judges(view: View) = gotoPassage(939563)
    fun ruth(view: View) = gotoPassage(1040553)
    fun samuel1(view: View) = gotoPassage(1053860)
    fun samuel2(view: View) = gotoPassage(1186112)
    fun kings1(view: View) = gotoPassage(1295108)
    fun kings2(view: View) = gotoPassage(1425198)
    fun chronicles1(view: View) = gotoPassage(1548613)
    fun chronicles2(view: View) = gotoPassage(1663184)
    fun ezra(view: View) = gotoPassage(1805227)
    fun nehemiah(view: View) = gotoPassage(1846656)
    fun estherGreek(view: View) = gotoPassage(1905298)
    fun esther(view: View) = gotoPassage(1942195)
    fun job(view: View) = gotoPassage(1973005)
    fun psalms(view: View) = gotoPassage(2071141)
    fun proverbs(view: View) = gotoPassage(2303513)
    fun ecclesiastes(view: View) = gotoPassage(2386860)
    fun songOfSolomon(view: View) = gotoPassage(2416003)
    fun isaiah(view: View) = gotoPassage(2430088)
    fun jeremiah(view: View) = gotoPassage(2628657)
    fun lamentations(view: View) = gotoPassage(2857392)
    fun ezekiel(view: View) = gotoPassage(2876049)
    fun daniel(view: View) = gotoPassage(3086118)
    fun hosea(view: View) = gotoPassage(3149327)
    fun joel(view: View) = gotoPassage(3177117)
    fun amos(view: View) = gotoPassage(3188098)
    fun obadiah(view: View) = gotoPassage(3210463)
    fun jonah(view: View) = gotoPassage(3214116)
    fun micah(view: View) = gotoPassage(3220901)
    fun nahum(view: View) = gotoPassage(3237589)
    fun habakkuk(view: View) = gotoPassage(3244661)
    fun zephaniah(view: View) = gotoPassage(3252781)
    fun haggai(view: View) = gotoPassage(3261466)
    fun zechariah(view: View) = gotoPassage(3267335)
    fun malachi(view: View) = gotoPassage(3301006)
    fun matthew(view: View) = gotoPassage(3310386)
    fun mark(view: View) = gotoPassage(3438839)
    fun luke(view: View) = gotoPassage(3520526)
    fun john(view: View) = gotoPassage(3659585)
    fun acts(view: View) = gotoPassage(3760943)
    fun romans(view: View) = gotoPassage(3894548)
    fun corinthians1(view: View) = gotoPassage(3946359)
    fun corinthians2(view: View) = gotoPassage(3996888)
    fun galatians(view: View) = gotoPassage(4029802)
    fun ephesians(view: View) = gotoPassage(4046575)
    fun philippians(view: View) = gotoPassage(4063423)
    fun colossians(view: View) = gotoPassage(4075361)
    fun thessalonians1(view: View) = gotoPassage(4086425)
    fun thessalonians2(view: View) = gotoPassage(4096399)
    fun timothy1(view: View) = gotoPassage(4102002)
    fun timothy2(view: View) = gotoPassage(4115098)
    fun titus(view: View) = gotoPassage(4124583)
    fun philemon(view: View) = gotoPassage(4129870)
    fun hebrews(view: View) = gotoPassage(4132281)
    fun james(view: View) = gotoPassage(4170584)
    fun peter1(view: View) = gotoPassage(4183108)
    fun peter2(view: View) = gotoPassage(4196946)
    fun john1(view: View) = gotoPassage(4205863)
    fun john2(view: View) = gotoPassage(4218997)
    fun john3(view: View) = gotoPassage(4220593)
    fun jude(view: View) = gotoPassage(4222233)
    fun revelation(view: View) = gotoPassage(4225847)
}
