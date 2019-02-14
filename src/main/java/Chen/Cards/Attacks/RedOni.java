package Chen.Cards.Attacks;

import Chen.Abstracts.BaseCard;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class RedOni extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RedOni",
            0,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.SPECIAL
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 10;
    private final static int UPG_DAMAGE = 2;

    public RedOni()
    {
        super(cardInfo, false);

        setDamage(DAMAGE,  UPG_DAMAGE);
        setExhaust(true);

        this.color = CardColor.COLORLESS;
    }

    @Override
    public void applyPowers() {
        calculateCardDamage(null);
    }

    @Override
    public void calculateDamageDisplay(AbstractMonster mo) {
        calculateCardDamage(mo);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        AbstractMonster farthestLeft = null;

        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped()) {
                if (farthestLeft == null)
                    farthestLeft = m;

                if (m.hb.cX < farthestLeft.hb.cX && m.hb.cX >= 0) {
                    farthestLeft = m;
                }
            }
        }
        super.calculateCardDamage(farthestLeft);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractMonster farthestLeft = null;

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                if (farthestLeft == null)
                    farthestLeft = mo;

                if (mo.hb.cX < farthestLeft.hb.cX && mo.hb.cX >= 0) {
                    farthestLeft = mo;
                }
            }
        }

        if (farthestLeft != null)
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(farthestLeft, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        }
    }
}