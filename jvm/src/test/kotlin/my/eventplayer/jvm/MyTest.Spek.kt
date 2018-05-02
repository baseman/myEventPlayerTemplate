package my.eventplayer.jvm

import co.remotectrl.eventplayer.AggregateId
import co.remotectrl.eventplayer.PlayEvent
import co.remotectrl.eventplayer.Player
import org.amshove.kluent.AnyException
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

class MyTest : Spek({

    describe("EventPlayer Aggregate") {

        val idVal = 1
        val id = AggregateId<MyModel>(Value = idVal)
        val model = MyModel(id = id, changeVal = false)

//todo: identifywhy on is hidden in intellij test runner
//        on("Command") {

            val cmd = MyChangeCommand(b = true)

            it("should return a resulting PlayEvent abstraction on successful execution") {

                val evt: PlayEvent<MyModel> = cmd.executeOn(model)

                evt.id.Value shouldEqual idVal
                evt.version shouldEqual 1
                (evt as MyChangedEvent).b shouldEqual cmd.b
            }

            it("should try to validate command input on execution") {
                { MyChangeCommand(b = false).executeOn(model) } shouldThrow AnyException
            }
//        }

//        on("Event") {

            val evt = MyChangedEvent(id = id, version = 1, b = true)

            it("should apply the event to the mutable values of the model") {

                evt.applyTo(model)

                model.changeVal shouldEqual evt.b
                model.latestVersion shouldEqual evt.version
            }
//        }

//        on("Player") {

            val player = Player<MyModel>()

            data class MyAddedEvent(override val id: AggregateId<MyModel>, override val version: Int, val addVal: Int) : PlayEvent<MyModel>() {
                override fun applyChangesTo(model: MyModel) {
                    model.sum += addVal
                }
            }

            it("should play over a list of events") {
                val evts = Array(3, { i -> MyAddedEvent(id = id, version = i, addVal = i) as PlayEvent<MyModel> })
                player.playFor(evts, model)

                model.sum shouldEqual 2 + 1
            }
//        }
    }
})