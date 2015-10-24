package langlan.sql.dsl.d;

import langlan.sql.dsl.e.SqlSyntaxException;

public abstract class InlineStrategySupport<T extends InlineStrategySupport<T>> extends RealThisSupport<T> {
	private int $status;
	private boolean $selfInvalid;

	/**
	 * Inline apply strategy of the sql-fragment-append-method immediately followed by this method.
	 *
	 * @param apply Inline-Apply-Flag indicates whether previous fragment-append-method should be applied.
	 * @throws SqlSyntaxException If this method is not invoked immediate following a criteria-about method or invoked
	 *                            repeatedly.
	 */
	public T $(boolean apply) throws SqlSyntaxException {
		switch ($status) {
			case 0:
				return apply ? realThis() : $invalidSelf();
			case 1:
				return apply ? realThis() : $invalidLastItem();
			case 2:
				throw new SqlSyntaxException("$ method cannot be invoked repeatedly on a single element!");
			default:
				throw new SqlSyntaxException("****** SHOULD NOT HAPPEN! ******");

		}
	}

	protected T $invalidLastItem() {
		$status = 2;
		return realThis();
	}

	private T $invalidSelf() {
		$selfInvalid = true;
		$status = 2;
		return realThis();
	}

	protected T $setInvokable() {
		$status = 1;
		return realThis();
	}

	protected boolean $isSelfInvalid() {
		return $selfInvalid;
	}
}
