package Chen.Cards.Attacks;

import Chen.Abstracts.DamageSpellCard;
import Chen.Abstracts.TwoFormCharacter;
import Chen.Actions.ChenActions.PhoenixWingsAction;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Actions.GenericActions.ApplyDamageAction;
import Chen.Actions.GenericActions.VFXIfAliveAction;
import Chen.Character.Chen;
import Chen.Interfaces.SpellCard;
import Chen.Util.CardInfo;
import Chen.Util.DamageInfoUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.Necronomicon;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import static Chen.ChenMod.makeID;

public class PhoenixWings extends DamageSpellCard {
    private final static CardInfo cardInfo = new CardInfo(
            "PhoenixWings",
            3,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 15;
    private final static int UPG_DAMAGE = 4;

    public PhoenixWings()
    {
        super(cardInfo, false);

        setMagic(DAMAGE,  UPG_DAMAGE);

        this.isMultiDamage = true;
    }

    @Override
    public void applyPowers() {
        CalculateDamageSpell(null);

        int index = 0;
        int totalDamage = 0;
        int baseHit = this.baseMagicNumber;
        int baseTotal = 0;

        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
        {
            if (!m.isDeadOrEscaped() && index < multiDamage.length)
            {
                totalDamage += multiDamage[index];
                baseTotal += baseHit;
            }
            index++;
        }

        this.baseDamage = baseTotal;
        this.damage = totalDamage;

        isDamageModified = false;

        if (this.damage != baseDamage)
            isDamageModified = true;

        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(null);

        int index = 0;
        int totalDamage = 0;
        int baseHit = this.baseMagicNumber;
        int baseTotal = 0;

        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
        {
            if (!m.isDeadOrEscaped() && index < multiDamage.length)
            {
                totalDamage += multiDamage[index];
                baseTotal += baseHit;
            }
            index++;
        }

        isDamageModified = false;
        this.baseDamage = baseTotal;
        this.damage = totalDamage;

        if (this.damage != baseDamage)
            isDamageModified = true;

        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        return new PhoenixWings();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof TwoFormCharacter && !((TwoFormCharacter) p).Form) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenHuman));
        }

        AbstractDungeon.actionManager.addToBottom(new PhoenixWingsAction(p, m, multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));

        AbstractDungeon.actionManager.addToBottom(new VFXIfAliveAction(m, new FireBurstParticleEffect(m.hb.cX, m.hb.cY)));
        AbstractDungeon.actionManager.addToBottom(new VFXIfAliveAction(m, new InflameEffect(m)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
    }
}