/* 
 * Spoon - http://spoon.gforge.inria.fr/
 * Copyright (C) 2006 INRIA Futurs <renaud.pawlak@inria.fr>
 * 
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify 
 * and/or redistribute the software under the terms of the CeCILL-C license as 
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info. 
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *  
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */

package spoon.support.reflect.reference;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spoon.Launcher;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.Factory;
import spoon.reflect.factory.FactoryImpl;
import spoon.reflect.reference.CtReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.DefaultJavaPrettyPrinter;
import spoon.support.visitor.SignaturePrinter;

public abstract class CtReferenceImpl implements CtReference, Serializable, Comparable<CtReference> {

	private static final long serialVersionUID = 1L;

	String simplename;

	transient Factory factory;

	public CtReferenceImpl() {
		super();
	}

	public int compareTo(CtReference o) {
		SignaturePrinter pr = new SignaturePrinter();
		pr.scan(this);
		String current = pr.getSignature();
		pr.reset();
		pr.scan(o);
		return current.compareTo(pr.getSignature());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof CtReference)
			return compareTo((CtReference) object) == 0;
		return false;
	}

	abstract protected AnnotatedElement getActualAnnotatedElement();

	public String getSimpleName() {
		return simplename;
	}
	
	public void setSimpleName(String simplename) {
		if (simplename.contains("?"))
			throw new RuntimeException("argl");
		Factory factory = getFactory();
		if (factory instanceof FactoryImpl)
			simplename = ((FactoryImpl) factory).dedup(simplename);
		this.simplename = simplename;
	}

	@Override
	public String toString() {
		DefaultJavaPrettyPrinter printer = new DefaultJavaPrettyPrinter(
				getFactory().getEnvironment());
		printer.scan(this);
		return printer.toString();
	}

	public Factory getFactory() {
		return factory;
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
	}

}
