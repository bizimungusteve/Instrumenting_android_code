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
	
	/*
	private List<Method> methList = new ArrayList<Method>() ;
	private AbstractProcessor<CtType<?>> processor ;
	public Starter(AbstractProcessor<CtType<?>> processor){
		this.processor = processor ;
	}*/
	
	public void AddLaunncher(Launcher launcher){
		this.launcher = launcher;
		
	}
	/*
	public void AddProcessor(Processor<T extends CtElement> processor){
		if(!this.launcher.equals(null)){
			this.launcher.addProcessor(processor);
		}
	}*/
	public static void main(String[] args) {
		System.out.println("starter\n");
		// TODO Auto-generated method stub
		
		final Launcher launcher = new Launcher();
		final String[]  arguments2= {"-x","-i","/home/bizimungu/workspace/m1/s2/pji/ws/test/OpenGL/HelloOpenGLES10"};
		final String[]  arguments1= {"-x","-i","/home/bizimungu/workspace/m1/s2/pji/ws/test/OpenGL/HelloOpenGLES20"};
		final String[]  arguments= {"-x","-i","/home/bizimungu/workspace/m1/s2/pji/ws/test/keep"};
		
	    //  final CtCodeSnippetStatement statementInConstructor = getFactory().Code().createCodeSnippetStatement("this.dates = dates");
	//	OnLongClickProcessor olp =new OnLongClickProcessor();
		
		//launcher.addProcessor(olp);		

//launcher.addProcessor(new OnclickProcessor());
		
//Handy for test_______!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//launcher.addProcessor(new ViewProcessor());	

		
		//launcher.addProcessor(new MethodsInViewProcessor(methode));
//launcher.addProcessor(new EventHandlersProcessor());
		List<Method> methList = new ArrayList<Method>() ;
	Method onclickmeth = new OnClickHandler();
	methList.add(onclickmeth);
	Method onKeymeth = new OnKeyHandler();
	methList.add(onKeymeth);
	Method onLayoutChange = new OnLayoutChangeHandler();
	methList.add(onLayoutChange);
	Method onLongClickmeth = new OnLongClickHandler();
	methList.add(onLongClickmeth);
	Method onScrollChangemeth = new OnScrollChangeHandler();
	methList.add(onScrollChangemeth);
	Method viewsOnTouchmeth = new ViewsOnTouchHandler();
	methList.add(viewsOnTouchmeth);
	//launcher.addProcessor(new MethodsInViewProcessor(onclickmeth));
//	launcher.addProcessor(new InterfaceListenerProcessor(onclickmeth));
	
	launcher.addProcessor(new OnEventMethodHandlersProcessor(methList));
	//launcher.run(arguments);
	launcher.run(arguments1);
	//launcher.run(arguments2);
System.out.println("starter done\n");

	}



}

