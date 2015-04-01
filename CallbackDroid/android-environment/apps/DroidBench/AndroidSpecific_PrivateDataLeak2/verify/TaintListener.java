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
import gov.nasa.jpf.vm.ElementInfo;
import gov.nasa.jpf.vm.Heap;
import gov.nasa.jpf.search.Search;

public class TaintListener extends ListenerAdapter{
	Search search;
	public TaintListener (Config config, JPF jpf)
	{
		search = jpf.getSearch();
	}

	@Override
	public void methodEntered (VM vm, ThreadInfo ti, MethodInfo mi)
	{
//		System.out.println("ENT: " + mi.getFullName());
//	if (mi.getFullName().equals("Driver.checkTaint(Ljava/lang/String;)V")) 
		String funName = mi.getFullName();
		if (funName.equals("android.telephony.SmsManager.sendTextMessage(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/app/PendingIntent;Landroid/app/PendingIntent;)V"))
		{
			System.out.println("sendSms() called!");
			int xobjref = ti.getTopFrame().getLocalVariable("text");
			//System.out.println(xobjref);
			ElementInfo ei = vm.getHeap().get(xobjref);
			//System.out.println(ei.asString());
//			System.out.println(ei.getObjectAttr().toString());
//			if (ei.getObjectAttr().equals("tainted")) {
			if ("tainted".equals(ei.getObjectAttr())) {
				//Instruction nextPc = ti.createAndThrowException("java.lang.RuntimeException");
				//ti.getModifiableTopFrame().setPC(nextPc);
				//vm.breakTransition();
				System.out.println("data leaked by sending text message!");
				search.terminate();
			}
		} else if (funName.contains("Log.d") || funName.contains("Log.e") || funName.contains("Log.i") ||
				funName.contains("Log.v") || funName.contains("Log.w")){
			System.out.println("Log() called!");
			int tagxobjref = ti.getTopFrame().getLocalVariable("tag");
			int msgxobjref = ti.getTopFrame().getLocalVariable("msg");
			ElementInfo tagEi = vm.getHeap().get(tagxobjref);
			ElementInfo msgEi = vm.getHeap().get(msgxobjref);
			if("tainted".equals(tagEi.getObjectAttr()) || "tainted".equals(msgEi.getObjectAttr())){
				System.out.println("data leaked by logging!");
				search.terminate();
			}
		}
	}

	@Override
	public void methodExited (VM vm, ThreadInfo ti, MethodInfo mi)
	{
		//System.out.println("EXT: " + mi.getFullName());
		String funName = mi.getUniqueName();
		if (funName.equals("getDeviceId()Ljava/lang/String;")) {
			System.out.println("getDeviceId() called!");
			Heap heap = vm.getHeap();
			StackFrame frame = ti.getTopFrame();
			int xobjref = frame.peek();
			ElementInfo ei = heap.get(xobjref);
			ei.setObjectAttr(new String("tainted"));
		}else if (funName.equals("getText()Landroid/text/Editable;")) {
			System.out.println("getText() called!");
			Heap heap = vm.getHeap();
			StackFrame frame = ti.getTopFrame();
			int xobjref = frame.peek();
			ElementInfo ei = heap.get(xobjref);
			ei.setObjectAttr(new String("tainted"));
		}else if (funName.equals("toString()Ljava/lang/String;") && mi.getClassName().equals("android.text.SpannableStringBuilder")) {
			System.out.println("EditText.toString()");
			Heap heap = vm.getHeap();
			StackFrame frame = ti.getTopFrame();
			int xobjref = frame.peek();
			ElementInfo ei = heap.get(xobjref);
			ei.setObjectAttr(new String("tainted"));
		}
	}

/*	@Override
	public void executeInstruction (VM vm, ThreadInfo ti, Instruction ins)
	{
		System.out.println("INT: " + ins.toString());
	}*/

}
