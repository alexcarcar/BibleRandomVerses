package carcar.alex.biblerandomverses;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

public class MainActivity extends AppCompatActivity {

    private static TextView txtPassage;
    public static final long SIZE = 2000;
    public static final long LINES = 4;
    public static String getPassage() {
        String passage = "";
        try {
            RandomAccessFile raf = new RandomAccessFile("all.txt","r");

            long n = raf.length()-SIZE;
            long pickStart = (long) Math.floor(Math.random()*n);
            raf.seek(pickStart);
            raf.readLine();
            for (int i=0; i<LINES; i++) {
                passage += raf.readLine();
                passage += "\n";
            }
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return passage;

    }

    public void loadBibleFile() {
        try {
            InputStream in = this.getResources().openRawResource(R.raw.all);
            this.getPackageResourcePath();
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput("all.txt", 0));
            int c;
            while ((c=in.read()) != -1) {
                out.write(c);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtPassage=(TextView)findViewById(R.id.text_box);
        //loadBibleFile();
        displayPassage();
    }

    public void displayPassage() {
        txtPassage.setText(this.getPackageResourcePath());
        //txtPassage.setText(getPassage());
    }

    public void pickPassage(View view) {
        displayPassage();
    }
}
