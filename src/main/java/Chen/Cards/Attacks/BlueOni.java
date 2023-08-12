package Chen.Cards.Attacks;

import Chen.Abstracts.StandardSpell;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbActivateParticle;

import static Chen.ChenMod.makeID;

public class BlueOni extends StandardSpell {
    private final static CardInfo cardInfo = new CardInfo(
            "BlueOni",
            0,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.SPECIAL
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 10;
    private final static int UPG_DAMAGE = 3;

    public BlueOni()
    {
        super(cardInfo, false);

        setDamage(DAMAGE,  UPG_DAMAGE);
        setExhaust(true);

        this.color = CardColor.COLORLESS;
    }

    @Override
    public StandardSpell getCopyAsSpellCard() {
        return new BlueOni();
    }

    @Override
    public void applyPowers() {
        AbstractMonster farthestRight = null;
        for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if (!mon.isDeadOrEscaped())
            {
                if (farthestRight == null)
                    farthestRight = mon;

                if (mon.hb.cX > farthestRight.hb.cX)
                {
                    farthestRight = mon;
                }
            }
        }

        if (farthestRight != null)
        {
            super.calculateCardDamage(farthestRight);
        }
        else
        {
            super.applyPowers();
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        applyPowers();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractMonster farthestRight = null;

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if (!mo.isDeadOrEscaped())
            {
                if (farthestRight == null)
                    farthestRight = mo;

                if (mo.hb.cX > farthestRight.hb.cX)
                {
                    farthestRight = mo;
                }
            }
        }

        if (farthestRight != null) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new FrostOrbActivateParticle(0, farthestRight.hb.cX, farthestRight.hb.cY)));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_FROST_CHANNEL", 0.2f));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_FROST_EVOKE", 0.2f));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(farthestRight, new DamageInfo(p, damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }
    }
}
