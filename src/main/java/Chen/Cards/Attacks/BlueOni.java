package Chen.Cards.Attacks;

import Chen.Abstracts.DamageSpellCard;
import Chen.Interfaces.SpellCard;
import Chen.Util.CardInfo;
import Chen.Variables.SpellDamage;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbActivateParticle;

import java.util.ArrayList;

import static Chen.ChenMod.makeID;

public class BlueOni extends DamageSpellCard {
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

        setMagic(DAMAGE,  UPG_DAMAGE);
        setExhaust(true);

        this.color = CardColor.COLORLESS;
    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        return new BlueOni();
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
        ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;

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
            super.calculateCardDamage(null);
        }
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
            AbstractDungeon.actionManager.addToBottom(new DamageAction(farthestRight, new DamageInfo(p, SpellDamage.getSpellDamage(this), this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }
    }
}
