package de.tudarmstadt.consistency.storelayer.distribution

import com.datastax.driver.core.Cluster
import de.tudarmstadt.consistency.storelayer.distribution
import de.tudarmstadt.consistency.storelayer.distribution.cassandra.CassandraTypeBinding

import scala.language.implicitConversions

/**
	* Created on 21.12.18.
	*
	* @author Mirko Köhler
	*/
trait SessionService[Id, Txid, Key, Data, TxStatus, Isolation, Consistency] {

	/* class definitions */
	type OpRef = de.tudarmstadt.consistency.storelayer.distribution.OpRef[Id, Key]
	type TxRef = de.tudarmstadt.consistency.storelayer.distribution.TxRef[Txid]

	def ref(id : Id, key : Key) : OpRef = OpRef(id, key)
	def ref(txid : Txid) : TxRef = TxRef(txid)

	implicit def tupleToRef(t : (Id, Key)) : OpRef = OpRef(t._1, t._2)


	//For communication with the outside world.


}