package com.rarible.protocol.contracts

import com.rarible.core.test.data.randomAddress
import com.rarible.core.test.data.randomBoolean
import com.rarible.core.test.data.randomWord
import io.daonomic.rpc.domain.Binary
import io.daonomic.rpc.domain.Word
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import scalether.domain.Address
import scalether.domain.response.Log
import scalether.java.Lists
import java.math.BigInteger

class ApprovalForAllEventWithFullDataTest {
    @Test
    fun `convert - full data`() {
        val log = Log(
            BigInteger.ONE,
            BigInteger.ONE,
            Word.apply(randomWord()),
            Word.apply(randomWord()),
            BigInteger.ONE,
            randomAddress(),
            Binary.apply("0x00000000000000000000000099ae538c1108cf7a235d7c263cb2cdff99efa60800000000000000000000000078fbb4c52102e85698f566102113b36529a46cf30000000000000000000000000000000000000000000000000000000000000001"),
            randomBoolean(),
            Lists.toScala(listOf(Word.apply("0x17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31"))),
            "test"
        )
        val event = ApprovalForAllEventWithFullData.apply(log)
        assertThat(event.owner()).isEqualTo(Address.apply("0x99ae538c1108cf7a235d7c263cb2cdff99efa608"))
        assertThat(event.operator()).isEqualTo(Address.apply("0x78fbb4c52102e85698f566102113b36529a46cf3"))
        assertThat(event.approved()).isTrue
    }
}