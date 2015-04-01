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
		if (mi.getFullName().equals("android.telephony.SmsManager.sendTextMessage(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/app/PendingIntent;Landroid/app/PendingIntent;)V"))
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
				System.out.println("sensitive data is leaked");
				search.terminate();
			}
		}
	}

	@Override
	public void methodExited (VM vm, ThreadInfo ti, MethodInfo mi)
	{
		//System.out.println("EXT: " + mi.getFullName());
		if (mi.getUniqueName().equals("getDeviceId()Ljava/lang/String;")) {
			System.out.println("getDeviceId() called!");
			Heap heap = vm.getHeap();
			StackFrame frame = ti.getTopFrame();
			frame.printSlots(System.out);
			int xobjref = frame.peek();
			ElementInfo ei = heap.get(xobjref);
			ClassInfo ci = ei.getClassInfo();
			System.out.println(ci.getName());
			ei.setObjectAttr(new String("tainted"));
		} 
		
	}

	@Override
	public void executeInstruction (VM vm, ThreadInfo ti, Instruction ins)
	{
	//	System.out.println("INT: " + ins.toString());
	}
}
