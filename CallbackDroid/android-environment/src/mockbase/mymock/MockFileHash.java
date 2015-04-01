package mymock;
import java.util.HashMap;

public class MockFileHash {
	private static HashMap<String, byte[]> fileHash = null;
	
	private MockFileHash() {
	}

	public static HashMap<String, byte[]> getMockFileHash() {
		if(fileHash == null)
			fileHash = new HashMap<String, byte[]>();
		return fileHash;
	}

	public void put(String key, byte[] value) {
		fileHash.put(key, value);
	}

	public byte[] get(String key) {
		return fileHash.get(key);
	}
}
