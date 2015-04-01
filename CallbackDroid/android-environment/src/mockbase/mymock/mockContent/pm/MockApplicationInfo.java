package mymock.mockContent.pm;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;

public class MockApplicationInfo extends ApplicationInfo {

	public MockApplicationInfo(Bundle bd) {
		metaData = bd;
	}

}
