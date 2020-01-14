package de.tuda.stg.consys.core.store

/**
 * Created on 14.01.20.
 *
 * @author Mirko Köhler
 */
trait StoredObject[StoreType <: Store, T <: StoreType#ObjType] {
	def invoke[R](methodId : String, args : Seq[Seq[Any]]) : R
}
