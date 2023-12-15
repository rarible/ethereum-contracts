package com.rarible.protocol.contracts.ens.v1

import io.daonomic.rpc.domain.Word
import scalether.abi.tuple.{Tuple1Type, Tuple2Type}
import scalether.abi.{AddressType, Event, Uint256Type}
import scalether.domain.request.{LogFilter, TopicFilter}
import scalether.domain.{Address, response}

import java.math.BigInteger

case class NameRenewedEvent(log: response.Log, tokenId: BigInteger, expires: BigInteger)

object NameRenewedEvent {
  import TopicFilter.simple

  val event = Event("NameRenewed", List(Uint256Type, Uint256Type), Tuple1Type(Uint256Type), Tuple1Type(Uint256Type))
  val id: Word = Word.apply("0x9b87a00e30f1ac65d898f070f8a3488fe60517182d0a2098e1b4b93a54aa9bd6")

  def filter(tokenId: BigInteger): LogFilter =
    LogFilter(topics = List(simple(id), Uint256Type.encodeForTopic(tokenId)))

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[NameRenewedEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(NameRenewedEvent(_))

  def apply(log: response.Log): NameRenewedEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val tokenId = event.indexed.type1.decode(log.topics(1), 0).value
    val expires = decodedData
    NameRenewedEvent(log, tokenId, expires)
  }
}
