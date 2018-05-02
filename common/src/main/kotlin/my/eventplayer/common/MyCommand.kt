package my.eventplayer.common

import co.remotectrl.eventplayer.AggregateId
import co.remotectrl.eventplayer.PlayEvent
import co.remotectrl.eventplayer.PlayCommand

data class MyChangeCommand(val b: Boolean) : PlayCommand<MyModel>() {
    override fun validate(model: MyModel) {
        if (model.changeVal == b) {
            throw Exception("Invalid Input")
        }
    }

    override fun getEvent(id: AggregateId<MyModel>, version: Int): PlayEvent<MyModel> {
        return MyChangedEvent(id = id, version = version, b = b)
    }
}