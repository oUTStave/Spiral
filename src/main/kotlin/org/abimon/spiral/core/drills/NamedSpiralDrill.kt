package org.abimon.spiral.core.drills

import org.abimon.spiral.core.SpiralConfig
import org.abimon.spiral.core.data.SpiralData
import org.abimon.spiral.core.lin.LinScript
import org.abimon.spiral.util.*
import org.parboiled.Action
import org.parboiled.BaseParser
import org.parboiled.Rule

//TODO: Support DR2 op codes too
object NamedSpiralDrill : DrillHead {
    val cmd = "NAMED"

    override fun Syntax(parser: BaseParser<Any>): Rule = parser.makeCommand {
        Sequence(
                clearTmpStack(cmd),
                OneOrMore(LineCodeMatcher),
                Action<Any> { SpiralData.dr1OpCodes.values.any { (_, name) -> name.equals(match(), true) } },
                pushTmpAction(this, cmd, this@NamedSpiralDrill),
                pushTmpAction(this, cmd),
                Optional(
                        '|'
                ),
                Optional(
                        ParamList(
                                cmd,
                                Sequence(
                                        OneOrMore(Digit()),
                                        pushToStack(this)
                                ),
                                Sequence(
                                        ',',
                                        Optional(Whitespace())
                                )
                        )
                ),
                pushTmpStack(this, cmd)
        )
    }


    override fun formScripts(rawParams: Array<Any>, config: SpiralConfig): Array<LinScript> {
        rawParams[0] = SpiralData.dr1OpCodes.entries.first { (_, pair) -> pair.second.equals("${rawParams[0]}", true) }.key.toString(16)
        return BasicSpiralDrill.formScripts(rawParams)
    }
}