package com.rarible.protocol.contracts.looksrare.v2

import io.daonomic.rpc.domain.Word
import scalether.abi._
import scalether.abi.array.{FixArrayType, VarArrayType}
import scalether.abi.tuple.{Tuple10Type, Tuple3Type, UnitType}
import scalether.domain.request.{LogFilter, TopicFilter}
import scalether.domain.{Address, response}

import java.math.BigInteger

case class TakerBidEvent(log: response.Log, nonceInvalidationParameters: (Array[Byte], BigInteger, java.lang.Boolean), bidUser: Address, bidRecipient: Address, strategyId: BigInteger, currency: Address, collection: Address, itemIds: Array[BigInteger], amounts: Array[BigInteger], feeRecipients: Array[Address], feeAmounts: Array[BigInteger])

object TakerBidEvent {
  import TopicFilter.simple

  val event = Event("TakerBid", List(Tuple3Type(Bytes32Type, Uint256Type, BoolType), AddressType, AddressType, Uint256Type, AddressType, AddressType, VarArrayType(Uint256Type), VarArrayType(Uint256Type), FixArrayType(2, AddressType), FixArrayType(3, Uint256Type)), UnitType, Tuple10Type(Tuple3Type(Bytes32Type, Uint256Type, BoolType), AddressType, AddressType, Uint256Type, AddressType, AddressType, VarArrayType(Uint256Type), VarArrayType(Uint256Type), FixArrayType(2, AddressType), FixArrayType(3, Uint256Type)))
  val id: Word = Word.apply("0x3ee3de4684413690dee6fff1a0a4f92916a1b97d1c5a83cdf24671844306b2e3")

  def filter(): LogFilter =
    LogFilter(topics = List(simple(id)))

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[TakerBidEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(TakerBidEvent(_))

  def apply(log: response.Log): TakerBidEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val nonceInvalidationParameters = decodedData._1
    val bidUser = decodedData._2
    val bidRecipient = decodedData._3
    val strategyId = decodedData._4
    val currency = decodedData._5
    val collection = decodedData._6
    val itemIds = decodedData._7
    val amounts = decodedData._8
    val feeRecipients = decodedData._9
    val feeAmounts = decodedData._10
    TakerBidEvent(log, nonceInvalidationParameters, bidUser, bidRecipient, strategyId, currency, collection, itemIds, amounts, feeRecipients, feeAmounts)
  }
}
