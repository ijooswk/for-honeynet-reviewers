import gov.nasa.jpf.JPFListener;
import gov.nasa.jpf.JPF;
import gov.nasa.jpf.Config;
import gov.nasa.jpf.ListenerAdapter;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.ThreadInfo;
import gov.nasa.jpf.vm.VM;
import gov.nasa.jpf.vm.ClassInfo;
import gov.nasa.jpf.vm.MethodInfo;
import gov.nasa.jpf.vm.StackFrame;
import gov.nasa.jpf.vm.NativeStackFrame;
import gov.nasa.jpf.vm.ElementInfo;
import gov.nasa.jpf.vm.Heap;
import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.vm.LocalVarInfo;

public class TaintListener extends ListenerAdapter{
	Search search;
	public TaintListener (Config config, JPF jpf)
	{
		search = jpf.getSearch();
	}

	@Override
	public void methodEntered (VM vm, ThreadInfo ti, MethodInfo mi)
	{
		String funName = mi.getFullName();
		Heap heap = vm.getHeap();
		StackFrame frame = ti.getTopFrame();
		//System.out.println(funName);
		if (funName.equals("android.telephony.SmsManager.sendTextMessage(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/app/PendingIntent;Landroid/app/PendingIntent;)V"))
		{
			int xobjref = frame.getLocalVariable("text");
			ElementInfo ei = heap.get(xobjref);
			if (ei.getObjectAttr() != null && ei.getObjectAttr().equals("tainted")) {
				System.out.println("sendSms() tainted!");
				search.terminate();
			}
		} else if (funName.contains("Log.d") || funName.contains("Log.e") || funName.contains("Log.i") ||
				funName.contains("Log.v") || funName.contains("Log.w")){
			int tagxobjref = frame.getLocalVariable("tag");
			int msgxobjref = frame.getLocalVariable("msg");
			ElementInfo tagEi = heap.get(tagxobjref);
			ElementInfo msgEi = heap.get(msgxobjref);
			if(tagEi.getObjectAttr() != null && tagEi.getObjectAttr().equals("tainted")){
				System.out.println("Log() tainted from tag!");
				search.terminate();
			}
			if(msgEi.getObjectAttr() != null && msgEi.getObjectAttr().equals("tainted")){
				System.out.println("Log() tainted from message!");
				search.terminate();
			}
		} else if(funName.equals("mymock.MockFileOutputStream.write([B)V")){
			int objref = frame.getLocalVariable("buffer");
			ElementInfo ei = heap.get(objref);
			if(ei.getObjectAttr() != null && ei.getObjectAttr().equals("tainted")){
				System.out.println("write() tainted!");
			}
		} 
	}

	@Override
	public void methodExited (VM vm, ThreadInfo ti, MethodInfo mi)
	{
		Heap heap = vm.getHeap();
		StackFrame frame = ti.getTopFrame();
		String funName = mi.getUniqueName();
		StackFrame callerframe = ti.getCallerStackFrame();
		//System.out.println(funName);
		if (funName.equals("getDeviceId()Ljava/lang/String;") && mi.getClassName().equals("mymock.MockTelephonyManager")) {
			System.out.println("getDeviceId() called!");
			int xobjref = frame.peek();
			ElementInfo ei = heap.get(xobjref);
			ei.setObjectAttr(new String("tainted"));
		}else if (funName.equals("getText()Landroid/text/Editable;")) {
			System.out.println("getText() called!");
			int xobjref = frame.peek();
			ElementInfo ei = heap.get(xobjref);
			ei.setObjectAttr(new String("tainted"));
		}else if (funName.equals("toString()Ljava/lang/String;") && mi.getClassName().equals("android.text.SpannableStringBuilder")) {
			System.out.println("EditText.toString()");
			int xobjref = frame.peek();
			ElementInfo ei = heap.get(xobjref);
			ei.setObjectAttr(new String("tainted"));
		}else if(funName.equals("getBytes()[B") && mi.getClassName().equals("java.lang.String")) {
			System.out.println("String.getbytes() called!");
			int xobjref = callerframe.peek();
			ElementInfo ei = heap.get(xobjref);
			if(ei.getObjectAttr().equals("tainted")){
				NativeStackFrame fr = (NativeStackFrame)frame;
				int r = (int) fr.getReturnValue();
				heap.get(r).setObjectAttr(new String("tainted"));
			}
		}else if(funName.equals("fooReadMark([B[B)V") && mi.getClassName().equals("mymock.MockFileInputStream")){
			int buf1ref = frame.getLocalVariable("buf1");
			int buf2ref = frame.getLocalVariable("buf2");
			ElementInfo ebuf1 = heap.get(buf1ref);
			ElementInfo ebuf2 = heap.get(buf2ref);
			if(ebuf2.getObjectAttr().equals("tainted")){
				ebuf1.setObjectAttr(new String("tainted"));
				System.out.println("read() tainted!");
			}
		}else if(funName.equals("trim()Ljava/lang/String;") && mi.getClassName().equals("java.lang.String")){
			int calref = callerframe.peek();
			ElementInfo ei = heap.get(calref);
			if(ei.getObjectAttr() != null && ei.getObjectAttr().equals("tainted")){
				NativeStackFrame ntfr = (NativeStackFrame)frame;
				int xobjref = (int)ntfr.getReturnValue();
				ElementInfo retei = heap.get(xobjref);
				retei.setObjectAttr(new String("tainted"));
				System.out.println("trim tainted!");
			}
		}else if(funName.equals("<init>([BII)V") && mi.getClassName().equals("java.lang.String")){
			ElementInfo thisEi = heap.get(frame.getLocalVariable("this"));
			ElementInfo btEi = heap.get(frame.getLocalVariable("bytes"));
			if(btEi.getObjectAttr() != null && btEi.getObjectAttr().equals("tainted")){
				thisEi.setObjectAttr(new String("tainted"));
				System.out.println("str init tainted!");
			}
		}
	}

/*	@Override
	public void executeInstruction (VM vm, ThreadInfo ti, Instruction ins)
	{
		System.out.println("INT: " + ins.toString());
	}*/

}
