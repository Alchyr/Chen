package Chen.Patches;

import Chen.Powers.DividedAttackPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import javassist.CtBehavior;

@SpirePatch(
        clz = DamageInfo.class,
        method = "applyPowers"
)
public class AfterimagesPatch {
    @SpireInsertPatch(
            locator= Locator.class,
            localvars={"tmp"}
    )
    public static SpireReturn Insert(DamageInfo __instance, AbstractCreature owner, AbstractCreature target, @ByRef float[] tmp)
    {
        if (tmp[0] == 0)
        {
            return SpireReturn.Continue();
        }
        if (owner.hasPower(DividedAttackPower.POWER_ID))
        {
            int divisionAmount = owner.getPower(DividedAttackPower.POWER_ID).amount;

            for (int i = 0; i < divisionAmount; i++)
            {
                tmp[0] = Math.max(1, tmp[0] / 2);
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
