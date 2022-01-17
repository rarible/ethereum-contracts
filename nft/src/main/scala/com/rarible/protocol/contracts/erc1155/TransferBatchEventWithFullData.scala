package com.rarible.protocol.contracts.erc1155

import com.rarible.contracts.erc1155.{TransferBatchEvent, TransferSingleEvent}
import io.daonomic.rpc.domain.Word
import scalether.abi.array.VarArrayType
import scalether.abi.tuple.{Tuple2Type, Tuple3Type, Tuple5Type, UnitType}
import scalether.abi.{AddressType, Event, Uint256Type}
import scalether.domain.request.{LogFilter, TopicFilter}
import scalether.domain.{Address, response}

import java.math.BigInteger

object TransferBatchEventWithFullData {
  import TopicFilter.simple

  val event: Event[UnitType.type, (Address, Address, Address, Array[BigInteger], Array[BigInteger])] =
    Event("TransferBatch", List(AddressType, AddressType, AddressType, VarArrayType(Uint256Type), VarArrayType(Uint256Type)), UnitType, Tuple5Type(AddressType, AddressType, AddressType, VarArrayType(Uint256Type), VarArrayType(Uint256Type)))
  val id: Word = Word.apply("0x4a39dc06d4c0dbc64b70af90fd698a233a518aa5d07e595d983b8c0526c8f7fb")

  def filter(): LogFilter =
    LogFilter(topics = List(simple(id)))

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[TransferBatchEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(TransferBatchEvent(_))

  def apply(log: response.Log): TransferBatchEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val _operator = decodedData._1
    val _from = decodedData._2
    val _to = decodedData._3
    val _ids = decodedData._4
    val _values = decodedData._5
    TransferBatchEvent(log, _operator, _from, _to, _ids, _values)
  }
}
