# Exercises on Scala Futures

Based on original idea of [Avraham Rosenzweig](https://github.com/avrahamr)

## Introduction

`Futures` provide a simple way to run an asynchronous computation. 
Future starts a computation when you create it and then eventually returns the result. 
For example, every RPC invocation at Wix is a function that returns a `Future` of the RPC service result. 
See more information about `Futures` API in the [documentation](https://www.scala-lang.org/api/2.12.2/scala/concurrent/Future.html)

`Futures` does not have timeouts but we can add them using Scala API and Java standard library.

## Exercises

### Kata1 "Required and Optional"

This is an exercise on composing Futures with a required and optional result.

### Kata2 "Delayed Future"

This is an exercise on creating a Future that completes after a given delay.

### Kata3 "Future with Timeout"

This is an exercise on creating a new Future that either completes with the result of a given Future if the given Future completes before a given timeout or fails with a timeout exception if the given Future completes after the timeout.

### Kata4 "Required and Optional with Timeout"

This is an exercise on composing Futures with a required and optional result (as in Kata1) but also with a given timeout.
