package mymock.mockView;

import java.io.File;  
import java.io.InputStream;  
import java.io.FileInputStream; 

import android.view.View;

import org.xmlpull.v1.XmlPullParser;  
import org.xmlpull.v1.XmlPullParserException;  
import org.xmlpull.v1.XmlPullParserFactory;  

import java.util.Hashtable;
import java.util.Iterator;

public class ApfViewGenerator
{
	public String actPath;
	public String layoutPath = null;
	Hashtable idNameTable = null;
	Hashtable<String, View> idViewTable = null;

	public ApfViewGenerator(String path)
	{
		actPath = path;
		idNameTable = new Hashtable();
		idViewTable = new Hashtable<String, View>();
	}
	
	public void parseMainLayout(int layoutID)
	{
		try
		{
		String input = "0x" + Integer.toHexString(layoutID);
		//System.out.println("input: " + input);

		File idFile = new File(actPath + "res/values/public.xml");
        InputStream in = new FileInputStream(idFile); 

		XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser parser =factory.newPullParser();
        parser.setInput(in, "UTF-8");

        int eventType = parser.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT)
		{
            switch (eventType) 
			{
            case XmlPullParser.START_DOCUMENT:
				//System.out.println("XmlPullParser.startDocument");
                break;
            case XmlPullParser.START_TAG:
				//System.out.println("XmlPullParser.startTag: " + parser.getName());
		 		int size = parser.getAttributeCount();
				String type = null;
				String name = null;
                String id = null;
         		
				if(size > 0)
                {
                    type = parser.getAttributeValue(0);
                    name = parser.getAttributeValue(1);
                    id = parser.getAttributeValue(2);
                    if(input.equals(id))
					{
						layoutPath = actPath + "res/" + type + "/" + name + ".xml";
					}
                    if("id".equals(type))
                    {
						//System.out.println(new String("@+id/" + name) + ": " + id);
                        idNameTable.put(new String("@+id/" + name), id);
                    }
                }

        
			         //System.out.println(i + " " + parser.getAttributeName(i));
			         //System.out.println(i + " " + parser.getAttributeValue(i));
         		
	//			System.out.println(" " + parser.getAttributeValue(2));
                break;
			case XmlPullParser.TEXT: 
				//System.out.println("XmlPullParser.text: " + parser.getText());
				break;
            case XmlPullParser.END_TAG:
				//System.out.println("XmlPullParser.endTag: " + parser.getName());
                break;
            }
            eventType=parser.next();
        }	
        in.close();
		}catch (Exception e) {  
            e.printStackTrace();  
        }  
 //       return list;
	
	}

	public void onSetContentView(int layoutID)
	{
		parseMainLayout(layoutID);
		parseView();
		generateCode();
      
	//	System.out.println(layoutPath);
	/*	System.out.println(idNameTable.toString());
		MockEditText myet = new MockEditText(null);
		myet.toString();
		MockButton mybt = new MockButton(null);
		mybt.toString();*/
		/*for(Iterator it = idViewTable.keySet().iterator(); it.hasNext(); )
		{
			String key = (String)it.next();
			Object value = idViewTable.get(key);
			System.out.println(value.getClass().getName());
		} */
	}
	
	public void parseView()
	{
		if(layoutPath == null)
			return;
		try
		{
			File layoutFile = new File(layoutPath);
        	InputStream in = new FileInputStream(layoutFile); 

			XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser parser =factory.newPullParser();
        	parser.setInput(in, "UTF-8");
			
        	int eventType = parser.getEventType();
	        while(eventType != XmlPullParser.END_DOCUMENT)
			{
        	    switch (eventType) 
				{
           		case XmlPullParser.START_DOCUMENT:
					//System.out.println("XmlPullParser.startDocument");
                	break;
	            case XmlPullParser.START_TAG:
					//System.out.println("XmlPullParser.startTag: " + parser.getName());
					String viewName = parser.getName();
					//System.out.println("viewName: " + viewName);
					if("EditText".equals(viewName))
					{
						/* find id */
						String viewId = parser.getAttributeValue("http://schemas.android.com/apk/res/android", "id");
						// Le edit
						if(viewId == null)
							break;							
						if (viewId.charAt(1) != '+'){
							viewId = viewId.substring(0, 1) + "+" + viewId.substring(1, viewId.length());
						}
						// end Le edit
						//System.out.println(viewId);
						//System.out.println(parser.getAttributeName(0));
						//System.out.println(parser.getAttributeNamespace(0));
						String viewDigital = (String)idNameTable.get(viewId);
						//System.out.println(viewDigital);
						MockEditText myet = new MockEditText(viewDigital, null);
						idViewTable.put(viewDigital, myet);

					}
					else if("Button".equals(viewName))
					{
						/* find id & onclick*/
						String viewId = parser.getAttributeValue("http://schemas.android.com/apk/res/android", "id");
						// Le edit
						if(viewId == null)
							break;							
						if (viewId.charAt(1) != '+'){
							viewId = viewId.substring(0, 1) + "+" + viewId.substring(1, viewId.length());
						}
						// end Le edit
						String viewDigital = (String)idNameTable.get(viewId);
						String onClickName = parser.getAttributeValue("http://schemas.android.com/apk/res/android", "onClick");

						MockButton mybt = new MockButton(viewDigital, null);
						/*mybt.setOnClickListener(new View.OnClickListener() {
		   					@Override
						    public void onClick(View v) {
		    	
		    				}
						});*/
						mybt.onClickName = onClickName;
						idViewTable.put(viewDigital, mybt);
					}
					else if("CheckBox".equals(viewName))
					{
						/* find id */
						String viewId = parser.getAttributeValue("http://schemas.android.com/apk/res/android", "id");
						// Le edit
						if(viewId == null)
							break;							
						if (viewId.charAt(1) != '+'){
							viewId = viewId.substring(0, 1) + "+" + viewId.substring(1, viewId.length());
						}
						// end Le edit
						String viewDigital = (String)idNameTable.get(viewId);
						//System.out.println(viewDigital);
						MockCheckBox mycb = new MockCheckBox(viewDigital, null);
						idViewTable.put(viewDigital, mycb);
					}
					else if("TextView".equals(viewName))
					{
						/* find id */
						String viewId = parser.getAttributeValue("http://schemas.android.com/apk/res/android", "id");
						// Le edit
						if(viewId == null)
							break;							
						if (viewId.charAt(1) != '+'){
							viewId = viewId.substring(0, 1) + "+" + viewId.substring(1, viewId.length());
						}
						// end Le edit
						String viewDigital = (String)idNameTable.get(viewId);
						//System.out.println(viewDigital);
						MockTextView mytv = new MockTextView(viewDigital, null);
						idViewTable.put(viewDigital, mytv);
					}
					else if("ListView".equals(viewName))
					{
						/* find id */
						String viewId = parser.getAttributeValue("http://schemas.android.com/apk/res/android", "id");
						// Le edit
						if(viewId == null)
							break;							
						if (viewId.charAt(1) != '+'){
							viewId = viewId.substring(0, 1) + "+" + viewId.substring(1, viewId.length());
						}
						// end Le edit
						String viewDigital = (String)idNameTable.get(viewId);
						//System.out.println(viewDigital);
						MockListView mylv = new MockListView(viewDigital, null);
						idViewTable.put(viewDigital, mylv);
					}
					else if("android.opengl.GLSurfaceView".equals(viewName))
					{
						String viewId = parser.getAttributeValue("http://schemas.android.com/apk/res/android", "id");
						// Le edit
						if(viewId == null)
							break;							
						if (viewId.charAt(1) != '+'){
							viewId = viewId.substring(0, 1) + "+" + viewId.substring(1, viewId.length());
						}
						// end Le edit
						String viewDigital = (String)idNameTable.get(viewId);
						MockGLSurfaceView mkgl = new MockGLSurfaceView(viewDigital, null);
						idViewTable.put(viewDigital, mkgl);
					}
					else if("ImageView".equals(viewName))
					{
						String viewId = parser.getAttributeValue("http://schemas.android.com/apk/res/android", "id");
						// Le edit
						if(viewId == null)
							break;							
						if (viewId.charAt(1) != '+'){
							viewId = viewId.substring(0, 1) + "+" + viewId.substring(1, viewId.length());
						}
						// end Le edit
						String viewDigital = (String)idNameTable.get(viewId);
						MockImageView mkiv = new MockImageView(viewDigital, null);
						idViewTable.put(viewDigital, mkiv);
					}
					else if("LinearLayout".equals(viewName))
					{
						String viewId = parser.getAttributeValue("http://schemas.android.com/apk/res/android", "id");
						// Le edit
						if(viewId == null)
							break;							
						if (viewId.charAt(1) != '+'){
							viewId = viewId.substring(0, 1) + "+" + viewId.substring(1, viewId.length());
						}
						// end Le edit
					//	System.out.println("viewid: " + viewId);
						String viewDigital = (String)idNameTable.get(viewId);
						MockLinearLayout mkll = new MockLinearLayout(viewDigital, null);
						idViewTable.put(viewDigital, mkll);
					}
					else
					{
					}
			         //System.out.println(i + " " + parser.getAttributeName(i));
			         //System.out.println(i + " " + parser.getAttributeValue(i));
         		
	//			System.out.println(" " + parser.getAttributeValue(2));
                break;
			case XmlPullParser.TEXT: 
				//System.out.println("XmlPullParser.text: " + parser.getText());
				break;
            case XmlPullParser.END_TAG:
				//System.out.println("XmlPullParser.endTag: " + parser.getName());
                break;
            }
            eventType=parser.next();
        }	
        in.close();
		}catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
	
	public void generateCode()
	{
		//generate hash table definition 
		System.out.println("Hashtable<String, View> id2view = new Hashtable<String, View>();");
				
		int index = 0;
		for(Iterator it = idViewTable.keySet().iterator(); it.hasNext(); )
		{
			String key = (String)it.next();
			Object value = idViewTable.get(key);
			if(value.getClass().getName().equals("mymock.mockView.MockEditText"))
			{
				System.out.println("MockEditText myet" + index + " = new MockEditText(\"" + ((MockEditText)value).id + "\", null);");
				System.out.println("id2view.put(myet" + index + ".id, myet" + index + ");");
			}
			else if(value.getClass().getName().equals("mymock.mockView.MockButton"))
			{
				System.out.println("MockButton mybt" + index + " = new MockButton(\"" + ((MockButton)value).id + "\", null);");
				/*generate the onclick listener of button*/
				if(((MockButton)value).onClickName != null)
				{
					System.out.println("mybt" + index + ".setOnClickListener(new View.OnClickListener() {");
					System.out.println("	@Override");
					System.out.println("    public void onClick(View v) {");
					System.out.println("act." + ((MockButton)value).onClickName + "(v);");
					System.out.println("}");
					System.out.println("});");
				}
				
				System.out.println("id2view.put(mybt" + index + ".id, mybt" + index + ");");
			}
			else if(value.getClass().getName().equals("mymock.mockView.MockCheckBox"))
			{
				System.out.println("MockCheckBox mycb" + index + " = new MockCheckBox(\"" + ((MockCheckBox)value).id + "\", null);");
				System.out.println("id2view.put(mycb" + index + ".id, mycb" + index + ");");
			}
			else if(value.getClass().getName().equals("mymock.mockView.MockTextView"))
			{
				System.out.println("MockTextView mytv" + index + " = new MockTextView(\"" + ((MockTextView)value).id + "\", null);");
				System.out.println("id2view.put(mytv" + index + ".id, mytv" + index + ");");
			}
			else if(value.getClass().getName().equals("mymock.mockView.MockListView"))
			{
				System.out.println("MockListView mylv" + index + " = new MockListView(\"" + ((MockListView)value).id + "\", null);");
				System.out.println("id2view.put(mylv" + index + ".id, mylv" + index + ");");
			}
			else if(value.getClass().getName().equals("mymock.mockView.MockGLSurfaceView"))
			{
				System.out.println("MockGLSurfaceView mysv" + index + " = new MockGLSurfaceView(\"" + ((MockGLSurfaceView)value).id + "\", null);");
				System.out.println("id2view.put(mysv" + index + ".id, mysv" + index + ");");
			}
			else if(value.getClass().getName().equals("mymock.mockView.MockImageView"))
			{
				System.out.println("MockImageView myiv" + index + " = new MockImageView(\"" + ((MockImageView)value).id + "\", null);");
				System.out.println("id2view.put(myiv" + index + ".id, myiv" + index + ");");
			}
			else if(value.getClass().getName().equals("mymock.mockView.MockLinearLayout"))
			{
				System.out.println("MockLinearLayout myll" + index + " = new MockLinearLayout(\"" + ((MockLinearLayout)value).id + "\", null);");
				System.out.println("id2view.put(myll" + index + ".id, myll" + index + ");");
			}
			else
			{
			}
			index++;
		} 
	}
}
