package de.tuda.stg.consys.core.store

import scala.collection.mutable
import scala.language.higherKinds
import scala.reflect.runtime.universe.TypeTag

/**
 * Created on 11.12.19.
 *
 * @author Mirko Köhler
 */
trait CachedTransactionContext extends TransactionContext {

	protected type CachedType[_ <: StoreType#ObjType]

	protected val cache : mutable.Map[StoreType#Addr, CachedType[_]] = mutable.HashMap.empty

	protected def rawToCached[T <: StoreType#ObjType : TypeTag](ref : StoreType#RawType[T]) : CachedType[T]

	protected def cachedToRaw[T <: StoreType#ObjType : TypeTag](cached : CachedType[T]) : StoreType#RawType[T]

	protected def cacheRaw[T <: StoreType#ObjType : TypeTag](addr : StoreType#Addr, raw : StoreType#RawType[T]) : Unit = {
		cache(addr) = rawToCached(raw)
	}


	override private[store] def replicateRaw[T <: StoreType#ObjType : TypeTag](addr : StoreType#Addr, obj : T, level : ConsistencyLevel) : StoreType#RawType[T] = {
		val res = super.replicateRaw[T](addr, obj, level)
		cacheRaw(addr, res)
		res
	}

	override private[store] def lookupRaw[T <: StoreType#ObjType : TypeTag](addr : StoreType#Addr, level : ConsistencyLevel) : StoreType#RawType[T] = cache.get(addr) match {
		case Some(cachedObject : CachedType[T@unchecked]@unchecked) =>
			cachedToRaw[T](cachedObject)
		case None =>
			val res = super.lookupRaw[T](addr, level)
			cacheRaw(addr, res)
			res
	}

}