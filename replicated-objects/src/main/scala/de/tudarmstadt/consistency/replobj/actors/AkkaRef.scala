package de.tudarmstadt.consistency.replobj.actors

import de.tudarmstadt.consistency.replobj.{ConsistencyLevel, Ref, ReplicatedObject}

/**
	* Created on 17.04.19.
	*
	* @author Mirko Köhler
	*/
private[actors] class AkkaRef[Addr, T <: AnyRef](val addr : Addr, val consistencyLevel : ConsistencyLevel, @transient private[actors] var replicaSystem : AkkaReplicaSystem[Addr]) extends Ref[Addr, T] {

	override implicit def deref : ReplicatedObject[T] = replicaSystem match {
		case null => sys.error(s"replica system has not been initialized properly. $toString")

		case akkaReplicaSystem: AkkaReplicaSystem[Addr] => akkaReplicaSystem.Replica.get(addr) match {
			case None => //retry
				sys.error(s"the replicated object '$addr' with consistency level $consistencyLevel is not available on this host.")

			case Some(rob : ReplicatedObject[T]) =>
				//Check that consistency level of reference matches the referenced object
				assert(rob.consistencyLevel == consistencyLevel, s"non-matching consistency levels. ref was $consistencyLevel and object was ${rob.consistencyLevel}")
				return rob
		}
	}

	override def toString : String = s"RefImpl($addr, $consistencyLevel)"
}