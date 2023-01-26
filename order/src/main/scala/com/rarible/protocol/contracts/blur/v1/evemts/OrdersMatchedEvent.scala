package com.rarible.protocol.contracts.blur.v1.evemts

import io.daonomic.rpc.domain.Word
import scalether.abi.array.VarArrayType
import scalether.abi.tuple.{Tuple13Type, Tuple2Type, Tuple4Type, Tuple5Type}
import scalether.abi._
import scalether.domain.request.{LogFilter, TopicFilter}
import scalether.domain.{Address, response}

import java.math.BigInteger

case class OrdersMatchedEvent(log: response.Log, maker: Address, taker: Address, sell: (Address, BigInteger, Address, Address, BigInteger, BigInteger, Address, BigInteger, BigInteger, BigInteger, Array[(BigInteger, Address)], BigInteger, Array[Byte]), sellHash: Array[Byte], buy: (Address, BigInteger, Address, Address, BigInteger, BigInteger, Address, BigInteger, BigInteger, BigInteger, Array[(BigInteger, Address)], BigInteger, Array[Byte]), buyHash: Array[Byte])

object OrdersMatchedEvent {
  import TopicFilter.simple

  val event = Event("OrdersMatched", List(AddressType, AddressType, Tuple13Type(AddressType, Uint8Type, AddressType, AddressType, Uint256Type, Uint256Type, AddressType, Uint256Type, Uint256Type, Uint256Type, VarArrayType(Tuple2Type(Uint16Type, AddressType)), Uint256Type, BytesType), Bytes32Type, Tuple13Type(AddressType, Uint8Type, AddressType, AddressType, Uint256Type, Uint256Type, AddressType, Uint256Type, Uint256Type, Uint256Type, VarArrayType(Tuple2Type(Uint16Type, AddressType)), Uint256Type, BytesType), Bytes32Type), Tuple2Type(AddressType, AddressType), Tuple4Type(Tuple13Type(AddressType, Uint8Type, AddressType, AddressType, Uint256Type, Uint256Type, AddressType, Uint256Type, Uint256Type, Uint256Type, VarArrayType(Tuple2Type(Uint16Type, AddressType)), Uint256Type, BytesType), Bytes32Type, Tuple13Type(AddressType, Uint8Type, AddressType, AddressType, Uint256Type, Uint256Type, AddressType, Uint256Type, Uint256Type, Uint256Type, VarArrayType(Tuple2Type(Uint16Type, AddressType)), Uint256Type, BytesType), Bytes32Type))
  val id: Word = Word.apply("0x61cbb2a3dee0b6064c2e681aadd61677fb4ef319f0b547508d495626f5a62f64")

  def filter(maker: Address, taker: Address): LogFilter =
    LogFilter(topics = List(simple(id), AddressType.encodeForTopic(maker), AddressType.encodeForTopic(taker)))

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[OrdersMatchedEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(OrdersMatchedEvent(_))

  def apply(log: response.Log): OrdersMatchedEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val maker = event.indexed.type1.decode(log.topics(1), 0).value
    val taker = event.indexed.type2.decode(log.topics(2), 0).value
    val sell = decodedData._1
    val sellHash = decodedData._2
    val buy = decodedData._3
    val buyHash = decodedData._4
    OrdersMatchedEvent(log, maker, taker, sell, sellHash, buy, buyHash)
  }
}
