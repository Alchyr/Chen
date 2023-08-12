package Chen.Cards.Skills;

import Chen.Abstracts.StandardSpell;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Character.Chen;
import Chen.Interfaces.SpellCard;
import Chen.Patches.TwoFormFields;
import Chen.Powers.Disoriented;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class Flicker extends StandardSpell {
    private final static CardInfo cardInfo = new CardInfo(
            "Flicker",
            1,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DEBUFF = 1;
    private final static int UPG_DEBUFF = 1;

    private final static int DRAW = 2;


    public Flicker()
    {
        super(cardInfo, false);

        setMagic(DEBUFF, UPG_DEBUFF);
        magicNumSpell();
    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        return new Flicker();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!TwoFormFields.getForm()) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenHuman));
        }

        if (this.magicNumber > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Disoriented(m, this.magicNumber), this.magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 2));
    }
}