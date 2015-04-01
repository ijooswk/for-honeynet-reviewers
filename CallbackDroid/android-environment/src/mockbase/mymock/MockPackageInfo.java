package mymock;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;

public class MockPackageInfo extends PackageInfo{

	public void setSignature(Signature[] sig){
		this.signatures = sig;
	}

}
