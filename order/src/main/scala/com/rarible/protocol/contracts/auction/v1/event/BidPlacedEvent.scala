package com.rarible.protocol.contracts.auction.v1.event

import io.daonomic.rpc.domain._
import scalether.abi._
import scalether.abi.tuple._
import scalether.domain._
import scalether.domain.request._

import java.math.BigInteger

case class BidPlacedEvent(log: response.Log, auctionId: BigInteger, buyer: Address, bid: (BigInteger, Array[Byte], Array[Byte]), endTime: BigInteger)

object BidPlacedEvent {
  import TopicFilter.simple

  val event = Event("BidPlaced", List(Uint256Type, AddressType, Tuple3Type(Uint256Type, Bytes4Type, BytesType), Uint256Type), Tuple1Type(Uint256Type), Tuple3Type(AddressType, Tuple3Type(Uint256Type, Bytes4Type, BytesType), Uint256Type))
  val id: Word = Word.apply("0x31477bb3bc1a29ff097ecf65625a5301d75d53b2a6548bc5df0c98d9922de814")

  def filter(auctionId: BigInteger): LogFilter =
    LogFilter(topics = List(simple(id), Uint256Type.encodeForTopic(auctionId)))

  def apply(receipt: scalether.domain.response.TransactionReceipt): List[BidPlacedEvent] =
    receipt.logs
      .filter(_.topics.head == id)
      .map(BidPlacedEvent(_))

  def apply(log: response.Log): BidPlacedEvent = {
    assert(log.topics.head == id)

    val decodedData = event.decode(log.data)
    val auctionId = event.indexed.type1.decode(log.topics(1), 0).value
    val buyer = decodedData._1
    val bid = decodedData._2
    val endTime = decodedData._3
    BidPlacedEvent(log, auctionId, buyer, bid, endTime)
  }
}















