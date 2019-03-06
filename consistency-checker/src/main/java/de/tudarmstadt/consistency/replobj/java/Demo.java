package de.tudarmstadt.consistency.replobj.java;

import akka.actor.ActorSystem;
import de.tudarmstadt.consistency.replobj.ConsistencyLevels;
import de.tudarmstadt.consistency.replobj.SomeObj;
import de.tudarmstadt.consistency.replobj.java.impl.JReplicaSystemAkkaImpl;

/**
 * Created on 01.03.19.
 *
 * @author Mirko Köhler
 */
public class Demo {


	public static void main(String[] args) throws InterruptedException {

		JReplicaSystem replicaSystem1 = JReplicaSystem.fromActorSystem(2552);
		JReplicaSystem replicaSystem2 = JReplicaSystem.fromActorSystem(2553);

		replicaSystem1.addReplicaSystem("127.0.0.1", 2553);
		replicaSystem2.addReplicaSystem("127.0.0.1", 2552);


		JRef<SomeObj> ref1Strong = replicaSystem1.replicate("os", new SomeObj(), ConsistencyLevels.Strong.class);
		JRef<SomeObj> ref2Strong = replicaSystem2.ref("os", SomeObj.class, ConsistencyLevels.Strong.class);

		JRef<SomeObj> ref1Weak = replicaSystem1.replicate("ow", new SomeObj(), ConsistencyLevels.Weak.class);
		JRef<SomeObj> ref2Weak = replicaSystem2.ref("ow", SomeObj.class, ConsistencyLevels.Weak.class);


		Thread.sleep(3000);

		ref1Strong.setField("f", 34);
		ref1Weak.setField("f", 42);

//		int i = ref1Strong.getField("f");

		System.out.println("ref1Strong.f = "  + ref1Strong.getField("f"));
		System.out.println("ref2Strong.f = "  + ref2Strong.getField("f"));

		System.out.println("ref1Weak.f = "  + ref1Weak.getField("f"));
		System.out.println("ref2Weak.f = "  + ref2Weak.getField("f"));

		ref2Weak.synchronize();

		System.out.println("ref1Weak.f = "  + ref1Weak.getField("f"));
		System.out.println("ref2Weak.f = "  + ref2Weak.getField("f"));
	}
}