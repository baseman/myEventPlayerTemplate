package my.eventplayer.jvm

import co.remotectrl.eventplayer.*

data class MyChangedEvent(override val id: AggregateId<MyModel>, override val version: Int, val b: Boolean) : PlayEvent<MyModel>() {
    override fun applyChangesTo(model: MyModel) {
        model.changeVal = b
    }
}