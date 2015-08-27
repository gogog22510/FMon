package gogog22510.appmon.assist;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.NotFoundException;

public abstract class AbstractTransformer implements ClassFileTransformer {
	protected Set<String> redefinedClass, redefinedMethod;

	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		return transformClass(classBeingRedefined, classfileBuffer);
	}
	
	protected byte[] transformClass(Class classToTransform, byte[] b) {
		ClassPool pool = ClassPool.getDefault();
		CtClass cl = null;
		try {
			cl = pool.makeClass(new java.io.ByteArrayInputStream(b));
			// if contains classes
			if(redefinedClass.contains(cl.getName())) {
				CtBehavior[] methods = cl.getDeclaredBehaviors();
				for (int i = 0; i < methods.length; i++) {
					if (methods[i].isEmpty() == false) {
						changeMethod(methods[i]);
					}
				}
				b = cl.toBytecode();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cl != null) {
				cl.detach();
			}
		}
		return b;
	}
	
	protected void changeMethod(CtBehavior method) throws NotFoundException, CannotCompileException {
		if(redefinedMethod.contains(method.getName())) {
			changeMethodImpl(method);
		}
	}
	
	protected abstract void changeMethodImpl(CtBehavior method) throws NotFoundException, CannotCompileException;
}
