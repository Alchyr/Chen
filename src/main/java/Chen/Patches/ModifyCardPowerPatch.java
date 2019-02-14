package Chen.Patches;

import Chen.Interfaces.ModifyCardPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatch(
        clz = AbstractCard.class,
        method = "applyPowers"
)
public class ModifyCardPowerPatch {
    @SpirePostfixPatch
    public static void Postfix(AbstractCard __instance)
    {
        AbstractPlayer p = AbstractDungeon.player;

        for (AbstractPower power : p.powers)
        {
            if (power instanceof ModifyCardPower)
            {
                ((ModifyCardPower) power).ModifyCard(__instance);
            }
        }
    }
}
