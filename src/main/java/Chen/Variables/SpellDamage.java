package Chen.Variables;

import Chen.Abstracts.DamageSpellCard;
import Chen.Interfaces.BlockSpellCard;
import Chen.Interfaces.NotMagicSpellCard;
import Chen.Powers.PreventAllBlockPower;
import basemod.abstracts.DynamicVariable;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.NoBlockPower;


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
            if (card instanceof NotMagicSpellCard)
            {
                return baseValue(card) != getSpellDamage(card);
            }
            return (baseValue(card) != getSpellDamage(card)) || card.isMagicNumberModified;
        }
        return card.isMagicNumberModified;
    }

    @Override
    public int value(AbstractCard card)
    {
        return getSpellDamage(card);
    }

    public static int getSpellDamage(AbstractCard card)
    {
        if (card instanceof DamageSpellCard)
        {
            return card.magicNumber;
        }
        return getSpellDamage(card, staticBaseValue(card));
    }

    public static int getSpellDamage(AbstractCard card, int base)
    {
        int modifier = 0;
        if (AbstractDungeon.player != null)
        {
            if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
                modifier = AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;

            if (card instanceof BlockSpellCard)
            {
                if (AbstractDungeon.player.hasPower(PreventAllBlockPower.POWER_ID) || AbstractDungeon.player.hasPower(NoBlockPower.POWER_ID))
                    return 0;

                float tmp = base + modifier;

                for (AbstractPower p : AbstractDungeon.player.powers)
                {
                    if (!p.ID.equals(DexterityPower.POWER_ID))
                        tmp = p.modifyBlock(tmp);
                }

                if (tmp < 0)
                    return 0;

                return MathUtils.floor(tmp);
            }
        }

        if (base+modifier < 0)
            return 0;

        return base + modifier;
    }

    @Override
    public int baseValue(AbstractCard card)
    {
        if (card instanceof NotMagicSpellCard)
        {
            return ((NotMagicSpellCard) card).getBaseValue();
        }
        return card.baseMagicNumber;
    }
    public static int staticBaseValue(AbstractCard card)
    {
        if (card instanceof NotMagicSpellCard)
        {
            return ((NotMagicSpellCard) card).getBaseValue();
        }
        return card.baseMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card)
    {
        if (card instanceof NotMagicSpellCard)
            return ((NotMagicSpellCard) card).upgradedSpellValue();

        return card.upgradedMagicNumber;
    }
}