package Chen.Cards.Skills;

import Chen.Abstracts.BaseCard;
import Chen.Interfaces.BlockSpellCard;
import Chen.Interfaces.NotMagicSpellCard;
import Chen.Interfaces.SpellCard;
import Chen.Util.CardInfo;
import Chen.Variables.SpellDamage;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Chen.ChenMod.makeID;

public class Fadeout extends BaseCard implements BlockSpellCard, NotMagicSpellCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Fadeout",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK = 7;
    private final static int UPG_BLOCK = 10;

    private final static int DEBUFF = 2;

    public Fadeout()
    {
        super(cardInfo, false);

        setMagic(DEBUFF);

        setExhaust(true);
    }

    @Override
    public boolean upgradedSpellValue() {
        return upgraded;
    }

    @Override
    public int getBaseValue() {
        if (upgraded)
        {
            return UPG_BLOCK;
        }
        else
        {
            return BLOCK;
        }
    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        return new Fadeout();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, SpellDamage.getSpellDamage(this)));

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false), this.magicNumber));
        }
    }
}