package mymock;
import java.io.FileOutputStream;
import java.io.Closeable;
import java.util.HashMap;
import java.io.FileNotFoundException;

public class MockFileOutputStream extends FileOutputStream implements Closeable{
	private String fileName;
	private HashMap<String, byte[]> fileHash = MockFileHash.getMockFileHash();

	public MockFileOutputStream(String fileName) throws FileNotFoundException{
		super(fileName);
		this.fileName = fileName;
	}

	public void write(byte[] buffer){
		fileHash.put(fileName, buffer);
	}

	public void close(){
	}
}
