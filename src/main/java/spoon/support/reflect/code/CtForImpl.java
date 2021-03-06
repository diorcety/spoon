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

package spoon.support.reflect.code;

import java.util.ArrayList;
import java.util.List;

import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtStatement;
import spoon.reflect.visitor.CtVisitor;
import spoon.support.reflect.declaration.CtElementImpl;

import static spoon.reflect.ModelElementContainerDefaultCapacities.FOR_INIT_STATEMENTS_CONTAINER_DEFAULT_CAPACITY;
import static spoon.reflect.ModelElementContainerDefaultCapacities.FOR_UPDATE_STATEMENTS_CONTAINER_DEFAULT_CAPACITY;

public class CtForImpl extends CtLoopImpl implements CtFor {
	private static final long serialVersionUID = 1L;

	CtExpression<Boolean> expression;

	List<CtStatement> forInit = EMPTY_LIST();

	List<CtStatement> forUpdate = EMPTY_LIST();

	public void accept(CtVisitor visitor) {
		visitor.visitCtFor(this);
	}

	public CtExpression<Boolean> getExpression() {
		return expression;
	}

	public List<CtStatement> getForInit() {
		return forInit;
	}

	public List<CtStatement> getForUpdate() {
		return forUpdate;
	}

	public void setExpression(CtExpression<Boolean> expression) {
		if (expression != null)
			expression.setParent(this);
		this.expression = expression;
	}

	@Override
	public void setForInit(List<CtStatement> statements) {
		this.forInit.clear();
		for (CtStatement stmt : statements) {
			addForInit(stmt);
		}
	}

	@Override
	public void setForUpdate(List<CtStatement> statements) {
		this.forUpdate.clear();
		for (CtStatement stmt : statements) {
			addForUpdate(stmt);
		}
	}
	

	@Override
	public boolean addForInit(CtStatement statement) {
		if (forInit == CtElementImpl.<CtStatement> EMPTY_LIST()) {
			forInit = new ArrayList<CtStatement>(
					FOR_INIT_STATEMENTS_CONTAINER_DEFAULT_CAPACITY);
		}
		statement.setParent(this);
		return forInit.add(statement);
	}

	@Override
	public boolean removeForInit(CtStatement statement) {
		return forInit != CtElementImpl.<CtStatement>EMPTY_LIST() &&
				forInit.remove(statement);
	}

	@Override
	public boolean addForUpdate(CtStatement statement) {
		if (forUpdate == CtElementImpl.<CtStatement> EMPTY_LIST()) {
			forUpdate = new ArrayList<CtStatement>(
					FOR_UPDATE_STATEMENTS_CONTAINER_DEFAULT_CAPACITY);
		}
		statement.setParent(this);
		return forUpdate.add(statement);
	}

	@Override
	public boolean removeForUpdate(CtStatement statement) {
		return forUpdate != CtElementImpl.<CtStatement>EMPTY_LIST() &&
				forUpdate.remove(statement);
	}

}
