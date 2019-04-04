/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.query.sqm.tree.domain;

import org.hibernate.metamodel.model.domain.spi.Navigable;
import org.hibernate.metamodel.model.domain.spi.NavigableContainer;
import org.hibernate.metamodel.model.domain.spi.PersistentCollectionDescriptor;
import org.hibernate.metamodel.model.domain.spi.PluralValuedNavigable;
import org.hibernate.query.NavigablePath;
import org.hibernate.query.sqm.consume.spi.SemanticQueryWalker;
import org.hibernate.query.sqm.produce.path.spi.SemanticPathPart;
import org.hibernate.query.sqm.produce.spi.SqmCreationState;
import org.hibernate.query.sqm.tree.expression.SqmExpression;

/**
 * @author Steve Ebersole
 */
public class SqmIndexedCollectionAccessPath implements SqmPath {
	private final SqmPath pluralDomainPath;
	private final SqmExpression selectorExpression;
	private final PersistentCollectionDescriptor collectionDescriptor;

	private String explicitAlias;

	public SqmIndexedCollectionAccessPath(
			SqmPath pluralDomainPath,
			SqmExpression selectorExpression) {
		this.pluralDomainPath = pluralDomainPath;
		this.selectorExpression = selectorExpression;

		this.collectionDescriptor = pluralDomainPath.as( PluralValuedNavigable.class ).getCollectionDescriptor();
	}

	public SqmExpression getSelectorExpression() {
		return selectorExpression;
	}

	@Override
	public NavigablePath getNavigablePath() {
		// todo (6.0) : this would require some String-ified form of the selector
		return null;
	}

	@Override
	public Navigable<?> getReferencedNavigable() {
		return collectionDescriptor.getElementDescriptor();
	}

	@Override
	public SqmPath getLhs() {
		return pluralDomainPath;
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
	public SemanticPathPart resolvePathPart(
			String name,
			String currentContextKey,
			boolean isTerminal,
			SqmCreationState creationState) {
		final Navigable subNavigable = ( (NavigableContainer) collectionDescriptor.getElementDescriptor() )
				.findNavigable( name );

		return subNavigable.createSqmExpression( this, creationState );
	}

	@Override
	public <T> T accept(SemanticQueryWalker<T> walker) {
		return walker.visitIndexedPluralAccessPath( this );
	}
}
