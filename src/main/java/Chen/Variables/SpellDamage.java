package Chen.Variables;

import Chen.Interfaces.BlockSpellCard;
import Chen.Interfaces.DamageSpellCard;
import Chen.Powers.PreventAllBlockPower;
import basemod.abstracts.DynamicVariable;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.HeavyBlade;
import com.megacrit.cardcrawl.cards.red.PerfectedStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.NoBlockPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.*;

public class SpellDamage extends DynamicVariable {
    @Override
    public String key()
    {
        return "SPELL"; //!SPELL!
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        if (AbstractDungeon.player != null)
        {
            return baseValue(card) != getSpellDamage(card);
        }
        return card.isMagicNumberModified;
    }

    @Override
    public int value(AbstractCard card)
    {
        return getSpellDamage(card, card instanceof BlockSpellCard);
    }

    public static int getSpellDamage(AbstractCard card)
    {
        return getSpellDamage(card, card instanceof BlockSpellCard);
    }
    public static int getSpellDamage(AbstractCard card, boolean isBlock)
    {
        int base = card.magicNumber;
        int modifier = 0;
        if (AbstractDungeon.player != null)
        {
            if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
                modifier = AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;

            if (isBlock && AbstractDungeon.player.hasPower(PreventAllBlockPower.POWER_ID) || AbstractDungeon.player.hasPower(NoBlockPower.POWER_ID))
                return 0;
        }

        if (base+modifier < 0)
            return 0;

        return base + modifier;
    }


    public static int getSpellDamage(AbstractCard card, int base)
    {
        int modifier = 0;
        if (AbstractDungeon.player != null)
        {
            if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
                modifier = AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;

            if (card instanceof BlockSpellCard && AbstractDungeon.player.hasPower(PreventAllBlockPower.POWER_ID) || AbstractDungeon.player.hasPower(NoBlockPower.POWER_ID))
                return 0;
        }

        if (base+modifier < 0)
            return 0;

        return base + modifier;
    }

    @Override
    public int baseValue(AbstractCard card)
    {
        return card.magicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card)
    {
        return card.upgradedMagicNumber;
    }
}