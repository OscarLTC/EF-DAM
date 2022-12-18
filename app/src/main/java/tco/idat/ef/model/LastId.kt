package tco.idat.ef.model

import kotlin.properties.Delegates

class LastId {
    companion object{
        var id by Delegates.notNull<Int>()
    }
}