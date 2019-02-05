package Chen.Patches;

import Chen.Interfaces.BetterOnGainBlockSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

@SpirePatch(
        clz = AbstractCreature.class,
        method = "addBlock"
)
public class BetterOnGainBlockPatch {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"tmp"}
    )
    public static SpireReturn InsertPatch(AbstractCreature __instance, int blockAmount, @ByRef float[] tmp)
    {
        for (AbstractPower p : __instance.powers)
        {
            if (p instanceof BetterOnGainBlockSubscriber)
            {
                if (!((BetterOnGainBlockSubscriber) p).onBetterGainBlock(tmp))
                {
                    return SpireReturn.Return(null);
                }
            }
        }
        return SpireReturn.Continue();
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "isPlayer");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
