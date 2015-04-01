package mymock;
import java.io.FileInputStream;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.lang.RuntimeException;

public class MockFileInputStream extends FileInputStream implements Closeable{
	private String fileName;
	private HashMap<String, byte[]> fileHash = MockFileHash.getMockFileHash();

	public MockFileInputStream(String name) throws FileNotFoundException {
		super(name);
		fileName = name;
	}

	public int read(byte[] buffer){
		byte[] bt = fileHash.get(fileName);
		if(bt == null) {
			return -1;
		}
		fooReadMark(buffer, bt);
		for(int i = 0; i < bt.length; i++)
			buffer[i] = bt[i];
		return 1;
	}

	private void fooReadMark(byte[] buf1, byte[] buf2){
	}

	public void close(){
	}
}
