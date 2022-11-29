package com.rarible.protocol.contracts.erc1155

import com.rarible.contracts.erc1155.TransferSingleEvent
import io.daonomic.rpc.domain.Word
import scalether.abi.tuple.{Tuple2Type, Tuple3Type, Tuple5Type, UnitType}
import scalether.abi.{AddressType, Event, Uint256Type}
import scalether.domain.request.{LogFilter, TopicFilter}
import scalether.domain.{Address, response}

import java.math.BigInteger

object TransferSingleEventTopics3 {
  import TopicFilter.simple

  val event: Event[Tuple2Type[Address, Address], (Address, BigInteger, BigInteger)] =
    Event("TransferSingle", List(AddressType, AddressType, AddressType, Uint256Type, Uint256Type), Tuple2Type(AddressType, AddressType), Tuple3Type(AddressType, Uint256Type, Uint256Type))
  val id: Word = Word.apply("0xc3d58168c5ae7397731d063d5bbf3d657854427343f4c083240f7aacaa2d0f62")

  def filter(operator: Address, from: Address, to: Address): LogFilter =
    LogFilter(topics = List(simple(id), AddressType.encodeForTopic(operator), AddressType.encodeForTopic(from), AddressType.encodeForTopic(to)))

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[TransferSingleEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(TransferSingleEvent(_))

  def apply(log: response.Log): TransferSingleEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val operator = event.indexed.type1.decode(log.topics(1), 0).value
    val from = event.indexed.type2.decode(log.topics(2), 0).value
    val to = decodedData._1
    val tokenId = decodedData._2
    val value = decodedData._3
    TransferSingleEvent(log, operator, from, to, tokenId, value)
  }
}