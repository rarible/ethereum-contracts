package com.rarible.protocol.contracts

import scalether.abi._
import scalether.abi.array.VarArrayType
import scalether.abi.tuple._
import scalether.domain.Address

import java.math.BigInteger

object Signatures {
  val setTokenURIPrefixSignature: Signature[String, Unit] =
    Signature("setTokenURIPrefix", Tuple1Type(StringType), UnitType) /*0x99e0dd7c*/

  val mintSignature: Signature[(Address, BigInteger, String), Unit] =
    Signature("mint", Tuple3Type(AddressType, Uint256Type, StringType), UnitType) /*0xd3fc9864*/

  val erc721V3mintSignature: Signature[(BigInteger, BigInteger, Array[Byte], Array[Byte], String), Unit] =
    Signature("mint", Tuple5Type(Uint256Type, Uint8Type, Bytes32Type, Bytes32Type, StringType), UnitType) /*0x7fbcc639*/

  val erc721V4mintSignature: Signature[(BigInteger, BigInteger, Array[Byte], Array[Byte], Array[(Address, BigInteger)], String), Unit] =
    Signature("mint", Tuple6Type(Uint256Type, Uint8Type, Bytes32Type, Bytes32Type, VarArrayType(Tuple2Type(AddressType, Uint256Type)), StringType), UnitType) /*0x6f0cfbfd*/

  val erc1155MintSignatureV1: Signature[(BigInteger, BigInteger, Array[Byte], Array[Byte], Array[(Address, BigInteger)], BigInteger, String), Unit] =
    Signature("mint", Tuple7Type(Uint256Type, Uint8Type, Bytes32Type, Bytes32Type, VarArrayType(Tuple2Type(AddressType, Uint256Type)), Uint256Type, StringType), UnitType) /*0x449f7fc6*/

}
