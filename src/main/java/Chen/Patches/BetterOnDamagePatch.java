package Chen.Patches;


import Chen.Interfaces.BetterOnDamageGiveSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.HeavyBlade;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

import java.util.ArrayList;

//TODO : Fix targeting all attacks. They do not work.

public class BetterOnDamagePatch {
    @SpirePatch(
            clz= AbstractCard.class,
            method="applyPowers"
    )
    public static class ApplyPowersSingle
    {
        @SpireInsertPatch(
                locator=Locator.class,
                localvars={"damageTypeForTurn", "tmp"}
        )
        public static void Insert(AbstractCard __instance, DamageInfo.DamageType damageTypeForTurn, @ByRef float[] tmp)
        {
            for (AbstractPower p : AbstractDungeon.player.powers)
            {
                if (p instanceof BetterOnDamageGiveSubscriber)
                {
                    tmp[0] = ((BetterOnDamageGiveSubscriber) p).betterAtDamageGive(tmp[0], damageTypeForTurn, null);
                    if (__instance.baseDamage != (int)tmp[0])
                    {
                        __instance.isDamageModified = true;
                    }
                }
            }
        }
        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz=AbstractCard.class,
            method="applyPowers"
    )
    public static class ApplyPowersMulti
    {
        @SpireInsertPatch(
                locator=Locator.class,
                localvars={"tmp", "i", "damageTypeForTurn"}
        )
        public static void Insert(AbstractCard __instance, float[] tmp, int i, DamageInfo.DamageType damageTypeForTurn)
        {
            for (AbstractPower p : AbstractDungeon.player.powers)
            {
                if (p instanceof BetterOnDamageGiveSubscriber)
                {
                    tmp[i] = ((BetterOnDamageGiveSubscriber) p).betterAtDamageGive(tmp[i], damageTypeForTurn, null);
                    if (__instance.baseDamage != (int)tmp[i])
                    {
                        __instance.isDamageModified = true;
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
                return new int[]{ LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[1] };
            }
        }
    }

    @SpirePatch(
            clz=AbstractCard.class,
            method="calculateCardDamage"
    )
    public static class CalculateCardSingle
    {
        @SpireInsertPatch(
                locator=Locator.class,
                localvars={"damageTypeForTurn", "tmp"}
        )
        public static void Insert(AbstractCard __instance, AbstractMonster mo, DamageInfo.DamageType damageTypeForTurn, @ByRef float[] tmp)
        {
            for (AbstractPower p : AbstractDungeon.player.powers)
            {
                if (p instanceof BetterOnDamageGiveSubscriber)
                {
                    tmp[0] = ((BetterOnDamageGiveSubscriber) p).betterAtDamageGive(tmp[0], damageTypeForTurn, mo);
                    if (__instance.baseDamage != (int)tmp[0])
                    {
                        __instance.isDamageModified = true;
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
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
                locator = Locator.class,
                localvars={"m", "tmp", "i", "damageTypeForTurn"}
        )
        public static void Insert(AbstractCard __instance, AbstractMonster mo, ArrayList<AbstractMonster> m, float[] tmp, int i, DamageInfo.DamageType damageTypeForTurn)
        {
            for (AbstractPower p : AbstractDungeon.player.powers)
            {
                if (p instanceof BetterOnDamageGiveSubscriber)
                {
                    tmp[i] = ((BetterOnDamageGiveSubscriber) p).betterAtDamageGive(tmp[i], damageTypeForTurn, m.get(i));
                    if (__instance.baseDamage != (int)tmp[i])
                    {
                        __instance.isDamageModified = true;
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
                return new int[]{ LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[1] };
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
                localvars={"type", "tmp"}
        )
        public static void Insert(DamageInfo __instance, AbstractCreature owner, AbstractCreature target, DamageInfo.DamageType type, @ByRef float[] tmp)
        {
            for (AbstractPower p : owner.powers)
            {
                if (p instanceof BetterOnDamageGiveSubscriber)
                {
                    tmp[0] = ((BetterOnDamageGiveSubscriber) p).betterAtDamageGive(tmp[0], type, target);
                }
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "isPlayer");
                return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}