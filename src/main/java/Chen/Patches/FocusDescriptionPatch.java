package Chen.Patches;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiwordKeywords;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;

import static Chen.ChenMod.makeID;

@SpirePatch(
        clz = FocusPower.class,
        method = "updateDescription"
)
public class FocusDescriptionPatch {
    public static final String POWER_ID = makeID("AltFocusPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    @SpirePostfixPatch
    public static void AlternateDescription(FocusPower __instance)
    {
        if (AbstractDungeon.player.getCardColor() == CardColorEnum.CHEN_COLOR)
        {
            if (__instance.amount > 0) {
                __instance.description = powerStrings.DESCRIPTIONS[0] + __instance.amount + powerStrings.DESCRIPTIONS[2];
                __instance.type = AbstractPower.PowerType.BUFF;
            } else {
                int tmp = -__instance.amount;
                __instance.description = powerStrings.DESCRIPTIONS[1] + tmp + powerStrings.DESCRIPTIONS[2];
                __instance.type = AbstractPower.PowerType.DEBUFF;
            }
        }
    }
}
