package com.rarible.protocol.contracts.collection

import io.daonomic.rpc.domain._
import scalether.abi._
import scalether.abi.tuple._
import scalether.domain._
import scalether.domain.request._

case class CreateEventWithFullData(log: response.Log, creator: Address, name: String, symbol: String)

object CreateEventWithFullData {

  import TopicFilter.simple

  val event = Event("Create", List(AddressType, StringType, StringType), UnitType, Tuple3Type(AddressType, StringType, StringType))
  val id: Word = Word.apply("0x750d13f39f16526306cffdefb909852b055c2ea79ee21d21b36402eddaae7036")

  def filter(creator: Address): LogFilter =
    LogFilter(topics = List(simple(id), AddressType.encodeForTopic(creator)))

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[CreateEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(CreateEvent(_))

  def apply(log: response.Log): CreateEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val creator = decodedData._1
    val name = decodedData._2
    val symbol = decodedData._3
    CreateEvent(log, creator, name, symbol)
  }
}