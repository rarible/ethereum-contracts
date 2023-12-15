package com.rarible.protocol.contracts.ens.v1

import io.daonomic.rpc.domain.Word
import scalether.abi.tuple.{Tuple1Type, Tuple2Type, Tuple3Type, UnitType}
import scalether.abi.{AddressType, BoolType, Event, Uint256Type}
import scalether.domain.request.{LogFilter, TopicFilter}
import scalether.domain.{Address, response}

import java.math.BigInteger

case class NameRegisteredEvent(log: response.Log, tokenId: BigInteger, owner: Address, expires: BigInteger)

object NameRegisteredEvent {
  import TopicFilter.simple

  val event = Event("NameRegistered", List(Uint256Type, AddressType, Uint256Type), Tuple2Type(Uint256Type, AddressType), Tuple1Type(Uint256Type))
  val id: Word = Word.apply("0xb3d987963d01b2f68493b4bdb130988f157ea43070d4ad840fee0466ed9370d9")

  def filter(tokenId: BigInteger, owner: Address): LogFilter =
    LogFilter(topics = List(simple(id), Uint256Type.encodeForTopic(tokenId), AddressType.encodeForTopic(owner)))

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[NameRegisteredEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(NameRegisteredEvent(_))

  def apply(log: response.Log): NameRegisteredEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val tokenId = event.indexed.type1.decode(log.topics(1), 0).value
    val owner = event.indexed.type2.decode(log.topics(2), 0).value
    val expires = decodedData
    NameRegisteredEvent(log, tokenId, owner, expires)
  }
}
