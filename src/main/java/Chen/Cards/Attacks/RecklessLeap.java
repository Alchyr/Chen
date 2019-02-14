package Chen.Cards.Attacks;

import Chen.Abstracts.BaseCard;
import Chen.Abstracts.TwoFormCharacter;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Character.Chen;
import Chen.Powers.AmountStackingDrawReduction;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawReductionPower;

import static Chen.ChenMod.makeID;

public class RecklessLeap extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RecklessLeap",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 9;
    private final static int UPG_DAMAGE = 1;

    private final static int DRAW = 2;
    private final static int UPG_DRAW = 1;
    private final static int DEBUFF = 1;

    public RecklessLeap()
    {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(DRAW, UPG_DRAW);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof TwoFormCharacter && ((TwoFormCharacter) p).Form) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenCat));
        }

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AmountStackingDrawReduction(p, DEBUFF), DEBUFF));
    }
}