package com.rarible.protocol.contracts

import com.rarible.contracts.erc721.ApprovalForAllEvent
import io.daonomic.rpc.domain.Word
import scalether.abi.tuple.{Tuple3Type, UnitType}
import scalether.abi.{AddressType, Event, Uint256Type}
import scalether.domain.request.{LogFilter, TopicFilter}
import scalether.domain.{Address, response}

import java.lang
import java.math.BigInteger

//noinspection DuplicatedCode
object ApprovalForAllEventWithFullData {

  import TopicFilter.simple

  val event: Event[UnitType.type, (Address, Address, BigInteger)] =
    Event("ApprovalForAll", List(AddressType, AddressType, Uint256Type), UnitType, Tuple3Type(AddressType, AddressType, Uint256Type))
  val id: Word = Word.apply("0x17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31")

  def filter(account: Address, operator: Address): LogFilter =
    LogFilter(topics = List(simple(id), AddressType.encodeForTopic(account), AddressType.encodeForTopic(operator)))

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[ApprovalForAllEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(ApprovalForAllEvent(_))

  def apply(log: response.Log): ApprovalForAllEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val account = decodedData._1
    val operator = decodedData._2
    val approved = decodedData._3 == BigInteger.ONE
    ApprovalForAllEvent(log, account, operator, approved)
  }
}
