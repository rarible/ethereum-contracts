package com.rarible.protocol.contracts.seaport.v1.events

import io.daonomic.rpc.domain.Word
import scalether.abi.{AddressType, Bytes32Type, Event, Uint256Type, Uint8Type}
import scalether.abi.array.VarArrayType
import scalether.abi.tuple.{Tuple2Type, Tuple4Type, Tuple5Type}
import scalether.domain.request.{LogFilter, TopicFilter}
import scalether.domain.request.TopicFilter.simple
import scalether.domain.{Address, response}

import java.math.BigInteger


case class OrderFulfilledEvent(log: response.Log, offerer: Address, zone: Address, orderHash: Array[Byte], recipient: Address, offer: Array[(BigInteger, Address, BigInteger, BigInteger)], consideration: Array[(BigInteger, Address, BigInteger, BigInteger, Address)])

object OrderFulfilledEvent {
  import TopicFilter.simple

  val event = Event("OrderFulfilled", List(Bytes32Type, AddressType, AddressType, AddressType, VarArrayType(Tuple4Type(Uint8Type, AddressType, Uint256Type, Uint256Type)), VarArrayType(Tuple5Type(Uint8Type, AddressType, Uint256Type, Uint256Type, AddressType))), Tuple2Type(AddressType, AddressType), Tuple4Type(Bytes32Type, AddressType, VarArrayType(Tuple4Type(Uint8Type, AddressType, Uint256Type, Uint256Type)), VarArrayType(Tuple5Type(Uint8Type, AddressType, Uint256Type, Uint256Type, AddressType))))
  val id: Word = Word.apply("0x9d9af8e38d66c62e2c12f0225249fd9d721c54b83f48d9352c97c6cacdcb6f31")

  def filter(offerer: Address, zone: Address): LogFilter =
    LogFilter(topics = List(simple(id), AddressType.encodeForTopic(offerer), AddressType.encodeForTopic(zone)))

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[OrderFulfilledEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(OrderFulfilledEvent(_))

  def apply(log: response.Log): OrderFulfilledEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val offerer = event.indexed.type1.decode(log.topics(1), 0).value
    val zone = event.indexed.type2.decode(log.topics(2), 0).value
    val orderHash = decodedData._1
    val recipient = decodedData._2
    val offer = decodedData._3
    val consideration = decodedData._4
    OrderFulfilledEvent(log, offerer, zone, orderHash, recipient, offer, consideration)
  }
}
