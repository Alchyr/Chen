package Chen.Patches;


import Chen.Interfaces.BetterOnDamageGiveSubscriber;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import javassist.CtBehavior;

import java.util.ArrayList;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;

public class OnDamagePatches {
    private static float betterOnDamage(AbstractCard card, AbstractMonster m, float damage)
    {
        for (AbstractPower p : AbstractDungeon.player.powers)
        {
            if (p instanceof BetterOnDamageGiveSubscriber)
            {
                damage = ((BetterOnDamageGiveSubscriber) p).betterAtDamageGive(damage, card.damageTypeForTurn, m);
                if (card.baseDamage != damage)
                {
                    card.isDamageModified = true;
                }
            }
        }

        return damage;
    }

    private static float betterOnDamage(DamageInfo info, AbstractCreature owner, AbstractCreature m, float damage)
    {
        for (AbstractPower p : owner.powers)
        {
            if (p instanceof BetterOnDamageGiveSubscriber)
            {
                damage = ((BetterOnDamageGiveSubscriber) p).betterAtDamageGive(damage, info.type, m);
                if (damage != info.base)
                    info.isModified = true;
            }
        }

        return damage;
    }

    @SpirePatch(
            clz=AbstractCard.class,
            method="calculateCardDamage"
    )
    public static class CalculateCardSingle
    {
        @SpireInsertPatch(
                locator=Locator.class,
                localvars={"tmp"}
        )
        public static void Insert(AbstractCard __instance, AbstractMonster mo, @ByRef float[] tmp)
        {
            tmp[0] = betterOnDamage(__instance, mo, tmp[0]);
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractStance.class, "atDamageGive");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz=AbstractCard.class,
            method="calculateCardDamage"
    )
    public static class CalculateCardMulti
    {
        @SpireInsertPatch(
                locator=Locator.class,
                localvars={"tmp", "m", "i"}
        )
        public static void Insert(AbstractCard __instance, AbstractMonster mo, float[] tmp, ArrayList<AbstractMonster> m, int i)
        {
            tmp[i] = betterOnDamage(__instance, m.get(i), tmp[i]);
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractStance.class, "atDamageGive");
                ArrayList<Matcher> matchers = new ArrayList<>();
                matchers.add(finalMatcher);
                return LineFinder.findInOrder(ctMethodToPatch, matchers, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz=DamageInfo.class,
            method="applyPowers"
    )
    public static class DamageInfoApplyPowers
    {
        @SpireInsertPatch(
                locator=Locator.class,
                localvars={"tmp"}
        )
        public static void Insert(DamageInfo __instance, AbstractCreature owner, AbstractCreature target, @ByRef float[] tmp)
        {
            tmp[0] = betterOnDamage(__instance, owner, target, tmp[0]);
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
                return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
