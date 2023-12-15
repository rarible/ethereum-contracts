package com.rarible.protocol.contracts.ens.v1

import io.daonomic.rpc.domain.Word
import scalether.abi.tuple.{Tuple1Type, Tuple2Type, Tuple3Type, UnitType}
import scalether.abi.{AddressType, BoolType, Event, Uint256Type}
import scalether.domain.request.{LogFilter, TopicFilter}
import scalether.domain.{Address, response}

import java.math.BigInteger

case class NameMigratedEvent(log: response.Log, tokenId: BigInteger, owner: Address, expires: BigInteger)

object NameMigratedEvent {
  import TopicFilter.simple

  val event = Event("NameMigrated", List(Uint256Type, AddressType, Uint256Type), Tuple2Type(Uint256Type, AddressType), Tuple1Type(Uint256Type))
  val id: Word = Word.apply("0xea3d7e1195a15d2ddcd859b01abd4c6b960fa9f9264e499a70a90c7f0c64b717")

  def filter(tokenId: BigInteger, owner: Address): LogFilter =
    LogFilter(topics = List(simple(id), Uint256Type.encodeForTopic(tokenId), AddressType.encodeForTopic(owner)))

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[NameMigratedEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(NameMigratedEvent(_))

  def apply(log: response.Log): NameMigratedEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val tokenId = event.indexed.type1.decode(log.topics(1), 0).value
    val owner = event.indexed.type2.decode(log.topics(2), 0).value
    val expires = decodedData
    NameMigratedEvent(log, tokenId, owner, expires)
  }
}

