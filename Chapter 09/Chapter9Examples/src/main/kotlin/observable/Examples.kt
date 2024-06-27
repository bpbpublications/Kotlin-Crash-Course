package observable

import kotlin.properties.Delegates

var observableProperty: String by Delegates.observable("<initial value>") {
        property, oldValue, newValue ->
            println("$oldValue -> $newValue")
        }
