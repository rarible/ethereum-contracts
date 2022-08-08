package com.rarible.protocol.contracts.x2y2.v1.events

import io.daonomic.rpc.domain.Word
import scalether.abi.{AddressType, Bytes32Type, BytesType, Event, Uint256Type, Uint8Type}
import scalether.abi.array.VarArrayType
import scalether.abi.tuple.{Tuple11Type, Tuple1Type, Tuple2Type}
import scalether.domain.request.{LogFilter, TopicFilter}
import scalether.domain.request.TopicFilter.simple
import scalether.domain.{Address, response}

import java.math.BigInteger

case class EvInventoryEvent(log: response.Log, itemHash: Array[Byte], maker: Address, taker: Address, orderSalt: BigInteger, settleSalt: BigInteger, intent: BigInteger, delegateType: BigInteger, deadline: BigInteger, currency: Address, dataMask: Array[Byte], item: (BigInteger, Array[Byte]), detail: (BigInteger, BigInteger, BigInteger, BigInteger, Array[Byte], Address, Array[Byte], BigInteger, BigInteger, BigInteger, Array[(BigInteger, Address)]))

object EvInventoryEvent {
  import TopicFilter.simple

  val event = Event("EvInventory", List(Bytes32Type, AddressType, AddressType, Uint256Type, Uint256Type, Uint256Type, Uint256Type, Uint256Type, AddressType, BytesType, Tuple2Type(Uint256Type, BytesType), Tuple11Type(Uint8Type, Uint256Type, Uint256Type, Uint256Type, Bytes32Type, AddressType, BytesType, Uint256Type, Uint256Type, Uint256Type, VarArrayType(Tuple2Type(Uint256Type, AddressType)))), Tuple1Type(Bytes32Type), Tuple11Type(AddressType, AddressType, Uint256Type, Uint256Type, Uint256Type, Uint256Type, Uint256Type, AddressType, BytesType, Tuple2Type(Uint256Type, BytesType), Tuple11Type(Uint8Type, Uint256Type, Uint256Type, Uint256Type, Bytes32Type, AddressType, BytesType, Uint256Type, Uint256Type, Uint256Type, VarArrayType(Tuple2Type(Uint256Type, AddressType)))))
  val id: Word = Word.apply("0x3cbb63f144840e5b1b0a38a7c19211d2e89de4d7c5faf8b2d3c1776c302d1d33")

  def filter(itemHash: Array[Byte]): LogFilter =
    LogFilter(topics = List(simple(id), Bytes32Type.encodeForTopic(itemHash)))

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[EvInventoryEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(EvInventoryEvent(_))

  def apply(log: response.Log): EvInventoryEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val itemHash = event.indexed.type1.decode(log.topics(1), 0).value
    val maker = decodedData._1
    val taker = decodedData._2
    val orderSalt = decodedData._3
    val settleSalt = decodedData._4
    val intent = decodedData._5
    val delegateType = decodedData._6
    val deadline = decodedData._7
    val currency = decodedData._8
    val dataMask = decodedData._9
    val item = decodedData._10
    val detail = decodedData._11
    EvInventoryEvent(log, itemHash, maker, taker, orderSalt, settleSalt, intent, delegateType, deadline, currency, dataMask, item, detail)
  }
}

