package mymock.mockView;

import android.opengl.GLSurfaceView;
import android.content.Context;

public class MockGLSurfaceView extends GLSurfaceView{

	public String id;
	public MockGLSurfaceView(String id, Context c){
		super(c);
		this.id = id;
	}

	public void setRenderer(GLSurfaceView.Renderer r){
	}
}
