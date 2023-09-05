package com.rarible.protocol.contracts.collection

import io.daonomic.rpc.domain.Word
import scalether.abi.array.VarArrayType
import scalether.abi.tuple.{Tuple1Type, Tuple2Type, UnitType}
import scalether.abi.{AddressType, Event, Uint256Type, StringType}
import scalether.domain.Address
import scalether.domain.request.{LogFilter, TopicFilter}
import scalether.domain.response.Log

import java.math.BigInteger

case class TokenUriRevealEvent(log: Log, index: BigInteger, revealedUri: String)

object TokenUriRevealEvent {

  import TopicFilter.simple

  val event = Event("TokenURIRevealed", List(Uint256Type, StringType), UnitType, Tuple2Type(Uint256Type, StringType))
  val id: Word = Word.apply("0x6df1d8db2a036436ffe0b2d1833f2c5f1e624818dfce2578c0faa4b83ef9998d")

  def filter(): LogFilter =
    LogFilter(topics = List(simple(id)))

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[TokenUriRevealEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(TokenUriRevealEvent(_))

  def apply(log: Log): TokenUriRevealEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val index = decodedData._1
    val revealedUri = decodedData._2
    TokenUriRevealEvent(log, index, revealedUri)
  }
}