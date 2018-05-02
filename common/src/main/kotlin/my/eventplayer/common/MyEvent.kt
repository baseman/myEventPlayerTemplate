package my.eventplayer.common

import co.remotectrl.eventplayer.AggregateId
import co.remotectrl.eventplayer.PlayEvent

data class MyChangedEvent(override val id: AggregateId<MyModel>, override val version: Int, val b: Boolean) : PlayEvent<MyModel>() {
    override fun applyChangesTo(model: MyModel) {
        model.changeVal = b
    }
}