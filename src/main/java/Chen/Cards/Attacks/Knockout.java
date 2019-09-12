package Chen.Cards.Attacks;

import Chen.Abstracts.BaseCard;
import Chen.Powers.Disoriented;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import static Chen.ChenMod.makeID;

public class Knockout extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Knockout",
            2,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 12;
    private final static int UPG_DAMAGE = 6;

    private boolean critical;

    public Knockout()
    {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);

        critical = false;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        critical = false;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);

        critical = false;

        if (mo != null && mo.hasPower(Disoriented.POWER_ID))
        {
            this.damage *= 2;
            this.isDamageModified = true;
            critical = true;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (critical)
        {
            if (m != null) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
            }

            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.8F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }
        else
        {
            if (m != null) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4.0F, m.hb.cY - m.hb.height / 4.0F)));
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }
}