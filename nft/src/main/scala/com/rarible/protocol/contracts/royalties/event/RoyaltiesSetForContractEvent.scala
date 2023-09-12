package com.rarible.protocol.contracts.royalties.event

import io.daonomic.rpc.domain.Word
import scalether.abi.array.VarArrayType
import scalether.abi.tuple.{Tuple1Type, Tuple2Type}
import scalether.abi.{AddressType, Event, Uint96Type}
import scalether.domain.{Address, response}
import scalether.domain.request.{LogFilter, TopicFilter}

import java.math.BigInteger

case class RoyaltiesSetForContractEvent(log: response.Log, token: Address, royalties: Array[(Address, BigInteger)])

object RoyaltiesSetForContractEvent {
  import TopicFilter.simple

  val event = Event("RoyaltiesSetForContract", List(AddressType, VarArrayType(Tuple2Type(AddressType, Uint96Type))), Tuple1Type(AddressType), Tuple1Type(VarArrayType(Tuple2Type(AddressType, Uint96Type))))
  val id: Word = Word.apply("0xc026171b9a7c9009d6a748a19a0a3cb877978a585e1647a87a786d724bbde127")

  def filter(token: Address): LogFilter =
    LogFilter(topics = List(simple(id), AddressType.encodeForTopic(token)))

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[RoyaltiesSetForContractEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(RoyaltiesSetForContractEvent(_))

  def apply(log: response.Log): RoyaltiesSetForContractEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val token = event.indexed.type1.decode(log.topics(1), 0).value
    val royalties = decodedData
    RoyaltiesSetForContractEvent(log, token, royalties)
  }
}
