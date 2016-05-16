package pji.spoon.start;
import pji.spoon.processors.*;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtType;
import spoon.Launcher;

import java.util.ArrayList;
import java.util.List;

import pji.spoon.methods.*;
import spoon.processing.*;
import spoon.reflect.declaration.CtElement;
/**
 * This class contains a static method to lunch our processors 
 * @author bizimungu
 *
 */
public class Starter {

	private Launcher launcher;
	private List<Method> methodList ;
	
	public Starter(Launcher launcher){
		this.launcher = launcher;
		this.methodList = new ArrayList<Method>();
	}
	
	
	
	/**
	 * Add a processor to the the Launcher of this starter
	 * @param processor
	 */
	
	public void addProcessor(AbstractProcessor <CtType<?>> processor){
		if(!this.launcher.equals(null)){
			this.launcher.addProcessor(processor);
		}
	}
	/**
	 * Add a method to the list of methods we want to detect with spoon
	 * @param meth
	 */
	public void addMethod(Method meth){
		this.methodList.add(meth);
	}
	
	public void run (String[] arguments){
		this.launcher.run(arguments);
	}
	
	public List<Method> getListMethods(){
		return this.methodList;
		
	}
	public static void main(String[] args) {
		
		System.out.println("starter\n");
		// TODO Auto-generated method stub
		
		final Launcher launcher = new Launcher();
		Starter starter = new Starter(launcher);
		final String[]  arguments2= {"-x","-i","/home/bizimungu/workspace/m1/s2/pji/ws/pji-instrumenting-androidcode/test/OpenGL/HelloOpenGLES10"};
		final String[]  arguments1= {"-x","-i","/home/bizimungu/workspace/m1/s2/pji/ws/pji-instrumenting-androidcode/test/OpenGL/HelloOpenGLES20"};
		final String[]  arguments= {"-x","-i","/home/bizimungu/workspace/m1/s2/pji/ws/pji-instrumenting-androidcode/test/keep"};
		
	    //  final CtCodeSnippetStatement statementInConstructor = getFactory().Code().createCodeSnippetStatement("this.dates = dates");
	//	OnLongClickProcessor olp =new OnLongClickProcessor();
		
		//launcher.addProcessor(olp);		

//launcher.addProcessor(new OnclickProcessor());
		
//Handy for test_______!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//launcher.addProcessor(new ViewProcessor());	

		
		//launcher.addProcessor(new MethodsInViewProcessor(methode));
//launcher.addProcessor(new EventHandlersProcessor());
	//launcher.addProcessor(new MethodsInViewProcessor(onclickmeth));
//	launcher.addProcessor(new InterfaceListenerProcessor(onclickmeth));
	
		/*----------------------------------------------------------*/
		
		//Adding Methods to detectect in the starter
		starter.addMethod(new OnClickHandler());
		starter.addMethod(new OnContextClickHandler());
		starter.addMethod(new OnDragHandler());
		starter.addMethod(new OnFocusChangeHandler());
		starter.addMethod(new OnGenericMotionHandler());
		starter.addMethod(new OnHoverHandler());
		starter.addMethod(new OnKeyHandler());
		starter.addMethod(new OnKeyUpHandler());
		starter.addMethod(new OnLayoutChangeHandler());
		starter.addMethod(new OnLongClickHandler());
		starter.addMethod(new OnScrollChangeHandler());
		starter.addMethod(new OnSystemUiVisibilityChangeHandler());
		starter.addMethod(new ViewsOnTouchHandler());
	
		
		
		//Adding a processor 
		starter.addProcessor(new ViewProcessor());
		starter.addProcessor(new OnEventMethodHandlersProcessor(starter.getListMethods()));
	
	// runing 
		starter.run(arguments);
	
		System.out.println("starter done\n");

	}



}

