# Instrumenting_android_code

The objective of this project is to collect runtime metrics during the execution of a set of Android apps.
To collect metrics we will apply source code instrumentation (using Spoon [2]). 
The instrumentation will collect information in relevant points of the code of the apps, for example when users interact with apps (click a button, type text, etc).


This repository contains: 

*the source of the project in /src
*the javadoc /doc
*Somme android apps used for test in /test
*an uml diagram for the architecture /uml 
*a file short desrciption in (french) of the chosen disign in /rapport  
 
 You can run it on your own application .
 
Running the project in eclipse environement: 
There are some modification to perfom in class "starter" :

- change or add a variable in this class wich represents the argments of spoon's compiler. 
- for instance :
   final String[]  myarguments= {"-x","-i","/home/bizimungu/workspace/m1/s2/pji/ws/pji-instrumenting-androidcode/test/keep"}; 
the last string is the exact path of the source code to be instrumented on your home directory
use the variable myarguments as the argument of the starter in this line 
....
starter.run(myarguments)


after porcessing will dsiplay:
-first all the methods within every class of the project 
-second the methods found among those given in arguments :
