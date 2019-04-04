/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.query.sqm.tree.domain;

import org.hibernate.metamodel.model.domain.spi.Navigable;
import org.hibernate.query.NavigablePath;
import org.hibernate.type.descriptor.java.spi.JavaTypeDescriptor;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractSqmSimplePath implements SqmNavigableReference {
	private final NavigablePath navigablePath;
	private final Navigable referencedNavigable;
	private final SqmPath lhs;

	private String explicitAlias;

	public AbstractSqmSimplePath(
			NavigablePath navigablePath,
			Navigable referencedNavigable,
			SqmPath lhs) {
		this( navigablePath, referencedNavigable, lhs, null );
	}

	public AbstractSqmSimplePath(
			NavigablePath navigablePath,
			Navigable referencedNavigable,
			SqmPath lhs,
			String explicitAlias) {
		this.navigablePath = navigablePath;
		this.referencedNavigable = referencedNavigable;
		this.lhs = lhs;
		this.explicitAlias = explicitAlias;
	}

	@Override
	public NavigablePath getNavigablePath() {
		return navigablePath;
	}

	@Override
	public Navigable getReferencedNavigable() {
		return referencedNavigable;
	}

	@Override
	public SqmPath getLhs() {
		return lhs;
	}

	@Override
	public String getExplicitAlias() {
		return explicitAlias;
	}

	@Override
	public void setExplicitAlias(String explicitAlias) {
		this.explicitAlias = explicitAlias;
	}

	@Override
	public JavaTypeDescriptor getJavaTypeDescriptor() {
		return getReferencedNavigable().getJavaTypeDescriptor();
	}

}
