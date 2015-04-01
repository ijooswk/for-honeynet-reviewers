package mymock;
import android.widget.Toast;
import java.lang.CharSequence;

public class MockToast extends Toast{
	private CharSequence cs;

	public MockToast(CharSequence text){
		super(null, text);
		cs = text;
	}

	public void show(){
		System.out.println(cs);
	}
}
