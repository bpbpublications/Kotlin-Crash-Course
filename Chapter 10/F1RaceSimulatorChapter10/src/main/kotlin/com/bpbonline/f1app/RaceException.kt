package com.bpbonline.f1app

import com.bpbonline.f1app.participants.Driver

open class RaceException(message: String) : Exception(message)
class SafetyCarException(message: String) : RaceException(message)
class YellowFlagException(message: String) : RaceException(message)
class UnrepairableCollisionException(
    message: String,
    val driver: Driver,
) : RaceException(message)
