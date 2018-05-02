package my.eventplayer.jvm

import co.remotectrl.eventplayer.Aggregate
import co.remotectrl.eventplayer.AggregateId

data class MyModel(override val id: AggregateId<MyModel>, var changeVal: Boolean, var sum: Int = 0) : Aggregate<MyModel>()