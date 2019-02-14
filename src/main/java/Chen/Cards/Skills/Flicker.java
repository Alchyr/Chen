package Chen.Cards.Skills;

import Chen.Abstracts.BaseCard;
import Chen.Abstracts.TwoFormCharacter;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Character.Chen;
import Chen.Interfaces.NotMagicSpellCard;
import Chen.Interfaces.SpellCard;
import Chen.Powers.Disoriented;
import Chen.Util.CardInfo;
import Chen.Variables.SpellDamage;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class Flicker extends BaseCard implements NotMagicSpellCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Flicker",
            1,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DEBUFF = 1;

    private final static int DRAW = 2;
    private final static int UPG_DRAW = 1;


    public Flicker()
    {
        super(cardInfo, false);

        setMagic(DRAW, UPG_DRAW);
    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        return new Flicker();
    }

    @Override
    public int getBaseValue() {
        return DEBUFF;
    }
    @Override
    public boolean upgradedSpellValue() {
        return false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof TwoFormCharacter && !((TwoFormCharacter) p).Form) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenHuman));
        }

        if (SpellDamage.getSpellDamage(this, DEBUFF) > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Disoriented(m, SpellDamage.getSpellDamage(this, DEBUFF)), SpellDamage.getSpellDamage(this, DEBUFF)));
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
    }
}