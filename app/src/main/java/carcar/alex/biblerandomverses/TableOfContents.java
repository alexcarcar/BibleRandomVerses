package carcar.alex.biblerandomverses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TableOfContents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);
    }

    public void closeWindow(View view) {
        this.finish();
    }

    private void gotoPassage(long index) {
        MainActivity.contentsBookmark = index;
        this.finish();
    }

    public void oldTestment(View view) {
        gotoPassage(0);
    }

    public void newTestment(View view) {
        gotoPassage(3310386);
    }

    public void genesis(View view) {
        gotoPassage(0);
    }

    public void exodus(View view) {
        gotoPassage(202530);
    }

    public void leviticus(View view) {
        gotoPassage(376409);
    }

    public void numbers(View view) {
        gotoPassage(506463);
    }

    public void deuteronomy(View view) {
        gotoPassage(686935);
    }

    public void joshua(View view) {
        gotoPassage(836909);
    }

    public void judges(View view) {
        gotoPassage(939563);
    }

    public void ruth(View view) {
        gotoPassage(1040553);
    }

    public void samuel1(View view) {
        gotoPassage(1053860);
    }

    public void samuel2(View view) {
        gotoPassage(1186112);
    }

    public void kings1(View view) {
        gotoPassage(1295108);
    }

    public void kings2(View view) {
        gotoPassage(1425198);
    }

    public void chronicles1(View view) {
        gotoPassage(1548613);
    }

    public void chronicles2(View view) {
        gotoPassage(1663184);
    }

    public void ezra(View view) {
        gotoPassage(1805227);
    }

    public void nehemiah(View view) {
        gotoPassage(1846656);
    }

    public void estherGreek(View view) {
        gotoPassage(1905298);
    }

    public void esther(View view) {
        gotoPassage(1942195);
    }

    public void job(View view) {
        gotoPassage(1973005);
    }

    public void psalms(View view) {
        gotoPassage(2071141);
    }

    public void proverbs(View view) {
        gotoPassage(2303513);
    }

    public void ecclesiastes(View view) {
        gotoPassage(2386860);
    }

    public void songOfSolomon(View view) {
        gotoPassage(2416003);
    }

    public void isaiah(View view) {
        gotoPassage(2430088);
    }

    public void jeremiah(View view) {
        gotoPassage(2628657);
    }

    public void lamentations(View view) {
        gotoPassage(2857392);
    }

    public void ezekiel(View view) {
        gotoPassage(2876049);
    }

    public void daniel(View view) {
        gotoPassage(3086118);
    }

    public void hosea(View view) {
        gotoPassage(3149327);
    }

    public void joel(View view) {
        gotoPassage(3177117);
    }

    public void amos(View view) {
        gotoPassage(3188098);
    }

    public void obadiah(View view) {
        gotoPassage(3210463);
    }

    public void jonah(View view) {
        gotoPassage(3214116);
    }

    public void micah(View view) {
        gotoPassage(3220901);
    }

    public void nahum(View view) {
        gotoPassage(3237589);
    }

    public void habakkuk(View view) {
        gotoPassage(3244661);
    }

    public void zephaniah(View view) {
        gotoPassage(3252781);
    }

    public void haggai(View view) {
        gotoPassage(3261466);
    }

    public void zechariah(View view) {
        gotoPassage(3267335);
    }

    public void malachi(View view) {
        gotoPassage(3301006);
    }

    public void matthew(View view) {
        gotoPassage(3310386);
    }

    public void mark(View view) {
        gotoPassage(3438839);
    }

    public void luke(View view) {
        gotoPassage(3520526);
    }

    public void john(View view) {
        gotoPassage(3659585);
    }

    public void acts(View view) {
        gotoPassage(3760943);
    }

    public void romans(View view) {
        gotoPassage(3894548);
    }

    public void corinthians1(View view) {
        gotoPassage(3946359);
    }

    public void corinthians2(View view) {
        gotoPassage(3996888);
    }

    public void galatians(View view) {
        gotoPassage(4029802);
    }

    public void ephesians(View view) {
        gotoPassage(4046575);
    }

    public void philippians(View view) {
        gotoPassage(4063423);
    }

    public void colossians(View view) {
        gotoPassage(4075361);
    }

    public void thessalonians1(View view) {
        gotoPassage(4086425);
    }

    public void thessalonians2(View view) {
        gotoPassage(4096399);
    }

    public void timothy1(View view) {
        gotoPassage(4102002);
    }

    public void timothy2(View view) {
        gotoPassage(4115098);
    }

    public void titus(View view) {
        gotoPassage(4124583);
    }

    public void philemon(View view) {
        gotoPassage(4129870);
    }

    public void hebrews(View view) {
        gotoPassage(4132281);
    }

    public void james(View view) {
        gotoPassage(4170584);
    }

    public void peter1(View view) {
        gotoPassage(4183108);
    }

    public void peter2(View view) {
        gotoPassage(4196946);
    }

    public void john1(View view) {
        gotoPassage(4205863);
    }

    public void john2(View view) {
        gotoPassage(4218997);
    }

    public void john3(View view) {
        gotoPassage(4220593);
    }

    public void jude(View view) {
        gotoPassage(4222233);
    }

    public void revelation(View view) {
        gotoPassage(4225847);
    }
}
