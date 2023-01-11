package com.rarible.protocol.contracts.collection

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

class CreateEventWithFullDataTest {
    @Test
    fun `convert - full data`() {
        val log = Log(
            BigInteger.ONE,
            BigInteger.ONE,
            Word.apply(randomWord()),
            Word.apply(randomWord()),
            BigInteger.ONE,
            randomAddress(),
            Binary.apply("0x0000000000000000000000006fbebbb59c2e67127a753ea422ad61c55713bfe9000000000000000000000000000000000000000000000000000000000000006000000000000000000000000000000000000000000000000000000000000000a00000000000000000000000000000000000000000000000000000000000000002313000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000023130000000000000000000000000000000000000000000000000000000000000"),
            randomBoolean(),
            Lists.toScala(listOf(Word.apply("0x750d13f39f16526306cffdefb909852b055c2ea79ee21d21b36402eddaae7036"))),
            "test"
        )
        val event = CreateEventWithFullData.apply(log)
        assertThat(event.creator()).isEqualTo(Address.apply("0x6fBEBBB59C2e67127a753EA422AD61c55713bFe9"))
        assertThat(event.name()).isEqualTo("10")
        assertThat(event.symbol()).isEqualTo("10")
    }
}