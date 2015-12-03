package carcar.alex.biblerandomverses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static TextView txtPassage;
    public static final long FILE_SIZE = 4289338;
	public static final long SIZE = 2000;
	public static final long LINES = 7;

	public static String readLine(InputStream source) {
		String line = "";
		char c;
		try {
			do {
				int i = source.read();
				if (i==-1) break; 
				c = (char) i;
				if (c!='\n') line += c;
			} while (c!='\n');
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}
	
    public String getPassage() {
		String passage = "";
		try {
			InputStream source = this.getResources().openRawResource(R.raw.all);
			long n = FILE_SIZE-SIZE;
			long pickStart = (long) Math.floor(Math.random()*n);
			if(source.skip(pickStart)<0) return "";
			readLine(source);
			for (int i=0; i<LINES; i++) {
				passage += readLine(source);
				passage += "\n\n";
			}
			source.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return passage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtPassage=(TextView)findViewById(R.id.text_box);
        displayPassage();
    }

    public void displayPassage() {
        txtPassage.setText(getPassage());
    }

    public void pickPassage(View view) {
        displayPassage();
    }
}
