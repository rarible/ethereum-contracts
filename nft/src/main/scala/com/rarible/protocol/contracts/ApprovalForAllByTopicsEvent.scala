package com.rarible.protocol.contracts

import com.rarible.contracts.erc721.ApprovalForAllEvent
import io.daonomic.rpc.domain.Word
import scalether.abi.{AddressType, BoolType, Event}
import scalether.abi.tuple.{Tuple3Type, UnitType}
import scalether.domain.{Address, response}
import scalether.domain.request.{LogFilter, TopicFilter}

object ApprovalForAllByTopicsEvent {

  import TopicFilter.simple

  val event: Event[Tuple3Type[Address, Address, java.lang.Boolean], Unit] =
    Event("ApprovalForAll", List(AddressType, AddressType, BoolType), Tuple3Type(AddressType, AddressType, BoolType), UnitType)

  val id: Word = Word.apply("0x17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31")

  def filter(owner: Address, operator: Address, approved: Boolean): LogFilter =
    LogFilter(topics = List(simple(id), AddressType.encodeForTopic(owner), AddressType.encodeForTopic(operator), BoolType.encodeForTopic(approved)))

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[ApprovalForAllEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(ApprovalForAllByTopicsEvent(_))

  def apply(log: response.Log): ApprovalForAllEvent = {
    assert(log.topics.head == id)

    val owner = event.indexed.type1.decode(log.topics(1), 0).value
    val operator = event.indexed.type2.decode(log.topics(2), 0).value
    val approved = event.indexed.type3.decode(log.topics(3), 0).value
    ApprovalForAllEvent(log, owner, operator, approved)
  }

}
