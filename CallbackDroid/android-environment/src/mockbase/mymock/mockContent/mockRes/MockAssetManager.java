package mymock.mockContent.mockRes;
import android.content.res.AssetManager;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

public class MockAssetManager extends AssetManager{
	private String path = "../../../../apps/malware/spitmo/asset/";

	public MockAssetManager(String dir)
	{
		path = dir;
	}

	public InputStream open(String fileName) throws IOException{
        //@yqq
        //debug
        //System.out.println(fileName == null ? "fileName null" : "fileName not null");
        //#yqq
		return new FileInputStream(path + fileName);
	}

}
