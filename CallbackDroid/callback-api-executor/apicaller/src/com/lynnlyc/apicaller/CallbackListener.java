
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
//import gov.nasa.jpf.jvm.bytecode.ReturnInstruction;
import gov.nasa.jpf.vm.DynamicElementInfo;
import gov.nasa.jpf.vm.FieldInfo;
import gov.nasa.jpf.vm.*;

import java.util.HashSet;


public class CallbackListener extends ListenerAdapter{
    Search search;
    HashSet<String> triggers;
    HashSet<String> callbacks;
    public CallbackListener (Config config, JPF jpf)
    {
        search = jpf.getSearch();
        triggers = new HashSet<String>();
        triggers.add("android.os.AsyncTask.execute([Ljava/lang/Object;)Landroid/os/AsyncTask;");
        callbacks = new HashSet<String>();
        callbacks.add("com.lynnlyc.apicaller.AppAsyncTask.onPreExecute()V");
        callbacks.add("com.lynnlyc.apicaller.AppAsyncTask.doInBackground([Ljava/lang/Object;)Ljava/lang/Object;");
        callbacks.add("com.lynnlyc.apicaller.AppAsyncTask.onPostExecute(Ljava/lang/Object;)V");
    }

    @Override
    public void methodEntered (VM vm, ThreadInfo ti, MethodInfo mi)
    {
        String funName = mi.getFullName();
        if (triggers.contains(funName)) {
            System.out.println("trigger entered: " + funName);
            LocalVarInfo[] localvars = mi.getArgumentLocalVars();

            int arg_idx = 0;
            for(LocalVarInfo localvar : localvars){
                //System.out.println(localvar);
                int objref = ti.getTopFrame().getLocalVariable(localvar.getSlotIndex());
                ElementInfo ei = vm.getHeap().get(objref);
                //System.out.println(ei);
                String vartag = funName + "-arg" + arg_idx;
                System.out.println("arg" + arg_idx + ":" + ei);
                //System.out.println("\ttag:" + ei.getObjectAttr());
                if(ei.isFrozen()) {
                    ei.defreeze();
                }
                ei.setObjectAttr(vartag);
                arg_idx++;
            }
        }

        if (callbacks.contains(funName)) {
            System.out.println("callback entered: " + funName);
            LocalVarInfo[] localvars = mi.getArgumentLocalVars();

            int arg_idx = 0;
            for(LocalVarInfo localvar : localvars){
                int objref = ti.getTopFrame().getLocalVariable(localvar.getSlotIndex());
                ElementInfo ei = vm.getHeap().get(objref);
                //System.out.println(ei);
                String vartag = funName + "-arg" + arg_idx;
                System.out.println("arg" + arg_idx + ":" + ei);
                System.out.println("\ttag:" + ei.getObjectAttr());
                if(ei.isFrozen()) {
                    ei.defreeze();
                }
                ei.setObjectAttr(vartag);
                arg_idx++;
            }
        }
        //System.out.println(funName);
    }

    @Override
    public void methodExited (VM vm, ThreadInfo ti, MethodInfo mi)
    {
        String funName = mi.getFullName();
        if (triggers.contains(funName)) {
            System.out.println("trigger exited: " + funName);

            System.out.println("ret: " + mi.getReturnType());
            if (mi.getReturnType().startsWith("L")) {
                int xobjref = ti.getTopFrame().peek();
                ElementInfo ei = vm.getHeap().get(xobjref);
                System.out.println("\ttag:" + ei.getObjectAttr());
            }
        }

        if (callbacks.contains(funName)) {
            System.out.println("callback exited: " + funName);

            System.out.println("ret: " + mi.getReturnType());
            if (mi.getReturnType().startsWith("L")) {
                int xobjref = ti.getTopFrame().peek();
                ElementInfo ei = vm.getHeap().get(xobjref);
                String vartag = funName + "-ret";
                if(ei.isFrozen()) {
                    ei.defreeze();
                }
                ei.setObjectAttr(vartag);
            }
        }
    }

/*  @Override
    public void executeInstruction (VM vm, ThreadInfo ti, Instruction ins)
    {
        System.out.println("INT: " + ins.toString());
    }
*/
}
