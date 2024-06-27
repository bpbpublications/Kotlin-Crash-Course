package com.bpbonline.f1app

open class RaceException(message: String) : Exception(message)
class SafetyCarException(message: String) : RaceException(message)
class YellowFlagException(message: String) : RaceException(message)